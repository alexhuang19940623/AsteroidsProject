package application;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Player extends Ship {
	AudioClip dies = new AudioClip(getClass().getResource("/boom.wav").toExternalForm());
	AudioClip jumps = new AudioClip(getClass().getResource("/hyperjump.wav").toExternalForm());
	AudioClip yippee = new AudioClip(getClass().getResource("/1UP.wav").toExternalForm());
	AudioClip scream = new AudioClip(getClass().getResource("/wilhelm.wav").toExternalForm());
	private boolean rotateLeft = false;
	private boolean rotateRight = false;
	private boolean thrusting = false;
	private boolean shooting = false;
	private boolean hyperSpaceAvailable = true;
	private int reloadTime;
	int lives = 3;
	int deathRespite = 25;
	int chill = 0; // ie invincibility
	int score = 0;
	int livesAwarded = 0;

	public Player() {
		this.pewpew = new AudioClip(getClass().getResource("/player.wav").toExternalForm());
		/* PLAYER SHIP OUTLINE */
		generateShape();
		this.generateOutlineCoordinates();
		/* POSITION AND VELOCITY */
		this.position = new Vector(Main.screenWidth/2,Main.screenHeight/2);
		this.velocity = new Vector(0,0);
		this.heading = 270;
		this.colour = Color.YELLOW;
		this.proximityRadius = 20.0;
	}
	
	private void generateShape() {
		/* adapted from http://dice.star-ranger.com/ */
		this.getPoints().setAll(
		-8.3, 0.0,
		-6.5, -0.6,
		-7.2, -2.8,
		-6.5, -4.2,
		-10.0, -8.7,
		-9.0, -10.0,
		-5.5, -9.6,
		0.0, -4.7,
		6.5, -2.9,
		6.5, -4.6,
		10.0, -2.4,
		20.0, 0.0,
		10.0, 2.4,
		6.5, 4.6,
		6.5, 2.9,
		0.0, 4.7,
		-5.5, 9.6,
		-9.0, 10.0,
		-10.0, 8.7,
		-6.5, 4.2,
		-7.2, 2.8,
		-6.5, 0.6
		);
	}
	
	@Override
	public void render(GraphicsContext context) {
		if (this.deathRespite == 0) {
			if (chill%6 == 0 && this.lives > 0) {
				super.render(context);
			}
		} else {
			this.deathRespite--;
		}
	}
	
	@Override
	public void update() {
		double speed = this.velocity.getLength();
		double increment = (2*speed+this.maxSpeed)/(3*this.maxSpeed);
		if (this.chill > 0) {
			this.chill--;
		}
		if (this.reloadTime > 0) {
			this.reloadTime--;
		}
		if (this.rotateLeft == true) {
			this.heading -= this.turnSpeed*increment;
		}
		else if (this.rotateRight == true) {
			this.heading += this.turnSpeed*increment;
		}
		if (this.thrusting == true) {
			this.thrust();
		}
		super.update();
	}
	
	public ArrayList<Bullet> pew() {
		ArrayList<Bullet> shot = new ArrayList<Bullet>();
		if (this.shooting == true && this.reloadTime == 0) {
			this.reloadTime = 10;
			shot.add(this.shoot());
		}
		return shot;
	}

	public void hyperJump() {
		if (this.hyperSpaceAvailable) {
			jumps.play();
			this.hyperSpaceAvailable = false;
			Vector vector = new Vector(Math.random()*Main.screenWidth, Math.random()*Main.screenHeight);
			this.chill = 125;
			this.position = vector;
			this.velocity = new Vector(0.0,0.0);
		}
	}
	
	@Override
	public ArrayList<Shrapnel> shipGoesBoom() {
		ArrayList<Shrapnel> explosion = new ArrayList<Shrapnel>();
		if (this.chill == 0) {
			dies.play();
			scream.play();
			this.lives --;
			this.chill=125;	
			this.deathRespite = 25;
			for (int i=0; i<20; i++) {
				explosion.add(new Shrapnel(this.velocity,this.position));
			}
			explosion.add(new Shrapnel(this.velocity,this.position,true));
			this.returnPlayer();
		}
		return explosion;
	}
	
	public void playerScored(int value) {
		this.score += value;
		if (this.score > (this.livesAwarded+1)*10000) {
			yippee.play();
			this.lives++;
			this.livesAwarded++;
		}
	}
	
	public void resetPlayer() {
		this.score = 0;
		this.lives = 3;
		this.livesAwarded = 0;
		this.heading = 270;
		this.velocity = new Vector(0,0);
		this.position = new Vector(Main.screenWidth/2,Main.screenHeight/2);
		this.rotateLeft = false;
		this.rotateRight = false;
		this.thrusting = false;
		this.shooting = false;
		this.hyperSpaceAvailable = true;
		this.reloadTime = 0;
		this.chill = 100;
	}
	
	public void returnPlayer() {
		this.heading = 270;
		this.velocity = new Vector(0,0);
		this.position = new Vector(Main.screenWidth/2,Main.screenHeight/2);
		this.rotateLeft = false;
		this.rotateRight = false;
		this.thrusting = false;
		this.chill = 125;	
	}
	
	public Text livesToText() {
		Text livesText = new Text();
		String livesStr = String.format("Lives: %d", this.lives);
		livesText.setText(livesStr);
		livesText.setFont(Font.font("Verdana", 20));
		livesText.setFill(Color.WHITE);
		livesText.setLayoutY(50);
		livesText.setLayoutX(50);
		return livesText;
	}
	
	public String livesToString() {
		String livesStr = String.format("Lives: %d", this.lives);
		return livesStr;
	}
	
	public Text scoreToText(int screenWidth) {
		Text scoreText = new Text();
		String livesStr = String.format("Score: %d", this.score);
		scoreText.setText(livesStr);
		scoreText.setFont(Font.font("Verdana", 20));
		scoreText.setFill(Color.WHITE);
		scoreText.setLayoutY(50);
		scoreText.setLayoutX(screenWidth - 150);
		return scoreText;
	}
	
	public String scoreToString() {
		String scoreStr = String.format("Score: %d", this.score);
		return scoreStr;
	}

	/* GETTERS & SETTERS */
	public int getLives() {
		return this.lives;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public boolean isRotateLeft() {
		return rotateLeft;
	}

	public void setRotateLeft(boolean rotateLeft) {
		this.rotateLeft = rotateLeft;
	}

	public boolean isRotateRight() {
		return rotateRight;
	}

	public void setRotateRight(boolean rotateRight) {
		this.rotateRight = rotateRight;
	}

	public boolean isThrusting() {
		return thrusting;
	}

	public void setThrusting(boolean thrusting) {
		this.thrusting = thrusting;
	}
	
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	public void setHyperSpaceAvailable(boolean hype) {
		this.hyperSpaceAvailable = hype;
	}

}
