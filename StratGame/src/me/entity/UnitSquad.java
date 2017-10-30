package me.entity;

import java.util.Collection;

import me.util.Vector2D;

public class UnitSquad {
	
	private static final int UNIT_OFFSET = 25;
	
	Collection<Unit> squadMembers;
	
	public UnitSquad(Collection<Unit> squadMembers) {
		this.squadMembers = squadMembers;
	}
	
	public void moveTo(Vector2D destination) {
		//calculate average squad location
		Vector2D average = new Vector2D(0, 0);
		for(Unit $u: squadMembers) {
			average.add($u.getPosition());
		}
		average.scale(1 / squadMembers.size());
		
		System.out.println(average);
		
		Vector2D offsetPerUnit = Vector2D.orthogonal(Vector2D.diffrence(destination, average));
		offsetPerUnit.scaleTo(UNIT_OFFSET);
		System.out.println(offsetPerUnit);
		
		Vector2D offset = new Vector2D(0, 0);
		for(Unit $u: squadMembers) {
			$u.moveTo(Vector2D.sum(destination, offset));
			offset.add(offsetPerUnit);
		}
	}
}
