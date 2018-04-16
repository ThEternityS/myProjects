package org.util;

public class Vector2D {

	private double x;
	private double y;
	
	public Vector2D() {
		this(0, 0);
	}
	
	public Vector2D(double x, double y) {
		set(x, y);
	}
	public Vector2D(Vector2D vec) {
		this(vec.getX(), vec.getY());
	}
	
	public void add(Vector2D summand) {
		this.addX(summand.getX());
		this.addY(summand.getY());
	}
	public void addX(double summand) {
		this.x += summand;
	}
	public void addY(double summand) {
		this.y += summand;
	}
	
	public void rotate(double angle) {
		this.setX(x * Math.cos(angle) - y * Math.sin(angle));
		this.setY(x * Math.sin(angle) + y * Math.cos(angle));
	}
	
	public void truncate(double maxLength) {
		if(getLength() > maxLength) {
			this.scaleTo(maxLength);
		}
	}
	
	public void normalize() {
		scaleTo(1);
	}
	
	public void scaleTo(double targetLength) {
		this.scale(targetLength / getLength());
	}
	
	public void scale(double factor) {
		this.scaleX(factor);
		this.scaleY(factor);
	}
	public void scaleX(double factor) {
		this.x *= factor;
	}
	public void scaleY(double factor) {
		this.y *= factor;
	}
	
	public double getLength() {
		return Math.sqrt(x * x + y * y);
	}
	
	public double getAngle() {
		return Math.atan2(y, x);
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void set(double x, double y) {
		setX(x);
		setY(y);
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public static Vector2D normalVector(Vector2D start) {
		return new Vector2D(start.getX(), -start.getY());
	}
	
	public static Vector2D intersection(Vector2D start1, Vector2D direction1, Vector2D start2, Vector2D direction2) {
		
		
		
		
		return null;
	}
	
}
