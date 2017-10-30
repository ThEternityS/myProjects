package me.main.managers;
import java.util.LinkedList;
import java.util.List;

import me.entity.Alive;
import me.entity.Displayable;
import me.entity.Entity;
import me.entity.Updateable;
import me.entity.overlayentity.CustomBound;
import me.entity.overlayentity.MovePing;
import me.entity.overlayentity.SelectionBox;
import me.util.Vector2D;

public class OverlayManager {
	
	//private List<Entity> overlayElements;
	private List<Entity> overlayEntities;
	private List<Updateable> overlayUpdates;
	private List<Displayable> overlayDisplays;
	private List<Alive> overlayAlives;
	
	public OverlayManager() {
		//overlayElements = new LinkedList<Entity>();
		overlayEntities = new LinkedList<Entity>();
		overlayUpdates = new LinkedList<Updateable>();
		overlayDisplays = new LinkedList<Displayable>();
		overlayAlives = new LinkedList<Alive>();
	}
	
	public void update() {
		removeDeadElements();
		for(Updateable $u: overlayUpdates) {
			$u.update();
		}
	}
	
	public void removeDeadElements() {
		/*
		List<Entity> _dead = new LinkedList<Entity>();
		
		for(Entity e: overlayElements) {
			if(e instanceof Alive) {
				if(!((Alive) e).isAlive()) _dead.add(e);
			}
			
		}
		
		overlayElements.removeAll(_dead);
		*/
		List<Alive> _dead = new LinkedList<Alive>();
		for(Alive $a: overlayAlives) {
			if(!$a.isAlive()) _dead.add($a);
		}
		
		for(Alive $a: _dead) {
			removeAll($a);
		}
	}
	
	
	public void addAll(Object obj) {
		
		if(obj instanceof Entity) {
			add((Entity) obj); 
		}
		if(obj instanceof Updateable) {
			add((Updateable) obj); 
		}
		if(obj instanceof Displayable) {
			add((Displayable) obj); 
		}
		if(obj instanceof Alive) {
			add((Alive) obj); 
		}
	}
	public void add(Entity a) {
		overlayEntities.add(a);
	}
	public void add(Updateable u) {
		overlayUpdates.add(u);
	}
	public void add(Displayable d) {
		overlayDisplays.add(d);
	}
	public void add(Alive a) {
		overlayAlives.add(a);
	}
	
	public void removeAll(Object obj) {
		
		if(obj instanceof Entity) {
			remove((Entity) obj); 
		}
		if(obj instanceof Updateable) {
			remove((Updateable) obj); 
		}
		if(obj instanceof Displayable) {
			remove((Displayable) obj); 
		}
		if(obj instanceof Alive) {
			remove((Alive) obj); 
		}
	}
	public void remove(Entity a) {
		overlayEntities.remove(a);
	}
	public void remove(Updateable u) {
		overlayUpdates.remove(u);
	}
	public void remove(Displayable d) {
		overlayDisplays.remove(d);
	}
	public void remove(Alive a) {
		overlayAlives.remove(a);
	}

	public List<Entity> getEntities() {
		return overlayEntities;
	}
	public List<Updateable> getUpdateables() {
		return overlayUpdates;
	}
	public List<Displayable> getDisplayables() {
		return overlayDisplays;
	}
	
	public void addSelectBox(Vector2D p_pos, Vector2D p_size) {
		addAll(new SelectionBox(p_pos.getIntX(), p_pos.getIntY(), p_size.getIntX(), p_size.getIntY()));
	}
	
	public void addMovePing(Vector2D position) {
		addAll(new MovePing(position.getIntX(), position.getIntY()));
	}
	
	public void addPlayFieldBounds(int w, int h) {
		addAll(new CustomBound(0, 0, 5, h));
		addAll(new CustomBound(0, 0, w, 5));
		addAll(new CustomBound(w - 5, 0, 5, h));
		addAll(new CustomBound(0, h - 5, w, 5));
	}
}
