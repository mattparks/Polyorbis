package polyorbis.uis.screens.settings;

import flounder.events.*;
import flounder.guis.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import polyorbis.camera.*;
import polyorbis.uis.*;
import polyorbis.uis.screens.*;

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
				sliderSensitivity.setProgress(newValue);
			}
		});
		sliderSensitivity.addChangeListener(() -> PolyCamera.setSensitivity(sliderSensitivity.getProgress()));

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
