package org.entities.units.movement.intelligent;

public class DropState implements MovementState {
	
	@Override
	public void executeMovement(MovementFSM fsm) {
		IntelligentMovement mm = fsm.getMovementComponent();
		mm.moveDown();
		mm.avoidCollisions();
		mm.steeringInertia();
	}
}
