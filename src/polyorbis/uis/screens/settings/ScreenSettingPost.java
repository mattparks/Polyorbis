package polyorbis.uis.screens.settings;

import flounder.events.*;
import flounder.guis.*;
import flounder.maths.vectors.*;
import polyorbis.post.*;
import polyorbis.uis.*;
import polyorbis.uis.screens.*;

public class ScreenSettingPost extends ScreenObject {
	public ScreenSettingPost(OverlaySlider slider, ScreenSettings settings) {
		super(slider, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		// Toggle Effects.
		GuiButtonText toggleEffects = new GuiButtonText(this, new Vector2f(0.5f, 0.20f), "Post Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.addEvent(new EventChange<Boolean>(PolyPost::isEffectsEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleEffects.setText("Post Enabled: " + newValue);
			}
		});
		toggleEffects.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				PolyPost.setEffectsEnabled(!PolyPost.isEffectsEnabled());
			}
		});

		// Toggle Bloom.
		GuiButtonText toggleBloom = new GuiButtonText(this, new Vector2f(0.5f, 0.27f), "Bloom Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.addEvent(new EventChange<Boolean>(PolyPost::isBloomEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleBloom.setText("Bloom Enabled: " + newValue);
			}
		});
		toggleBloom.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				PolyPost.setBloomEnabled(!PolyPost.isBloomEnabled());
			}
		});

		// Toggle Motion Blur.
		GuiButtonText toggleMotionBlur = new GuiButtonText(this, new Vector2f(0.5f, 0.34f), "Motion Blur Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.addEvent(new EventChange<Boolean>(PolyPost::isMotionBlurEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleMotionBlur.setText("Motion Blur Enabled: " + newValue);
			}
		});
		toggleMotionBlur.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				PolyPost.setMotionBlurEnabled(!PolyPost.isMotionBlurEnabled());
			}
		});

		// Toggle Tilt Shift.
		GuiButtonText toggleTiltShift = new GuiButtonText(this, new Vector2f(0.5f, 0.41f), "Tilt Shift Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.addEvent(new EventChange<Boolean>(PolyPost::isTiltShiftEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleTiltShift.setText("Tilt Shift Enabled: " + newValue);
			}
		});
		toggleTiltShift.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				PolyPost.setTiltShiftEnabled(!PolyPost.isTiltShiftEnabled());
			}
		});

		// Toggle Lens Flare.
		GuiButtonText toggleLensFlare = new GuiButtonText(this, new Vector2f(0.5f, 0.48f), "Lens Flare Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.addEvent(new EventChange<Boolean>(PolyPost::isLensFlareEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleLensFlare.setText("Lens Flare Enabled: " + newValue);
			}
		});
		toggleLensFlare.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				PolyPost.setLensFlareEnabled(!PolyPost.isLensFlareEnabled());
			}
		});

		// Toggle Effect Grain.
		GuiButtonText toggleGrain = new GuiButtonText(this, new Vector2f(0.5f, 0.55f), "Grain Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.addEvent(new EventChange<Boolean>(PolyPost::isGrainEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleGrain.setText("Grain Enabled: " + newValue);
			}
		});
		toggleGrain.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				PolyPost.setGrainEnabled(!PolyPost.isGrainEnabled());
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
