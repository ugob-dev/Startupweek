package data;

import javafx.scene.input.KeyCode;

public class Command {
	KeyCode left, right, up, down, space, pause;
	
	public Command() {
		this.left = KeyCode.LEFT;
		this.right = KeyCode.RIGHT;
		this.up = KeyCode.UP;
		this.down = KeyCode.DOWN;
		this.space = KeyCode.SPACE;
		this.pause = KeyCode.P;
	}

	/**
	 * @return the pause
	 */
	public KeyCode getPause() {
		return pause;
	}

	/**
	 * @param pause the pause to set
	 */
	public void setPause(KeyCode pause) {
		this.pause = pause;
	}

	/**
	 * @return the left
	 */
	public KeyCode getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(KeyCode left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public KeyCode getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(KeyCode right) {
		this.right = right;
	}

	/**
	 * @return the up
	 */
	public KeyCode getUp() {
		return up;
	}

	/**
	 * @param up the up to set
	 */
	public void setUp(KeyCode up) {
		this.up = up;
	}

	/**
	 * @return the down
	 */
	public KeyCode getDown() {
		return down;
	}

	/**
	 * @param down the down to set
	 */
	public void setDown(KeyCode down) {
		this.down = down;
	}

	/**
	 * @return the space
	 */
	public KeyCode getSpace() {
		return space;
	}

	/**
	 * @param space the space to set
	 */
	public void setSpace(KeyCode space) {
		this.space = space;
	}
}
