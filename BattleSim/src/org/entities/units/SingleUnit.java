package org.entities.units;

import org.entities.EffectInitiator;
import org.entities.Updateable;
import org.entities.units.movement.MovementBehaviour;
import org.entities.units.movement.MovementModule;
import org.entities.units.movement.SlingMovement;
import org.entities.units.movement.StraightMovement;
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
				myMovements = new IntelligentMovement(this);
		}
		
		switch(shooting) {
			case PEACEFUL:
				myShooter = null;
			case LASER:
				myShooter = new LaserShooter(this);
		}
	}
	
	@Override
	public void update() {
		
		if(myMovements != null) {
			myMovements.moveHost();
			
			/*
			double ex = this.getX() + this.getRadius();
			double ey = this.getY() + this.getRadius();
			double ex2 = ex - myMovements.getVelocity().getX() * 10;
			double ey2 = ey - myMovements.getVelocity().getY() * 10;
			EffectInitiator.getInstance().createLineEffect(ex, ey, ex2, ey2);
			*/
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
