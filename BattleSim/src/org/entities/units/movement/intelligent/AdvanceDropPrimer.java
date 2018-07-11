package org.entities.units.movement.intelligent;

public class AdvanceDropPrimer implements MovementFSMPrimer{

	@Override
	public void primeMovementFSM(MovementFSM mfsm) {
		mfsm.pushState(new DropState());
		mfsm.pushState(new AdvanceState());
	}

}
