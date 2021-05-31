package engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import data.Ennemy;
import data.EnnemyDog;
import data.Hero;
import data.Position;
import data.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Engine {

	private Timer engineClock;
	private int SpawnEnnemy;
	private Random gen;
	private User.COMMAND command;
	private boolean moveLeft, moveRight, moveUp, moveDown;
	private double heroesVX, heroesVY;
	private int heroesStep;
	private Hero hero;
	private Ennemy ennemies;
	private Stage stage;
	private Scene scene, popupScene;
	private int seconds;
	private int stateEngine;
	
	public int getStateEngine() {
		return stateEngine;
	}
	
	/*
	 * stateEngine = 0 : stop
	 * stateEngine = 1 : running
	 * stateEngine = 2 : pause
	 */
	public void setStateEngine(int stateEngine) {
		this.stateEngine = stateEngine;
	}


	public Engine() {
	}

	public void init() {
		seconds=0;
		engineClock = new Timer();
		SpawnEnnemy = 0;
		command = User.COMMAND.NONE;
		gen = new Random();
		moveLeft = false;
		moveRight = false;
		moveUp = false;
		moveDown = false;
		heroesVX = 0;
		heroesVY = 0;
		heroesStep = 10;
		stateEngine = 0;
	}

	public void bindHero(Hero service) {
		hero = service;
	}

	public void bindEnnemy(Ennemy service) {
		ennemies = service;
	}

	public void start() {
			engineClock.schedule(new TimerTask() {
				public void run() {
					boolean startRun = (stage.getScene().lookup("#imvHome") == null) ? true : false;
					// le traitement se lance seulement si la scene principale est le jeu
					if(startRun && stateEngine == 1) {	
						if(seconds<=10) {
							seconds++;
						}
						else {
							hero.setScore(hero.getScore()+1);
							seconds=0;
						}
						
						SpawnEnnemy++;
						// if (gen.nextInt(100)<3)
		
						if (SpawnEnnemy == 1) {
							spawnPhantom();
						}
						if (SpawnEnnemy == 50) {
							spawnPhantom();
							SpawnEnnemy = 2;
						}
		
						updateSpeedHeroes();
						updateCommandHeroes();
						updatePositionHeroes();

						ArrayList<EnnemyDog> phantoms = new ArrayList<EnnemyDog>();

						for (EnnemyDog p : ennemies.getPhantoms()) {
							double xcalc = hero.getHeroPosition().x - p.getPosition().x;
							double ycalc = hero.getHeroPosition().y - p.getPosition().y;
		
							if (xcalc < 0)
								moveLeft(p);
							if (xcalc > 0)
								moveRight(p);
							if (ycalc < 0)
								moveUp(p);
							if (ycalc > 0)
								moveDown(p);
							
							 if (collisionHeroesPhantom(p)){
								 hero.setAlive(false);
							 } 
							 else {
								 if(p.getPosition().x>0) {
									 phantoms.add(p);
								 }
							 }
						}
					
						//
						hero.setStepNumber(hero.getStepNumber() + 1);
						
						if(hero.isAlive() == false) {
							Platform.runLater(new Runnable() {
						        public void run() {
						        	openMenu(popupScene, true);
						        }
							});
						}
					}
				}
			}, 0, 100);
	}

	/*
	 * met fin au thread du moteur engine
	 */
	public void stop() {
		engineClock.cancel();
	}
	
	/*
	 * met pause au thread de l'engine
	 */
	public void pause() {
		try {
			engineClock.wait(9999999);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * permet de reprendre le d�roulement du noyau
	 */
	public void resume() {
		engineClock.notify();
	}

	private void updateCommandHeroes() {
		// limit du terrain
		double limitLeft = 25;
		double limitTop = 25;
		double limitRight = scene.getWidth() - 100;
		double limitBottom = scene.getHeight() - 130;

		if (moveLeft && hero.getHeroPosition().x > limitLeft) {
			setHeroesVX(getHeroesVX() - heroesStep);
		}
		if (moveRight && hero.getHeroPosition().x < limitRight) {
			setHeroesVX(getHeroesVX() + heroesStep);
		}
		if (moveUp && hero.getHeroPosition().y > limitTop) {
			setHeroesVY(getHeroesVY() - heroesStep);
		}
		if (moveDown && hero.getHeroPosition().y < limitBottom) {
			setHeroesVY(getHeroesVY() + heroesStep);
		}
	}

	public double getHeroesVX() {
		return heroesVX;
	}

	public void setHeroesVX(double heroesVX) {
		this.heroesVX = heroesVX;
	}

	public double getHeroesVY() {
		return heroesVY;
	}

	public void setHeroesVY(double heroesVY) {
		this.heroesVY = heroesVY;
	}

	private void updatePositionHeroes() {

		hero.setHeroPosition(
				new Position(hero.getHeroPosition().x + getHeroesVX(), hero.getHeroPosition().y + getHeroesVY()));

		// if (data.getHeroesPosition().x<0) data.setHeroesPosition(new
		// Position(0,data.getHeroesPosition().y));
		// etc...

	}

	private void updateSpeedHeroes() {
		heroesVX *= 0.50;
		heroesVY *= 0.50;
	}

	public void setHeroesCommand(User.COMMAND c) {
		if (c == User.COMMAND.LEFT) {
			moveLeft = true;
			hero.moveLeft = true;
			hero.moveRight = false;
			hero.moveDown = false;
			hero.moveUp = false;
		}
		;
		if (c == User.COMMAND.RIGHT) {
			moveRight = true;
			hero.moveRight = true;
			hero.moveLeft = false;
			hero.moveDown = false;
			hero.moveUp = false;
		}
		;
		if (c == User.COMMAND.UP) {
			moveUp = true;
			hero.moveUp = true;
		}
		;
		if (c == User.COMMAND.DOWN) {
			moveDown = true;
			hero.moveDown = true;
		}
		;
		if (c == User.COMMAND.SPACE)
			;
	}

	public void releaseHeroesCommand(User.COMMAND c) {
		if (c == User.COMMAND.LEFT)
			moveLeft = false;
		if (c == User.COMMAND.RIGHT)
			moveRight = false;
		if (c == User.COMMAND.UP)
			moveUp = false;
		if (c == User.COMMAND.DOWN)
			moveDown = false;
	}

	// FONCTION definis la position de d'apparition des phamtoms
	private void spawnPhantom() {
		int x = (int) (gen.nextInt((int) (this.stage.getWidth() * .6)) + this.stage.getWidth() * .1);
		int y = (int) (gen.nextInt((int) (this.stage.getHeight() * .6)) + this.stage.getHeight() * .1);
		boolean cont = true;

		if (x >= 0 && x <= this.stage.getWidth() / 2) {
			x = 0;
		} else {
			x = (int) this.stage.getWidth();
		}
		ennemies.addPhantom(new Position(x, y));
		
		Media sound = new Media(new File("src/sound/chipmunk.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}

	private void moveLeft(EnnemyDog p) {
		p.setPosition(new Position(p.getPosition().x - 7, p.getPosition().y));
		p.moveLeft = true;
		p.moveRight = false;
	}

	private void moveRight(EnnemyDog p) {
		p.setPosition(new Position(p.getPosition().x + 7, p.getPosition().y));
		p.moveLeft = false;
		p.moveRight = true;
	}

	private void moveUp(EnnemyDog p) {
		p.setPosition(new Position(p.getPosition().x, p.getPosition().y - 7));
	}

	private void moveDown(EnnemyDog p) {
		p.setPosition(new Position(p.getPosition().x, p.getPosition().y + 7));
	}

	public void bindStage(Stage stage) {
		// TODO Auto-generated method stub
		this.stage = stage;
	}

	public void bindScene(Scene scene) {
		this.scene = scene;
	}
	
	public void bindPopupMenuScene(Scene popupScene) {
		this.popupScene = popupScene;
	}
	

	/*
	 * Ouvre la boite de dialogue contenant le menu de s�lection
	 */
	public void openMenu(Scene menuPopupScene, boolean endGame) {
		// met en pause le thread
		setStateEngine(2);
    	
		// instanciation des �l�ments graphique
		ImageView imvHero = (ImageView)stage.getScene().lookup("#imvHero");
		Stage secondaryStage = new Stage();
		
		// si ce n'est pas une fin de partie on n'affiche pas gameover
		if(!endGame) {
			menuPopupScene.lookup("#gameover").setVisible(false);
			menuPopupScene.lookup("#btnGoGameMenuPopup").setDisable(false);
		}
		else {
			menuPopupScene.lookup("#gameover").setVisible(true);
			menuPopupScene.lookup("#btnGoGameMenuPopup").setDisable(true);
		}
		
		// option du stage
		secondaryStage.initStyle(StageStyle.UNDECORATED);
		secondaryStage.setTitle("Menu");
		secondaryStage.initModality(Modality.APPLICATION_MODAL);
		secondaryStage.initOwner(imvHero.getScene().getWindow());
		secondaryStage.setResizable(false);
		secondaryStage.setScene(menuPopupScene);
		secondaryStage.setWidth(400.0);
		secondaryStage.setHeight(300.0);
		secondaryStage.centerOnScreen();
		
		// affichage
		secondaryStage.show();
	}
	
	public void closeMenu(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage secondaryStage = (Stage) source.getScene().getWindow();
		secondaryStage.close();
		
		// reprends le thread
		setStateEngine(1);
	}

	private boolean collisionHeroesPhantom(EnnemyDog p){
	    return (
	      (hero.getHeroPosition().x-p.getPosition().x)*(hero.getHeroPosition().x-p.getPosition().x)+
	      (hero.getHeroPosition().y-p.getPosition().y)*(hero.getHeroPosition().y-p.getPosition().y) <
	      0.25*(hero.getHeroesWidth()+p.getEnnemyWidth())*(hero.getHeroesWidth()+p.getEnnemyWidth())
	    );
	  }
	
	/*
	private boolean moveXIsAllowed(int index) {
		boolean result = false;
		EnnemyDog thisEnnemy = ennemies.getPhantoms().get(index);
		int counter = 0;
		
		for (EnnemyDog p : ennemies.getPhantoms()) {
			if(counter != index) {
				double spaceX = Math.abs(thisEnnemy.getPosition().x - p.getPosition().x);
				
				if(spaceX >= 20) {
					return true;
				}
			}
			counter++;
		}
		return result;
	}
	
	private boolean moveYIsAllowed(int index) {
		boolean result = false;
		EnnemyDog thisEnnemy = ennemies.getPhantoms().get(index);
		int counter = 0;
		
		for (EnnemyDog p : ennemies.getPhantoms()) {
			if(counter != index) {
				double spaceY = Math.abs(thisEnnemy.getPosition().y - p.getPosition().y);
				
				if(spaceY >= 40) {
					return true;
				}
			}
			counter++;
		}
		return result;
	}
	*/
}
