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
	private static final float SPEED = 14.0f;

	private Vector3f rotation;
	private float radius;

	private Vector3f direction;

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

		this.health = health * Maths.randomInRange(0.6f, 1.0f);
		this.killed = false;

		this.shootTime = 0.0f;
	}

	@Override
	public void update() {
		// Do not update on paused.
		if (FlounderGuis.getGuiMaster() == null || FlounderGuis.getGuiMaster().isGamePaused()) {
			return;
		}

		if (health <= 0.0f && !killed) {
			ComponentPlayer player = PolyWorld.getEntityPlayer() == null ? null : (ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class);

			getEntity().remove();
			killed = true;

			if (player != null) {
				player.addExperience(15);
			}
		}

		if (!killed) {
			shootTime += Framework.getDelta();

			if (shootTime > 2.0f) {
				if (PolyWorld.getEntityPlayer() != null) {
					Vector2f playerRotation = ((ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class)).getRotation();

					float distance = Vector3f.getDistance(PolyWorld.getEntityPlayer().getPosition(), getEntity().getPosition());

					if (distance < 3.33f) {
						if (playerRotation == null) {
							return;
						}

						Vector2f thisRotation = new Vector2f(rotation.y, rotation.z); // TODO: Aim in the right direction.
						Vector2f direction = Vector2f.subtract(thisRotation, playerRotation, null);

						if (direction.isZero()) {
							direction.x += 0.03f;
						}

						// direction.scale(Maths.randomInRange(-Maths.randomInRange(1.0f, 2.0f), +Maths.randomInRange(1.0f, 2.0f))); // TODO: Decrease error over time.
						direction.normalize();
						PolyWorld.fireProjectile(new Vector3f(rotation), radius, (int) (3.0f - (3.0f * Math.pow(1.0f / Maths.randomInRange(1.0f, 3.0f), 2))), direction, false); // TODO: Fix selected attacks.
					}

					shootTime = 0.0f;
				}
			}

			// The wacky rotation effect on run.
			float as = Math.min(Math.abs(direction.y) + Math.abs(direction.z), 1.0f);
			float rx = as * 15.0f * (float) (Math.sin(0.25 * 15.0f * Framework.getTimeSec()) - Math.sin(1.2 * 15.0f * Framework.getTimeSec()) + Math.cos(0.5 * 15.0f * Framework.getTimeSec()));
			float rz = as * 15.0f * (float) (Math.cos(0.25 * 15.0f * Framework.getTimeSec()) - Math.cos(1.2 * 15.0f * Framework.getTimeSec()) + Math.sin(0.5 * 15.0f * Framework.getTimeSec()));

			// Moves and rotates the player.
			Vector3f right = new Vector3f(direction).scale(SPEED * Framework.getDelta());
			Vector3f.add(rotation, right, rotation);
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
