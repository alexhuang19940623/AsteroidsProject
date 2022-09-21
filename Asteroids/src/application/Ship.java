package application;

import java.util.ArrayList;

import javafx.scene.media.AudioClip;

public class Ship extends MovingPolygon {
	AudioClip pewpew = new AudioClip(getClass().getResource("/enemy.wav").toExternalForm());
	AudioClip dies = new AudioClip(getClass().getResource("/boom.wav").toExternalForm());
	protected int turnSpeed = 7;
	protected double acceleration = 0.2;
	protected double maxSpeed = 8;

	public Bullet shoot() {
		pewpew.play();
		Bullet bullet = new Bullet(this.heading, this.getPosition(), this.velocity);
		return bullet;

	}

	public void thrust() {
		// generate thrust vector
		double thrustX = acceleration*Math.cos(Math.toRadians(this.heading));
		double thrustY = acceleration*Math.sin(Math.toRadians(this.heading));
		Vector thrust = new Vector(thrustX,thrustY);

		// apply thrust
		this.velocity.add(thrust);

		// reduce velocity magnitude if it exceeds maxSpeed
		if (this.velocity.getLength() > this.maxSpeed) {
			this.velocity.setLength(this.maxSpeed);
		}
	}
	
	public ArrayList<Shrapnel> shipGoesBoom() {
		dies.play();
		ArrayList<Shrapnel> explosion = new ArrayList<Shrapnel>();
		for (int i=0; i<20; i++) {
				explosion.add(new Shrapnel(this.velocity,this.position));
			}
		return explosion;
	}


}
