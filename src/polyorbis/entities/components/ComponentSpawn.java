package polyorbis.entities.components;

import flounder.entities.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import polyorbis.entities.instances.*;
import polyorbis.world.*;

import javax.swing.*;
import java.util.*;

public class ComponentSpawn extends IComponentEntity implements IComponentEditor {
	private static final float SPAWN_VARIATION = 10.0f;

	private Vector3f rotation;
	private float radius;

	private List<Entity> spawned;
	private float targetTime;

	/**
	 * Creates a new ComponentSpawn.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentSpawn(Entity entity) {
		super(entity);
	}

	public ComponentSpawn(Entity entity, Vector3f rotation, float radius) {
		super(entity);

		this.rotation = rotation;
		this.radius = radius;

		this.spawned = new ArrayList<>();
		this.targetTime = -1;
	}

	@Override
	public void update() {
		// Do not update on paused.
		if (FlounderGuis.get().getGuiMaster() == null || FlounderGuis.get().getGuiMaster().isGamePaused()) {
			return;
		}

		spawned.removeIf(entity -> entity == null || entity.isRemoved());

		// After waiting for the player to make the first move...
		if (targetTime == -1.0f && ((ComponentPlayer) PolyWorld.get().getEntityPlayer().getComponent(ComponentPlayer.class)).getSurvivalTime() != 0.0f) {
			this.targetTime = Framework.get().getTimeSec() + Maths.randomInRange(2.0f, 4.0f);
		}

		if (targetTime != -1.0f && Framework.get().getTimeSec() - targetTime > 0.0f) {
			int current = 0;

			int maxAllowed = 1;

			this.targetTime = Framework.get().getTimeSec() + Maths.randomInRange(3.0f, 10.0f / (float) maxAllowed);

			switch ((int) Maths.randomInRange(0.0f, 3.0f)) {
				case 0:
					// Spawns new health if there are currently less than 1 in the world from this spawn.
					for (Entity entity : spawned) {
						if (!entity.isRemoved() && entity instanceof InstanceHealth) {
							current++;
						}
					}

					if (PolyWorld.get().calculateScore(PolyWorld.get().getEntityPlayer()) > 3800) {
						maxAllowed = 3;
					} else if (PolyWorld.get().calculateScore(PolyWorld.get().getEntityPlayer()) > 900) {
						maxAllowed = 2;
					}

					if (current < maxAllowed) {
						Entity e = new InstanceHealth(FlounderEntities.get().getEntities(), randomEntityRotation(), radius);
						spawned.add(e);
					}

					break;
				case 1:
					// Spawns new ammo if there are currently less than 1 in the world from this spawn.
					for (Entity entity : spawned) {
						if (!entity.isRemoved() && entity instanceof InstanceAmmo) {
							current++;
						}
					}

					if (current < 1) {
						Entity e = new InstanceAmmo(FlounderEntities.get().getEntities(), randomEntityRotation(), radius);
						spawned.add(e);
					}

					break;
				case 2:
					// Spawns a new enemy if there are currently less than 2 in the world from this spawn.
					for (Entity entity : spawned) {
						if (!entity.isRemoved() && entity instanceof InstanceEnemy) {
							current++;
						}
					}

					if (PolyWorld.get().calculateScore(PolyWorld.get().getEntityPlayer()) > 3800) {
						maxAllowed = 5;
					} else if (PolyWorld.get().calculateScore(PolyWorld.get().getEntityPlayer()) > 1800) {
						maxAllowed = 4;
					} else if (PolyWorld.get().calculateScore(PolyWorld.get().getEntityPlayer()) > 900) {
						maxAllowed = 3;
					} else if (PolyWorld.get().calculateScore(PolyWorld.get().getEntityPlayer()) > 450) {
						maxAllowed = 2;
					}

					if (current < maxAllowed) {
						Entity e = new InstanceEnemy(FlounderEntities.get().getEntities(), randomEntityRotation(), radius);

						if (PolyWorld.get().getEntityPlayer().getCollider() == null || e.getCollider() == null) {
							return;
						}

						if (PolyWorld.get().getEntityPlayer().getCollider().intersects(e.getCollider()).isIntersection()) {
							e.forceRemove();
						} else {
							spawned.add(e);
						}
					}

					break;
			}
		}
	}

	private Vector3f randomEntityRotation() {
		return new Vector3f(rotation.x + (SPAWN_VARIATION * Maths.randomInRange(-1.0f, 1.0f)), rotation.y + (SPAWN_VARIATION * Maths.randomInRange(-1.0f, 1.0f)), rotation.z + (SPAWN_VARIATION * Maths.randomInRange(-1.0f, 1.0f)));
	}

	@Override
	public void dispose() {
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
}
