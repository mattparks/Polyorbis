package polyorbis.entities.components;

import flounder.entities.*;
import flounder.helpers.*;
import flounder.maths.*;

import javax.swing.*;

public class ComponentEnemy extends IComponentEntity implements IComponentEditor {
	private float health;

	/**
	 * Creates a new ComponentEnemy.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentEnemy(Entity entity) {
		super(entity);
	}

	public ComponentEnemy(Entity entity, float health) {
		super(entity);

		this.health = health;
	}

	@Override
	public void update() {
		if (health <= 0.0f) {
			getEntity().forceRemove();
		}
	}

	public float getHealth() {
		return health;
	}

	public void modifyHealth(float change) {
		this.health += change;
		this.health = Maths.clamp(this.health, 0.0f, 1.0f);
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
