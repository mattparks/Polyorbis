package polyorbis.uis;

import flounder.fonts.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.visual.*;
import polyorbis.entities.components.*;
import polyorbis.world.*;

import java.util.Timer;
import java.util.*;

public class OverlayDebug extends ScreenObject {
	private TextObject fpsText;
	private TextObject upsText;
	private TextObject rotationText;
	private boolean updateText;

	public OverlayDebug(ScreenObject parent) {
		super(parent, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		fpsText = createStatus("FPS: 0", 0.01f);
		upsText = createStatus("UPS: 0", 0.04f);
		rotationText = createStatus("ROTATION: [0, 0, 0]", 0.07f);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				updateText = true;
			}
		}, 0, 100);
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
			fpsText.setText("FPS: " + Maths.roundToPlace(1.0f / Framework.getDeltaRender(), 1));
			upsText.setText("UPS: " + Maths.roundToPlace(1.0f / Framework.getDelta(), 1));

			rotationText.setText("ROTATION: [" + (PolyWorld.getEntityPlayer() == null ? "NULL" : "0.0, " + Maths.roundToPlace(((ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class)).getCurrentY(), 1) + ", " + Maths.roundToPlace(((ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class)).getCurrentZ(), 1) + "]"));
			updateText = false;
		}
	}

	@Override
	public void deleteObject() {
	}
}