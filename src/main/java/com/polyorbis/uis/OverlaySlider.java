package com.polyorbis.uis;

import com.polyorbis.*;
import com.polyorbis.uis.screens.*;

import com.flounder.guis.*;
import com.flounder.maths.vectors.*;
import com.flounder.visual.*;

public class OverlaySlider extends ScreenObject {
	private ScreenPause screenPause;

	private ScreenObject secondaryScreen;
	private ScreenObject newSecondaryScreen;

	public OverlaySlider(ScreenObject parent) {
		super(parent, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		this.screenPause = new ScreenPause(this);

		this.secondaryScreen = null;
		this.newSecondaryScreen = null;
	}

	@Override
	public void updateObject() {
		if (newSecondaryScreen != null && secondaryScreen.getAlpha() == 0.0f) {
			secondaryScreen = newSecondaryScreen;
			newSecondaryScreen = null;
		}

		if (screenPause.getAlpha() == 1.0f && secondaryScreen != null && secondaryScreen.getAlpha() == 0.0f && newSecondaryScreen == null) {
			secondaryScreen = null;
		}
	}

	public void setNewSecondaryScreen(ScreenObject secondScreen) {
		if (secondaryScreen == secondScreen || newSecondaryScreen == secondScreen) {
			return;
		}

		newSecondaryScreen = secondScreen;
		newSecondaryScreen.setAlphaDriver(new SlideDriver(0.0f, 1.0f, PolyGuis.SLIDE_TIME));

		if (secondaryScreen != null) {
			secondaryScreen.setAlphaDriver(new SlideDriver(1.0f, 0.0f, PolyGuis.SLIDE_TIME));
		} else {
			secondaryScreen = newSecondaryScreen;
			newSecondaryScreen = null;
		}

		if (screenPause.getAlpha() == 1.0f) {
			screenPause.setAlphaDriver(new SlideDriver(1.0f, 0.0f, PolyGuis.SLIDE_TIME));
		}
	}

	public void closeSecondaryScreen() {
		if (newSecondaryScreen != null) {
			return;
		}

		if (secondaryScreen != null && secondaryScreen.getAlpha() == 1.0f) {
			secondaryScreen.setAlphaDriver(new SlideDriver(1.0f, 0.0f, PolyGuis.SLIDE_TIME));
			screenPause.setAlphaDriver(new SlideDriver(0.0f, 1.0f, PolyGuis.SLIDE_TIME));
		}
	}

	public float getBlurFactor() {
		return getAlpha();
	}

	@Override
	public void deleteObject() {
	}
}
