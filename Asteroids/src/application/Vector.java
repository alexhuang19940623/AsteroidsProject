package application;

// https://www.youtube.com/watch?v=9xsT6Z6HQfw&t=1798s&ab_channel=LeeStemkoski
// Where we found the vector class.
public class Vector {
	private double x;
	private double y;

	public Vector() {
		this.set(0,  0);

	}
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector other) {
		this.x += other.x;
		this.y += other.y;
	}

	public void subtract(Vector other) {
		this.x -= other.x;
		this.y -= other.y;
	}

	public void multiply(double m) {
		this.x *= m;
		this.y *= m;
	}

	public double getLength() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setLength( double l) {
		double currentLength = this.getLength();
		if (currentLength == 0)
			return;
		// Make vector length 1
		this.multiply(1/currentLength);
		// scale vector to have length L
		this.multiply(l);
	}

	public double getAngle() {
		return Math.toDegrees(Math.atan2(this.y,  this.x));
	}

	public void setAngle(double angle) {
		double l = this.getLength();
		double angleRadians = Math.toRadians(angle);
		this.x = l * Math.cos(angleRadians);
		this.y = l * Math.sin(angleRadians);
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

}
