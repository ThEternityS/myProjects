package me.gamestate;

public abstract class GameState {
	
	protected GameStateStack myStack;
	
	public GameState(GameStateStack s) {
		this.myStack = s;
	}
	
	public abstract void update();
	
	public GameStateStack getMyStack() {
		return myStack;
	}
}
