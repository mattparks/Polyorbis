package polyorbis.entities.components;

import flounder.entities.*;
import flounder.helpers.*;
import flounder.maths.vectors.*;
import flounder.shadows.*;

import javax.swing.*;

public class ComponentCelestial extends IComponentEntity implements IComponentEditor {
	private Vector3f startPosition;
	private Vector3f startRotation;

	/**
	 * Creates a new ComponentCelestial.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentCelestial(Entity entity) {
		super(entity);

		if (entity != null) {
			this.startPosition = new Vector3f(entity.getPosition());
			this.startRotation = new Vector3f(entity.getRotation());
		} else {
			this.startPosition = new Vector3f();
			this.startRotation = new Vector3f();
		}
	}

	@Handler.Function(Handler.FLAG_UPDATE_PRE)
	public void update() {
		Vector3f.multiply(FlounderShadows.getLightPosition(), startPosition, getEntity().getPosition());

		//	if (FlounderCamera.getCamera() != null) {
		//		Vector3f.add(getEntity().getPosition(), FlounderCamera.getCamera().getPosition(), getEntity().getPosition());
		//	}

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

	@Handler.Function(Handler.FLAG_DISPOSE)
	public void dispose() {
	}
}
