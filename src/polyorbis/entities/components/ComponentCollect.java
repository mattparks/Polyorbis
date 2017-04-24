package polyorbis.entities.components;

import flounder.devices.*;
import flounder.entities.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.sounds.*;
import polyorbis.world.*;

import javax.swing.*;

public class ComponentCollect extends IComponentEntity implements IComponentEditor {
	private Sound playSound;
	private Collected collected;

	/**
	 * Creates a new ComponentCollect.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentCollect(Entity entity) {
		super(entity);
	}

	public ComponentCollect(Entity entity, Sound playSound, Collected collected) {
		super(entity);

		this.playSound = playSound;
		this.collected = collected;
	}

	@Override
	public void update() {
		// Do not update on paused.
		if (FlounderGuis.getGuiMaster() == null || FlounderGuis.getGuiMaster().isGamePaused()) {
			return;
		}

		if (PolyWorld.getEntityPlayer().getCollider() == null || getEntity().getCollider() == null) {
			return;
		}

		if (PolyWorld.getEntityPlayer().getCollider().intersects(getEntity().getCollider()).isIntersection()) {
			if (playSound != null) {
				FlounderSound.playSystemSound(playSound);
			}

			collected.action((ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class));
			getEntity().remove();
		}
	}

	@Override
	public void addToPanel(JPanel panel) {
	}

	@Override
	public void editorUpdate() {
	}

	@Override
	public Pair<String[], String[]> getSaveValues(String entityName) {
		return new Pair<>(
				new String[]{}, // Static variables
				new String[]{} // Class constructor
		);
	}

	@Override
	public void dispose() {
	}

	public static interface Collected {
		void action(ComponentPlayer pc);
	}
}
