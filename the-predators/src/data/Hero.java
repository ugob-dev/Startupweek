package data;

public class Hero {

	private Position heroPosition;
	private int stepNumber, score;
	private double heroesWidth, heroesHeight;
	public boolean moveLeft, moveRight, moveUp, moveDown;
	private String id;
	public boolean isAlive;

	public Hero() {
		init();
	}

	public Position getHeroPosition() {
		return heroPosition;
	}

	public void setHeroPosition(Position heroPosition) {
		this.heroPosition = heroPosition;
	}

	public int getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getHeroesWidth() {
		return heroesWidth;
	}

	public void setHeroesWidth(double heroesWidth) {
		this.heroesWidth = heroesWidth;
	}

	public double getHeroesHeight() {
		return heroesHeight;
	}

	public void setHeroesHeight(double heroesHeight) {
		this.heroesHeight = heroesHeight;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void init() {
		// hercules = new Heroes;
		id = "imvHero";
		heroPosition = new Position(500, 300);
		stepNumber = 0;
		score = 0;
		heroesWidth = 60;
		heroesHeight = 90;
		isAlive=true;
	}
}
