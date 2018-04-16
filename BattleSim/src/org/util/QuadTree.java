package org.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.entities.units.Collideable;

public class QuadTree {

	private static final int CAPACITY = 2;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private List<QuadTree> subTrees = null;
	private List<Collideable> elements;
	
	public QuadTree(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		elements = new LinkedList<Collideable>();
	}
	
	public boolean insert(Collideable c) {
		
		//if not in this quad tree
		if(!this.contains(c)) return false;
		
		//tree has no sub trees as of yet
		if(subTrees == null) {
			//if capacity of this quad tree is not yet reached
			if(elements.size() < CAPACITY) return elements.add(c);
			//capacity was reached
			createSubTrees();
		}
		
		//c can be inserted into a suitable sub tree
		if(insertIntoSubTree(c)) return true;
		
		//c can not be inserted into a sub tree
		return elements.add(c);
	}
	
	public boolean insertAll(Collection<? extends Collideable> collideables) {
		boolean ret = true;
		for(Collideable c: collideables) {
			ret &= insert(c);
		}
		return ret;
	}
	
	public Collideable remove(Collideable c) {
		//remove from elements
		if(elements.remove(c)) return c;
		
		QuadTree t = findMySubTree(c);
		if(t == null) return null;
		
		//remove from sub tree if not in elements
		return t.remove(c);
	}
	
	private boolean contains(Collideable c) {
		double diameter = c.getRadius() *2;
		return this.contains(c.getX() - c.getRadius(), c.getY(), diameter, diameter);
	}
	
	private boolean contains(double containedX, double containedY, double containedWidth, double containedHeight) {
		if(this.x > containedX) return false; //left side doesn't fit
		if(this.y > containedY) return false; //top doesn't fit
		if(this.x + this.width < containedX + containedWidth) return false; //right side does'nt fit
		if(this.y + this.height < containedY + containedHeight) return false; //left side doesn't fit
		return true;
	}
	
	private void createSubTrees() {
		subTrees = new ArrayList<QuadTree>();
		
		int newWidth = (int) (width / 2);
		int newHeight = (int) (height / 2);
		
		subTrees.add(new QuadTree(0, 0, newWidth, newHeight));
		subTrees.add(new QuadTree(0, newHeight + 1, newWidth, newHeight));
		subTrees.add(new QuadTree(newWidth + 1, 0, newWidth, newHeight));
		subTrees.add(new QuadTree(newWidth + 1, newHeight + 1, newWidth, newHeight));
		
		//move elements with a fitting sub tree into said one
		for(Collideable c: elements) {
			if(insertIntoSubTree(c)) elements.remove(c);
		}
	}

	private boolean insertIntoSubTree(Collideable c) {
		QuadTree t = findMySubTree(c);
		if(t == null) return false;
		return t.insert(c);
	}
	
	private QuadTree findMySubTree(Collideable c) {
		if(subTrees == null) return null;
		
		for(QuadTree t: subTrees) {
			if(t.contains(c)) return t;
		}
		return null;
	}
	
	public boolean hasCollision(Collideable unit) {
		
		//has a collision with any element in this tree element
		for(Collideable treeElement: elements) {
			if(Collideable.collide(treeElement, unit)) return true;
		}
		//no collisions as there are no further elements
		List<QuadTree> collidingSubTrees = getCollidingTrees(unit);
		if(collidingSubTrees == null) return false;
		
		//any of the colliding sub trees has a collision
		for(QuadTree st: collidingSubTrees) {
			if(st.hasCollision(unit)) return true;
		}
		//no collisions found
		return false;
	}
	
	public boolean hasIntersections(int x1, int y1, int x2, int y2) {
		for(Collideable c: elements) {
			if(lineCollision(x1, y1, x2, y2, c)) return true;
		}
		
		return false;
	}
	
	private boolean lineCollision(int x1, int y1, int x2, int y2, Collideable c) {
		double m = (y2 - y1) / (x2 - x1);
		double n = y1 - m * x1;
		double qa = 1 + m * m;
		double qb = -2 * c.getX() + 2 * m * n - 2 * c.getY() * m;
		double qc = c.getX() * c.getX() + c.getY() * c.getY() + n * n - 2 * c.getY() * n - c.getRadius() * c.getRadius();
		
		double solution1 = (-qb + Math.sqrt(qb * qb) - 4 * qa * qc) / (2 * qa);
		double solution2 = (-qb - Math.sqrt(qb * qb) - 4 * qa * qc) / (2 * qa);
		
		if(solution1 >= x1 || solution1 <= x2) return true;
		if(solution2 >= x1 || solution2 <= x2) return true;
		return false;
	}
	
	private List<QuadTree> getCollidingTrees(Collideable c) {

		//no sub trees -> no collisions
		if(subTrees == null) return null;
		
		List<QuadTree> colliding = new LinkedList<QuadTree>();
		
		//add each colliding sub tree to the return statement
		for(QuadTree st: subTrees) {
			if(st.collides(c)) colliding.add(st);
		}
		return colliding;
	}
	
	public List<QuadTree> getCollidingTrees(int x1, int y1, int x2, int y2) {
		
		//no sub trees -> no collisions
		if(subTrees == null) return null;
		
		List<QuadTree> colliding = new LinkedList<QuadTree>();
		

		//add each colliding sub tree to the return statement
		for(QuadTree st: subTrees) {
			if(st.collides(x1, y1, x2, y2)) colliding.add(st);
		}
		return colliding;
	}
	
	public List<Collideable> getCollisions(Collideable c) {
		List<Collideable> collisions = new LinkedList<Collideable>();
		
		for(Collideable listedCollideable: getPossibleCollisions(c)) {
			if(Collideable.collide(listedCollideable, c)) collisions.add(listedCollideable);
		}
		
		return collisions;
	}
	
	private boolean collides(Collideable c) {
		double diameter = c.getRadius() *2;
		if(this.x > c.getX() + diameter) return false; //box 1 is right of box 2
		if(this.x + this.width < c.getX()) return false; //box 1 is left of box 2
		if(this.y > c.getY() + diameter) return false; //box 1 is below box 2
		if(this.y + this.height < c.getY()) return false; //box 1 is above box 2
		return true;
	}
	private boolean collides(int x1, int y1, int x2, int y2) {
		int minX = (x1 > x2) ? x2 : x1;
		int maxX = (x1 > x2) ? x1 : x2;
		int minY = (y1 > y2) ? y2 : y1;
		int maxY = (y1 > y2) ? y1 : y2;
		
		if(this.x > maxX) return false; //box 1 is right of box 2
		if(this.x + this.width < minX) return false; //box 1 is left of box 2
		if(this.y > maxY) return false; //box 1 is below box 2
		if(this.y + this.height < minY) return false; //box 1 is above box 2
		return true;
	}
	
	private List<Collideable> getPossibleCollisions(Collideable c) {
		List<Collideable> possibleCollisions = new LinkedList<Collideable>();
		
		//add my own elements
		possibleCollisions.addAll(elements);
		
		List<QuadTree> collidingSubTrees = getCollidingTrees(c);
		
		//return as there are no further elements in this branch
		if(collidingSubTrees == null) return possibleCollisions;
		
		//add all elements of colliding sub trees
		for(QuadTree st: collidingSubTrees) {
			if(st.collides(c)) {
				possibleCollisions.addAll(st.getPossibleCollisions(c));
			}
		}
		
		return possibleCollisions;
	}
	
}
