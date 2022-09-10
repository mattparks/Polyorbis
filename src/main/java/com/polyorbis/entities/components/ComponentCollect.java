package com.polyorbis.entities.components;

import com.flounder.devices.*;
import com.flounder.entities.*;
import com.flounder.guis.*;
import com.flounder.helpers.*;
import com.flounder.sounds.*;

import javax.swing.*;

import com.polyorbis.world.*;

public class ComponentCollect extends IComponentEntity implements IComponentEditor {
	private Sound playSound;
	private Collected collected;

	/**
	 * Creates a new ComponentCollect.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentCollect(Entity entity) {
		super(entity);
	}

	public ComponentCollect(Entity entity, Sound playSound, Collected collected) {
		super(entity);

		this.playSound = playSound;
		this.collected = collected;
	}

	@Override
	public void update() {
		// Do not update on paused.
		if (FlounderGuis.get().getGuiMaster() == null || FlounderGuis.get().getGuiMaster().isGamePaused()) {
			return;
		}

		if (PolyWorld.get().getEntityPlayer().getCollider() == null || getEntity().getCollider() == null) {
			return;
		}

		if (PolyWorld.get().getEntityPlayer().getCollider().intersects(getEntity().getCollider()).isIntersection()) {
			if (playSound != null) {
				FlounderSound.get().playSystemSound(playSound);
			}

			collected.action((ComponentPlayer) PolyWorld.get().getEntityPlayer().getComponent(ComponentPlayer.class));
			getEntity().remove();
		}
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

	public interface Collected {
		void action(ComponentPlayer pc);
	}
}
