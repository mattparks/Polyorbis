package polyorbis;

import flounder.devices.*;
import flounder.events.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.inputs.*;
import flounder.networking.*;
import flounder.particles.*;
import flounder.renderer.*;
import flounder.resources.*;
import flounder.shadows.*;
import flounder.skybox.*;
import flounder.sounds.*;
import flounder.standards.*;
import flounder.tasks.*;
import flounder.textures.*;
import polyorbis.post.*;
import polyorbis.world.*;

import static flounder.platform.Constants.*;

public class PolyInterface extends Standard {
	private Playlist gamePlaylist;

	public PolyInterface() {
		super(FlounderEvents.class, FlounderTasks.class, FlounderNetwork.class, FlounderShadows.class, FlounderParticles.class, FlounderSkybox.class, PolyPost.class, PolyWorld.class);
	}

	@Override
	public void init() {
		Framework.get().setFpsLimit(PolyConfigs.FRAMEWORK_FPS_LIMIT.getInteger());
		FlounderTextures.get().setAnisotropyLevel(PolyConfigs.TEXTURES_ANISOTROPY_MAX.getFloat());
		FlounderDisplay.get().setWindowSize(PolyConfigs.DISPLAY_WIDTH.getInteger(), PolyConfigs.DISPLAY_HEIGHT.getInteger());
		FlounderDisplay.get().setTitle("Polyorbis");
		FlounderDisplay.get().setIcons(new MyFile[]{new MyFile(MyFile.RES_FOLDER, "icon", "icon.png")});
		FlounderDisplay.get().setVSync(PolyConfigs.DISPLAY_VSYNC.getBoolean());
		FlounderDisplay.get().setAntialiasing(PolyConfigs.DISPLAY_ANTIALIAS.getBoolean());
		FlounderDisplay.get().setSamples(0);
		FlounderDisplay.get().setFullscreen(PolyConfigs.DISPLAY_FULLSCREEN.getBoolean());
		FlounderDisplay.get().setHidden(false);

		FlounderSound.get().getMusicPlayer().setVolume(PolyConfigs.MUSIC_VOLUME.setReference(() -> FlounderSound.get().getMusicPlayer().getVolume()).getFloat());
		FlounderSound.get().getSourcePool().setSystemVolume(PolyConfigs.SOUND_VOLUME.setReference(() -> FlounderSound.get().getSourcePool().getSystemVolume()).getFloat());

		gamePlaylist = new Playlist();
		gamePlaylist.addMusic(Sound.loadSoundInBackground(new MyFile(MyFile.RES_FOLDER, "music", "A#Bb-3-3-sine-120.wav"), 0.10f, 1.0f));
		gamePlaylist.addMusic(Sound.loadSoundInBackground(new MyFile(MyFile.RES_FOLDER, "music", "a-3-3-triangle-100.wav"), 0.10f, 1.0f));
		FlounderSound.get().getMusicPlayer().playMusicPlaylist(gamePlaylist, true, 3.0f, 5.0f);

		if (PolyConfigs.MUSIC_ENABLED.setReference(() -> !FlounderSound.get().getMusicPlayer().isPaused()).getBoolean()) {
			FlounderSound.get().getMusicPlayer().unpauseTrack();
		}

		FlounderEvents.get().addEvent(new EventStandard() {
			KeyButton screenshot = new KeyButton(GLFW_KEY_F2);

			@Override
			public boolean eventTriggered() {
				return screenshot.wasDown();
			}

			@Override
			public void onEvent() {
				FlounderDisplay.get().screenshot();
			}
		});

		FlounderEvents.get().addEvent(new EventStandard() {
			KeyButton fullscreen = new KeyButton(GLFW_KEY_F11);

			@Override
			public boolean eventTriggered() {
				return fullscreen.wasDown();
			}

			@Override
			public void onEvent() {
				FlounderDisplay.get().setFullscreen(!FlounderDisplay.get().isFullscreen());
			}
		});

		FlounderEvents.get().addEvent(new EventStandard() {
			KeyButton wireframe = new KeyButton(GLFW_KEY_P);

			@Override
			public boolean eventTriggered() {
				return wireframe.wasDown() && !FlounderGuis.get().getGuiMaster().isGamePaused();
			}

			@Override
			public void onEvent() {
				FlounderOpenGL.get().goWireframe(!FlounderOpenGL.get().isInWireframe());
			}
		});

		/*FlounderTasks.get().addTask(new EventStandard() {
			KeyButton closeWindow = new KeyButton(GLFW_KEY_DELETE);

			@Override
			public boolean eventTriggered() {
				return closeWindow.wasDown() && !FlounderGuis.getGuiMaster().isGamePaused();
			}

			@Override
			public void onEvent() {
				Framework.requestClose();
			}
		});*/
	}

	@Override
	public void update() {
	}

	@Override
	public void profile() {

	}

	@Override
	public void dispose() {
		PolyConfigs.saveAllConfigs();
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
