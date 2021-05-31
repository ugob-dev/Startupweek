package viewer;

import java.util.ArrayList;

import data.Ennemy;
import data.EnnemyDog;
import data.Hero;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Viewer {

	private ImageView heroesAvatar;
	private Image heroesSpriteSheet;
	private ArrayList<Rectangle2D> heroesAvatarViewports;
	private int heroesAvatarViewportIndex;
	private ArrayList<Integer> heroesAvatarXModifiers;
	private ArrayList<Integer> heroesAvatarYModifiers;
	private ImageView ennemyAvatar;
	private Image ennemySpriteSheet;
	private ArrayList<Rectangle2D> ennemyAvatarViewports;
	private int ennemyAvatarViewportIndex;
	private Hero heroPrincipal;
	private Ennemy ennemies;

	public void bindHero(Hero service) {
		heroPrincipal = service;
	}

	public void bindEnnemy(Ennemy service) {
		ennemies = service;
	}

	public void init() {

		// Yucky hard-conding
		heroesSpriteSheet = new Image("file:src/Image/chat.png");
		ennemySpriteSheet = new Image("file:src/Image/predators.png");

		heroesAvatar = new ImageView(heroesSpriteSheet);
		heroesAvatar.setId(heroPrincipal.getId());

		heroesAvatar.setFitHeight(100);
		heroesAvatar.setFitWidth(100);
		heroesAvatarViewports = new ArrayList<Rectangle2D>();
		heroesAvatarXModifiers = new ArrayList<Integer>();
		heroesAvatarYModifiers = new ArrayList<Integer>();

		heroesAvatarViewportIndex = 0;
		ennemyAvatarViewportIndex = 0;

		heroesAvatarViewports.add(new Rectangle2D(125, 29, 136, 202));
		heroesAvatarViewports.add(new Rectangle2D(285, 31, 135, 197));
		heroesAvatarViewports.add(new Rectangle2D(444, 33, 132, 184));
		heroesAvatarViewports.add(new Rectangle2D(608, 31, 135, 196));
		heroesAvatarViewports.add(new Rectangle2D(116, 258, 135, 202));
		heroesAvatarViewports.add(new Rectangle2D(281, 258, 134, 200));
		heroesAvatarViewports.add(new Rectangle2D(446, 263, 133, 189));
		heroesAvatarViewports.add(new Rectangle2D(601, 258, 134, 200));

		// heroesAvatarXModifiers.add(6);heroesAvatarYModifiers.add(-6);
		// heroesAvatarXModifiers.add(2);heroesAvatarYModifiers.add(-8);
		// heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-10);
		// heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-13);
		// heroesAvatarXModifiers.add(5);heroesAvatarYModifiers.add(-15);
		// heroesAvatarXModifiers.add(5);heroesAvatarYModifiers.add(-13);
		// heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(-9);
		// heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(-6);
		//

	}

	public Parent getPanel() {

		Text scoring = new Text(15,40,"");
		scoring.setText("Score:"+ heroPrincipal.getScore());
		scoring.setFont(new Font(30));
		
		// Sprite Heros
		if (heroPrincipal.moveLeft == true) {

			heroesAvatarViewports.removeAll(heroesAvatarViewports);
			heroesAvatarViewports = new ArrayList<Rectangle2D>();
			heroesAvatarViewports.add(new Rectangle2D(1313, 28, 136, 202));
			heroesAvatarViewports.add(new Rectangle2D(1154, 30, 135, 197));
			heroesAvatarViewports.add(new Rectangle2D(998, 32, 132, 184));
			heroesAvatarViewports.add(new Rectangle2D(831, 30, 135, 196));
			heroesAvatarViewports.add(new Rectangle2D(1323, 257, 135, 202));
			heroesAvatarViewports.add(new Rectangle2D(1159, 257, 134, 200));
			heroesAvatarViewports.add(new Rectangle2D(995, 262, 133, 189));
			heroesAvatarViewports.add(new Rectangle2D(839, 257, 134, 200));
			// heroesAvatarViewports.add(new Rectangle2D(204,386,95,192));
		} else if (heroPrincipal.moveRight == true) {

			heroesAvatarViewports.removeAll(heroesAvatarViewports);
			heroesAvatarViewports = new ArrayList<Rectangle2D>();
			heroesAvatarViewports.add(new Rectangle2D(125, 29, 136, 202));
			heroesAvatarViewports.add(new Rectangle2D(285, 31, 135, 197));
			heroesAvatarViewports.add(new Rectangle2D(444, 33, 132, 184));
			heroesAvatarViewports.add(new Rectangle2D(608, 31, 135, 196));
			heroesAvatarViewports.add(new Rectangle2D(116, 258, 135, 202));
			heroesAvatarViewports.add(new Rectangle2D(281, 258, 134, 200));
			heroesAvatarViewports.add(new Rectangle2D(446, 263, 133, 189));
			heroesAvatarViewports.add(new Rectangle2D(601, 258, 134, 200));
		}

		int heroIndex = heroesAvatarViewportIndex / 7;

		// heroesScale=data.getHeroesHeight()*shrink/heroesAvatarViewports.get(index).getHeight();

		heroesAvatar.setViewport(heroesAvatarViewports.get(heroIndex));
		// heroesAvatar.setFitHeight(heroPrincipal.getHeroesHeight());
		heroesAvatar.setPreserveRatio(true);
		heroesAvatar.setTranslateX(heroPrincipal.getHeroPosition().x);
		heroesAvatar.setTranslateY(heroPrincipal.getHeroPosition().y);
		heroesAvatarViewportIndex = (heroesAvatarViewportIndex + 1) % (heroesAvatarViewports.size() * 7);

		Group panel = new Group();
		// panel.setId("test");
		// panel.getStylesheets().addAll(this.getClass().getResource("/style/game.css").toExternalForm());
		// background
		// Canvas canvas = new Canvas( 3000, 500 );
		// panel.getChildren().add( canvas );
		//
		// GraphicsContext gc = canvas.getGraphicsContext2D();
		// Image background = new Image( "file:src/Image/terrain.png" );
		// gc.drawImage( background, 0, 0 );

		// Sprite Ennemy
		panel.getChildren().addAll(heroesAvatar);

		// <EnnemyDog> Dogs = ennemies.getPhantoms();
		EnnemyDog p;

		ennemyAvatarViewports = new ArrayList<Rectangle2D>();
		ennemyAvatarViewports.add(new Rectangle2D(18, 9, 104, 186));
		ennemyAvatarViewports.add(new Rectangle2D(166, 13, 102, 191));
		ennemyAvatarViewports.add(new Rectangle2D(308, 14, 108, 176));
		ennemyAvatarViewports.add(new Rectangle2D(457, 14, 102, 183));
		ennemyAvatarViewports.add(new Rectangle2D(593, 13, 102, 186));
		ennemyAvatarViewports.add(new Rectangle2D(741, 12, 108, 189));
		ennemyAvatarViewports.add(new Rectangle2D(871, 15, 118, 179));
		ennemyAvatarViewports.add(new Rectangle2D(1018, 11, 108, 185));

		ennemyAvatarViewportIndex = (ennemyAvatarViewportIndex + 1) % (ennemyAvatarViewports.size() * 7);

		for (int i = 0; i < ennemies.getPhantoms().size(); i++) {
			p = ennemies.getPhantoms().get(i);

			if (p.moveRight == true) {
				ennemyAvatarViewports.removeAll(ennemyAvatarViewports);
				ennemyAvatarViewports = new ArrayList<Rectangle2D>();
				ennemyAvatarViewports.add(new Rectangle2D(18, 9, 104, 186));
				ennemyAvatarViewports.add(new Rectangle2D(166, 13, 102, 191));
				ennemyAvatarViewports.add(new Rectangle2D(308, 14, 108, 176));
				ennemyAvatarViewports.add(new Rectangle2D(457, 14, 102, 183));
				ennemyAvatarViewports.add(new Rectangle2D(593, 13, 102, 186));
				ennemyAvatarViewports.add(new Rectangle2D(741, 12, 108, 189));
				ennemyAvatarViewports.add(new Rectangle2D(871, 15, 118, 179));
				ennemyAvatarViewports.add(new Rectangle2D(1018, 11, 108, 185));
			} else if (p.moveLeft == true) {
				ennemyAvatarViewports.removeAll(ennemyAvatarViewports);
				ennemyAvatarViewports = new ArrayList<Rectangle2D>();
				ennemyAvatarViewports.add(new Rectangle2D(1013, 247, 104, 185));
				ennemyAvatarViewports.add(new Rectangle2D(867, 250, 102, 191));
				ennemyAvatarViewports.add(new Rectangle2D(719, 251, 108, 176));
				ennemyAvatarViewports.add(new Rectangle2D(576, 251, 102, 183));
				ennemyAvatarViewports.add(new Rectangle2D(440, 250, 102, 186));
				ennemyAvatarViewports.add(new Rectangle2D(286, 249, 108, 189));
				ennemyAvatarViewports.add(new Rectangle2D(146, 252, 118, 179));
				ennemyAvatarViewports.add(new Rectangle2D(8, 247, 108, 185));
			}
			int ennemyIndex = ennemyAvatarViewportIndex / 7;
			ennemyAvatar = new ImageView(ennemySpriteSheet);
			ennemyAvatar.setFitHeight(110);
			ennemyAvatar.setFitWidth(110);
			ennemyAvatar.setViewport(null);
			ennemyAvatar.setViewport(ennemyAvatarViewports.get(ennemyIndex));
			ennemyAvatar.setPreserveRatio(true);

			ennemyAvatar.setTranslateX(p.getPosition().x);
			ennemyAvatar.setTranslateY(p.getPosition().y);
			panel.getChildren().add(ennemyAvatar);
		}
		
		panel.getChildren().add(scoring);

		return panel;
	}
}
