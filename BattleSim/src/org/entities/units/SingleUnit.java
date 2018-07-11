package org.entities.units;

import org.entities.Updateable;
import org.entities.units.movement.MovementBehaviour;
import org.entities.units.movement.MovementModule;
import org.entities.units.movement.SlingMovement;
import org.entities.units.movement.StraightMovement;
import org.entities.units.movement.intelligent.EncirclePrimer;
import org.entities.units.movement.intelligent.IntelligentMovement;
import org.entities.units.shooting.LaserShooter;
import org.entities.units.shooting.ShootingBehaviour;
import org.entities.units.shooting.ShootingModule;

public class SingleUnit extends Unit implements Updateable {
	
	private boolean living;
	private Affiliation affiliation;
	private MovementModule myMovements;
	private ShootingModule myShooter;
	
	public SingleUnit(int x, int y, Affiliation affil, MovementBehaviour movement, ShootingBehaviour shooting) {
		super(x, y);
		living = true;
		affiliation = affil;
		
		switch(movement) {
			case STAGNANT:
				myMovements = null;
				break;
			case STRAIGHT:
				myMovements = new StraightMovement(this);
				break;
			case SLING:
				myMovements = new SlingMovement(this);
				break;
			case INTELLIGENT:
				myMovements = new IntelligentMovement(this, new EncirclePrimer(new CollisionScheme(100, 100, 0)));
		}
		
		switch(shooting) {
			case PEACEFUL:
				myShooter = null;
				break;
			case LASER:
				myShooter = new LaserShooter(this);
		}
	}
	
	@Override
	public void update() {
		
		if(myMovements != null) {
			myMovements.moveHost();
		}
		
		if(myShooter != null) {
			myShooter.shoot();
		}
	}

	@Override
	public Affiliation getAffiliation() {
		return affiliation;
	}

	@Override
	public boolean isAlive() {
		return living;
	}

	@Override
	public double getDirection() {
		return (myMovements.getVelocity()).getAngle();
	}

}
