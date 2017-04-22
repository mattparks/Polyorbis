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
	private static final float SPAWN_VARIATION = 15.0f;

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
		if (FlounderGuis.getGuiMaster() == null || FlounderGuis.getGuiMaster().isGamePaused()) {
			return;
		}

		spawned.removeIf(entity -> entity == null || entity.isRemoved());

		// After waiting for the player to make the first move...
		if (targetTime == -1.0f && ((ComponentPlayer) PolyWorld.getEntityPlayer().getComponent(ComponentPlayer.class)).getSurvivalTime() != 0.0f) {
			this.targetTime = Framework.getTimeSec() + Maths.randomInRange(3.0f, 5.0f);
		}

		if (targetTime != -1.0f && Framework.getTimeSec() - targetTime > 0.0f) {
			this.targetTime = Framework.getTimeSec() + Maths.randomInRange(3.0f, 10.0f); // TODO: Increase frequency with player XP.

			switch ((int) Maths.randomInRange(0.0f, 0.0f)) {
				case 0:
					// Spawns new health if there are currently less than 3 in the world from this spawn.
					int current = 0;

					for (Entity entity : spawned) {
						if (!entity.isRemoved() && entity instanceof InstanceHealth) {
							current++;
						}
					}

					if (current < 1) {
						Entity e = new InstanceHealth(FlounderEntities.getEntities(), new Vector3f(), new Vector3f());
						entityRotate(e, 0.0f);
						spawned.add(e);
					}

					break;
				// TODO: Spawn power refills, health, and enemies.
			}
		}
	}

	private void entityRotate(Entity entity, float addedHeight) {
		Vector3f rotationEntity = new Vector3f(rotation.x + (SPAWN_VARIATION * Maths.randomInRange(-1.0f, 1.0f)), rotation.y + (SPAWN_VARIATION * Maths.randomInRange(-1.0f, 1.0f)), rotation.z + (SPAWN_VARIATION * Maths.randomInRange(-1.0f, 1.0f)));
		entity.setPosition(Vector3f.rotate(new Vector3f(0.0f, radius + addedHeight, 0.0f), rotationEntity, null));
		entity.setRotation(new Vector3f(0.0f, rotationEntity.y, rotationEntity.z));
		entity.setMoved();
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
