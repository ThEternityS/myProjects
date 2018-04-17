package org.entities.units.shooting;

import org.entities.EffectInitiator;
import org.entities.units.Collideable;
import org.entities.units.Unit;

public class LaserShooter implements ShootingModule {

	private Unit myHost;
	
	private TargetAquisition myTargeting;
	
	private Collideable target;
	private int targetAquisitionTime = 1;
	private int aquisitionTimer;
	
	public LaserShooter(Unit host) {
		this.myHost = host;
		this.myTargeting = new TargetClosest(host);
		this.aquisitionTimer = targetAquisitionTime;
	}
	
	@Override
	public void shoot() {
		
		Collideable c = myTargeting.getCurrentTarget();
		//target stayed the same
		if(c != null && target == c) {
			aquisitionTimer--;
			if(aquisitionTimer <= 0) {
				
				//shoot here
				EffectInitiator.getInstance().createLineEffect(myHost.getX() + myHost.getRadius(), myHost.getY() + myHost.getRadius(), target.getX() + target.getRadius(), target.getY() + target.getRadius());
				
				return;
			}
		}else if(target != c){
			//set new target
			target = c;
			//reset target timer if target swapped
			aquisitionTimer = targetAquisitionTime;
		}
	}
}
