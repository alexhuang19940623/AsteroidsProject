package application;

import java.util.ArrayList;

import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class Asteroid extends MovingPolygon {
	AudioClip dies = new AudioClip(getClass().getResource("/rocks.wav").toExternalForm()); // used for destruction sound effect
	private int wedges = 20; // number of wedges used to generate an asteroid's coordinates
	private double scale; // asteroids have scale to differentiate between small (0.25), medium (0.5) and large (1)
	private double speedFactor = 2; // this determines how much the scale affects the speed
	private double turnSpeed; // how quickly the asteroid rotates
	private double inner_radius = 27; // default size for large asteroids (scale = 1)
	private double outer_ratio = 1.5; // set as a ratio to enable easier scaling

	public Asteroid(Player player) {
		/* Default constructor, used for large asteroids */
		this.scale = 1.0; // default size
		/* OUTLINE */
		generateShape(); // generates coordinates for the asteroid (used for hitbox/intersection)
		this.generateOutlineCoordinates(); // separates the coordinates for rendering
		/* POSITION & VELOCITY */
		this.heading = Math.random()*360; // start facing a random direction
		origin(player.getPosition()); // generate a random starting position that won't be on the player
		this.velocity = new Vector(1.0/(this.scale*speedFactor)+Math.random(),0.0); // random speed based on scale, initially the vector has 0 direction (hence 0.0 as 2nd coordinate)
		this.velocity.setAngle(this.heading); // turn the velocity in a random direction (heading was set as random, may as well use that)
		this.turnSpeed = (Math.random()-0.5)*3.5; // random turn speed & direction
		this.colour = Color.VIOLET; // asteroid outline colour
		this.proximityRadius = 27*1.5; // Furthest point cannot be larger than outer radius used to generate shape
	}
	
	public Asteroid(double size, Vector pos, Vector vel) {
		/* Constructor used for smaller asteroids, takes into account parent asteroid position & velocity */
		this.scale = size; // parent asteroid lets constructor know what scale to use
		this.wedges = (int) (30*this.scale); // smaller asteroids need less detail so use reduced number of wedges (optimization attempt)
		/* OUTLINE */
		generateShape(); // generate coordinates (used for hitbox)
		this.generateOutlineCoordinates(); // separates coordinates into double arrays used for rendering
		/* POSITION & VELOCITY */
		this.heading = vel.getAngle() + (Math.random()-0.5)*90; // initial direction determined by parent asteroid with random element added to it
		this.position = new Vector(pos.getX(),pos.getY()); // initial position same as parent asteroid
		this.velocity = new Vector(1.0/(this.scale*speedFactor)+Math.random(),0.0); // initial velocity determined by parent asteroid, scale plus random element
		this.velocity.setAngle(this.heading); // set initial direction of travel to heading
		this.turnSpeed = (Math.random()-0.5)*3.5; // random turn speed and direction
		this.colour = Color.VIOLET; // asteroid outline colour
		this.proximityRadius = 27*1.5*this.scale; // Furthest point cannot be larger than outer radius used to generate shape
	}
	

	private void generateShape() {
		double wedgeAngle = 360/wedges; // angular size of each wedge
		for (int i = 0; i < wedges; i++) {
			/* GET RANDOM ANGLE WITHIN WEDGE */
			double angle = wedgeAngle*i + Math.random()*wedgeAngle;
			// wedgeAngle*i is the start of the wedge
			// Math.random()*wedgeAngle is a random number of degrees (up to the size of a wedge angle) to increment along

			/* SET THE RADIUS LENGTH */
			double outer_radius = inner_radius*outer_ratio;
			double radius_difference = outer_radius-inner_radius;
			// set a random radius length between inner_radius & outer_radius & scale it depending on asteroid scale
			Vector radius = new Vector(scale*(inner_radius + Math.random()*radius_difference),0.0);
			// point the angle in the correct direction
			radius.setAngle(angle);

			/* ADD POINTS TO SHAPE */
			this.getPoints().addAll(radius.getX(),radius.getY());
		}
	}
	
	private void origin(Vector playerPos) {
		Vector origin = new Vector(playerPos.getX(),playerPos.getY()); // asteroid origin is set to same position as player
		Vector randomShift = new Vector(100+Math.random()*(Main.screenWidth-200),100+Math.random()*(Main.screenWidth-200)); // moved at least 100 away from player + random element up to (the screen size - 200), this will ensure no asteroid spawn with 100 of the player
		origin.add(randomShift); // apply random shift
		this.position = origin; // set the asteroids position, this may end up off the screen but the screen wrap will take care of that in the first update()
	}

	public ArrayList<Asteroid> asteroidGoesBoom() {
		dies.play(); // play asteroid destroyed sound effect
		ArrayList<Asteroid> littleBois = new ArrayList<Asteroid>(); // declare asteroid array for new asteroids
		double newScale; // declare new scale
		// set new scale based on old scale
		if (this.scale == 1) {
			newScale = 0.5;
		} else if (this.scale == 0.5) {
			newScale = 0.25;
		} else {
			return littleBois;
		}
		// generate new asteroids
		for (int i=0; i<2; i++) {
			littleBois.add(new Asteroid(newScale,this.position,this.velocity));
		}
		// return new asteroids
		return littleBois;
	}
	
	@Override
	public void update() {
		super.update();
		this.heading += this.turnSpeed; // adds constant rotation to update
	}
	
	/* GETTERS & SETTESR */
	public double getScale() {
		return this.scale;
	}
	
	// value is based on size
	public int getValue() {
		int value;
		if (this.scale == 1) {
			value = 20;
		} else if (this.scale == 0.5) {
			value = 50;
		} else {
			value = 100;
		}
		return value;
	}
}