package engine;

import data.Command;
import data.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import viewer.Viewer;

public class EventManager {
	
	private Scene homeScene, gameScene, menuPopupScene, parameterScene;
	private Stage stage;
	private Engine engine;
	private Viewer viewer;
	private Command command;

	public EventManager(Scene homeScene, Scene parameterScene, Scene gameScene, Scene menuPopupScene, Stage stage, Engine engine, Viewer viewer, Command command) {
		this.homeScene = homeScene;
		this.parameterScene = parameterScene;
		this.gameScene = gameScene;
		this.menuPopupScene = menuPopupScene;
		this.stage = stage;
		this.engine = engine;
		this.viewer = viewer;
		this.command = command;
	}
	
	/*
	 * Defini la gestion des evenements sur les touches
	 * moving() : deplacement
	 * startEngine() : d�marre le noyau
	 * stopApp() : arrete l'application
	 */
	
	// d�placement du joueur
	public void handleTouch() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == command.getLeft())
					engine.setHeroesCommand(User.COMMAND.LEFT);
				if (event.getCode() == command.getRight())
					engine.setHeroesCommand(User.COMMAND.RIGHT);
				if (event.getCode() == command.getUp())
					engine.setHeroesCommand(User.COMMAND.UP);
				if (event.getCode() == command.getDown())
					engine.setHeroesCommand(User.COMMAND.DOWN);
				if (event.getCode() == command.getSpace())
					engine.setHeroesCommand(User.COMMAND.SPACE);
				if (event.getCode() == command.getPause())
					engine.openMenu(menuPopupScene, false);
				
				event.consume();
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == command.getLeft())
					engine.releaseHeroesCommand(User.COMMAND.LEFT);
				if (event.getCode() == command.getRight())
					engine.releaseHeroesCommand(User.COMMAND.RIGHT);
				if (event.getCode() == command.getUp())
					engine.releaseHeroesCommand(User.COMMAND.UP);
				if (event.getCode() == command.getDown())
					engine.releaseHeroesCommand(User.COMMAND.DOWN);
				if (event.getCode() == command.getSpace())
					engine.releaseHeroesCommand(User.COMMAND.SPACE);
				if (event.getCode() == command.getPause()) 
					engine.openMenu(menuPopupScene, false);
				
				event.consume();
			}
		});
	}
	
	// quand le jeu est affich� on d�marre le moteur engine
	public void startEngine() {
		stage.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				engine.start();
			}
		});
	}

	// quand on ferme l'application on stoppe le service engine
	public void stopApp() {
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				engine.stop();
				Platform.exit();
				System.exit(0);
			}
		});
	}
	
	
	/*
	 * G�re les actions sur les boutons
	 * btnOnClick()
	 */
	
	public void btnOnClick() {
		// Menu principal
		Button btnStart = (Button) homeScene.lookup("#btnStart");
		Button btnParam = (Button) homeScene.lookup("#btnParam");
		Button btnExit = (Button) homeScene.lookup("#btnExit");
		
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				engine.setStateEngine(1);
				
				// ajout l'image de fond � la scene principale (solution temporaire)
				Image grass = new Image("file:src/image/terrain.png");
				ImagePattern grassSheet = new ImagePattern(grass);
				gameScene.setFill(grassSheet);

				stage.setScene(gameScene);
				stage.setMaximized(true);
				stage.setFullScreen(true);
				stage.setResizable(true);
				stage.show();
			}
		});
		
		btnParam.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(parameterScene);
				stage.centerOnScreen();
				stage.setResizable(false);
				stage.show();
			}
		});
		
		btnExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				engine.stop();
				Platform.exit();
				System.exit(0);
			}
		});
		
		// Menu parametre
		Button btnLeft = (Button) parameterScene.lookup("#btnLeft");
		Button btnRight = (Button) parameterScene.lookup("#btnRight");
		Button btnUp = (Button) parameterScene.lookup("#btnUp");
		Button btnDown = (Button) parameterScene.lookup("#btnDown");
		Button btnGoBack = (Button) parameterScene.lookup("#btnGoBack");
		
		btnLeft.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TextField txfLeft = (TextField) parameterScene.lookup("#txfLeft");
				btnLeft.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						command.setLeft(event.getCode());
						txfLeft.setText(event.getCode().toString());
					}
				});
			}
		});
		
		btnRight.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TextField txfRight = (TextField) parameterScene.lookup("#txfRight");
				btnRight.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						command.setRight(event.getCode());
						txfRight.setText(event.getCode().toString());
					}
				});
			}
		});
		
		btnUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TextField txfUp = (TextField) parameterScene.lookup("#txfUp");
				btnUp.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						command.setUp(event.getCode());
						txfUp.setText(event.getCode().toString());
					}
				});
			}
		});
		
		btnDown.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TextField txfDown = (TextField) parameterScene.lookup("#txfDown");
				btnDown.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						command.setDown(event.getCode());
						txfDown.setText(event.getCode().toString());
					}
				});
			}
		});
		
		btnGoBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(homeScene);
				stage.centerOnScreen();
				stage.setResizable(false);
				stage.show();
			}
		});
		
		// Menu en cours de jeu
		Button btnGoGameMenuPopup = (Button) menuPopupScene.lookup("#btnGoGameMenuPopup");
		Button btnGoHomeMenuPopup = (Button) menuPopupScene.lookup("#btnGoHomeMenuPopup");
		
		btnGoGameMenuPopup.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				engine.closeMenu(event);
			}
		});
		
		btnGoHomeMenuPopup.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				engine.setStateEngine(0);
				
				Stage menuStage = (Stage) btnGoHomeMenuPopup.getScene().getWindow();
				menuStage.close();
				
				stage.setScene(homeScene);
				stage.centerOnScreen();
				stage.setResizable(false);
				stage.show();
			}
		});
	}
	
	
	/*
	 * Gestion du redimensionnement du stage
	 */
}
