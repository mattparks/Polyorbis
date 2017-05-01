package polyorbis.entities.components;

import flounder.entities.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.maths.vectors.*;

import javax.swing.*;

public class ComponentRotate extends IComponentEntity implements IComponentEditor {
	private Vector3f rotation;
	private float radius;

	private Vector3f axis;
	private float speed;

	/**
	 * Creates a new ComponentRotate.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentRotate(Entity entity) {
		super(entity);
	}

	public ComponentRotate(Entity entity, Vector3f rotation, float radius, Vector3f axis, float speed) {
		super(entity);

		this.rotation = rotation;
		this.radius = radius;

		this.axis = axis;
		this.speed = speed;
	}

	@Override
	public void update() {
		// Do not update on paused.
		if (FlounderGuis.get().getGuiMaster() == null || FlounderGuis.get().getGuiMaster().isGamePaused()) {
			return;
		}

		Vector3f.add(getEntity().getRotation(), new Vector3f(axis).scale(speed * 360.0f * Framework.getDelta()), getEntity().getRotation());
		getEntity().setMoved();
	}

	public float getSpeed() {
		return speed;
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
