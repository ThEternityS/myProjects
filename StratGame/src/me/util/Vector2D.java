package me.util;

import java.util.Collection;

public class Vector2D {

	private double x;
	private double y;
	
	public Vector2D(double x, double y) {
		set(x, y);
	}
	
	public Vector2D(Vector2D v) {
		this(v.getX(), v.getY());
	}
	
	public void maxSize(double maxX, double maxY) {
		if(x > maxX) x = maxX;
		if(y > maxY) y = maxY;
	}
	public void minSize(double minX, double minY) {
		if(x < minX) x = minX;
		if(y < minY) y = minY;
	}
	public void minAndMax(double abs) {
		minSize(-abs, -abs);
		maxSize(abs, abs);
	}
	
	public void add(Vector2D summand) {
		add(summand.getX(), summand.getY());
	}
	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	/**
	 * scales the x value of the vector
	 * @param factorX to scale by
	 */
	public void scaleX(double factorX) {
		this.x *= factorX;
	}
	
	/**
	 * scales the y value of the vector
	 * @param factorY to scale by
	 */
	public void scaleY(double factorY) {
		this.y *= factorY;
	}
	
	
	/**
	 * scales the length of the vector
	 * @param factor to scale by
	 */
	public void scale(double factor) {
		scaleX(factor);
		scaleY(factor);
	}
	
	public void scaleTo(double p_targetLength) {
		this.scale(Math.sqrt((p_targetLength * p_targetLength) / (x * x + y * y)));
	}
	
	public void truncate(double p_maxLength) {
		if(this.length() > p_maxLength) this.scaleTo(p_maxLength);
	}
	
	public Vector2D average(Collection<Vector2D> p_cloud) {
		double _x = 0;
		double _y = 0;
		for(Vector2D $v: p_cloud) {
			_x += $v.getX();
			_y += $v.getY();
		}
		Vector2D _average = new Vector2D(_x, _y);
		_average.scale(1 / p_cloud.size());
		return _average;
	}
	
	public double calcAngle() {
		return Math.atan2(this.x, -this.y);
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void set(double x, double y) {
		setX(x);
		setY(y);
	}
	
	public double getX() {
		return x;
	}
	public int getIntX() {
		return (int) x;
	}
	public double getY() {
		return y;
	}
	public int getIntY() {
		return (int) y;
	}
	
	public static Vector2D sum(Vector2D summand1, Vector2D summand2) {
		return new Vector2D(summand1.getX() + summand2.getX(), summand1.getY() + summand2.getY());
	}
	
	public static Vector2D diffrence(Vector2D subtrahend, Vector2D minuend) {
		return new Vector2D(subtrahend.getX() - minuend.getX(), subtrahend.getY() - minuend.getY());
	}
	
	public static Vector2D product(Vector2D base, double factor) {
		return new Vector2D(base.getX() * factor, base.getY() * factor);
	}
	
	public static Vector2D orthogonal(Vector2D v) {
		return new Vector2D(v.getX(), -v.getY());
	}
	
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y;
	}
}
