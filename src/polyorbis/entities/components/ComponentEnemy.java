package polyorbis.entities.components;

import flounder.entities.*;
import flounder.helpers.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import polyorbis.world.*;

import javax.swing.*;

public class ComponentEnemy extends IComponentEntity implements IComponentEditor {
	private Vector3f rotation;
	private float radius;

	private float health;
	private boolean killed;

	/**
	 * Creates a new ComponentEnemy.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentEnemy(Entity entity) {
		super(entity);
	}

	public ComponentEnemy(Entity entity, Vector3f rotation, float radius, float health) {
		super(entity);

		this.rotation = rotation;
		this.radius = radius;

		this.health = health * Maths.randomInRange(0.6f, 1.0f);
		this.killed = false;
	}

	@Override
	public void update() {
		if (health <= 0.0f && !killed) {
			ComponentPlayer player = PolyWorld.getEntityPlayer() == null ? null : (ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class);

			getEntity().remove();
			killed = true;

			if (player != null) {
				player.addExperience(15);
			}
		}

		if (!killed) {
			// TODO: Do ai.
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
