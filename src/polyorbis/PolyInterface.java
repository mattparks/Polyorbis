package polyorbis;

import flounder.devices.*;
import flounder.events.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.inputs.*;
import flounder.networking.*;
import flounder.particles.*;
import flounder.shadows.*;
import flounder.skybox.*;
import flounder.sounds.*;
import flounder.standards.*;
import polyorbis.post.*;
import polyorbis.world.*;

import static org.lwjgl.glfw.GLFW.*;

public class PolyInterface extends Standard {
	private Playlist gamePlaylist;

	public PolyInterface() {
		super(FlounderDisplay.class, FlounderKeyboard.class, FlounderSound.class, FlounderEvents.class, FlounderNetwork.class, FlounderShadows.class, FlounderParticles.class, FlounderSkybox.class, PolyPost.class, PolyWorld.class);
	}

	@Override
	public void init() {
		FlounderSound.getMusicPlayer().setVolume(PolyConfigs.MUSIC_VOLUME.setReference(() -> FlounderSound.getMusicPlayer().getVolume()).getFloat());
		FlounderSound.getSourcePool().setSystemVolume(PolyConfigs.SOUND_VOLUME.setReference(() -> FlounderSound.getSourcePool().getSystemVolume()).getFloat());

		gamePlaylist = new Playlist();
		// gamePlaylist.addMusic(Sound.loadSoundInBackground(new MyFile(MyFile.RES_FOLDER, "music", "09-hitori-bocchi-1b.wav"), 0.80f, 1.0f));
		FlounderSound.getMusicPlayer().playMusicPlaylist(gamePlaylist, true, 4.0f, 10.0f);

		if (PolyConfigs.MUSIC_ENABLED.setReference(() -> !FlounderSound.getMusicPlayer().isPaused()).getBoolean()) {
			FlounderSound.getMusicPlayer().unpauseTrack();
		}

		FlounderEvents.addEvent(new IEvent() {
			KeyButton screenshot = new KeyButton(GLFW_KEY_F2);

			@Override
			public boolean eventTriggered() {
				return screenshot.wasDown();
			}

			@Override
			public void onEvent() {
				FlounderDisplay.screenshot();
			}
		});

		FlounderEvents.addEvent(new IEvent() {
			KeyButton fullscreen = new KeyButton(GLFW_KEY_F11);

			@Override
			public boolean eventTriggered() {
				return fullscreen.wasDown();
			}

			@Override
			public void onEvent() {
				FlounderDisplay.setFullscreen(!FlounderDisplay.isFullscreen());
			}
		});

		FlounderEvents.addEvent(new IEvent() {
			KeyButton wireframe = new KeyButton(GLFW_KEY_P);

			@Override
			public boolean eventTriggered() {
				return wireframe.wasDown() && !FlounderGuis.getGuiMaster().isGamePaused();
			}

			@Override
			public void onEvent() {
				OpenGlUtils.goWireframe(!OpenGlUtils.isInWireframe());
			}
		});

		FlounderEvents.addEvent(new IEvent() {
			KeyButton closeWindow = new KeyButton(GLFW_KEY_DELETE);

			@Override
			public boolean eventTriggered() {
				return closeWindow.wasDown() && !FlounderGuis.getGuiMaster().isGamePaused();
			}

			@Override
			public void onEvent() {
				Framework.requestClose();
			}
		});
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
