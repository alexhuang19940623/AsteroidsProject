package application;

import javafx.scene.paint.Color;

public class Bullet extends MovingPolygon {
	private int lifespan = 120;

	public Bullet(double shipHeading, Vector shipPosition, Vector shipVelocity) {
		/* BULLET OUTLINE */
		this.getPoints().setAll(
		-2.0,0.0,
		0.0,2.0,
		2.0,0.0,
		0.0,-2.0
		);
		this.generateOutlineCoordinates();
		/* POSITION & VELOCITY */
		this.position = new Vector(shipPosition.getX(),shipPosition.getY()); // fired from the ship
		this.velocity = new Vector(5.0,0.0); // speed bullet is fired
		this.velocity.setAngle(shipHeading); // set direction of bullet to direction of ship
		this.velocity.add(shipVelocity); // take into account ship velocity
		this.colour = Color.CYAN;
		this.proximityRadius = 2.0;
	}

	public void decremientLifespan() {
		this.lifespan --;
	}

	public int getLifespan() {
		return this.lifespan;
	}
}
