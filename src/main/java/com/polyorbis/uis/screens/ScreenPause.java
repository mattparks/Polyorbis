package com.polyorbis.uis.screens;

import com.polyorbis.*;
import com.polyorbis.uis.*;

import com.flounder.fonts.*;
import com.flounder.framework.*;
import com.flounder.guis.*;
import com.flounder.maths.*;
import com.flounder.maths.vectors.*;
import com.flounder.visual.*;

public class ScreenPause extends ScreenObject {
	public ScreenPause(OverlaySlider slider) {
		super(slider, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		// Game Title.
		TextObject title = new TextObject(this, new Vector2f(0.5f, 0.15f), "Polyorbis", 5.0f, FlounderFonts.CANDARA, 1.0f, GuiAlign.CENTRE);
		title.setInScreenCoords(true);
		title.setColour(new Colour(1.0f, 1.0f, 1.0f, 1.0f));
		title.setBorderColour(new Colour(0.0f, 0.0f, 0.0f));
		title.setBorder(new ConstantDriver(0.022f));

		float yPosition = 0.30f;
		float ySpacing = 0.07f;

		// Resume.
		GuiButtonText resumeToMenu = new GuiButtonText(this, new Vector2f(0.5f, yPosition += 1.2f * ySpacing), "Resume Game", GuiAlign.CENTRE);
		resumeToMenu.addLeftListener(() -> ((PolyGuis) FlounderGuis.get().getGuiMaster()).togglePause(false));

		// Settings.
		ScreenSettings screenSettings = new ScreenSettings(slider);
		screenSettings.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText settings = new GuiButtonText(this, new Vector2f(0.5f, yPosition += ySpacing), "Settings", GuiAlign.CENTRE);
		settings.addLeftListener(() -> slider.setNewSecondaryScreen(screenSettings));

		// About.
		ScreenAbout screenAbout = new ScreenAbout(slider);
		screenAbout.setAlphaDriver(new ConstantDriver(0.0f));
		GuiButtonText about = new GuiButtonText(this, new Vector2f(0.5f, yPosition += ySpacing), "About", GuiAlign.CENTRE);
		about.addLeftListener(() -> slider.setNewSecondaryScreen(screenAbout));

		// Exit.
		GuiButtonText exitToMenu = new GuiButtonText(this, new Vector2f(0.5f, yPosition += 1.2f * ySpacing), "Exit To Desktop", GuiAlign.CENTRE);
		exitToMenu.addLeftListener(() -> Framework.get().requestClose(false));
	}

	@Override
	public void updateObject() {
	}

	@Override
	public void deleteObject() {

	}
}
