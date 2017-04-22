package polyorbis.entities.components;

import flounder.entities.*;
import flounder.helpers.*;

import javax.swing.*;

public class ComponentProjectile extends IComponentEntity implements IComponentEditor {
	private float damage;

	/**
	 * Creates a new ComponentProjectile.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentProjectile(Entity entity) {
		super(entity);
	}

	public ComponentProjectile(Entity entity, float damage) {
		super(entity);

		this.damage = damage;
	}

	@Override
	public void update() {
	}

	public float getDamage() {
		return damage;
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
}
