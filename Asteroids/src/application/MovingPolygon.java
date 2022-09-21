package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class MovingPolygon extends Polygon {
	protected Vector position; // The polygon's x & y coordinates in the game-space
	protected Vector velocity; // Vector's magnitude is the polygon's speed, Vector's angle is the Polygon's cirection of travel
	protected double heading; // The direction the polygon is facing in degrees
	protected double proximityRadius; // Distance from the polygon's origin to its furthest point
	protected double[] outlineX; // A double array of all of the polygon's x-coordinates - used for rendering
	protected double[] outlineY; // A double array of all of the polygon's y-coordinates - used for rendering
	protected Color colour; // The colour of the polygon's outline

	public void render(GraphicsContext context) {
		/* Used to render the polygon in the game */
		context.save(); // Save the state of the graphicsContext's transformations to be restored later (e.g. with 0 translation or rotation)
		context.translate(this.position.getX(),this.position.getY()); // move the graphicsContext so when the polygon is rendered it's rendered in the correct position
		context.rotate(this.heading); // rotate the graphicsContext so when the polygon is rendered it is rendered facing the correct direction
		context.setFill(Color.WHITE); // set the fill colour of the polygon
		Bloom bloom = new Bloom(0.8); // set the bloom (a type of glow effect) intensity
		context.setEffect(bloom); // sets the graphicsContext to draw with the bloom
		context.setLineWidth(1.0); // sets the line width of the polygon
		context.setStroke(this.colour); // sets the line colour of the polygon
		context.strokePolygon(this.getOutlineX(),this.getOutlineY(),this.getOutlineX().length); // draws the white interior of the polygon
		context.fillPolygon(this.getOutlineX(),this.getOutlineY(),this.getOutlineX().length); // draws the outline fo the polygon
		context.restore(); // restores the graphicsContext (e.g. zeros out the translation & rotation)
	}

	public void update() {
		/* MOVE OBJECT */
		this.position.add(this.velocity);
		/* Restricting heading value to 0-360 degrees */
		this.heading = this.heading%360;

		/* SCREEN WRAPPING */
		if (this.position.getX() > Main.screenWidth) {
			this.position.setX(this.position.getX()-Main.screenWidth);
		}
		if (this.position.getX() < 0) {
			this.position.setX(this.position.getX()+Main.screenWidth);
		}
		if (this.position.getY() > Main.screenHeight) {
			this.position.setY(this.position.getY()-Main.screenHeight);
		}
		if (this.position.getY() < 0) {
			this.position.setY(this.position.getY()+Main.screenHeight);
		}
		
		/* SET POSITION & ROTATION OF HITBOX */
		this.setTranslateX(this.position.getX());
		this.setTranslateY(this.position.getY());
		this.setRotate(this.heading);
	}
	
	public boolean isProximal(MovingPolygon other) {
		/* This function checks the distance between this polygon and another polygon,
		 * this is to see if there's a chance the objects might intersect,
		 * if the objects are not proximal to one another they won't intersect.
		 * It works by adding the proximalRadii of the polygons
		 * and comparing it to the distance between the polygons.
		 * If the distance between the polygons is less than the summed prxomityRadii then
		 * the polygons are proximal and may intersect (so returns true), otherwise it returns false */
		if (this.distanceTo(other) < (this.proximityRadius + other.proximityRadius)) {
			return true;
		} else {
			return false;
		}
	}

	public double distanceTo(MovingPolygon other) {
		/* A function that gets the distance between the position of this polygon and another polygon */
		Vector distanceVector = new Vector(this.getPosition().getX(),this.getPosition().getY()); // create a new vector based on the polygon's position
		distanceVector.subtract(other.getPosition()); // subtract the vector of the other polygon's position, leaving a vector that goes from one position to the other
		return distanceVector.getLength(); // return the length of this vector
	}
	
	public boolean checkCollision(MovingPolygon other) {
		/* Checks if two polygons intersect and returns true if they do or false if not */
		Shape intersection = Shape.intersect(this, other);
		return intersection.getBoundsInLocal().getWidth() != -1;
	}
		
	/* GETTERS AND SETTESR */
	public Vector getPosition() {
		return this.position;
	}
	public Vector getVelocity() {
		return this.velocity;
	}

	public double getHeading() {
		return this.heading;
	}

	public void setPosition(Vector newPosition) {
		this.position = newPosition;
	}

	public void setHeading(double newHeading) {
		this.heading = newHeading;
	}

	public void setVelocity(Vector newVelocity) {
		this.velocity = newVelocity;
	}
	
	public void generateOutlineCoordinates() {
		/* This method is used to generate the two double[] arrays used for rendering.
		 * It takes the polygon's coordinates (which are used as a hitbox) and separates
		 * out the x & y coordinates and adds them to the relevant double array. */
		this.outlineX = new double[this.getPoints().size()/2];
		this.outlineY = new double[this.getPoints().size()/2];
		for (int i=0; i < this.getPoints().size()/2; i++) {
			this.outlineX[i] = this.getPoints().get(2*i);
			this.outlineY[i] = this.getPoints().get(2*i+1);
		}		
	}
	
	public double[] getOutlineX() {
		return this.outlineX;
	}
	
	public double[] getOutlineY() {
		return this.outlineY;
	}
	
}