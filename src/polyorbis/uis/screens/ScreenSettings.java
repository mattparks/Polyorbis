package polyorbis.uis.screens;

import flounder.guis.*;
import flounder.maths.vectors.*;
import flounder.visual.*;
import polyorbis.uis.*;
import polyorbis.uis.screens.settings.*;

public class ScreenSettings extends ScreenObject {
	public ScreenSettings(OverlaySlider slider) {
		super(slider, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		// Left and right Panes.
		ScreenObject paneLeft = new ScreenObjectEmpty(this, new Vector2f(0.25f, 0.5f), new Vector2f(0.5f, 1.0f), true);
		ScreenObject paneRight = new ScreenObjectEmpty(this, new Vector2f(0.75f, 0.5f), new Vector2f(0.5f, 1.0f), true);

		// Screen Debug.
		ScreenSettingDebug screenDebug = new ScreenSettingDebug(slider, this);
		screenDebug.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText debug = new GuiButtonText(paneLeft, new Vector2f(0.25f, 0.2f), "Debug", GuiAlign.CENTRE);
		debug.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				slider.setNewSecondaryScreen(screenDebug);
			}
		});

		// Screen Controls.
		ScreenSettingControls screenControls = new ScreenSettingControls(slider, this);
		screenControls.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText controls = new GuiButtonText(paneLeft, new Vector2f(0.25f, 0.27f), "Controls", GuiAlign.CENTRE);
		controls.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				slider.setNewSecondaryScreen(screenControls);
			}
		});

		// Screen Audio.
		ScreenSettingAudio screenAudio = new ScreenSettingAudio(slider, this);
		screenAudio.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText audio = new GuiButtonText(paneLeft, new Vector2f(0.25f, 0.34f), "Audio", GuiAlign.CENTRE);
		audio.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				slider.setNewSecondaryScreen(screenAudio);
			}
		});

		// Screen Graphics.
		ScreenSettingGraphics screenGraphics = new ScreenSettingGraphics(slider, this);
		screenGraphics.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText graphics = new GuiButtonText(paneRight, new Vector2f(0.75f, 0.20f), "Graphics", GuiAlign.CENTRE);
		graphics.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				slider.setNewSecondaryScreen(screenGraphics);
			}
		});

		// Screen Post.
		ScreenSettingPost settingPost = new ScreenSettingPost(slider, this);
		settingPost.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText post = new GuiButtonText(paneRight, new Vector2f(0.75f, 0.27f), "Post Effects", GuiAlign.CENTRE);
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
