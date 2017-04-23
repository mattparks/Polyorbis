package polyorbis.entities.components;

import flounder.entities.*;
import flounder.framework.*;
import flounder.helpers.*;
import flounder.logger.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import polyorbis.world.*;

import javax.swing.*;
import java.util.*;

public class ComponentEnemy extends IComponentEntity implements IComponentEditor {
	private static final float SPEED = 0.8f;

	private Vector3f rotation;
	private float radius;

	private float deviation;
	private float randomness;

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

		this.deviation = 0.2f;
		this.randomness = Maths.randomInRange(0.0f, 10.0f);

		this.health = health * Maths.randomInRange(0.6f, 1.0f);
		this.killed = false;

	/*	java.util.Timer timer = new java.util.Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!killed) {
					Vector2f playerRotation = PolyWorld.getEntityPlayer() == null ? null : ((ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class)).getRotation();
					if (playerRotation == null) {
						return;
					}
					Vector2f thisRotation = new Vector2f(rotation.y, rotation.z);
					Vector2f direction = Vector2f.subtract(thisRotation, playerRotation, null);
				//	FlounderLogger.log(direction.lengthSquared());
					if (direction.lengthSquared() / Math.pow(360.0, 2.0) > 0.01f) {
						return;
					}
					if (direction.isZero()) {
						direction.x += 0.03f;
					}
					direction.scale(1.0f / 360.0f);
					direction.normalize();
					PolyWorld.fireProjectile(new Vector3f(rotation), radius, 1, direction, false);
				} else {
					this.cancel();
				}
			}
		}, 0, 2000);*/
	}

	@Override
	public void update() {
		if (health <= 0.0f && !killed) {
			ComponentPlayer player = PolyWorld.getEntityPlayer() == null ? null : (ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class);

			getEntity().remove();
			killed = true;

		//	PolyWorld.fireProjectile(new Vector3f(rotation), radius, 3, new Vector2f(0.03f, 0.0f), false);

			if (player != null) {
				player.addExperience(15);
			}
		}

		if (!killed) {
			// TODO: Do ai.
			Vector3f right = new Vector3f(0.0f, 1.0f, 1.0f).scale(SPEED * Framework.getDelta());
			right.y -= deviation * Math.sin(Math.PI * randomness * Framework.getTimeSec());
			right.z += deviation * Math.sin(Math.PI * randomness * Framework.getTimeSec());
			Vector3f.add(rotation, right, rotation);
			Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, getEntity().getPosition());
			getEntity().setMoved();
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
