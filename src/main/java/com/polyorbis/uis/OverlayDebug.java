package com.polyorbis.uis;

import com.flounder.events.*;
import com.flounder.fonts.*;
import com.flounder.framework.*;
import com.flounder.guis.*;
import com.flounder.maths.*;
import com.flounder.maths.vectors.*;
import com.flounder.visual.*;

import java.util.Timer;

import com.polyorbis.entities.components.*;
import com.polyorbis.world.*;

import java.util.*;

public class OverlayDebug extends ScreenObject {
	private TextObject fpsText;
	private TextObject upsText;
	private TextObject rotationText;
	private boolean updateText;

	public OverlayDebug(ScreenObject parent) {
		super(parent, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		fpsText = createStatus("FPS: 0", 0.13f);
		upsText = createStatus("UPS: 0", 0.16f);
		rotationText = createStatus("ROTATION: [0, 0, 0]", 0.19f);

		FlounderEvents.get().addEvent(new EventTime(1.0f, true) {
			@Override
			public void onEvent() {
				updateText = true;
			}
		});
	}

	private TextObject createStatus(String content, float yPos) {
		TextObject text = new TextObject(this, new Vector2f(0.01f, 0.01f + yPos), content, 0.75f, FlounderFonts.CANDARA, 0.5f, GuiAlign.LEFT);
		text.setInScreenCoords(false);
		text.setColour(new Colour(1.0f, 1.0f, 1.0f));
		text.setBorderColour(new Colour(0.15f, 0.15f, 0.15f));
		text.setBorder(new ConstantDriver(0.04f));
		return text;
	}

	@Override
	public void updateObject() {
		if (updateText) {
			fpsText.setText("FPS: " + Maths.roundToPlace(1.0f / Framework.get().getDeltaRender(), 1));
			upsText.setText("UPS: " + Maths.roundToPlace(1.0f / Framework.get().getDelta(), 1));

			rotationText.setText("ROTATION: [" + (PolyWorld.get().getEntityPlayer() == null ? "NULL" : "0.0, " + Maths.roundToPlace(((ComponentPlayer) PolyWorld.get().getEntityPlayer().getComponent(ComponentPlayer.class)).getCurrentY(), 1) + ", " + Maths.roundToPlace(((ComponentPlayer) PolyWorld.get().getEntityPlayer().getComponent(ComponentPlayer.class)).getCurrentZ(), 1) + "]"));
			updateText = false;
		}
	}

	@Override
	public void deleteObject() {
	}
}