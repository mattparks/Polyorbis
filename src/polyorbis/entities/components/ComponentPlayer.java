package polyorbis.entities.components;

import flounder.devices.*;
import flounder.entities.*;
import flounder.framework.*;
import flounder.guis.*;
import flounder.helpers.*;
import flounder.inputs.*;
import flounder.maths.*;
import flounder.maths.vectors.*;
import flounder.resources.*;
import flounder.sounds.*;
import polyorbis.world.*;

import javax.swing.*;

import static org.lwjgl.glfw.GLFW.*;

public class ComponentPlayer extends IComponentEntity implements IComponentEditor {
	public final static Sound SOUND_GAMEOVER = Sound.loadSoundInBackground(new MyFile(FlounderSound.SOUND_FOLDER, "gameover.wav"), 1.0f, 1.0f);
	public final static Sound SOUND_JUMP = Sound.loadSoundInBackground(new MyFile(FlounderSound.SOUND_FOLDER, "jump.wav"), 1.0f, 1.0f);

	private static final float PLAYER_HEIGHT = 0.22f;
	private static final float PLAYER_ACCELERATION = 10.0f;
	private static final float PLAYER_SPEED = 1.2f;
	private static final float PLAYER_JUMP = 0.06f;
	private static final float PLAYER_GRAVITY = -0.3f;

	private float currentY;
	private float currentZ;
	private float currentRadius;

	private float currentSpeedY;
	private float currentSpeedZ;
	private float currentSpeedUp;

	private int experience;
	private float survivalTime;
	private int kills;
	private float health;
	private boolean dead;

	private float charge1;
	private float charge2;
	private float charge3;
	private int selectedCharge;

	private IButton inputSelectCycle;
	private KeyButton inputSelect1;
	private KeyButton inputSelect2;
	private KeyButton inputSelect3;
	private IAxis inputY;
	private IAxis inputZ;
	private IButton inputJump;
	private IButton inputFire;

	/**
	 * Creates a new ComponentPlayer.
	 *
	 * @param entity The entity this component is attached to.
	 */
	public ComponentPlayer(Entity entity) {
		super(entity);

		IButton leftKeyButtons = new KeyButton(GLFW_KEY_A, GLFW_KEY_LEFT);
		IButton rightKeyButtons = new KeyButton(GLFW_KEY_D, GLFW_KEY_RIGHT);
		IButton upKeyButtons = new KeyButton(GLFW_KEY_W, GLFW_KEY_UP);
		IButton downKeyButtons = new KeyButton(GLFW_KEY_S, GLFW_KEY_DOWN);
		IButton jumpButtons = new KeyButton(GLFW_KEY_SPACE);

		this.currentY = 0.0f;
		this.currentZ = 0.0f;

		this.currentRadius = PLAYER_HEIGHT;
		this.currentSpeedY = 0.0f;
		this.currentSpeedZ = 0.0f;
		this.currentSpeedUp = 0.0f;

		this.experience = 0;
		this.survivalTime = 0.0f;
		this.kills = 0;
		this.health = 1.0f;
		this.dead = false;

		this.charge1 = 1.0f;
		this.charge2 = 0.5f;
		this.charge3 = 0.2f;
		this.selectedCharge = 1;

		this.inputSelectCycle = new CompoundButton(new KeyButton(GLFW_KEY_Q), new KeyButton(GLFW_KEY_E), new JoystickButton(0, 1));
		this.inputSelect1 = new KeyButton(GLFW_KEY_1);
		this.inputSelect2 = new KeyButton(GLFW_KEY_2);
		this.inputSelect3 = new KeyButton(GLFW_KEY_3);
		this.inputY = new CompoundAxis(new ButtonAxis(leftKeyButtons, rightKeyButtons), new JoystickAxis(0, 0));
		this.inputZ = new CompoundAxis(new ButtonAxis(downKeyButtons, upKeyButtons), new JoystickAxis(0, 1));
		this.inputJump = new CompoundButton(jumpButtons, new JoystickButton(0, 0));
		this.inputFire = new CompoundButton(new MouseButton(GLFW_MOUSE_BUTTON_LEFT), new JoystickButton(0, 5));
	}

