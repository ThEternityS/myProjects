package org.entities.units.movement.intelligent;

import org.entities.units.Collideable;
import org.entities.units.Unit;
import org.entities.units.movement.MovementModule;
import org.main.EntityHolder;
import org.util.Vector2D;

public class IntelligentMovement implements MovementModule {

	private static final double FRICTION = 0.75;
	private static final int OBSERVE_RADIUS = 35;
	private static final int KEEP_DISTANCE = 20;
	
	private Unit myHost;
	private MovementFSM myFSM;
	
	private double maxForce = 0.4;
		
	private Vector2D velocity;
	private Vector2D force;

	
	public IntelligentMovement(Unit host, MovementFSMPrimer movementPrimer) {
		myHost = host;
		
		myFSM = new MovementFSM(this);
		movementPrimer.primeMovementFSM(myFSM);
		
		velocity = new Vector2D();
		force = new Vector2D(); 
	}
	
	@Override
	public Vector2D getVelocity() {
		return velocity;
	}
	
	@Override
	public void moveHost() {
		
		myFSM.execute();
		
		finalizeMovements();
	}
	
	private void finalizeMovements() {
		force.truncate(maxForce);
		velocity.add(force);
		velocity.scale(FRICTION);
		myHost.move(velocity.getX(), velocity.getY());
		force.set(0, 0);
	}
	
	public void avoidCollisions() {
		Vector2D avoidanceForce = new Vector2D();
		for(Collideable peer: EntityHolder.getUnitsInVincinity(myHost, OBSERVE_RADIUS)) {
			Vector2D unitConnection = vectorToHost(peer);
			double inverseFac = OBSERVE_RADIUS / (KEEP_DISTANCE + unitConnection.getLength());
			unitConnection.scale(inverseFac);
			avoidanceForce.add(unitConnection);
		}
		avoidanceForce.truncate(maxForce);
		
		force.add(avoidanceForce);
	}
	
	public void moveStraight() {
		force.addX(maxForce);
	}
	public void moveDown( ) {
		force.addY(maxForce);
	}
	
	public void steeringInertia() {
		Vector2D inertiaForce = new Vector2D(velocity);
		inertiaForce.truncate(maxForce);
		force.add(inertiaForce);
	}

	private Vector2D vectorToHost(Collideable c) {
		Vector2D vec = new Vector2D();
		vec.setX(myHost.getX() - c.getX());
		vec.setY(myHost.getY() - c.getY());
		return vec;
	}
}
