package application;

import javafx.scene.paint.Color;

public class Shrapnel extends MovingPolygon {
	private int lifetime;
	private double turnSpeed;
	
	public Shrapnel(Vector vel, Vector pos) {
		this.getPoints().setAll(
		-1.0,0.0,
		0.0,1.0,
		1.0,0.0,
		0.0,-1.0
		);
		this.generateOutlineCoordinates();		
		this.lifetime = (int) (15 + Math.floor(Math.random()*10));
		this.position = new Vector(pos.getX(),pos.getY());
		this.velocity = new Vector((Math.random()-0.5)*4,(Math.random()-0.5)*4);
		this.velocity.add(vel);
		this.heading = 0;
		this.turnSpeed = 1;
		this.colour = Color.ORANGE;
	}
	
	public Shrapnel(Vector vel, Vector pos, boolean special) {
		this.getPoints().setAll(
				0.0, 1.8,
				-3.6, 1.8,
				-3.6, 3.6,
				0.0, 2.7,
				-0.9, 3.6,
				0.0, 5.4,
				0.9, 5.4,
				1.8, 3.6,
				0.9, 2.7,
				3.6, 3.6,
				4.5, 2.7,
				0.9, 1.8,
				0.9, -1.8,
				-0.9, -6.3,
				-1.8, -5.4,
				0.0, -1.8,
				-3.6, -4.5,
				-4.5, -3.6,
				0.0, -0.9
		);
		this.generateOutlineCoordinates();		
		this.lifetime = 40;
		this.position = new Vector(pos.getX(),pos.getY());
		this.velocity = new Vector(vel.getX(),vel.getY());
		this.velocity.multiply(1.2);
		this.heading = 0;
		this.turnSpeed = 7;
		this.colour = Color.BLACK;
	}
	
	
	@Override
	public void update() {
		super.update();
		this.heading += this.turnSpeed;
		this.lifetime--;
	}
	
	public int getLifetime() {
		return this.lifetime;
	}
}
