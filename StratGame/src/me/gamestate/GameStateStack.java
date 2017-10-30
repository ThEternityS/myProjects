package me.gamestate;

import java.util.LinkedList;
import java.util.List;

import me.main.Main;

public class GameStateStack {

	private Main myMain;
	private List<GameState> states;
	
	public GameStateStack(Main m) {
		states = new LinkedList<GameState>();
		this.myMain= m;
	}
	
	public void replace(PossibleState p_state) {
		pop();
		push(p_state);
	}
	
	public void push(PossibleState p_state) {
		GameState _newState = null;
		switch(p_state) {
			case IDLE_STATE:
				_newState = new IdleState(this, myMain.getKeyManager());
				break;
			case SELECT_UNIT_STATE:
				_newState = new SelectUnitsState(this, myMain.getKeyManager(), myMain.getDisplayManager(), myMain.getOverlayManager(), myMain.getUnitManager());
				break;
			case ISSUE_COMMAND_STATE:
				_newState = new IssueCommandState(this, myMain.getKeyManager(), myMain.getDisplayManager(), myMain.getOverlayManager(), myMain.getUnitManager());
				break;
		}
		
		states.add(_newState);
	}
	
	public void pop() {
		states.remove(states.size() - 1);
	}
	
	public void update() {
		if(states.size() == 0) return;
		states.get(states.size() - 1).update();
	}
}
