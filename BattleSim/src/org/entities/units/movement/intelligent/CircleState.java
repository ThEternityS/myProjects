package org.entities.units.movement.intelligent;

import org.entities.units.Collideable;

public class CircleState implements MovementState {

	Collideable target;

	public CircleState(Collideable c) {
		this.target = c;
	}
	
	@Override
	public void executeMovement(MovementFSM fsm) {
		
		IntelligentMovement mm = fsm.getMovementComponent();
		
		mm.encircle(target);
		
		mm.avoidCollisions();
		mm.steeringInertia();
	}

}
