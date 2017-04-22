package polyorbis;

import flounder.camera.*;
import flounder.devices.*;
import flounder.entities.*;
import flounder.fbos.*;
import flounder.fonts.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.maths.vectors.*;
import flounder.particles.*;
import flounder.physics.bounding.*;
import flounder.post.filters.*;
import flounder.post.piplines.*;
import flounder.renderer.*;
import flounder.shadows.*;
import flounder.skybox.*;
import polyorbis.post.*;
import polyorbis.world.*;

public class PolyRenderer extends RendererMaster {
	private static final Vector4f POSITIVE_INFINITY = new Vector4f(0.0f, 1.0f, 0.0f, Float.POSITIVE_INFINITY);

	private ShadowRenderer shadowRenderer;
	private SkyboxRenderer skyboxRenderer;
	private EntitiesRenderer entitiesRenderer;
	private ParticleRenderer particleRenderer;
	private BoundingRenderer boundingRenderer;
	private GuisRenderer guisRenderer;
	private FontRenderer fontRenderer;

	private FBO rendererFBO;

	private PipelineMRT pipelineMRT;
	private PipelineBloom pipelineBloom;
	private FilterBlurMotion filterBlurMotion;
	private FilterTiltShift filterTiltShift;
	private FilterLensFlare filterLensFlare;
	private PipelinePaused pipelinePaused;
	private FilterGain filterGain;

	public PolyRenderer() {
		super(FlounderDisplay.class);
	}

	@Override
	public void init() {
		this.shadowRenderer = new ShadowRenderer();
		this.skyboxRenderer = new SkyboxRenderer();
		this.entitiesRenderer = new EntitiesRenderer();
		this.particleRenderer = new ParticleRenderer();
		this.boundingRenderer = new BoundingRenderer();
		this.guisRenderer = new GuisRenderer();
		this.fontRenderer = new FontRenderer();

		this.rendererFBO = FBO.newFBO(1.0f).attachments(3).withAlphaChannel(true).depthBuffer(DepthBufferType.TEXTURE).create();

		this.pipelineMRT = new PipelineMRT();
		this.pipelineBloom = new PipelineBloom();
		this.filterBlurMotion = new FilterBlurMotion();
		this.filterTiltShift = new FilterTiltShift(0.5f, 1.1f, 0.002f, 5.0f);
		this.filterLensFlare = new FilterLensFlare();
		this.pipelinePaused = new PipelinePaused();
		this.filterGain = new FilterGain(2.3f);
	}

	@Override
	public void render() {
		// Shadow rendering.
		renderShadows();

		// Binds the render FBO.
		rendererFBO.bindFrameBuffer();

		// Scene rendering.
		renderScene(POSITIVE_INFINITY);

		// Unbinds the render FBO.
		rendererFBO.unbindFrameBuffer();

		// Post rendering.
		renderPost(FlounderGuis.getGuiMaster().isGamePaused(), FlounderGuis.getGuiMaster().getBlurFactor());
	}

	private void renderShadows() {
		// Sets the player model to render.
		entitiesRenderer.setRenderPlayer(true);

		// Renders the shadows.
		shadowRenderer.render(POSITIVE_INFINITY, FlounderCamera.getCamera());
	}

	private void renderScene(Vector4f clipPlane) {
		// Sets the player model to render in first person view.
		entitiesRenderer.setRenderPlayer(true);

		// Clears and renders.
		Camera camera = FlounderCamera.getCamera();
		OpenGlUtils.prepareNewRenderParse(0.0f, 0.0f, 0.0f);

		skyboxRenderer.render(clipPlane, camera);
		entitiesRenderer.render(clipPlane, camera);

		boundingRenderer.render(clipPlane, camera);

		particleRenderer.render(clipPlane, camera);
	}

	private void renderPost(boolean isPaused, float blurFactor) {
		pipelineMRT.setShadowFactor(1.0f);
		pipelineMRT.renderPipeline(
				rendererFBO.getColourTexture(0), // Colours
				rendererFBO.getColourTexture(1), // Normals
				rendererFBO.getColourTexture(2), // Extras
				rendererFBO.getDepthTexture(), // Depth
				shadowRenderer.getShadowMap() // Shadow Map
		);
		FBO output = pipelineMRT.getOutput();

		// Render post effects if enabled.
		if (PolyPost.isEffectsEnabled()) {
			// Render Bloom Filter.
			if (PolyPost.isBloomEnabled()) {
				pipelineBloom.renderPipeline(output.getColourTexture(0));
				output = pipelineBloom.getOutput();
			}

			// Render Motion Blur Filter.
			if (PolyPost.isMotionBlurEnabled()) {
				filterBlurMotion.applyFilter(output.getColourTexture(0), rendererFBO.getDepthTexture());
				output = filterBlurMotion.fbo;
			}

			// Render Tilt Shift Filter.
			if (PolyPost.isTiltShiftEnabled()) {
				filterTiltShift.applyFilter(output.getColourTexture(0));
				output = filterTiltShift.fbo;
			}

			// Render Lens Flare Filter.
			if (PolyPost.isLensFlareEnabled() && PolyWorld.getEntitySun() != null) {
				filterLensFlare.setSunPosition(PolyWorld.getEntitySun().getPosition());
				filterLensFlare.setWorldHeight(128.0f); // High enough to always show lens flare.
				filterLensFlare.applyFilter(output.getColourTexture(0));
				output = filterLensFlare.fbo;
			}

			// Render Pause Pipeline.
			if (isPaused || blurFactor != 0.0f) {
				pipelinePaused.setBlurFactor(blurFactor);
				pipelinePaused.renderPipeline(output.getColourTexture(0));
				output = pipelinePaused.getOutput();
			}
		}

		// Scene independents.
		renderIndependents(output);

		// Applies grain to the final image.
		if (PolyPost.isGrainEnabled()) {
			filterGain.applyFilter(output.getColourTexture(0));
			output = filterGain.fbo;
		}

		// Displays the image to the screen.
		output.blitToScreen();
	}

	private void renderIndependents(FBO output) {
		output.bindFrameBuffer();
		guisRenderer.render(null, null);
		fontRenderer.render(null, null);
		output.unbindFrameBuffer();
	}

	@Override
	public void profile() {
	}

	public ShadowRenderer getShadowRenderer() {
		return shadowRenderer;
	}

	@Override
	public void dispose() {
		shadowRenderer.dispose();
		skyboxRenderer.dispose();
		entitiesRenderer.dispose();
		particleRenderer.dispose();
		boundingRenderer.dispose();
		guisRenderer.dispose();
		fontRenderer.dispose();

		rendererFBO.delete();

		pipelineMRT.dispose();
		pipelineBloom.dispose();
		filterBlurMotion.dispose();
		filterTiltShift.dispose();
		filterLensFlare.dispose();
		pipelinePaused.dispose();
		filterGain.dispose();
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
