package polyorbis.uis;

import flounder.fonts.*;
import flounder.guis.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.visual.*;
import polyorbis.*;
import polyorbis.world.*;

public class OverlayHelp extends ScreenObject {
	private PlayData lastData;

	public OverlayHelp(ScreenObject parent) {
		super(parent, new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f));
		super.setInScreenCoords(false);

		// Game Title.
		TextObject title = new TextObject(this, new Vector2f(0.5f, 0.10f), "Welcome to Polyorbis!", 4.0f, FlounderFonts.CANDARA, 1.0f, GuiAlign.CENTRE);
		title.setInScreenCoords(true);
		title.setColour(new Colour(1.0f, 1.0f, 1.0f, 1.0f));
		title.setBorderColour(new Colour(0.0f, 0.0f, 0.0f));
		title.setBorder(new ConstantDriver(0.022f));

		TextObject textShare = new TextObject(this, new Vector2f(0.5f, 0.22f), "Share your high scores on our ldjam page along with a review. Thank you for playing Polyorbis!", 1.4f, FlounderFonts.SEGOE, 0.5f, GuiAlign.CENTRE); // TODO: Expand!
		textShare.setInScreenCoords(true);
		textShare.setColour(new Colour(1.0f, 1.0f, 1.0f));

		// Restart.
		GuiButtonText exitToMenu = new GuiButtonText(this, new Vector2f(0.5f, 0.60f), "Start Game", GuiAlign.CENTRE);
		exitToMenu.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				((PolyGuis) FlounderGuis.getGuiMaster()).toggleHelp();
			}
		});

		this.lastData = null;
	}

	@Override
	public void updateObject() {
	}

	@Override
	public void deleteObject() {

	}
}