	@Override
	public void update() {
		// Do not update on paused.
		if (FlounderGuis.getGuiMaster() == null || FlounderGuis.getGuiMaster().isGamePaused()) {
			return;
		}

		// Kill the player!
		if (health <= 0.0f && !dead) {
			FlounderSound.playSystemSound(SOUND_GAMEOVER);
			PolyWorld.fireProjectile(new Vector3f(0.0f, currentY, currentZ), currentRadius, 3, new Vector2f(0.03f, 0.0f), true);
			getEntity().remove();
			new java.util.Timer().schedule(
					new java.util.TimerTask() {
						@Override
						public void run() {
							PolyWorld.setEndGameData(new PlayData(experience, survivalTime, kills));
						}
					},
					3000
			);
			dead = true;
			return;
		}

		// Calculate speeds.
		float planetRadius = PolyWorld.getEntityPlanet() == null ? 0.0f : PolyWorld.getEntityPlanet().getScale();

		if (inputY.getAmount() == 0.0f) {
			currentSpeedY += (currentSpeedY <= 0.0f ? 1.0f : -1.0f) * Math.abs(currentSpeedY) * PLAYER_ACCELERATION * Framework.getDelta();
		} else {
			currentSpeedY += inputY.getAmount() * PLAYER_SPEED * PLAYER_ACCELERATION * Framework.getDelta();
			currentSpeedY = Maths.clamp(currentSpeedY, -PLAYER_SPEED, PLAYER_SPEED);
		}

		if (inputZ.getAmount() == 0.0f) {
			currentSpeedZ += (currentSpeedZ <= 0.0f ? 1.0f : -1.0f) * Math.abs(currentSpeedZ) * PLAYER_ACCELERATION * Framework.getDelta();
		} else {
			currentSpeedZ += -inputZ.getAmount() * PLAYER_SPEED * PLAYER_ACCELERATION * Framework.getDelta();
			currentSpeedZ = Maths.clamp(currentSpeedZ, -PLAYER_SPEED, PLAYER_SPEED);
		}

		// Move player current positions.
		currentY += currentSpeedY * 100.0f * Framework.getDelta();
		currentZ += currentSpeedZ * 100.0f * Framework.getDelta();

		if (inputJump.isDown() && currentSpeedUp == 0.0f && currentRadius <= planetRadius + PLAYER_HEIGHT) {
			FlounderSound.playSystemSound(SOUND_JUMP);
			currentSpeedUp = PLAYER_JUMP;
		}

		currentSpeedUp += PLAYER_GRAVITY * Framework.getDelta();
		currentRadius += currentSpeedUp * 100.0f * Framework.getDelta();

		// Collision with the planet.
		if (currentRadius < planetRadius + PLAYER_HEIGHT) {
			currentRadius = planetRadius + PLAYER_HEIGHT;
			currentSpeedUp = 0.0f;
		}

		// Updates attacks, health, experience and survival time.
		if (survivalTime == 0.0f) {
			if (currentSpeedY != 0.0f || currentSpeedZ != 0.0f || currentSpeedUp != 0.0f) {
				survivalTime += Framework.getDelta();
			}
		} else {
			survivalTime += Framework.getDelta();
		}

		if (inputSelect1.wasDown()) {
			selectedCharge = 1;
		} else if (inputSelect2.wasDown()) {
			selectedCharge = 2;
		} else if (inputSelect3.wasDown()) {
			selectedCharge = 3;
		} else if (inputSelectCycle.wasDown()) {
			selectedCharge++;

			if (selectedCharge > 3) {
				selectedCharge = 1;
			}
		}

		if (!(charge1 != 0.0f && charge2 != 0.0f && charge3 != 0.0f)) {
			if (selectedCharge == 1 && charge1 == 0.0f) {
				selectedCharge = 2;
			}

			if (selectedCharge == 2 && charge2 == 0.0f) {
				if (charge1 == 0.0f) {
					selectedCharge = 3;
				} else {
					selectedCharge = 1;
				}
			}

			if (selectedCharge == 3 && charge3 == 0.0f) {
				selectedCharge = 1;
			}
		}

		if (inputFire.wasDown()) {
			// Gets the projectile direction based off of the position.
			Vector2f direction = new Vector2f(currentSpeedY, currentSpeedZ);

			// Calculates the direction from the mouse click position.
			if (!FlounderJoysticks.isConnected(0)) {
				direction.set((FlounderMouse.getPositionX() * 2.0f - 1.0f) + 0.03f, FlounderMouse.getPositionY() * 2.0f - 1.0f);
				direction.y /= FlounderDisplay.getAspectRatio();
			}

			// Fixes any zero vectors.
			if (direction.isZero()) {
				direction.x += 0.03f;
			}

			// Normalize speeds into directions.
			direction.normalize();

			switch (selectedCharge) {
				case 1:
					if (charge1 > 0.0f) {
						PolyWorld.fireProjectile(new Vector3f(0.0f, currentY, currentZ), currentRadius, 1, direction, true);
					}

					charge1 -= 0.07f;
					charge1 = Maths.clamp(charge1, 0.0f, 1.0f);
					break;
				case 2:
					if (charge2 > 0.0f) {
						PolyWorld.fireProjectile(new Vector3f(0.0f, currentY, currentZ), currentRadius, 2, direction, true);
					}

					charge2 -= 0.09f;
					charge2 = Maths.clamp(charge2, 0.0f, 1.0f);
					break;
				case 3:
					if (charge3 > 0.0f) {
						PolyWorld.fireProjectile(new Vector3f(0.0f, currentY, currentZ), currentRadius, 3, direction, true);
					}

					charge3 -= 0.20f;
					charge3 = Maths.clamp(charge3, 0.0f, 1.0f);
					break;
			}
		}

		// Normalizes angles and limits Y rotation to make movement easier.
		currentY = Maths.normalizeAngle(currentY);
		currentZ = Maths.clamp(currentZ, 2.0f, 178.0f);

		// The wacky rotation effect on run.
		float as = Math.min((Math.abs(currentSpeedY) + Math.abs(currentSpeedZ)) / PLAYER_SPEED, 1.0f) + Math.min(9.0f * Math.abs(currentSpeedUp), 1.0f);
		float rx = as * 15.0f * (float) (Math.sin(0.25 * 15.0f * Framework.getTimeSec()) - Math.sin(1.2 * 15.0f * Framework.getTimeSec()) + Math.cos(0.5 * 15.0f * Framework.getTimeSec()));
		float rz = as * 15.0f * (float) (Math.cos(0.25 * 15.0f * Framework.getTimeSec()) - Math.cos(1.2 * 15.0f * Framework.getTimeSec()) + Math.sin(0.5 * 15.0f * Framework.getTimeSec()));

		// Moves and rotates the player.
		Vector3f.rotate(new Vector3f(0.0f, currentRadius, 0.0f), new Vector3f(0.0f, currentY, currentZ), getEntity().getPosition());
		getEntity().getRotation().set(rx, currentY, currentZ + rz);
		getEntity().setMoved();

		// A system like this would be used in flat terrain to do collision. But due to the simple nature of the game this is skipped.
		//	Vector3f dd = Vector3f.rotate(new Vector3f(0.0f, currentRadius, 0.0f), new Vector3f(0.0f, currentY, currentZ), null);
		//	Vector3f.subtract(dd, getEntity().getPosition(), dd);
		//	Vector3f dr = new Vector3f(rx, currentY, currentZ + rz);
		//	Vector3f.subtract(dr, getEntity().getRotation(), dr);
		//	getEntity().move(dd, dr);
		//	getEntity().setMoved();
	}

