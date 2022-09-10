package com.polyorbis.entities.components;

import com.flounder.devices.*;
import com.flounder.entities.*;
import com.flounder.framework.*;
import com.flounder.guis.*;
import com.flounder.helpers.*;
import com.flounder.maths.*;
import com.flounder.maths.vectors.*;
import com.flounder.resources.*;
import com.flounder.sounds.*;

import javax.swing.*;

import com.polyorbis.world.*;

public class ComponentProjectile extends IComponentEntity implements IComponentEditor {
	public final static Sound SOUND_SHOOT = Sound.loadSoundInBackground(new MyFile(FlounderSound.SOUND_FOLDER, "laserShoot.wav"), 1.0f, 1.0f);
	public final static Sound SOUND_DAMAGE = Sound.loadSoundInBackground(new MyFile(FlounderSound.SOUND_FOLDER, "laserDamage.wav"), 1.0f, 1.0f);

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
		if (FlounderGuis.get().getGuiMaster() == null || FlounderGuis.get().getGuiMaster().isGamePaused()) {
			return;
		}

		spentTime += Framework.get().getDelta();

		if (spentTime > timeout) {
			getEntity().remove();
		}

		ComponentPlayer realPlayer = PolyWorld.get().getEntityPlayer() == null ? null : (ComponentPlayer) PolyWorld.get().getEntityPlayer().getComponent(ComponentPlayer.class);

		for (Entity entity : FlounderEntities.get().getEntities().getAll(null)) {
			if (entity != null) {
				ComponentEnemy enemy = (ComponentEnemy) entity.getComponent(ComponentEnemy.class);
				ComponentPlayer player = (ComponentPlayer) entity.getComponent(ComponentPlayer.class);

				if (entity.getCollider() != null && getEntity().getCollider() != null && getEntity().getCollider().intersects(entity.getCollider()).isIntersection()) {
					if (enemy != null && playerSpawned) {
						enemy.modifyHealth(damage);

						if (realPlayer != null) {
							realPlayer.addKill();
							realPlayer.addExperience(5);
						}

						getEntity().remove();
						FlounderSound.get().playSystemSound(SOUND_DAMAGE);
					} else if (player != null && !playerSpawned) {
						player.modifyHealth(damage + 0.6f);
						getEntity().remove();
						FlounderSound.get().playSystemSound(SOUND_DAMAGE);
					}
				}
			}
		}

		Vector3f right = new Vector3f(direction).scale(360.0f * Framework.get().getDelta());
		right.y -= deviation * 100.0f * Math.cos(Math.PI * randomness * Framework.get().getTimeSec()) * Framework.get().getDelta();
		right.z += deviation * 100.0f * Math.sin(Math.PI * randomness * Framework.get().getTimeSec()) * Framework.get().getDelta();

		if (rotation.z <= 20.0f || rotation.z >= 160.0f) {
			getEntity().remove();
		}

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
