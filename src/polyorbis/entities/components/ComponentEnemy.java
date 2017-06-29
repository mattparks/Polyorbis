package polyorbis.entities.components;

import flounder.entities.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import polyorbis.world.*;

import javax.swing.*;

public class ComponentEnemy extends IComponentEntity implements IComponentEditor {
	private static final float SPEED = 12.0f;

	private Vector3f rotation;
	private float radius;

	private Vector3f direction;

	private boolean reverse;
	private float reverseTime;

	private float health;
	private boolean killed;

	private float shootTime;

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

		this.direction = new Vector3f(0.0f, Maths.randomInRange(-1.0f, 1.0f), Maths.randomInRange(-1.0f, 1.0f)).normalize();

		this.reverse = false;
		this.reverseTime = 0.0f;

		this.health = health * Maths.randomInRange(0.6f, 1.0f);
		this.killed = false;

		this.shootTime = 0.0f;
	}

	@Override
	public void update() {
		// Do not update on paused.
		if (FlounderGuis.get().getGuiMaster() == null || FlounderGuis.get().getGuiMaster().isGamePaused()) {
			return;
		}

		float delta = Math.min(1.0f / 60.0f, Framework.get().getDelta());

		if (health <= 0.0f && !killed) {
			ComponentPlayer player = PolyWorld.get().getEntityPlayer() == null ? null : (ComponentPlayer) PolyWorld.get().getEntityPlayer().getComponent(ComponentPlayer.class);

			getEntity().remove();
			killed = true;

			if (player != null) {
				player.addExperience(15);
			}
		}

		if (!killed) {
			shootTime += Framework.get().getDelta();

			if (shootTime > 3.2f) {
				if (PolyWorld.get().getEntityPlayer() != null) {
					Vector2f playerRotation = ((ComponentPlayer) PolyWorld.get().getEntityPlayer().getComponent(ComponentPlayer.class)).getRotation();
					playerRotation.x = Maths.normalizeAngle(playerRotation.x);
					playerRotation.y = Maths.normalizeAngle(playerRotation.y);

					float distance = Vector3f.getDistance(PolyWorld.get().getEntityPlayer().getPosition(), getEntity().getPosition());

					if (distance < 4.0f) {
						Vector2f thisRotation = new Vector2f(rotation.y, rotation.z);
						thisRotation.x = Maths.normalizeAngle(thisRotation.x);
						thisRotation.y = Maths.normalizeAngle(thisRotation.y);
						Vector2f direction = Vector2f.subtract(playerRotation, thisRotation, null);

						if (direction.isZero()) {
							direction.x += 0.03f;
						}

						direction.scale(Maths.randomInRange(1.0f, 20000.0f / Math.min(PolyWorld.get().calculateScore(PolyWorld.get().getEntityPlayer()), 3000)));
						direction.normalize();
						direction.scale(0.5f); // 1/2 normal speed.

						float random = (float) Math.random();
						int projectile = 1;

						if (random < 0.05f) {
							projectile = 3;
						} else if (random < 0.3f) {
							projectile = 2;
						}

						PolyWorld.get().fireProjectile(new Vector3f(rotation), radius, projectile, direction, false);
					}

					shootTime = 0.0f;
				}
			}

			// The wacky rotation effect on run.
			float as = Math.min(Math.abs(direction.y) + Math.abs(direction.z), 1.0f);
			float rx = as * 15.0f * (float) (Math.sin(0.25 * 15.0f * Framework.get().getTimeSec()) - Math.sin(1.2 * 15.0f * Framework.get().getTimeSec()) + Math.cos(0.5 * 15.0f * Framework.get().getTimeSec()));
			float rz = as * 15.0f * (float) (Math.cos(0.25 * 15.0f * Framework.get().getTimeSec()) - Math.cos(1.2 * 15.0f * Framework.get().getTimeSec()) + Math.sin(0.5 * 15.0f * Framework.get().getTimeSec()));

			// Moves and rotates the player.
			Vector3f right = new Vector3f(direction).scale((reverse ? -1.0f : 1.0f) * SPEED * delta);
			Vector3f.add(rotation, right, rotation);

			reverseTime += Framework.get().getDelta();

			if (rotation.z <= 20.0f || rotation.z >= 160.0f) {
				if (reverseTime > 3.0f) {
					reverseTime = 0.0f;
					reverse = !reverse;
				}
			}

			// Normalizes angles and limits Y rotation to make movement easier.
			rotation.y = Maths.normalizeAngle(rotation.y);
			rotation.z = Maths.clamp(rotation.z, 20.0f, 160.0f);

			Vector3f.rotate(new Vector3f(0.0f, radius, 0.0f), rotation, getEntity().getPosition());
			getEntity().getRotation().set(rx, rotation.y, rotation.z + rz);
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
