package org.entities.units.movement.intelligent;

import java.util.LinkedList;
import java.util.List;

public class MovementFSM {

	private IntelligentMovement myHost;
	private List<MovementState> myStates;

	public MovementFSM(IntelligentMovement host) {
		this.myHost = host;
		myStates = new LinkedList<MovementState>();
	}
	
	public void execute() {
		(myStates.get(0)).executeMovement(this);
	}
	
	public IntelligentMovement getMovementComponent() {
		return myHost;
	}
	
	public void popState() {
		myStates.remove(0);
	}
	private void pushState(MovementState ms) {
		myStates.add(0, ms);
	}
	
	public void pushDropState() {
		pushState(new DropState());
	}
	
	public void pushAdvanceState() {
		pushState(new AdvanceState());
	}
}
