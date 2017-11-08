package me.entity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import me.util.Vector2D;

public class UnitSquad {
	
	private static final int UNIT_OFFSET = 55;
	
	private Unit leader;
	private List<Unit> squadMembers;
	
	public UnitSquad(Collection<Unit> squadUnits) {
		this.squadMembers = new LinkedList<Unit>();
		this.squadMembers.addAll(squadUnits);
		this.leader = null;
	}
	
	public void moveStraightTo(Vector2D destination) {
		calcLeader(destination);
		
		//arrange units around squad leader
		Vector2D offset = Vector2D.diffrence(destination, leader.getPosition());
		//offset = 
		offset.scaleTo(UNIT_OFFSET);
		
		
		//initiate movement
		leader.moveTo(destination);
		
		for(int i = 0; i < squadMembers.size(); i++) {
			Unit $u = squadMembers.get(i);
			Vector2D target = Vector2D.sum(destination, Vector2D.product(offset, i + 1));
			
			System.out.println("hallo" + target);
			$u.moveTo(target);
		}
	}
	
	private void calcLeader(Vector2D destination) {
		double leaderDistance = Double.MAX_VALUE;

		if(leader != null) squadMembers.add(leader);
		
		//get closest member to destination
		for(Unit $u: squadMembers) {
			Vector2D dis = Vector2D.diffrence($u.getPosition(), destination);
			if(dis.length() < leaderDistance) {
				leaderDistance = dis.length();
				leader = $u;
			}
		}
		
		if(leaderDistance != Double.MAX_VALUE) squadMembers.remove(leader);
	}
}
