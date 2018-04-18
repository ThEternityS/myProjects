package org.entities.units.shooting;

import org.entities.initiator.EffectInitiator;
import org.entities.units.Collideable;
import org.entities.units.Unit;

public class LaserShooter implements ShootingModule {

	private Unit myHost;
	
	private TargetAquisition myTargeting;
	
	private Collideable target = null;
	
	public LaserShooter(Unit host) {
		this.myHost = host;
		this.myTargeting = new TargetClosest(host);
	}
	
	@Override
	public void shoot() {
		
		target = myTargeting.getCurrentTarget();
		//target stayed the same
		if(target != null) {
			
			double midX = myHost.getX() + myHost.getRadius();
			double midY = myHost.getY() + myHost.getRadius();
			double tmx = target.getX() + target.getRadius();
			double tmy = target.getY() + target.getRadius();
							
			EffectInitiator.getInstance().createLineEffect(midX, midY, tmx, tmy);
			return;
		}
	}
}
