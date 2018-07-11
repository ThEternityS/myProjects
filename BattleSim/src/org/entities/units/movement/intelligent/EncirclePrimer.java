package org.entities.units.movement.intelligent;

import org.entities.units.Collideable;

public class EncirclePrimer implements MovementFSMPrimer {

	Collideable target;
	
	public EncirclePrimer(Collideable c) {
		this.target = c;
	}
	
	@Override
	public void primeMovementFSM(MovementFSM mfsm) {
		mfsm.pushState(new CircleState(target));
	}

}
