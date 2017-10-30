package me.main.managers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import me.entity.Entity;
import me.entity.QuadTree;
import me.entity.Unit;
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
		/*
		for(Unit $u: selectedUnits) {
			$u.moveTo(p_destination);
		}
		*/
		Vector2D average = new Vector2D(0, 0);
		for(Unit $u: selectedUnits) {
			average.add($u.getPosition());
		}
		average.scale(1 / selectedUnits.size());
		
		System.out.println(average);
		
		Vector2D offsetPerUnit = Vector2D.orthogonal(Vector2D.diffrence(destination, average));
		offsetPerUnit.scaleTo(50);
		
		//offsetPerUnit.set(50, 0);
		
		Vector2D offset = new Vector2D(0, 0);
		for(Unit $u: selectedUnits) {
			$u.moveTo(Vector2D.sum(destination, offset));
			offset.add(offsetPerUnit);
		}
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
