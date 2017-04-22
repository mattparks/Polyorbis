package polyorbis.entities.components;

import flounder.entities.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.maths.vectors.*;

import javax.swing.*;

public class ComponentProjectile extends IComponentEntity implements IComponentEditor {
	private Vector3f rotation;
	private float radius;

	private float damage;
	private Vector3f direction;
	private float timeout;

	private float spentTime;

	/**
	 * Creates a new ComponentProjectile.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentProjectile(Entity entity) {
		super(entity);
	}

	public ComponentProjectile(Entity entity, Vector3f rotation, float radius, float damage, Vector3f direction, float timeout) {
		super(entity);

		this.rotation = rotation;
		this.radius = radius;

		this.damage = damage;

		this.direction = direction;

		this.timeout = timeout;

		this.spentTime = 0.0f;
	}

	@Override
	public void update() {
		// Do not update on paused.
		if (FlounderGuis.getGuiMaster() == null || FlounderGuis.getGuiMaster().isGamePaused()) {
			return;
		}

		spentTime += Framework.getDelta();

		if (spentTime > timeout) {
			getEntity().forceRemove();
		}

		for (Entity entity : FlounderEntities.getEntities().getAll()) {
			if (entity != null) {
				ComponentEnemy enemy = (ComponentEnemy) entity.getComponent(ComponentEnemy.class);

				if (enemy != null && entity.getCollider() != null && getEntity().getCollider() != null && getEntity().getCollider().intersects(entity.getCollider()).isIntersection()) {
					getEntity().forceRemove();
					//	enemy.modifyHealth(damage); // TODO
				}
			}
		}

		Vector3f.add(rotation, new Vector3f(direction).scale(360.0f * Framework.getDelta()), rotation);
		Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, getEntity().getPosition());
		getEntity().setMoved();
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
