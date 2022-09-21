package application;

import javafx.scene.paint.Color;

public class Enemy extends Ship {
	private double turnSpeed = 2.0;

	public Enemy(Player player) {
		/* ENEMY SHIP OUTLINE */
		generateShape(); // threw it into a method as it's a lot of coordinates
		this.generateOutlineCoordinates();
		/* POSITION & VELOCITY */
		setPosition();
		// starting direction is towards player
		Vector bearing = new Vector(player.position.getX(),player.position.getY());
		bearing.subtract(this.position);
		this.heading = bearing.getAngle();
		this.velocity = new Vector(1.0,0.0);
		this.velocity.setAngle(this.heading);
		this.maxSpeed = 2.0;
		this.acceleration = 0.1;
		this.colour = Color.GREEN;
		this.proximityRadius = 20.3;
	}

	private void generateShape() {
		/* adapted from http://dice.star-ranger.com/ */
		this.getPoints().setAll(
				-9.5, -0.7,
				-7.0, -0.8,
				-7.2, -3.4,
				-8.9, -3.7,
				-10.0, -4.7,
				-10.0, -5.9,
				-6.6, -7.9,
				-1.8, -9.7,
				6.1, -10.0,
				11.8, -8.7,
				15.8, -6.7,
				18.8, -4.5,
				20.0, -2.9,
				17.0, -4.7,
				13.2, -6.2,
				9.6, -6.7,
				5.7, -6.5,
				2.6, -5.5,
				0.8, -3.8,
				0.3, -1.4,
				3.0, -1.4,
				4.4, -0.6,
				4.4, 0.6,
				3.0, 1.4,
				0.3, 1.4,
				0.8, 3.8,
				2.6, 5.5,
				5.7, 6.5,
				9.6, 6.7,
				13.2, 6.2,
				17.0, 4.7,
				20.0, 2.9,
				18.8, 4.5,
				15.8, 6.7,
				11.8, 8.7,
				6.1, 10.0,
				-1.8, 9.7,
				-6.6, 7.9,
				-10.0, 5.9,
				-10.0, 4.7,
				-8.9, 3.7,
				-7.2, 3.4,
				-7.0, 0.8,
				-9.5, 0.7
		);
	}

	private void setPosition() {
		if (Math.random() < 0.5) {
			this.position = new Vector(0.0,Math.random()*Main.screenHeight);
		} else {
			this.position = new Vector(Main.screenWidth,Math.random()*Main.screenHeight);
		}
	}
	
	private void trackPlayer(Vector playerPosition, Vector playerVelocity) {
		Vector bearing = new Vector(playerPosition.getX(),playerPosition.getY());
		Vector predicted = new Vector(playerVelocity.getX(),playerVelocity.getY());
		predicted.multiply(30); // how far into the future to look
		bearing.add(predicted);
		bearing.subtract(this.position);
		double desired = bearing.getAngle();
		double difference = desired - this.heading;
		if (Math.abs(difference) < this.turnSpeed) {
			// enemy on track, don't turn
			return;
		}
		if (Math.abs(difference)>180) {
			difference += difference > 0 ? -360 : 360;
		}
		if (difference > 0) {
			this.heading += this.turnSpeed;
		} else if (difference < 0) {
			this.heading -= this.turnSpeed;
		}

		/* Reference: https://gamedev.stackexchange.com/questions/49613/how-to-rotate-enemy-to-face-player */
		/* Original implementation below */
//		if (desired > this.heading) {
//			this.heading += turnSpeed;
//		} else if (desired < this.heading){
//			this.heading -= turnSpeed;
//		}
	}

	public void update(Player player) {
		super.update();
		trackPlayer(player.position,player.velocity);
		thrust();
	}

}
