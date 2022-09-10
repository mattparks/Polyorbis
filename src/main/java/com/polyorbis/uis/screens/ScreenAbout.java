package com.polyorbis.uis.screens;

import com.polyorbis.uis.*;

import com.flounder.fonts.*;
import com.flounder.guis.*;
import com.flounder.maths.*;
import com.flounder.maths.vectors.*;
import com.flounder.visual.*;

public class ScreenAbout extends ScreenObject {
	public ScreenAbout(OverlaySlider slider) {
		super(slider, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		// Text 1.
		String t1 = "F1 for game help. Polyorbis was written in 3 days for the Ludum Dare 38 Jam, created by: Mattparks and Decaxon. " +
				"The competitions theme is: A Small World. We used our open-sourced Java OpenGL game engine: Flounder, found at https://github.com/Equilibrium-Games/Flounder-Engine, to create our low poly action game. " +
				"Info on how to play the game can be found in the F1 help menu. Our twitters are @Mattparks5855, @decaxon, and @EquilGame.";
		TextObject text1 = new TextObject(this, new Vector2f(0.5f, 0.4f), t1, 1.4f, FlounderFonts.CANDARA, 0.7f, GuiAlign.CENTRE);
		text1.setInScreenCoords(true);
		text1.setColour(new Colour(1.0f, 1.0f, 1.0f, 1.0f));
		text1.setBorderColour(new Colour(0.0f, 0.0f, 0.0f));
		text1.setBorder(new ConstantDriver(0.175f));
		text1.setAlphaDriver(new ConstantDriver(0.75f));

		// Back.
		GuiButtonText back = new GuiButtonText(this, new Vector2f(0.5f, 0.9f), "Back", GuiAlign.CENTRE);
		back.addLeftListener(slider::closeSecondaryScreen);
	}

	@Override
	public void updateObject() {
	}

	@Override
	public void deleteObject() {

	}
}
