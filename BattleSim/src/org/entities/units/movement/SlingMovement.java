package org.entities.units.movement;

import org.entities.units.Unit;
import org.util.Vector2D;

public class SlingMovement implements MovementModule {

	private Unit myHost;
	
	private double vx = 1;
	private double vy = 0;
	
	private double accely = 0.05;
	private double friction = 0.93;
	
	private boolean ascending = true;
	private int counterMax = 50;
	private int counter = 0;
	
	public SlingMovement(Unit host) {
		myHost = host;
	}
	
	@Override
	public void moveHost() {
		if(ascending) {
			counter++;
			//swap direction
			if(counter > counterMax) ascending = false;
			
			vy += accely;
			
		}else {
			counter--;
			//swap direction
			if(counter < -counterMax) ascending = true;
			
			vy -= accely;
			
		}
		
		vy *= friction;
		
		myHost.move(vx, vy);
	}

	@Override
	public Vector2D getVelocity() {
		return new Vector2D(vx, vy);
	}
}
