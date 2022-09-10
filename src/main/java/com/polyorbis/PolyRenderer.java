package com.polyorbis;

import com.polyorbis.post.*;
import com.polyorbis.world.*;

import com.flounder.camera.*;
import com.flounder.devices.*;
import com.flounder.entities.*;
import com.flounder.fbos.*;
import com.flounder.fonts.*;
import com.flounder.guis.*;
import com.flounder.maths.vectors.*;
import com.flounder.particles.*;
import com.flounder.physics.bounding.*;
import com.flounder.post.filters.*;
import com.flounder.post.piplines.*;
import com.flounder.renderer.*;
import com.flounder.shadows.*;
import com.flounder.skybox.*;

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
		this.filterTiltShift = new FilterTiltShift(0.5f, 1.1f, 0.003f, 2.0f);
		this.filterLensFlare = new FilterLensFlare();
		this.pipelinePaused = new PipelinePaused();
		this.filterGain = new FilterGain(2.7f);
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
		if (FlounderGuis.get().getGuiMaster() != null) {
			renderPost(FlounderGuis.get().getGuiMaster().isGamePaused(), FlounderGuis.get().getGuiMaster().getBlurFactor());
		}
	}

	private void renderShadows() {
		// Renders the shadows.
		shadowRenderer.render(POSITIVE_INFINITY, FlounderCamera.get().getCamera());
	}

	private void renderScene(Vector4f clipPlane) {
		// Clears and renders.
		Camera camera = FlounderCamera.get().getCamera();
		FlounderOpenGL.get().prepareNewRenderParse(0.0f, 0.0f, 0.0f);

		particleRenderer.render(clipPlane, camera);

		skyboxRenderer.render(clipPlane, camera);
		entitiesRenderer.render(clipPlane, camera);

		boundingRenderer.render(clipPlane, camera);
	}

	private void renderPost(boolean isPaused, float blurFactor) {
		pipelineMRT.renderPipeline(
				rendererFBO.getColourTexture(0), // Colours
				rendererFBO.getColourTexture(1), // Normals
				rendererFBO.getColourTexture(2), // Extras
				rendererFBO.getDepthTexture(), // Depth
				shadowRenderer.getShadowMap() // Shadow Map
		);
		FBO output = pipelineMRT.getOutput();

		// Render post effects if enabled.
		if (PolyPost.get().isEffectsEnabled()) {
			// Render Bloom Filter.
			if (PolyPost.get().isBloomEnabled()) {
				pipelineBloom.renderPipeline(output.getColourTexture(0));
				output = pipelineBloom.getOutput();
			}

			// Render Motion Blur Filter.
			if (PolyPost.get().isMotionBlurEnabled()) {
				filterBlurMotion.applyFilter(output.getColourTexture(0), rendererFBO.getDepthTexture());
				output = filterBlurMotion.fbo;
			}

			// Render Tilt Shift Filter.
			if (PolyPost.get().isTiltShiftEnabled()) {
				filterTiltShift.applyFilter(output.getColourTexture(0));
				output = filterTiltShift.fbo;
			}

			// Render Lens Flare Filter.
			if (PolyPost.get().isLensFlareEnabled() && PolyWorld.get().getEntitySun() != null) {
				filterLensFlare.setSunPosition(PolyWorld.get().getEntitySun().getPosition());
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
		if (PolyPost.get().isGrainEnabled()) {
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
