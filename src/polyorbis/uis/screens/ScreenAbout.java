package polyorbis.uis.screens;

import flounder.fonts.*;
import flounder.guis.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.visual.*;
import polyorbis.uis.*;

public class ScreenAbout extends ScreenObject {
	public ScreenAbout(OverlaySlider slider) {
		super(slider, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		// Text 1.
		String t1 = "Polyorbis was written in 3 days for the Ludum Dare 38 jam by Mattparks and Decaxon."; // TODO: Expand!
		TextObject text1 = new TextObject(this, new Vector2f(0.5f, 0.4f), t1, 1.4f, FlounderFonts.CANDARA, 0.7f, GuiAlign.CENTRE);
		text1.setInScreenCoords(true);
		text1.setColour(new Colour(1.0f, 1.0f, 1.0f, 1.0f));
		text1.setBorderColour(new Colour(0.0f, 0.0f, 0.0f));
		text1.setBorder(new ConstantDriver(0.175f));
		text1.setAlphaDriver(new ConstantDriver(0.75f));

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
