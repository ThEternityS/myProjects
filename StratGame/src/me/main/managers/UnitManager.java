package me.main.managers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import me.entity.Entity;
import me.entity.QuadTree;
import me.entity.Unit;
import me.entity.UnitSquad;
import me.util.Vector2D;

public class UnitManager {
	private List<Unit> allUnits;
	private List<Unit> controllableUnits;
	private List<Unit> selectedUnits;
	
	private QuadTree<Unit> treeRepresentation;
	
	public UnitManager() {
		allUnits = new LinkedList<Unit>();
		controllableUnits = new LinkedList<Unit>();
		selectedUnits = new LinkedList<Unit>();
		treeRepresentation = new QuadTree<Unit>(-200, -200, 6400, 3400);
	}
	
	public void update() {
		treeRepresentation.clear();
		treeRepresentation.insertAll(allUnits);
		for(Unit $u: allUnits) {
			$u.update();
		}
	}
	
	public void moveSelected(Vector2D destination) {
		UnitSquad squad = new UnitSquad(selectedUnits);
		squad.moveStraightTo(destination);
	}
	
	public void setSelectedUnits(Collection<Unit> p_selection) {
		selectedUnits.clear();
		selectedUnits.addAll(p_selection);
		//only keep categorized units
		selectedUnits.retainAll(controllableUnits);
	}
	
	public void addControllableUnit(Unit p_unit) {
		allUnits.add(p_unit);
		controllableUnits.add(p_unit);
	}
	public void addUncontrollable(Unit p_unit) {
		allUnits.add(p_unit);
	}
	
	public void remove(Unit p_unit) {
		if(allUnits.contains(p_unit)) allUnits.remove(p_unit);
		if(controllableUnits.contains(p_unit)) controllableUnits.remove(p_unit);
		if(selectedUnits.contains(p_unit)) selectedUnits.remove(p_unit);
	}
	
	public List<Unit> getControllableUnits() {
		return controllableUnits;
	}
	
	public List<Unit> getAllUnits() {
		return allUnits;
	}
	
	public List<Unit> getSelectedUnits() {
		return selectedUnits;
	}
	
	public Set<Unit> getAllContainedIn(Entity p_selectBox) {
		return treeRepresentation.getAllContainedIn(p_selectBox);
	}

}
