package me.gamestate;

import me.main.managers.KeyManager;

public class IdleState extends GameState {

	private KeyManager km;
	
	public IdleState(GameStateStack s, KeyManager km) {
		super(s);
		this.km = km;
	}

	@Override
	public void update() {
		if(km.isLeftButtonPressed()) {
			myStack.replace(PossibleState.SELECT_UNIT_STATE);
		}
	}
}
