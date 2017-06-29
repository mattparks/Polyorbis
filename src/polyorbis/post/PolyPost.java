package polyorbis.post;

import flounder.framework.*;
import polyorbis.*;

public class PolyPost extends Module {
	private boolean effectsEnabled;

	private boolean bloomEnabled;
	private boolean motionBlurEnabled;
	private boolean tiltShiftEnabled;
	private boolean lensFlareEnabled;
	private boolean grainEnabled;

	public PolyPost() {
		super();
	}

	@Handler.Function(Handler.FLAG_INIT)
	public void init() {
		this.effectsEnabled = PolyConfigs.POST_EFFECTS_ENABLED.getBoolean();

		this.bloomEnabled = PolyConfigs.POST_BLOOM_ENABLED.getBoolean();
		this.motionBlurEnabled = PolyConfigs.POST_MOTIONBLUR_ENABLED.getBoolean();
		this.tiltShiftEnabled = PolyConfigs.POST_TILTSHIFT_ENABLED.getBoolean();
		this.lensFlareEnabled = PolyConfigs.POST_LENSFLARE_ENABLED.getBoolean();
		this.grainEnabled = PolyConfigs.POST_GRAIN_ENABLED.getBoolean();
	}

	@Handler.Function(Handler.FLAG_UPDATE_PRE)
	public void update() {
	}

	public boolean isEffectsEnabled() {
		return this.effectsEnabled;
	}

	public void setEffectsEnabled(boolean effectsEnabled) {
		this.effectsEnabled = effectsEnabled;
	}

	public boolean isBloomEnabled() {
		return this.bloomEnabled;
	}

	public void setBloomEnabled(boolean bloomEnabled) {
		this.bloomEnabled = bloomEnabled;
	}

	public boolean isMotionBlurEnabled() {
		return this.motionBlurEnabled;
	}

	public void setMotionBlurEnabled(boolean motionBlurEnabled) {
		this.motionBlurEnabled = motionBlurEnabled;
	}

	public boolean isTiltShiftEnabled() {
		return this.tiltShiftEnabled;
	}

	public void setTiltShiftEnabled(boolean tiltShiftEnabled) {
		this.tiltShiftEnabled = tiltShiftEnabled;
	}

	public boolean isLensFlareEnabled() {
		return this.lensFlareEnabled;
	}

	public void setLensFlareEnabled(boolean lensFlareEnabled) {
		this.lensFlareEnabled = lensFlareEnabled;
	}

	public boolean isGrainEnabled() {
		return this.grainEnabled;
	}

	public void setGrainEnabled(boolean grainEnabled) {
		this.grainEnabled = grainEnabled;
	}

	@Handler.Function(Handler.FLAG_DISPOSE)
	public void dispose() {
	}

	@Module.Instance
	public static PolyPost get() {
		return (PolyPost) Framework.get().getInstance(PolyPost.class);
	}
}
