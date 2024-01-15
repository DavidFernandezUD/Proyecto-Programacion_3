package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class KeyHandler implements KeyListener {

	private final Set<Integer> pressedKeys = new HashSet<>();
	private final Map<Integer, Boolean> keyToggleStates = new HashMap<>();

	// Stack is used to keep record of the last pressed keys in order
	private final Stack<Integer> pressedMoveKeys = new Stack<>();

	public boolean keyPressedE;

	public boolean isKeyPressed(int keyCode) {
		return pressedKeys.contains(keyCode);
	}

	public boolean isLastMoveKeyPressed(int keyCode) {
		if (!pressedMoveKeys.isEmpty()) {
			return keyCode == pressedMoveKeys.lastElement();
		}
		return false;
	}

	public boolean isMoveKeyPressed() {
		return pressedKeys.contains(KeyEvent.VK_W) || pressedKeys.contains(KeyEvent.VK_A)
				|| pressedKeys.contains(KeyEvent.VK_S) || pressedKeys.contains(KeyEvent.VK_D);
	}

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

	public String getKeyPressed() {
		if (!pressedKeys.isEmpty()) {
			int keyCode = pressedKeys.iterator().next();
			return KeyEvent.getKeyText(keyCode);
		}
		return null;
	}
}
