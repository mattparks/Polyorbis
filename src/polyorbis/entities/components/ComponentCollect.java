package polyorbis.entities.components;

import flounder.entities.*;
import flounder.helpers.*;
import polyorbis.world.*;

import javax.swing.*;

public class ComponentCollect extends IComponentEntity implements IComponentEditor {
	private Collected collected;

	/**
	 * Creates a new ComponentCollect.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentCollect(Entity entity) {
		super(entity);
	}

	public ComponentCollect(Entity entity, Collected collected) {
		super(entity);

		this.collected = collected;
	}

	@Override
	public void update() {
		if (PolyWorld.getEntityPlayer().getCollider() == null || getEntity().getCollider() == null) {
			return;
		}

		if (PolyWorld.getEntityPlayer().getCollider().intersects(getEntity().getCollider()).isIntersection()) {
			//	((ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class)).modifyHealth(health);
			collected.action((ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class));
			getEntity().forceRemove();
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
