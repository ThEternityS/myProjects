package org.entities.units.shooting;

import java.util.LinkedList;
import java.util.List;

import org.entities.units.Animate;
import org.entities.units.Collideable;
import org.main.EntityHolder;

public class TargetClosest implements TargetAquisition {

	private int targetRadius = 50;
	
	private Animate myHost;
	
	public TargetClosest(Animate host) {
		this.myHost = host;
	}
	
	@Override
	public Collideable getCurrentTarget() {
		
		Collideable closest = null;
		double distance = targetRadius + 1;
		
		List<Animate> possibleTargets = EntityHolder.getUnitsInVincinity(myHost, targetRadius);

		//remove all friendly ships
		possibleTargets.removeAll(getFriends(possibleTargets));
		
		
		//find closest enemy
		
		//check if in vision
		
		
		for(Animate target: possibleTargets) {
			if(closest == null) {
				closest = target;
				continue;
			}
			
			double currentDistance = Collideable.getDistance(myHost, target);
			if(currentDistance < distance) {
				closest = target;
				distance = currentDistance;
			}
		}
		return closest;
	}
	
	private List<Animate> getFriends(List<Animate> allShips) {
		List<Animate> friendlyShips = new LinkedList<Animate>();
		for(Animate ship: allShips) {
			if(ship.getAffiliation() == myHost.getAffiliation()) friendlyShips.add(ship);
		}
		return friendlyShips;
	}
}
