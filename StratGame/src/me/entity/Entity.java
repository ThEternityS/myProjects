package me.entity;

public interface Entity {

	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	
	/**
	 * determines if entity 1 intersects with entity 2
	 * @param e1
	 * @param e2
	 * @return true if the entities intersect/touch/contain each other
	 */
	public static boolean intersects(Entity e1, Entity e2) {
		//if(contains(e1, e2) || contains(e2, e1)) return true;
		/*
		int _e1x1 = e1.getX();
		int _e2x1 = e2.getX();
		int _e1x2 = e1.getX() + e1.getWidth();
		int _e2x2 = e2.getX() + e2.getWidth();
		
		int _e1y1 = e1.getY();
		int _e2y1 = e2.getY();
		int _e1y2 = e1.getY() + e1.getHeight();
		int _e2y2 = e2.getY() + e2.getHeight();
		
		boolean _xIntersect = _e1x1 <= _e2x1 && _e1x2 >= _e2x1 || _e1x1 <= _e2x2 && _e1x2 >= _e2x2;
		boolean _yIntersect = _e1y1 <= _e2y1 && _e1y2 >= _e2y1 || _e1y1 <= _e2y2 && _e1y2 >= _e2y2;
		return (_xIntersect && _yIntersect);
		*/
		
		//e1 is left from e2
		if(e1.getX() + e1.getWidth() < e2.getX()) return false;
		//e1 is right form e2
		if(e1.getX() > e2.getX() + e2.getWidth()) return false;
		//e1 is over e2
		if(e1.getY() + e1.getHeight() < e2.getY()) return false;
		//e1 is under e2
		if(e1.getY() > e2.getY() + e2.getHeight()) return false;
		return true;
	}
	
	/**
	 * calculates if the contained is contained in the container
	 * @param p_container
	 * @param p_contained
	 * @return true if the container contains the contained, false if the container doesn't contain/intersects the contained
	 */
	public static boolean contains(Entity p_container, Entity p_contained) {
		boolean _containsX = p_container.getX() <= p_contained.getX() && p_container.getX() + p_container.getWidth() >= p_contained.getX() + p_contained.getWidth();
		boolean _containsY = p_container.getY() <= p_contained.getY() && p_container.getY() + p_container.getHeight() >= p_contained.getY() + p_contained.getHeight();
		if(_containsX && _containsY) return true;
		return false;
	}
}
