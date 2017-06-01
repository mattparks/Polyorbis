package polyorbis.uis.screens;

import flounder.events.*;
import flounder.guis.*;
import flounder.maths.vectors.*;
import flounder.visual.*;
import polyorbis.*;
import polyorbis.uis.*;
import polyorbis.uis.screens.settings.*;
import polyorbis.world.*;

public class ScreenSettings extends ScreenObject {
	public ScreenSettings(OverlaySlider slider) {
		super(slider, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		// Left and right Panes.
		ScreenObject paneLeft = new ScreenObjectEmpty(this, new Vector2f(0.25f, 0.5f), new Vector2f(0.5f, 1.0f), true);
		ScreenObject paneRight = new ScreenObjectEmpty(this, new Vector2f(0.75f, 0.5f), new Vector2f(0.5f, 1.0f), true);

		// Screen Controls.
		ScreenSettingControls screenControls = new ScreenSettingControls(slider, this);
		screenControls.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText controls = new GuiButtonText(paneLeft, new Vector2f(0.25f, 0.2f), "Controls", GuiAlign.CENTRE);
		controls.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				slider.setNewSecondaryScreen(screenControls);
			}
		});

		// Toggle Game Help.
		GuiButtonText toggleGameHelp = new GuiButtonText(paneLeft, new Vector2f(0.25f, 0.27f), "Game Help: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(((PolyGuis) FlounderGuis.get().getGuiMaster())::isGameHelp) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleGameHelp.setText("Game Help: " + newValue);
			}
		});
		toggleGameHelp.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				((PolyGuis) FlounderGuis.get().getGuiMaster()).setGamehelp(!((PolyGuis) FlounderGuis.get().getGuiMaster()).isGameHelp());
			}
		});

		// Toggle Atmosphere.
		GuiButtonText toggleAtmosphere = new GuiButtonText(paneLeft, new Vector2f(0.25f, 0.34f), "Atmosphere: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyWorld.get()::hasAtmosphere) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleAtmosphere.setText("Atmosphere: " + newValue);
			}
		});
		toggleAtmosphere.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				PolyWorld.get().setAtmosphere(!PolyWorld.get().hasAtmosphere());
			}
		});

		// Screen Audio.
		ScreenSettingAudio screenAudio = new ScreenSettingAudio(slider, this);
		screenAudio.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText audio = new GuiButtonText(paneRight, new Vector2f(0.75f, 0.20f), "Audio", GuiAlign.CENTRE);
		audio.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				slider.setNewSecondaryScreen(screenAudio);
			}
		});

		// Screen Graphics.
		ScreenSettingGraphics screenGraphics = new ScreenSettingGraphics(slider, this);
		screenGraphics.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText graphics = new GuiButtonText(paneRight, new Vector2f(0.75f, 0.27f), "Graphics", GuiAlign.CENTRE);
		graphics.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				slider.setNewSecondaryScreen(screenGraphics);
			}
		});

		// Screen Post.
		ScreenSettingPost settingPost = new ScreenSettingPost(slider, this);
		settingPost.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText post = new GuiButtonText(paneRight, new Vector2f(0.75f, 0.34f), "Post Effects", GuiAlign.CENTRE);
		post.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				slider.setNewSecondaryScreen(settingPost);
			}
		});

		// Back.
		GuiButtonText back = new GuiButtonText(this, new Vector2f(0.5f, 0.9f), "Back", GuiAlign.CENTRE);
		back.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				slider.closeSecondaryScreen();
			}
		});
	}

	@Override
	public void updateObject() {
	}

	@Override
	public void deleteObject() {

	}
}
