package polyorbis;

import flounder.devices.*;
import flounder.framework.*;
import flounder.helpers.*;
import flounder.parsing.*;
import flounder.profiling.*;
import flounder.resources.*;
import flounder.shadows.*;
import flounder.textures.*;
import org.lwjgl.glfw.*;
import polyorbis.post.*;
import polyorbis.world.*;

/**
 * A class that contains a bunch of config references.
 */
public class PolyConfigs {
	// Main configs.
	private static final Config CONFIG_MAIN = new Config(new MyFile(Framework.getRoamingFolder(), "configs", "settings.conf"));
	public static final ConfigData PROFILER_ENABLED = CONFIG_MAIN.getData(ConfigSection.DEBUG, "profilerEnabled", false, FlounderProfiler::isOpen);
	public static final ConfigData WIREFRAME_ENABLED = CONFIG_MAIN.getData(ConfigSection.DEBUG, "wireframeEnabled", false, OpenGlUtils::isInWireframe);

	public static final ConfigData ATMOSPHERE_ENABLED = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "atmosphereEnabled", true);
	public static final ConfigData STARTHELP_ENABLED = CONFIG_MAIN.getData(ConfigSection.DEBUG, "starthelpEnabled", true);

	public static final ConfigData MUSIC_ENABLED = CONFIG_MAIN.getData(ConfigSection.AUDIO, "musicEnabled", true);
	public static final ConfigData MUSIC_VOLUME = CONFIG_MAIN.getData(ConfigSection.AUDIO, "musicVolume", 0.3f);
	public static final ConfigData SOUND_VOLUME = CONFIG_MAIN.getData(ConfigSection.AUDIO, "soundVolume", 0.5f);

	public static final ConfigData DISPLAY_WIDTH = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "displayWidth", 1080, FlounderDisplay::getWindowWidth);
	public static final ConfigData DISPLAY_HEIGHT = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "displayHeight", 720, FlounderDisplay::getWindowHeight);
	public static final ConfigData DISPLAY_VSYNC = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "displayVSync", false, FlounderDisplay::isVSync);
	public static final ConfigData DISPLAY_ANTIALIAS = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "displayAntialias", true, FlounderDisplay::isAntialiasing);
	public static final ConfigData DISPLAY_FULLSCREEN = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "displayFullscreen", false, FlounderDisplay::isFullscreen);
	public static final ConfigData FRAMEWORK_FPS_LIMIT = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "frameworkFpsLimit", 144, Framework::getFpsLimit);
	public static final ConfigData TEXTURES_ANISOTROPY_MAX = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "texturesAnisotropyMax", 8.0f, FlounderTextures::getAnisotropyLevel);

	public static final ConfigData BRIGHTNESS_BOOST = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "brightnessBoost", 0.22f, FlounderShadows::getBrightnessBoost);
	public static final ConfigData SHADOWMAP_SIZE = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "shadowmapSize", 8192, FlounderShadows::getShadowSize);
	public static final ConfigData SHADOWMAP_PCF = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "shadowmapPcf", 0, FlounderShadows::getShadowPCF);
	public static final ConfigData SHADOWMAP_BIAS = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "shadowmapBias", 0.0003f, FlounderShadows::getShadowBias);
	public static final ConfigData SHADOWMAP_DARKNESS = CONFIG_MAIN.getData(ConfigSection.GRAPHICS, "shadowmapDarkness", 0.5f, FlounderShadows::getShadowDarkness);

	public static final ConfigData POST_EFFECTS_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "effectsEnabled", true, PolyPost::isEffectsEnabled);
	public static final ConfigData POST_BLOOM_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "bloomEnabled", true, PolyPost::isBloomEnabled);
	public static final ConfigData POST_MOTIONBLUR_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "motionBlurEnabled", false, PolyPost::isMotionBlurEnabled);
	public static final ConfigData POST_TILTSHIFT_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "tiltShiftEnabled", true, PolyPost::isTiltShiftEnabled);
	public static final ConfigData POST_LENSFLARE_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "lensFlareEnabled", true, PolyPost::isLensFlareEnabled);
	public static final ConfigData POST_GRAIN_ENABLED = CONFIG_MAIN.getData(ConfigSection.POST, "grainEnabled", true, PolyPost::isGrainEnabled);

	public static final ConfigData CAMERA_SENSITIVITY = CONFIG_MAIN.getData(ConfigSection.CONTROLS, "cameraSensitivity", 1.0f); // Reference set in camera.
	public static final ConfigData CAMERA_REANGLE = CONFIG_MAIN.getData(ConfigSection.CONTROLS, "cameraReangle", GLFW.GLFW_MOUSE_BUTTON_RIGHT); // Reference set in camera.

	// Save configs.
	private static final Config CONFIG_SAVE = new Config(new MyFile(Framework.getRoamingFolder(), "saves", "save.conf"));
	public static final ConfigData SAVE_HIGHSCORE = CONFIG_SAVE.getData(ConfigSection.WORLD, "highScore", 0, PolyWorld::getHighsore);

	/**
	 * Saves the configs when closing the game.
	 */
	public static void saveAllConfigs() {
		CONFIG_MAIN.save();
		CONFIG_SAVE.save();
	}
}
