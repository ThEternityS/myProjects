package org.entities.units;

import org.entities.Alive;

public class UnitPart extends Unit implements Animate, Alive {
	
	private CompoundUnit myHost;
	private double fireAngle;
	
	public UnitPart(CompoundUnit cu, int x, int y, double direction) {
		super(x, y);
		myHost = cu;
		this.fireAngle = direction;
	}
	
	@Override
	public Object partOf() {
		return myHost;
	}

	@Override
	public Affiliation getAffiliation() {
		return myHost.getAffiliation();
	}

	@Override
	public boolean isAlive() {
		return myHost.isAlive();
	}

	@Override
	public double getDirection() {
		return fireAngle;
	}
}
