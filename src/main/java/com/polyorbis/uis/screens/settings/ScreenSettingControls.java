package com.polyorbis.uis.screens.settings;

import com.polyorbis.camera.*;
import com.polyorbis.uis.*;
import com.polyorbis.uis.screens.*;

import com.flounder.events.*;
import com.flounder.guis.*;
import com.flounder.maths.*;
import com.flounder.maths.vectors.*;

public class ScreenSettingControls extends ScreenObject {
	public ScreenSettingControls(OverlaySlider slider, ScreenSettings settings) {
		super(slider, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		// Slider Camera Sensitivity.
		GuiSliderText sliderSensitivity = new GuiSliderText(this, new Vector2f(0.5f, 0.20f), "Sensitivity: ", 0.1f, 7.0f, PolyCamera.getSensitivity(), GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Float>(PolyCamera::getSensitivity) {
			@Override
			public void onEvent(Float newValue) {
				sliderSensitivity.setText("Sensitivity: " + Maths.roundToPlace(newValue, 2));
				sliderSensitivity.setValue(newValue);
			}
		});
		sliderSensitivity.addChangeListener(() -> PolyCamera.setSensitivity(sliderSensitivity.getValue()));

		// Key Select Mouse Reangle.

		// Back.
		GuiButtonText back = new GuiButtonText(this, new Vector2f(0.5f, 0.9f), "Back", GuiAlign.CENTRE);
		back.addLeftListener(() -> slider.setNewSecondaryScreen(settings));
	}

	@Override
	public void updateObject() {
	}

	@Override
	public void deleteObject() {

	}
}
