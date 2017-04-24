package polyorbis.entities.components;

import flounder.entities.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import polyorbis.world.*;

import javax.swing.*;
import java.util.*;

public class ComponentProjectile extends IComponentEntity implements IComponentEditor {
	private Vector3f rotation;
	private float radius;

	private float damage;
	private Vector3f direction;
	private float deviation;
	private float randomness;
	private float timeout;

	private boolean playerSpawned;

	private float spentTime;

	/**
	 * Creates a new ComponentProjectile.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentProjectile(Entity entity) {
		super(entity);
	}

	public ComponentProjectile(Entity entity, Vector3f rotation, float radius, float damage, Vector3f direction, float deviation, float timeout, boolean playerSpawned) {
		super(entity);

		this.rotation = rotation;
		this.radius = radius;

		this.damage = damage;
		this.direction = direction.scale(Maths.randomInRange(0.8f, 1.2f));
		this.deviation = deviation;
		this.randomness = Maths.randomInRange(0.0f, 10.0f);
		this.timeout = timeout;

		this.playerSpawned = playerSpawned;

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
			getEntity().remove();
		}

		ComponentPlayer realPlayer = PolyWorld.getEntityPlayer() == null ? null : (ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class);

		for (Entity entity : new ArrayList<>(FlounderEntities.getEntities().getAll())) {
			if (entity != null) {
				ComponentEnemy enemy = (ComponentEnemy) entity.getComponent(ComponentEnemy.class);
				ComponentPlayer player = (ComponentPlayer) entity.getComponent(ComponentPlayer.class);

				if (entity.getCollider() != null && getEntity().getCollider() != null && getEntity().getCollider().intersects(entity.getCollider()).isIntersection()) {
					if (enemy != null && playerSpawned) {
						enemy.modifyHealth(damage);

						if (realPlayer != null) {
							realPlayer.addExperience(5);
						}

						getEntity().remove();
					} else if (player != null && !playerSpawned) {
						player.modifyHealth(damage + 0.45f);
						getEntity().remove();
					}
				}
			}
		}

		Vector3f right = new Vector3f(direction).scale(360.0f * Framework.getDelta());
		right.y -= deviation * Math.cos(Math.PI * randomness * Framework.getTimeSec());
		right.z += deviation * Math.sin(Math.PI * randomness * Framework.getTimeSec());
		Vector3f.add(rotation, right, rotation);
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
