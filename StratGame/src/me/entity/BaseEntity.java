package me.entity;

public class BaseEntity implements Entity {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public BaseEntity() {
		
	}
	
	public BaseEntity(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
}
