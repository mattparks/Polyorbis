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
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isEffectsEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleEffects.setText("Post Enabled: " + newValue);
			//	toggleEffects.setProgress(newValue);
			}
		});
		toggleEffects.addLeftListener(() -> PolyPost.get().setEffectsEnabled(!PolyPost.get().isEffectsEnabled()));

		// Toggle Bloom.
		GuiButtonText toggleBloom = new GuiButtonText(this, new Vector2f(0.5f, 0.27f), "Bloom Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isBloomEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleBloom.setText("Bloom Enabled: " + newValue);
			//	toggleBloom.setProgress(newValue);
			}
		});
		toggleBloom.addLeftListener(() -> PolyPost.get().setBloomEnabled(!PolyPost.get().isBloomEnabled()));

		// Toggle Motion Blur.
		GuiButtonText toggleMotionBlur = new GuiButtonText(this, new Vector2f(0.5f, 0.34f), "Motion Blur Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isMotionBlurEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleMotionBlur.setText("Motion Blur Enabled: " + newValue);
			//	toggleMotionBlur.setProgress(newValue);
			}
		});
		toggleMotionBlur.addLeftListener(() -> PolyPost.get().setMotionBlurEnabled(!PolyPost.get().isMotionBlurEnabled()));

		// Toggle Tilt Shift.
		GuiButtonText toggleTiltShift = new GuiButtonText(this, new Vector2f(0.5f, 0.41f), "Tilt Shift Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isTiltShiftEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleTiltShift.setText("Tilt Shift Enabled: " + newValue);
			//	toggleTiltShift.setProgress(newValue);
			}
		});
		toggleTiltShift.addLeftListener(() -> PolyPost.get().setTiltShiftEnabled(!PolyPost.get().isTiltShiftEnabled()));

		// Toggle Lens Flare.
		GuiButtonText toggleLensFlare = new GuiButtonText(this, new Vector2f(0.5f, 0.48f), "Lens Flare Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isLensFlareEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleLensFlare.setText("Lens Flare Enabled: " + newValue);
			//	toggleLensFlare.setProgress(newValue);
			}
		});
		toggleLensFlare.addLeftListener(() -> PolyPost.get().setLensFlareEnabled(!PolyPost.get().isLensFlareEnabled()));

		// Toggle Effect Grain.
		GuiButtonText toggleGrain = new GuiButtonText(this, new Vector2f(0.5f, 0.55f), "Grain Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isGrainEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleGrain.setText("Grain Enabled: " + newValue);
			//	toggleGrain.setProgress(newValue);
			}
		});
		toggleGrain.addLeftListener(() -> PolyPost.get().setGrainEnabled(!PolyPost.get().isGrainEnabled()));

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
