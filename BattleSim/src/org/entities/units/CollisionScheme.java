package org.entities.units;

public class CollisionScheme implements Collideable {

	private double x, y;
	private int radius;
	
	public CollisionScheme(double x, double y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getRadius() {
		return radius;
	}

	@Override
	public Object partOf() {
		return this;
	}

}
