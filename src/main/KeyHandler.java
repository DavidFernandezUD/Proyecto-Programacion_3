package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/** Class that manages keyboard user input, and provides
 * multiple methods to facilitate their use.
 * @author david.f@opendeusto.es*/
public class KeyHandler implements KeyListener {

	private final Set<Integer> pressedKeys = new HashSet<>();
	public final Map<Integer, Boolean> keyToggleStates = new HashMap<>();

	// Stack is used to keep record of the last pressed keys in order
	private final Stack<Integer> pressedMoveKeys = new Stack<>();

	public boolean keyPressedE;

	/** Returns true if the specified key is being pressed.
	 * @param keyCode Key Code of the key that is being checked.
	 * @return true if the key is being pressed, false waterways.*/
	public boolean isKeyPressed(int keyCode) {
		return pressedKeys.contains(keyCode);
	}

	/** Returns true if a key is the last "move key" (a, w, s, d)
	 * that has been pressed.
	 * @param keyCode Key Code of the key that is being checked.
	 * @return true if the last move key pressed is the keyCode.*/
	public boolean isLastMoveKeyPressed(int keyCode) {
		if (!pressedMoveKeys.isEmpty()) {
			return keyCode == pressedMoveKeys.lastElement();
		}
		return false;
	}

	/** Returns true if a "move key" (a, w, s, d) is being pressed.
	 * @return true if a move key is pressed.*/
	public boolean isMoveKeyPressed() {
		return pressedKeys.contains(KeyEvent.VK_W) || pressedKeys.contains(KeyEvent.VK_A)
				|| pressedKeys.contains(KeyEvent.VK_S) || pressedKeys.contains(KeyEvent.VK_D);
	}

	/** Returns the toggle state of a given key. The toggle state of each key
	 * is stored as a boolean, and switches its value just when that key is pressed
	 * down again.
	 * @param keyCode Key Code of the key that is being checked.
	 * @return the toggle state of the given key.*/
	public boolean isKeyToggled(int keyCode) {
		return keyToggleStates.getOrDefault(keyCode, false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}


	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		// Key Toggling
		if (!pressedKeys.contains(keyCode)) { // Only if the key wasn't already pressed it is toggled
			if (!keyToggleStates.containsKey(keyCode)) {
				keyToggleStates.put(keyCode, true);
			} else {
				keyToggleStates.put(keyCode, !keyToggleStates.get(keyCode));
			}

			// Adding move and attack keys to their respective stacks the first time they
			// are pressed
			if (isMoveKey(keyCode)) {
				pressedMoveKeys.push(keyCode);
			}
		}

		// Key Pressing
		pressedKeys.add(keyCode);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (e.getKeyCode() == KeyEvent.VK_E) {
			keyPressedE = false;
		}

		pressedKeys.remove(keyCode);

		// Removing move and attack keys from their respective stacks
		if (isMoveKey(keyCode)) {
			pressedMoveKeys.removeElement(keyCode);
		}
	}

	private boolean isMoveKey(int keyCode) {
		return keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_S
				|| keyCode == KeyEvent.VK_D;
	}

	/** Returns the character represented by the last pressed key.
	 * @return The last pressed character.
	 * @author juanjose.restrepo@opendeusto.es*/
	public String getKeyPressed() {
		if (!pressedKeys.isEmpty()) {
			int keyCode = pressedKeys.iterator().next();
			return KeyEvent.getKeyText(keyCode);
		}
		return null;
	}
}
