package main;

import java.io.IOException;

import data.Command;
import data.Ennemy;
import data.Hero;
import engine.Engine;
import engine.EventManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import viewer.Viewer;

public class Main extends Application {

	private static AnimationTimer timer;
	private static Viewer viewer;
	private static Engine engine;
	private static Command command;
	private static Hero hero;
	private static Ennemy ennemies;
	private static int DefaultWidth = 1200, DefaultHeigth = 760;
	public FXMLLoader loader;
	public StackPane homeLayout, menuPopupScene, parameterLayout;
	public AnchorPane menuPopupLayout;
	public Group gameLayout;
	public EventManager em;
	
	/*
	 * Retourne la vue Home
	 */
	public StackPane initHomeLayout() {
		// pr�pare la sc�ne principale
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/layout/HomeView.fxml"));
		try {
			homeLayout = (StackPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return homeLayout;
	}
	
	public StackPane initParameterLayout() {
		// pr�pare la scene param�tre
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/layout/ParameterView.fxml"));
		try {
			parameterLayout = (StackPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return parameterLayout;
	}
	
	/*
	 * Retourne la vue Game
	 */
	public Group initGameLayout() {
		// pr�pare la scene du jeu
		gameLayout = (Group) viewer.getPanel();
		return gameLayout;
	}
	
	/*
	 * Retourne la popup du menu
	 */
	public AnchorPane initMenuPopupLayout() {
		// pr�pare la sc�ne principale
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/layout/MenuOverviewPopup.fxml"));
		try {
			menuPopupLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return menuPopupLayout;
	}
	
	/*
	 * Parametre et affiche le stage
	 */
	public void setStage(Stage stage, Scene scene) {
		// met un titre dans la fen�tre
		stage.setTitle("The Predator");
		// met le logo pour identifier l'application
		stage.getIcons().add(new Image("file:image/logo.png"));
		// met la page d'accueil dans le stage
		stage.setScene(scene);
				
		// desactiver le redimenssionnement automatique
		stage.setResizable(false);
		
		// affiche la scene
		stage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		viewer = new Viewer();
		engine = new Engine();
		hero = new Hero();
		ennemies = new Ennemy();
		command = new Command();

		viewer.bindHero(hero);
		viewer.bindEnnemy(ennemies);
		engine.bindHero(hero);
		engine.bindEnnemy(ennemies);
		
		// appel la m�thode start
		launch(args);
	}

	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * Lance l'application
	 */
	public void start(Stage primaryStage) {
		if(engine.getStateEngine() == 0) {
			// initialise les variables du moteur
			engine.init();
			// initialise les donn�es de la vue
			viewer.init();
		}
		
		// defini la scene du jeu
		final Scene gameScene = new Scene(initGameLayout());
		final Scene homeScene = new Scene(initHomeLayout());
		final Scene parameterScene = new Scene(initParameterLayout());
		final Scene menuPopupScene = new Scene(initMenuPopupLayout(), 200, 100);
		
		engine.bindScene(gameScene);
		engine.bindStage(primaryStage);
		engine.bindPopupMenuScene(menuPopupScene);
		
		// gestionnaire d'�venements
		em = new EventManager(homeScene, parameterScene, gameScene, menuPopupScene, primaryStage, engine, viewer, command);
		em.btnOnClick();
		em.handleTouch();
		em.startEngine();
		em.stopApp();

		// active le timer
		timer = new AnimationTimer() {
			@Override
			public void handle(long l) {
				if(engine.getStateEngine() == 1) {
					gameScene.setRoot(initGameLayout());
				}
			}
		};
		timer.start();
		
		// demarre le stage
		setStage(primaryStage, homeScene);
	}

}
