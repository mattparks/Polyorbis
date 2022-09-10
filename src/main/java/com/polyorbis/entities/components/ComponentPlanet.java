package com.polyorbis.entities.components;

import com.flounder.entities.*;
import com.flounder.guis.*;
import com.flounder.helpers.*;
import com.flounder.maths.vectors.*;

import javax.swing.*;

import com.polyorbis.entities.instances.*;
import com.polyorbis.world.*;

public class ComponentPlanet extends IComponentEntity implements IComponentEditor {
	private static final float SPAWN_HEIGHT = 0.1f;

	private Entity star;
	private Entity atmosphere;
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

	public ComponentPlanet(Entity entity, Entity star, Entity atmosphere, Entity[] moons, Vector3f[] spawnRotations) {
		super(entity);

		this.star = star;
		this.atmosphere = atmosphere;
		this.moons = moons;
		this.spawnRotations = spawnRotations;
		this.spawnEntities = null;
	}

	@Override
	public void update() {
		// Do not update on paused.
		if (FlounderGuis.get().getGuiMaster() == null || FlounderGuis.get().getGuiMaster().isGamePaused()) {
			return;
		}

		if (spawnEntities == null) {
			spawnEntities = new Entity[spawnRotations.length];

			for (int i = 0; i < spawnRotations.length; i++) {
				Entity e = new InstanceSpawn(FlounderEntities.get().getEntities(), spawnRotations[i], getEntity().getScale() + SPAWN_HEIGHT);
				spawnEntities[i] = e;
			}
		}

		// Reorder atmosphere to be the last rendered.
		if (atmosphere != null) {
			FlounderEntities.get().getEntities().remove(atmosphere);

			if (PolyWorld.get().hasAtmosphere()) {
				FlounderEntities.get().getEntities().add(atmosphere);
			}
		}
	}

	public Entity getStar() {
		return star;
	}

	public Entity getAtmosphere() {
		return atmosphere;
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
		if (star != null) {
			star.forceRemove();
		}

		if (atmosphere != null) {
			atmosphere.forceRemove();
		}

		if (moons != null) {
			for (Entity moon : moons) {
				moon.forceRemove();
			}
		}

		if (spawnEntities != null) {
			for (Entity spawn : spawnEntities) {
				spawn.forceRemove();
			}
		}
	}
}
