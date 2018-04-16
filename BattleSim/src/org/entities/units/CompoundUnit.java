package org.entities.units;

import java.util.LinkedList;
import java.util.List;

import org.entities.Alive;
import org.entities.Directional;
import org.entities.Updateable;
import org.util.Vector2D;

public class CompoundUnit implements Updateable, Affiliated, Alive, Directional {

	private boolean living;
	private Affiliation affiliation;
	private List<UnitPart> myCompounds;
	private List<Vector2D> myConnectors;
	
	public CompoundUnit(List<UnitPart> parts) {
		myCompounds = parts; 
		myConnectors = new LinkedList<Vector2D>();
		living = true;
		createConnectors();
	}
	
	private void createConnectors() {
		
		List<UnitPart> parts = myCompounds;
		UnitPart head = parts.remove(0);
		
		for(UnitPart p: parts) {
			
			Vector2D connector = new Vector2D(p.getX() - head.getX(), p.getY() - head.getY());
			myConnectors.add(connector);
		}
	}
	
	public void addCompound(UnitPart up) {
		myCompounds.add(up);
	}

	@Override
	public void update() {
		//manage all unit parts
	}

	@Override
	public Affiliation getAffiliation() {
		return affiliation;
	}

	@Override
	public boolean isAlive() {
		return living;
	}

	@Override
	public double getDirection() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
