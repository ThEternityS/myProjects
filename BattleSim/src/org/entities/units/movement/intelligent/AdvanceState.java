package org.entities.units.movement.intelligent;

public class AdvanceState implements MovementState {

	private int i;
	
	public AdvanceState() {
		i = 300;
	}
	
	@Override
	public void executeMovement(MovementFSM fsm) {
		
		i--;
		if(i <= 0) fsm.popState();
		
		IntelligentMovement mm = fsm.getMovementComponent();
		mm.moveStraight();
		mm.avoidCollisions();
		mm.steeringInertia();
	}	
}
