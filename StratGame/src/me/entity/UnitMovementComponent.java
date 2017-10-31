package me.entity;

import java.util.Set;

import me.main.managers.UnitManager;
import me.util.Vector2D;

public class UnitMovementComponent implements Updateable {

	private static final double FRICTION = 0.8;
	private static final double MAX_FORCE = 0.5;
	private static final double CUT_OFF_VELOCITY = 0.1;
	
	private static final double SLOWING_DISTANCE = 80;
	
	private static final int SPACING_DISTANCE = 25;
	private static final int LEEWAY_DISTANCE = 1;
	
	private UnitManager myUnitManager;
	private Unit host;
	
	private Vector2D velocity;
	private Vector2D steeringForce;

	public UnitMovementComponent(UnitManager p_manager, Unit p_host) {
		this.myUnitManager = p_manager;
		this.host = p_host;
		velocity = new Vector2D(0, 0);
		steeringForce = new Vector2D(0, 0);
	}
	
	/**
	 * initiates a fluid movement of the host unit towards the given position slowing down on approach
	 * @param p_target to move towards
	 */
	public void moveTo(Vector2D target) {
		Vector2D directionForce = calcDistance(target);
		double distance = directionForce.length();
		
		if(distance < LEEWAY_DISTANCE) return;
		
		directionForce.scaleTo(MAX_FORCE);
		if(distance < SLOWING_DISTANCE) {
			directionForce.scale(distance / SLOWING_DISTANCE);
		}

		steeringForce.add(directionForce);
	}
	
	/**
	 * avoid collision with other units
	 */
	public void collisionAvoidance() {
		BaseEntity avoidanceSquare = new BaseEntity(host.getPosition().getIntX() - SPACING_DISTANCE, host.getPosition().getIntY() - SPACING_DISTANCE, SPACING_DISTANCE * 2, SPACING_DISTANCE * 2);
		Set<Unit> unitsToAvoid =  myUnitManager.getAllContainedIn(avoidanceSquare);
		unitsToAvoid.remove(host);
		
		Vector2D avoidanceForce = new Vector2D(0, 0);
		for(Unit $u: unitsToAvoid) {
			avoidanceForce.add(calcAvoidUnitForce($u));
		}
		
		avoidanceForce.truncate(MAX_FORCE);
		steeringForce.add(avoidanceForce);
	}
	
	private Vector2D calcAvoidUnitForce(Unit unit) {
		Vector2D avoidUnitForce = calcDistance(unit.getPosition());
		double distance = avoidUnitForce.length();
		
		avoidUnitForce.scale(-1);
		avoidUnitForce.truncate(MAX_FORCE);
		if(distance < SPACING_DISTANCE) avoidUnitForce.scale(SPACING_DISTANCE / (SPACING_DISTANCE - distance));
		return avoidUnitForce;
	}
	
	/**
	 * calculate the hosts distance towards the given target
	 * @param p_target to calculate the distance to
	 * @return Vector2D representing the distance and direction to the target
	 */
	public Vector2D calcDistance(Vector2D p_target) {
		return Vector2D.diffrence(p_target, host.getPosition());
	}
	
	/**
	 * velocity at which the host moves
	 * @return the current velocity
	 */
	public Vector2D getVelocity() {
		return velocity;
	}
	
	@Override
	public void update() {
		steeringForce.truncate(MAX_FORCE);
		//velocity.scale(FRICTION);
		velocity.add(steeringForce);
		velocity.scale(FRICTION);
		//if(velocity.length() < CUT_OFF_VELOCITY) velocity.set(0, 0);
		host.getPosition().add(velocity);
		
		//reset steering
		steeringForce.set(0, 0);
	}
	
}
