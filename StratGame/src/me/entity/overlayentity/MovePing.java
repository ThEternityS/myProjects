package me.entity.overlayentity;
import java.awt.Image;

import me.entity.Alive;
import me.entity.Displayable;
import me.entity.Entity;
import me.entity.Updateable;
import me.util.ImageCreator;


public class MovePing implements Entity, Alive, Updateable, Displayable {

	private int x;
	private int y;
	private int ttl;
	private static Image[] images = {
			ImageCreator.getInstance().loadImage("move1.png"),
			ImageCreator.getInstance().loadImage("move2.png"),
			ImageCreator.getInstance().loadImage("move3.png"),		
	};
	
	public MovePing(int x, int y) {
		ttl = 100;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void update() {
		ttl--;
	}
	
	@Override
	public int getX() {
		//return x;
		return x - 5;
	}
	@Override
	public int getY() {
		//return y;
		return y - 10;
	}
	@Override
	public int getWidth() {
		return 10;
	}
	@Override
	public int getHeight() {
		return 10;
	}
	@Override
	public Image getImage() {
		if(100 >= ttl && ttl > 90) return images[0];
		if(90 >= ttl && ttl > 80) return images[1];
		if(80 >= ttl && ttl > 70) return images[2];
		if(70 >= ttl && ttl > 60) return images[0];
		if(60 >= ttl && ttl > 50) return images[1];
		if(50 >= ttl && ttl > 40) return images[2];
		if(30 >= ttl && ttl > 20) return images[0];
		if(20 >= ttl && ttl > 10) return images[1];
		return images[2];
	}

	@Override
	public double getAngel() {
		return 0;
	}
	
	@Override
	public boolean isAlive() {
		if(ttl <= 0) return false;
		return true;
	}
	
}
