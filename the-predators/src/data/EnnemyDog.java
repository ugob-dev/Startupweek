package data;

public class EnnemyDog {
	private Position EnnemyPosition;
	private static double EnnemyWidth=60, EnnemyEnHeight= 90;

	public static double getEnnemyEnHeight() {
		return EnnemyEnHeight;
	}

	public enum MOVE {
		LEFT, RIGHT, UP, DOWN
	}

	public boolean moveLeft, moveRight, moveUp, moveDown;
	private double ennemyWidth, ennemyHeight;

	public EnnemyDog(Position p) {
		EnnemyPosition = p;
	}

	public Position getPosition() {
		return this.EnnemyPosition;
	}

	public void setPosition(Position p) {
		EnnemyPosition = p;
	}

	public double getEnnemyWidth() {
		return ennemyWidth;
	}

	public double getEnnemyHeight() {
		return ennemyHeight;
	}

	public void setHeroesHeight(double ennemyHeight) {
		this.ennemyHeight = ennemyHeight;
	}

	public MOVE getAction() {
		return MOVE.LEFT;
	}
}
