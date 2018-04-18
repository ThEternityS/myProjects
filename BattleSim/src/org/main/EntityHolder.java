package org.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.entities.Alive;
import org.entities.Renderable;
import org.entities.Updateable;
import org.entities.effects.Effect;
import org.entities.units.Affiliation;
import org.entities.units.Animate;
import org.entities.units.Collideable;
import org.entities.units.CollisionScheme;
import org.entities.units.SingleUnit;
import org.entities.units.movement.MovementBehaviour;
import org.entities.units.shooting.ShootingBehaviour;
import org.util.QuadTree;

public class EntityHolder {
	
	private List<Updateable> updateEntities;
	private List<Alive> aliveEntities;
	private static List<Animate> animateCollisionEntities;
	private List<Collideable> inanimateCollisionEntities;
	private List<List<Renderable>> renderEntities;
	
	private static QuadTree collisionTree;
	
	public EntityHolder() {
		updateEntities = new LinkedList<Updateable>();
		aliveEntities = new LinkedList<Alive>();
		animateCollisionEntities = new LinkedList<Animate>();
		inanimateCollisionEntities = new LinkedList<Collideable>();
		createCollisionTree();
		
		renderEntities = new LinkedList<List<Renderable>>();
		for(int i = 0; i < 3; ++i) {
			renderEntities.add(new LinkedList<Renderable>());
		}	
	}
	
	public boolean createSingleUnit(int x, int y, Affiliation affiliation, MovementBehaviour mb, ShootingBehaviour sb) {
		
		SingleUnit su = new SingleUnit(x, y, affiliation, mb, sb);
		
		//do nothing if the new unit collides with an old one
		if(collisionTree.hasCollision(su)) {
			System.out.println("Unit not created due to collision");
			return false;
		}
		
		//adds the unit to all of its applicable managing lists
		return addEntityToApplicableLists(su, 1);
	}
	
	public boolean createEffect(Effect e, int renderDepth) {
		if(renderDepth < 0 || renderDepth > renderEntities.size()) return false;
		return addEntityToApplicableLists(e, renderDepth);
	}
	
	public void update() {
		
		removeDeadEntities();
		
		createCollisionTree();
		
		for(Updateable u: updateEntities) {
			u.update();
		}
	}
	
	public Collection<Renderable> getRenderEntities() {
		List<Renderable> allRenderables = new ArrayList<Renderable>();
		
		allRenderables.addAll(renderEntities.get(0));
		allRenderables.addAll(renderEntities.get(1));
		allRenderables.addAll(renderEntities.get(2));
		
		return allRenderables;
	}
	
	private void removeDeadEntities() {
		List<Alive> entitiesToRemove = new LinkedList<Alive>();
		for(Alive a: aliveEntities) {
			if(!a.isAlive()) entitiesToRemove.add(a);
		}
		
		for(Alive deadEntity: entitiesToRemove) {
			removeEntityFromApplicableLists(deadEntity);
		}
	}
	
	private boolean addEntityToApplicableLists(Object o, int renderDepth) {
		
		boolean success = true;
		
		if(o instanceof Updateable) {
			success &= updateEntities.add((Updateable) o);
		}
		if(o instanceof Alive) {
			success &= aliveEntities.add((Alive) o);
		}
		if(o instanceof Animate) {
			success &= animateCollisionEntities.add((Animate) o);
			success &= collisionTree.insert((Collideable) o);
		}else if(o instanceof Collideable) {
			success &= inanimateCollisionEntities.add((Collideable) o);
		}
		if(o instanceof Renderable) {
			if(renderDepth >= 0 || renderDepth < renderEntities.size()) {
				success &= (renderEntities.get(renderDepth)).add((Renderable) o);
			}else {
				success &= false;
			}
		}
		
		//roll back if something failed
		if(!success) removeEntityFromApplicableLists(o);
		
		return success;
	}
	
	private void removeEntityFromApplicableLists(Object o) {
		if(o instanceof Updateable) {
			updateEntities.remove((Updateable) o);
		}
		if(o instanceof Alive) {
			aliveEntities.remove(o);
		}
		if(o instanceof Animate) {
			animateCollisionEntities.remove((Animate) o);
			collisionTree.remove((Collideable) o);
		}else if(o instanceof Collideable) {
			inanimateCollisionEntities.remove((Collideable) o);
		}
		if(o instanceof Renderable) {
			for(List<Renderable> zRenderables: renderEntities)
			zRenderables.remove((Renderable) o);
		}
	}
	
	private void createCollisionTree() {
		collisionTree = new QuadTree(-250, -250, 1300, 1100);	
		
		if(!collisionTree.insertAll(animateCollisionEntities)) {
			System.err.println("couldn't fit all given collideables into the collision tree");
		}
	}
	
	public static List<Animate> getUnitsInVincinity(Collideable center, int radius) {
		
		List<Collideable> collideables = collisionTree.getCollisions(new CollisionScheme(center.getX(), center.getY(), radius));
		
		//remove unwanted units
		List<Collideable> toRemove = new LinkedList<Collideable>();
		for(Collideable c: collideables) {
			//remove own unit components
			if(c.partOf() == center.partOf()) toRemove.add(c);
		}
		collideables.removeAll(toRemove);
		
		return EntityHolder.getAnimateList(collideables);
	}
	
	public static List<Animate> getVisibleUnitsInVincinity(Collideable center,  int radius) {
		
		List<Collideable> collideables = collisionTree.getCollisions(new CollisionScheme(center.getX(), center.getY(), radius));
		
		//remove unwanted units
		List<Collideable> toRemove = new LinkedList<Collideable>();
		for(Collideable c: collideables) {
			
			//remove own unit components
			if(c.partOf() == center.partOf()) {
				toRemove.add(c);
				continue;
			}
			
			//remove inanimate objects
			if(!(c instanceof Animate)) {
				toRemove.add(c);
				continue;
			}
			
			//remove LOS blocked units
			if(!EntityHolder.isInSightOf(center, c)) toRemove.add(c);
			
		}
		collideables.removeAll(toRemove);
		
		return EntityHolder.getAnimateList(collideables);
	}
	
	private static List<Animate> getAnimateList(List<Collideable> cList) {
		
		List<Animate> ret = new LinkedList<Animate>();
		for(Collideable c: cList) {
			if(c instanceof Animate) ret.add((Animate) c);
		}
		return ret;
	}
	
	public static boolean isInSightOf(Collideable observer, Collideable observed) {
		
		
		return false;
	}
	
}
