package polyorbis.post;

import flounder.framework.*;
import flounder.profiling.*;
import polyorbis.*;

public class PolyPost extends Module {
	private static final PolyPost INSTANCE = new PolyPost();
	public static final String PROFILE_TAB_NAME = "Poly Post";

	private boolean effectsEnabled;

	private boolean bloomEnabled;
	private boolean motionBlurEnabled;
	private boolean tiltShiftEnabled;
	private boolean lensFlareEnabled;
	private boolean grainEnabled;

	public PolyPost() {
		super(ModuleUpdate.UPDATE_PRE, PROFILE_TAB_NAME);
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

	@Handler.Function(Handler.FLAG_PROFILE)
	public void profile() {
		FlounderProfiler.get().add(PROFILE_TAB_NAME, "Effects Enabled", effectsEnabled);
		FlounderProfiler.get().add(PROFILE_TAB_NAME, "Bloom Enabled", bloomEnabled);
		FlounderProfiler.get().add(PROFILE_TAB_NAME, "Motion Blur Enabled", motionBlurEnabled);
		FlounderProfiler.get().add(PROFILE_TAB_NAME, "Lens Flare Enabled", lensFlareEnabled);
		FlounderProfiler.get().add(PROFILE_TAB_NAME, "Grain Filter Enabled", grainEnabled);
	}

	public static boolean isEffectsEnabled() {
		return INSTANCE.effectsEnabled;
	}

	public static void setEffectsEnabled(boolean effectsEnabled) {
		INSTANCE.effectsEnabled = effectsEnabled;
	}

	public static boolean isBloomEnabled() {
		return INSTANCE.bloomEnabled;
	}

	public static void setBloomEnabled(boolean bloomEnabled) {
		INSTANCE.bloomEnabled = bloomEnabled;
	}

	public static boolean isMotionBlurEnabled() {
		return INSTANCE.motionBlurEnabled;
	}

	public static void setMotionBlurEnabled(boolean motionBlurEnabled) {
		INSTANCE.motionBlurEnabled = motionBlurEnabled;
	}

	public static boolean isTiltShiftEnabled() {
		return INSTANCE.tiltShiftEnabled;
	}

	public static void setTiltShiftEnabled(boolean tiltShiftEnabled) {
		INSTANCE.tiltShiftEnabled = tiltShiftEnabled;
	}

	public static boolean isLensFlareEnabled() {
		return INSTANCE.lensFlareEnabled;
	}

	public static void setLensFlareEnabled(boolean lensFlareEnabled) {
		INSTANCE.lensFlareEnabled = lensFlareEnabled;
	}

	public static boolean isGrainEnabled() {
		return INSTANCE.grainEnabled;
	}

	public static void setGrainEnabled(boolean grainEnabled) {
		INSTANCE.grainEnabled = grainEnabled;
	}


	@Handler.Function(Handler.FLAG_DISPOSE)
	public void dispose() {
	}
}
