package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
	public static int screenWidth = 1200;
	public static int screenHeight = 800;
	public static int maxBullets = 11;
	int levelTimeGap = 50;
	private int level = 1;
	String levelString = String.format("level: %d", level);
	public double changeEnemySpawns = 0.001; // FOR TESTING PURPOSES SET TO 1, OTHERWISE SOMETHING LIKE 0.001
	public double chanceEnemyShoots = 0.01;
	public boolean gameStarted = false;
//	public boolean instructionsWanted = false;
	
	@Override
	public void start(Stage stage) {
		try {
			stage.setTitle("Asteroids - Group 14");
			Image icon = new Image("logo.png");
			stage.getIcons().add(icon);
			stage.setResizable(false);

			// setup gameScene so it can be set by the button
			BorderPane gameRoot = new BorderPane();
			Image bg = new Image("background.jpg");
			BackgroundImage background = new BackgroundImage(bg,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
			gameRoot.setBackground(new Background(background));
			Scene gameScene = new Scene(gameRoot);


			/* MENU */
			BorderPane menuRoot = new BorderPane();
			menuRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
			Group buttons = new Group();
			Scene menuScene = new Scene(menuRoot,screenWidth,screenHeight,Color.BLACK);
			ButtonClass btn = new ButtonClass(screenWidth,screenHeight);
			menuRoot.setBackground(new Background(background));
			
			Text title = new Text("Asteroids");
			title.setFill(Color.WHITE);
			
			InnerShadow is = new InnerShadow();
			title.setEffect(is);
			title.setFont(Font.font("Verdana", FontWeight.BOLD,FontPosture.ITALIC, 90));
			Button startGame = btn.getStartButton();
			Button instructions = btn.getInstructions();
			buttons.getChildren().add(startGame);
			buttons.getChildren().add(instructions);
			
			
			// Instructions
//			BorderPane instructRoot = new BorderPane();
//			instructRoot.setBackground(new Background(background));
//			instructRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
//			Scene instructScreen = new Scene(instructRoot,screenWidth,screenHeight,Color.BLACK);
			// Use button below to create back button. Functionality should be there already
//			Button back = btn.getBack();
			
			// Scores
			HighScore scoreboard = new HighScore();

			// when player beats a highscore
			VBox scoreItems = new VBox();
			TextField textField = scoreboard.getTextField(screenWidth,screenHeight);
			
			Button submit = btn.getSubmit();
			Text congrats = scoreboard.congrats();
			Text cBody = scoreboard.congratsBody();




			scoreItems.setAlignment(Pos.CENTER);

			scoreItems.getChildren().addAll(congrats, cBody, textField, submit);
			scoreItems.setSpacing(50);
			BorderPane scoreRoot = new BorderPane();
			Scene highScoreScene = new Scene(scoreRoot,screenWidth,screenHeight,Color.BLACK);
			scoreRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

			scoreRoot.setCenter(scoreItems);
			scoreRoot.setBackground(new Background(background));

			VBox menuItems = new VBox();

			Text one = scoreboard.topScoreToText(0, screenWidth,screenHeight);
			Text two = scoreboard.topScoreToText(1, screenWidth,screenHeight);
			Text three = scoreboard.topScoreToText(2, screenWidth,screenHeight);

			menuItems.getChildren().addAll(title,one, two, three, startGame, instructions);

			menuItems.setAlignment(Pos.CENTER);
			menuItems.setSpacing(50);

			menuRoot.setCenter(menuItems);
			stage.setScene(menuScene);

			// initialize enemy array
			List<Enemy> enemyShips = new ArrayList<>();

			// initialize asteroids
			List<Asteroid> asteroids=new ArrayList<>();

			// initialize playerBullets
			ArrayList<Bullet> playerBullets = new ArrayList<>();

			// initialize enemyBullets
			ArrayList<Bullet> enemyBullets = new ArrayList<>();
			
			// initialize shrapnel
			ArrayList<Shrapnel> shrapnelPieces = new ArrayList<>();


			Player player = new Player();
			
			// Instructions button pressed
			instructions.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent Event) {
					Instruction info = new Instruction();
					info.start(new Stage());
					
//					instructionsWanted = true;					
				}
			});			
			
			// Start game button pressed
			startGame.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent Event) {
					gameStarted = true;
					level = 1;
					enemyShips.clear();
					asteroids.clear();
					playerBullets.clear();
					enemyBullets.clear();
					shrapnelPieces.clear();
					asteroids.add(new Asteroid(player)); // initial asteroid
					stage.setScene(gameScene);

				}
			});


			// GAME SCENE
			Controller controller = new Controller();
			gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					controller.show(event, player);
