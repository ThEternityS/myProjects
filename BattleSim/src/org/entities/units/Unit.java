package org.entities.units;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import org.entities.Alive;
import org.entities.Directional;
import org.entities.Renderable;
import org.main.ImageCreator;

public abstract class Unit implements Animate, Renderable, Alive, Directional {

	private static final int RADIUS = 8;
	private static final Image[] MY_IMAGE;
	static {
		MY_IMAGE = new Image[2];
		MY_IMAGE[0] = ImageCreator.createCircle(Color.GREEN, RADIUS);
		MY_IMAGE[1] = ImageCreator.createCircle(Color.RED, RADIUS);
	}
	
	protected double x;
	protected double y;
	
	public Unit(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(double xOffset, double yOffset) {
		this.x += xOffset;
		this.y += yOffset;
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
		return RADIUS;
	}

	@Override
	public Object partOf() {
		return this;
	}
	
	@Override
	public void render(Graphics2D g2d) {
		
		Image img = MY_IMAGE[getAffiliation().ordinal()];
		
		int x1 = (int) (this.getX() + 0.5);
		int y1 = (int) (this.getY() + 0.5);
		
		int d = RADIUS * 2;
		
		int x2 = x1 + d;
		int y2 = y1 + d;
		
		g2d.drawImage(img, x1, y1, x2, y2, 0, 0, d, d, null);
	}
}
