package me.particles;

import java.awt.Color;

import me.entity.Alive;
import me.entity.Updateable;
import me.util.Vector2D;

public class Particle implements Alive, Updateable {

	private Vector2D position;
	private Vector2D velocity;
	private int ttl;
	private Color myColor;
	
	public Particle(Vector2D pos, Vector2D vel, Color col, int ttl) {
		this.position = new Vector2D(pos);
		this.velocity = new Vector2D(vel);
		this.myColor = col;
		this.ttl = ttl;
	}
	
	@Override
	public boolean isAlive() {
		if(ttl > 0) return true;
		return false;
	}
	@Override
	public void update() {
		velocity.scale(0.99);
		if(velocity.length() < 0.1) velocity.set(0, 0);
		position.add(velocity);
		ttl--;
	}
	
	public int getX() {
		return position.getIntX();
	}
	public int getY() {
		return position.getIntY();
	}
	
	public Color getColor() {
		return myColor;
	}
	
}
