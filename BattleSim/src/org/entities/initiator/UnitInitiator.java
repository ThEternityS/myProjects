package org.entities.initiator;

import org.entities.units.Affiliation;
import org.entities.units.movement.MovementBehaviour;
import org.entities.units.shooting.ShootingBehaviour;

public class UnitInitiator extends Initiator {

	private static UnitInitiator myInstance;
	
	private UnitInitiator() {}
	
	public static UnitInitiator getInstance() {
		if(myInstance == null) myInstance = new UnitInitiator();
		return myInstance;
	}
	
	public boolean createAlliedStagnantUnit(int x, int y) {
		if(this.getHolder() == null) return false;
		return this.getHolder().createSingleUnit(x, y, Affiliation.ALLY, MovementBehaviour.STAGNANT, ShootingBehaviour.LASER);
	}
	
	public boolean createAlliedIntelligentUnit(int x, int y) {
		if(this.getHolder() == null) return false;
		return this.getHolder().createSingleUnit(x, y, Affiliation.ALLY, MovementBehaviour.INTELLIGENT, ShootingBehaviour.LASER);
	}
	
	public boolean createEnemyStagnantUnit(int x, int y) {
		if(this.getHolder() == null) return false;
		return this.getHolder().createSingleUnit(x, y, Affiliation.ENEMY, MovementBehaviour.STAGNANT, ShootingBehaviour.PEACEFUL);
	}

}
