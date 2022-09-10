package com.polyorbis.uis.screens.settings;

import com.polyorbis.post.*;
import com.polyorbis.uis.*;
import com.polyorbis.uis.screens.*;

import com.flounder.events.*;
import com.flounder.guis.*;
import com.flounder.maths.vectors.*;

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
			//	toggleEffects.setValue(newValue);
			}
		});
		toggleEffects.addLeftListener(() -> PolyPost.get().setEffectsEnabled(!PolyPost.get().isEffectsEnabled()));

		// Toggle Bloom.
		GuiButtonText toggleBloom = new GuiButtonText(this, new Vector2f(0.5f, 0.27f), "Bloom Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isBloomEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleBloom.setText("Bloom Enabled: " + newValue);
			//	toggleBloom.setValue(newValue);
			}
		});
		toggleBloom.addLeftListener(() -> PolyPost.get().setBloomEnabled(!PolyPost.get().isBloomEnabled()));

		// Toggle Motion Blur.
		GuiButtonText toggleMotionBlur = new GuiButtonText(this, new Vector2f(0.5f, 0.34f), "Motion Blur Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isMotionBlurEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleMotionBlur.setText("Motion Blur Enabled: " + newValue);
			//	toggleMotionBlur.setValue(newValue);
			}
		});
		toggleMotionBlur.addLeftListener(() -> PolyPost.get().setMotionBlurEnabled(!PolyPost.get().isMotionBlurEnabled()));

		// Toggle Tilt Shift.
		GuiButtonText toggleTiltShift = new GuiButtonText(this, new Vector2f(0.5f, 0.41f), "Tilt Shift Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isTiltShiftEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleTiltShift.setText("Tilt Shift Enabled: " + newValue);
			//	toggleTiltShift.setValue(newValue);
			}
		});
		toggleTiltShift.addLeftListener(() -> PolyPost.get().setTiltShiftEnabled(!PolyPost.get().isTiltShiftEnabled()));

		// Toggle Lens Flare.
		GuiButtonText toggleLensFlare = new GuiButtonText(this, new Vector2f(0.5f, 0.48f), "Lens Flare Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isLensFlareEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleLensFlare.setText("Lens Flare Enabled: " + newValue);
			//	toggleLensFlare.setValue(newValue);
			}
		});
		toggleLensFlare.addLeftListener(() -> PolyPost.get().setLensFlareEnabled(!PolyPost.get().isLensFlareEnabled()));

		// Toggle Effect Grain.
		GuiButtonText toggleGrain = new GuiButtonText(this, new Vector2f(0.5f, 0.55f), "Grain Enabled: ", GuiAlign.CENTRE);
		FlounderEvents.get().addEvent(new EventChange<Boolean>(PolyPost.get()::isGrainEnabled) {
			@Override
			public void onEvent(Boolean newValue) {
				toggleGrain.setText("Grain Enabled: " + newValue);
			//	toggleGrain.setValue(newValue);
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
