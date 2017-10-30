package me.util;

public class Line2D {

	private Vector2D offset;
	private Vector2D direction;
	
	public Line2D(Vector2D p_offset, Vector2D p_direction) {
		this.offset = p_offset;
		this.direction = p_direction;
	}
	
	public Line2D orthogonal() {
		return new Line2D(new Vector2D(offset), new Vector2D(direction.getX(), -direction.getY()));
	}
}
