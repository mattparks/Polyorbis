package polyorbis.entities.components;

import flounder.entities.*;
import flounder.helpers.*;

import javax.swing.*;

public class ComponentSpawn extends IComponentEntity implements IComponentEditor {
	/**
	 * Creates a new ComponentSpawn.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentSpawn(Entity entity) {
		super(entity);
	}

	@Override
	public void update() {
		// TODO: Spawn power refills, health, and enemies.
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
