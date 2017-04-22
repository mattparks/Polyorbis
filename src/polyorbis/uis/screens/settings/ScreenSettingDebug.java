package polyorbis.uis.screens.settings;

import flounder.events.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.maths.vectors.*;
import flounder.profiling.*;
import polyorbis.uis.*;
import polyorbis.uis.screens.*;

public class ScreenSettingDebug extends ScreenObject {
	public ScreenSettingDebug(OverlaySlider slider, ScreenSettings settings) {
		super(slider, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		// Toggle Profiler.
		GuiButtonText toggleProfiler = new GuiButtonText(this, new Vector2f(0.5f, 0.20f), "Developer Profiler: ", GuiAlign.CENTRE);
		FlounderEvents.addEvent(new EventChange<Boolean>(FlounderProfiler::isOpen) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleProfiler.setText("Developer Profiler: " + newValue);
			}
		});
		toggleProfiler.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				FlounderProfiler.toggle(!FlounderProfiler.isOpen());
			}
		});

		// Toggle Wireframe.
		GuiButtonText toggleWireframe = new GuiButtonText(this, new Vector2f(0.5f, 0.27f), "Wireframe Mode: ", GuiAlign.CENTRE);
		FlounderEvents.addEvent(new EventChange<Boolean>(OpenGlUtils::isInWireframe) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleWireframe.setText("Wireframe Mode: " + newValue);
			}
		});
		toggleWireframe.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				OpenGlUtils.goWireframe(!OpenGlUtils.isInWireframe());
			}
		});

		// Back.
		GuiButtonText back = new GuiButtonText(this, new Vector2f(0.5f, 0.9f), "Back", GuiAlign.CENTRE);
		back.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				slider.setNewSecondaryScreen(settings);
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
