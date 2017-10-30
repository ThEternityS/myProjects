package me.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuadTree<E extends Entity> extends BaseEntity {
	
	private static final int CAPACITY = 5;
	
	private List<QuadTree<E>> subTrees = null;
	private Set<E> elements;
	
	public QuadTree(int x, int y, int w, int h) {
		super(x, y, w, h);
		elements = new HashSet<E>();
	}
	
	public QuadTree(Entity e) {
		this(e.getX(), e.getY(), e.getWidth(), e.getHeight());
	}
	
	public Set<E> getAllContainedIn(Entity p_container) {

		HashSet<E> _ret = new HashSet<E>();
		
		//if this quad tree is completely included return all its elements
		if(Entity.contains(p_container, this)) {
			return this.getAllElements();
		}
		if(!Entity.intersects(p_container, this)) {
			//do nothing here as there is no intersection and therefore no elements to add
		}else {
			//add intersecting elements from elements
			_ret.addAll(getElementsIn(p_container));
			//add intersecting elements from subtrees
			if(subTrees != null) {
				for(QuadTree<E> $t: subTrees) {
					_ret.addAll($t.getAllContainedIn(p_container));
				}
			}
		}
		return _ret;
		
	}
	
	private Set<E> getElementsIn(Entity p_container) {
		Set<E> _ret = new HashSet<E>();
		for(E $e: elements) {
			if(Entity.intersects(p_container, $e)) _ret.add($e);
		}
		return _ret;
	}
	
	public List<QuadTree<E>> getSubTrees() {
		return subTrees;
	}
	
	private Set<E> getElements() {
		return elements;
	}
	
	//return all elements in a quad tree included in its sub trees and own elements
	private Set<E> getAllElements() {
		HashSet<E> _ret = new HashSet<E>();
		if(subTrees == null) return elements;
		else {
			for(QuadTree<E> $t: subTrees) {
				_ret.addAll($t.getAllElements());
			}
		}
		return _ret;
	}

	public boolean insert(E e) {

		//return if element is null
		if(e == null) return false;
		
		//check capacity and add to elements in case
		if(!Entity.contains(this, e)) return false;
		
		if(subTrees == null) {
			//add to elements if there is still space and no subtrees are present
			if(elements.size() < CAPACITY) return elements.add(e);
			//create sub trees if capacity is exceeded
			createSubTrees();
		}
		//insert into correct sub tree
		if(insertIntoSubTrees(e)) return true;

		//add to elements if everything else fails
		return elements.add(e);
	}
	
	public boolean insertAll(Collection<? extends E> col) {
		if(col == null) return false;
		boolean _bret = false;
		for(E $e: col) {
			boolean _b = insert($e);
			_bret =  _bret || _b;
		}
		return _bret;
	}
	
	private void createSubTrees() {
		
		int _x1 = (int) (width / 2);
		int _y1 = (int) (height / 2);
		int _w1 = _x1 - x;
		int _h1 = _y1 - y;
		int _w2 = width - _x1 - 1;
		int _h2 = height - _y1 - 1;
		
		subTrees = new ArrayList<QuadTree<E>>(4);
		
		
		subTrees.add(new QuadTree<E>(x, y, _w1, _h1));
		subTrees.add(new QuadTree<E>(_x1 + 1, y, _w2, _h1));
		subTrees.add(new QuadTree<E>(_x1 + 1, _y1 + 1, _w2, _h2));
		subTrees.add(new QuadTree<E>(x, _y1 + 1, _w1, _h2));
		
		
		Set<E> _rem = new HashSet<E>();
		//insert current elements into sub trees
		for(E $e: elements) {
			if(insertIntoSubTrees($e)) {
				_rem.add($e);
			}
		}
		//remove all elements which were inserted into subtrees
		elements.removeAll(_rem);
	}
	
	private boolean insertIntoSubTrees(E e) {
		if(subTrees == null) return false;
		for(QuadTree<E> $t: subTrees) {
			if(Entity.contains($t, e)) return $t.insert(e);
		}
		return false;
	}
	
	@Override
	public String toString() {
		if(subTrees == null) return "end ";
		String _ret = "new tree: " + getElements().size() + " ";
		for(QuadTree<E> $t: subTrees) {
			_ret += "subtree: " + $t.getElements().size() + " " + $t.toString();
		}
		return _ret;
	}
	
	public void clear() {
		elements = new HashSet<E>();
		subTrees = null;
	}
	
	public boolean contains(Object obj) {
		for(E $e: elements) {
			if($e == obj) return true;
		}
		if(subTrees != null) {
			return subTrees.get(0).contains(obj) || subTrees.get(1).contains(obj) || subTrees.get(2).contains(obj) || subTrees.get(3).contains(obj);
		}
		return false;
	}
	
	public boolean containsAll(Collection<?> col) {
		for(Object $obj: col) {
			if(!contains($obj)) return false;
		}
		return true;
	}
	
	public boolean isEmpty() {
		if(elements.size() == 0 && subTrees == null) return true;
		return false;
	}
	
	public boolean remove(Object obj) {
		if(!contains(obj)) return false;
		if(elements.contains(obj)) return elements.remove(obj);
		for(QuadTree<E> $t: subTrees) {
			if($t.contains(obj)) return $t.remove(obj);
		}
		//should never be reached
		return false;
	}
	
	public boolean removeAll(Collection<?> col) {
		boolean _b = false;
		for(Object $obj: col) {
			_b = _b || remove($obj);
		}
		return _b;
	}
	
	public int size() {
		if(subTrees == null) return elements.size();
		return elements.size() + subTrees.get(0).size() + subTrees.get(1).size() + subTrees.get(2).size() + subTrees.get(3).size();
	}
	
}