//					if (event.getCode() == KeyCode.SPACE) {
//						if (playerBullets.size() <= maxBullets) {
//							playerBullets.add(player.shoot());
//						}
//					} else {
//						controller.show(event, player);
//					}

				}

			});
			gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					controller.keyReleased(event, player);
				}

			});

			Canvas gameCanvas = new Canvas(screenWidth,screenHeight);
			GraphicsContext gameContext = gameCanvas.getGraphicsContext2D();
			gameRoot.setCenter(gameCanvas);


			Text livesLeft = player.livesToText();
			Text playerScore = player.scoreToText(screenWidth);
			Text currentLevel = new Text();
			currentLevel.setText(levelString);
			currentLevel.setFont(Font.font("Verdana", 20));
			currentLevel.setFill(Color.WHITE);
			currentLevel.setLayoutX(screenWidth/2-40);
			currentLevel.setLayoutY(50);

			gameRoot.getChildren().addAll(livesLeft, playerScore,currentLevel);

			// GAMELOOP
			AnimationTimer gameloop = new AnimationTimer()
			{
				public void handle(long nanotime)
				{
					if (gameStarted) {
						/* CHECK IF LEVEL IS CLEARED */

						if (asteroids.size()==0 && enemyShips.size()==0) {
							levelTimeGap--;
						}

						if (levelTimeGap == 0) {
							level++;
							levelString = String.format("level: %d", level);
							currentLevel.setText(levelString);
							levelTimeGap = 50;
							for (int i=0; i<level; i++) {
								asteroids.add(new Asteroid(player));
							}
							player.setHyperSpaceAvailable(true);
						}

						/* POSSIBLY SPAWN ANOTHER ENEMY */
						// every 5th level max number of enemies increases by 1
						if(enemyShips.size()<Math.floor(level/5)+1) {
							if (Math.random() < changeEnemySpawns) {
								enemyShips.add(new Enemy(player));
							}
						}
						
						/* POSSIBLY HAVE ENEMIES SHOOT */
						if (enemyBullets.size()<enemyShips.size()) {
							for (int i=0; i < enemyShips.size(); i++) {
								if (Math.random() < chanceEnemyShoots) {
									enemyBullets.add(enemyShips.get(i).shoot());
								}
							}							
						}
						

						/* UPDATE POSITIONS */
						/* update player position */
						player.update();
						if (playerBullets.size() <= maxBullets) {
							playerBullets.addAll(player.pew());							
						}

						/* update player bullets */
						for (int i = 0; i < playerBullets.size(); i++) {
							if (playerBullets.get(i).getLifespan() > 0) {
								playerBullets.get(i).update();
								playerBullets.get(i).decremientLifespan();

							} else {
								playerBullets.remove(i);
							}
						}

						/* update enemyBullets positions */
						for (int i=0; i<enemyBullets.size(); i++) {
							if (enemyBullets.get(i).getLifespan() > 0) {
								enemyBullets.get(i).update();
								enemyBullets.get(i).decremientLifespan();
							} else {
								enemyBullets.remove(i);
							}
						}
						
						/* update shrapnel positions */
						for (int i=0; i<shrapnelPieces.size(); i++) {
							if (shrapnelPieces.get(i).getLifetime() > 0) {
								shrapnelPieces.get(i).update();
							} else {
								shrapnelPieces.remove(i);
							}
						}

						/* update asteroid positions */
						for (int i = 0; i < asteroids.size(); i++) {
							asteroids.get(i).update();
						}

						/* update enemy position */
						for (int i=0; i < enemyShips.size(); i++) {
							enemyShips.get(i).update(player);
						}


						/* COLLISION DETECTION */
						/* check asteroid collisions */
						for (int i = 0; i < asteroids.size(); i++) {
							/* check against player */
							if (asteroids.get(i).isProximal(player)) {
								if (asteroids.get(i).checkCollision(player)) {
									asteroids.addAll(asteroids.get(i).asteroidGoesBoom());
									player.playerScored(asteroids.get(i).getValue());
									playerScore.setText(player.scoreToString());
									asteroids.remove(i);
									shrapnelPieces.addAll(player.shipGoesBoom());
									livesLeft.setText(player.livesToString());
									break;
								}								
							}
							
							/* check against player bullets */
							for (int j = 0; j < playerBullets.size(); j++) {
								if (asteroids.get(i).isProximal(playerBullets.get(j))) {
									if (asteroids.get(i).checkCollision(playerBullets.get(j))) {
										asteroids.addAll(asteroids.get(i).asteroidGoesBoom());
										player.playerScored(asteroids.get(i).getValue());
										playerScore.setText(player.scoreToString());
										livesLeft.setText(player.livesToString()); // in case extra life gained.
										asteroids.remove(i);
										playerBullets.remove(j);
										break;
									}									
								}
							}
						}
						/* check enemy collisions */
						for (int i=0; i < enemyShips.size(); i++) {
							/* against player */
							if (enemyShips.get(i).isProximal(player)) {
								if (enemyShips.get(i).checkCollision(player)) {
									shrapnelPieces.addAll(player.shipGoesBoom());
									livesLeft.setText(player.livesToString());
									shrapnelPieces.addAll(enemyShips.get(i).shipGoesBoom());
									enemyShips.remove(i);
									break;
								}
							}
							/* against player bullets */
							for (int j = 0; j < playerBullets.size(); j++) {
								if (enemyShips.get(i).isProximal(playerBullets.get(j))) {
									if (enemyShips.get(i).checkCollision(playerBullets.get(j))) {
										shrapnelPieces.addAll(enemyShips.get(i).shipGoesBoom());
										enemyShips.remove(i);
										playerBullets.remove(j);
										player.playerScored(200);
										playerScore.setText(player.scoreToString());
										livesLeft.setText(player.livesToString()); // in case extra life gained
										break;
									}
									
								}
							}

						}

						/* check enemy bullets collisions */
						for (int i=0; i<enemyBullets.size(); i++) {
							if ((enemyBullets.get(i).isProximal(player)) ) {
								if (enemyBullets.get(i).checkCollision(player)) {
									enemyBullets.remove(i);
									shrapnelPieces.addAll(player.shipGoesBoom());
									livesLeft.setText(player.livesToString());
									break;
								}					
							}
						}



						/* RENDERING */
						/* CLEAR FRAME */
						gameContext.clearRect(0, 0, screenWidth, screenHeight);

						/* OBJECTS */
						/* player bullets */
						for (int i = 0; i < playerBullets.size(); i++) {
							playerBullets.get(i).render(gameContext);
						}

						/* player */
						player.render(gameContext);


						/* enemy bullets */
						for (int i=0; i<enemyBullets.size(); i++) {
							enemyBullets.get(i).render(gameContext);
						}
						

						/* enemy */
						for (int i=0; i < enemyShips.size(); i++) {
							enemyShips.get(i).render(gameContext);
						}

						/* asteroids */
						for (int i = 0; i < asteroids.size(); i++) {
							asteroids.get(i).render(gameContext);
						}

						/* shrapnel */
						for (int i=0; i<shrapnelPieces.size(); i++) {
							shrapnelPieces.get(i).render(gameContext);
						}

						if (player.getLives() == 0 && shrapnelPieces.size()==0) {
							gameStarted = false;
						}
					}

					else if (!gameStarted) {
						
						if(scoreboard.isNewHighScore(player.getScore())) {
							// show screen to input name
//							System.out.println("new high score");
//							player.resetPlayer();
							stage.setScene(highScoreScene);
							submit.setOnAction(e -> {

								String name = textField.getText();
								scoreboard.updateHighScore(name, player.getScore());
								player.resetPlayer();
								livesLeft.setText(player.livesToString());
								playerScore.setText(player.scoreToString());
								level = 1;
								levelString = String.format("level: %d", level);
								currentLevel.setText(levelString);
								one.setText(scoreboard.topScoreToString(0, screenWidth,screenHeight));
								two.setText(scoreboard.topScoreToString(1, screenWidth,screenHeight));
								three.setText(scoreboard.topScoreToString(2, screenWidth,screenHeight));
								stage.setScene(menuScene);

							});

						}
						else {
							player.resetPlayer();
							stage.setScene(menuScene);
//							if (instructionsWanted) {

//								stage.setScene(instructScreen);
//							}
						}
						// end game sequence
					}

				}
			};

			gameloop.start();

			stage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
