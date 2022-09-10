package com.polyorbis;

import com.flounder.devices.*;
import com.flounder.framework.*;
import com.flounder.parsing.config.*;
import com.flounder.resources.*;
import com.flounder.shadows.*;
import com.flounder.textures.*;

import static com.flounder.platform.Constants.*;

import com.polyorbis.post.*;
import com.polyorbis.world.*;

/**
 * A class that contains a bunch of config references.
 */
public class PolyConfigs {
	// Main configs.
	private static final Config CONFIG_MAIN = new Config(new MyFile(Framework.getRoamingFolder("polyorbis"), "configs", "settings.conf"));

	public static final ConfigData ATMOSPHERE_ENABLED = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "atmosphereEnabled", true);
	public static final ConfigData GAMEHELP_ENABLED = CONFIG_MAIN.getData(ConfigSection.DEBUG, "gamehelpEnabled", true);

	public static final ConfigData MUSIC_ENABLED = CONFIG_MAIN.getData(ConfigSection.AUDIO, "musicEnabled", true);
	public static final ConfigData MUSIC_VOLUME = CONFIG_MAIN.getData(ConfigSection.AUDIO, "musicVolume", 0.05f);
	public static final ConfigData SOUND_VOLUME = CONFIG_MAIN.getData(ConfigSection.AUDIO, "soundVolume", 0.22f);

	public static final ConfigData DISPLAY_WIDTH = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "displayWidth", 1080, () -> FlounderDisplay.get().getWindowWidth());
	public static final ConfigData DISPLAY_HEIGHT = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "displayHeight", 720, () -> FlounderDisplay.get().getWindowHeight());
	public static final ConfigData DISPLAY_VSYNC = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "displayVSync", false, () -> FlounderDisplay.get().isVSync());
	public static final ConfigData DISPLAY_ANTIALIAS = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "displayAntialias", true, () -> FlounderDisplay.get().isAntialiasing());
	public static final ConfigData DISPLAY_FULLSCREEN = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "displayFullscreen", false, () -> FlounderDisplay.get().isFullscreen());
	public static final ConfigData FRAMEWORK_FPS_LIMIT = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "frameworkFpsLimit", 100, () -> Framework.get().getFpsLimit());
	public static final ConfigData TEXTURES_ANISOTROPY_MAX = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "texturesAnisotropyMax", 4.0f, () -> FlounderTextures.get().getAnisotropyLevel());

	public static final ConfigData BRIGHTNESS_BOOST = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "brightnessBoost", 0.25f, () -> FlounderShadows.get().getBrightnessBoost());
	public static final ConfigData SHADOWMAP_SIZE = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "shadowmapSize", 8192, () -> FlounderShadows.get().getShadowSize());
	public static final ConfigData SHADOWMAP_PCF = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "shadowmapPcf", 0, () -> FlounderShadows.get().getShadowPCF());
	public static final ConfigData SHADOWMAP_BIAS = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "shadowmapBias", 0.0003f, () -> FlounderShadows.get().getShadowBias());
	public static final ConfigData SHADOWMAP_DARKNESS = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "shadowmapDarkness", 0.4f, () -> FlounderShadows.get().getShadowDarkness());
	public static final ConfigData SHADOWMAP_UNLIMITED = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "shadowmapUnlimited", true, () -> FlounderShadows.get().isRenderUnlimited());

	public static final ConfigData POST_EFFECTS_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "effectsEnabled", true, () -> PolyPost.get().isEffectsEnabled());
	public static final ConfigData POST_BLOOM_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "bloomEnabled", true, () -> PolyPost.get().isBloomEnabled());
	public static final ConfigData POST_MOTIONBLUR_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "motionBlurEnabled", false, () -> PolyPost.get().isMotionBlurEnabled());
	public static final ConfigData POST_TILTSHIFT_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "tiltShiftEnabled", true, () -> PolyPost.get().isTiltShiftEnabled());
	public static final ConfigData POST_LENSFLARE_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "lensFlareEnabled", true, () -> PolyPost.get().isLensFlareEnabled());
	public static final ConfigData POST_GRAIN_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "grainEnabled", true, () -> PolyPost.get().isGrainEnabled());

	public static final ConfigData CAMERA_SENSITIVITY = CONFIG_MAIN.getData(ConfigSection.CONTROLS, "cameraSensitivity", 1.0f); // Reference set in camera.
	public static final ConfigData CAMERA_REANGLE = CONFIG_MAIN.getData(ConfigSection.CONTROLS, "cameraReangle", GLFW_MOUSE_BUTTON_RIGHT); // Reference set in camera.

	// Save configs.
	private static final Config CONFIG_SAVE = new Config(new MyFile(Framework.getRoamingFolder("polyorbis"), "saves", "save.conf"));
	public static final ConfigData SAVE_HIGHSCORE = CONFIG_SAVE.getData(ConfigSection.WORLD, "highScore", 0, () -> PolyWorld.get().getHighsore());

	/**
	 * Saves the configs when closing the game.
	 */
	public static void saveAllConfigs() {
		CONFIG_MAIN.save();
		CONFIG_SAVE.save();
	}
}
