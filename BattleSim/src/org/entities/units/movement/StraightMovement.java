package org.entities.units.movement;

import org.entities.units.Unit;
import org.util.Vector2D;

public class StraightMovement implements MovementModule {

	public Unit myHost;
	
	public StraightMovement(Unit host) {
		myHost = host;
	}
	
	@Override
	public void moveHost() {
		myHost.move(1, 0);
	}

	@Override
	public Vector2D getVelocity() {
		return new Vector2D(1, 0);
	}

}
