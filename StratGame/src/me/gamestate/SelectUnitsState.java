package me.gamestate;

import java.util.LinkedList;
import java.util.List;

import me.entity.BaseEntity;
import me.entity.Entity;
import me.entity.Unit;
import me.main.managers.DisplayManager;
import me.main.managers.KeyManager;
import me.main.managers.OverlayManager;
import me.main.managers.UnitManager;
import me.util.Vector2D;

public class SelectUnitsState extends GameState{

	private KeyManager km;
	private DisplayManager dm;
	private OverlayManager om;
	private UnitManager um;
	
	public SelectUnitsState(GameStateStack s, KeyManager km, DisplayManager dm, OverlayManager om, UnitManager um) {
		super(s);
		this.km = km;
		this.dm = dm;
		this.om = om;
		this.um = um;
	}
	
	@Override
	public void update() {
		if(km.isLeftButtonPressed()) {
			Vector2D _dim = Vector2D.diffrence(km.getMousePositionRelativeToComponent(), km.getLeftDragStart());
			om.addSelectBox(dm.convertToGlobal(km.getLeftDragStart()), _dim);
		}else {
			//left button pressed was released
			int _x = (int) dm.convertToGlobalX(km.getLeftDragStart().getX());
			int _y = (int) dm.convertToGlobalY(km.getLeftDragStart().getY());
			int _w = (int) (dm.convertToGlobalX(km.getMousePositionRelativeToComponent().getX())) - _x;
			int _h = (int) (dm.convertToGlobalY(km.getMousePositionRelativeToComponent().getY())) - _y;
			
			BaseEntity _selectBox = new BaseEntity(_x, _y, _w, _h);
			System.out.println(_x + " " + _y + " " + _w + " " + _h) ;
			List<Unit> _l = new LinkedList<Unit>();
			for(Unit $u: um.getControllableUnits()) {
				if(Entity.contains(_selectBox, $u)) _l.add($u);
			}
			um.setSelectedUnits(_l);
			//if no units were selected go to idle
			if(um.getSelectedUnits().size() == 0) {
				myStack.replace(PossibleState.IDLE_STATE);
			}else {
				//if units were selected go to issue command
				//avoid wrong inputs
				km.readRightButtonClick();	
				myStack.replace(PossibleState.ISSUE_COMMAND_STATE);
			}
		}
	}

}
