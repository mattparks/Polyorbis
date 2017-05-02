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

		TextObject textShare = new TextObject(this, new Vector2f(0.5f, 0.49f),
				"This game was developed by Mattparks & Decaxon for the Ludum Dare 38 Jam: A Small World. " +
						"Controls: Escape opens a pause menu with settings, F1 toggles this help menu, F2 takes a screenshot to a roaming/home folder, F3 toggles a debug overlay, and F4 toggles the HUD, F11 fullscreen. " +
						"The mouse is used to aim your attacks, and left click to fire the selected attack. WASD + Space to move. 1-2-3 cycles the selected attacks (highlighted in the bottom left corner in the HUD). " +
						"To zoom the camera use the scroll wheel, and to orientate hold right mouse and move the mouse to a new angle." +
						"Your goal is to pickup pink hearts for health and yellow boxes for ammunition, red enemies will spawn and try to kill you, touching them will damage you! " +
						"Your score will increase the longer you survive and with the amount of enemies you kill. " +
						"The first yellow attack is a inaccurate medium-damage common fast attack, the second blue attack is a accurate high-damage slow attack, and the third red attack is a explosion-shotgun style attack.",
				1.4f, FlounderFonts.SEGOE, 0.7f, GuiAlign.CENTRE);
		textShare.setInScreenCoords(true);
		textShare.setColour(new Colour(1.0f, 1.0f, 1.0f));

		// Restart.
		GuiButtonText exitToMenu = new GuiButtonText(this, new Vector2f(0.5f, 0.87f), "Start Game", GuiAlign.CENTRE);
		exitToMenu.addLeftListener(new ScreenListener() {
			@Override
			public void eventOccurred() {
				((PolyGuis) FlounderGuis.get().getGuiMaster()).toggleHelp();
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
