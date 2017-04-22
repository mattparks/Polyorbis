package polyorbis.entities.components;

import flounder.entities.*;
import flounder.helpers.*;
import flounder.maths.vectors.*;
import polyorbis.entities.instances.*;

import javax.swing.*;

public class ComponentPlanet extends IComponentEntity implements IComponentEditor {
	private static final float SPAWN_HEIGHT = 0.1f;

	private Entity star;
	private Entity[] moons;
	private Vector3f[] spawnRotations;
	private Entity[] spawnEntities;

	/**
	 * Creates a new ComponentPlanet.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentPlanet(Entity entity) {
		super(entity);
	}

	public ComponentPlanet(Entity entity, Entity star, Entity[] moons, Vector3f[] spawnRotations) {
		super(entity);

		this.star = star;
		this.moons = moons;
		this.spawnRotations = spawnRotations;
		this.spawnEntities = null;
	}

	@Override
	public void update() {
		if (spawnEntities == null) {
			spawnEntities = new Entity[spawnRotations.length];

			for (int i = 0; i < spawnRotations.length; i++) {
				Entity e = new InstanceSpawn(FlounderEntities.getEntities(), new Vector3f(), new Vector3f());
				Vector3f.rotate(new Vector3f(0.0f, getEntity().getScale() + SPAWN_HEIGHT, 0.0f), spawnRotations[i], e.getPosition());
				e.getRotation().set(0.0f, spawnRotations[i].y, spawnRotations[i].z + 90.0f);
				e.setMoved();
				spawnEntities[i] = e;
			}
		}
	}

	public Entity getStar() {
		return star;
	}

	public Entity[] getMoons() {
		return moons;
	}

	public Vector3f[] getSpawnRotations() {
		return spawnRotations;
	}

	public Entity[] getSpawnEntities() {
		return spawnEntities;
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
		star.forceRemove();

		for (Entity moon : moons) {
			moon.forceRemove();
		}

		for (Entity spawn : spawnEntities) {
			spawn.forceRemove();
		}
	}
}
