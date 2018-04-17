package org.entities.units;

public interface Collideable {

	public static boolean collide(Collideable c1, Collideable c2) {
		//collision with itself
		if(c1.partOf() == c2.partOf()) return false;
		
		double distX = c2.getX() - c1.getX();
		double distY = c2.getY() - c1.getY();
		double rads = c1.getRadius() + c2.getRadius();

		return distX * distX + distY * distY <= rads * rads;
	}
	
	public static double getDistance(Collideable c1, Collideable c2) {
		double distX = c2.getX() - c1.getX();
		double distY = c2.getY() - c1.getY();
		return Math.sqrt(distX * distX + distY * distY);
	}
	
	public double getX();
	public double getY();
	public double getRadius();
	public Object partOf();
}