	public Vector2f getRotation() {
		return new Vector2f(currentY, currentZ);
	}

	public float getCurrentY() {
		return currentY;
	}

	public float getCurrentZ() {
		return currentZ;
	}

	public int getExperience() {
		return experience;
	}

	public void addExperience(int xp) {
		this.experience += xp;
	}

	public int getKills() {
		return kills;
	}

	public void addKill() {
		this.kills++;
	}

	public float getSurvivalTime() {
		return survivalTime;
	}

	public float getHealth() {
		return health;
	}

	public void modifyHealth(float change) {
		this.health += change;
		this.health = Maths.clamp(this.health, 0.0f, 1.0f);
	}

	public float getCharge1() {
		return charge1;
	}

	public void addCharge1(float c1) {
		this.charge1 += c1;
		this.charge1 = Maths.clamp(this.charge1, 0.0f, 1.0f);
	}

	public float getCharge2() {
		return charge2;
	}

	public void addCharge2(float c2) {
		this.charge2 += c2;
		this.charge2 = Maths.clamp(this.charge2, 0.0f, 1.0f);
	}

	public float getCharge3() {
		return charge3;
	}

	public void addCharge3(float c3) {
		this.charge3 += c3;
		this.charge3 = Maths.clamp(this.charge3, 0.0f, 1.0f);
	}

	public int getSelectedCharge() {
		return selectedCharge;
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
