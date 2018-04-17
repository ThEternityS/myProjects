package me.gamestate;
import me.main.managers.DisplayManager;
import me.main.managers.KeyManager;
import me.main.managers.OverlayManager;
import me.main.managers.UnitManager;
import me.util.Vector2D;

public class IssueCommandState extends GameState{

	private KeyManager km;
	private DisplayManager dm;
	private OverlayManager om;
	private UnitManager um;

	public IssueCommandState(GameStateStack s, KeyManager km, DisplayManager dm, OverlayManager om, UnitManager um) {
		super(s);
		this.km = km;
		this.dm = dm;
		this.om = om;
		this.um = um;
	}
	
	@Override
	public void update() {
		if(km.readRightButtonClick()) {
			Vector2D _dest = dm.convertToGlobal(km.getMousePositionRelativeToComponent());
			om.addMovePing(_dest);
			om.addConnectionLine(um.getLeader(_dest).getPosition(), _dest);
			


			um.moveSelected(_dest);
		
			
			//myStack.replace(PossibleState.IDLE_STATE);	
		}
		if(km.isLeftButtonPressed()) {
			myStack.replace(PossibleState.SELECT_UNIT_STATE);
		}
	}
	
}
