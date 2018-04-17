package me.entity.overlayentity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import me.entity.Alive;
import me.entity.BaseEntity;
import me.entity.Displayable;
import me.util.Vector2D;

public class ConnectionLine extends BaseEntity implements Displayable, Alive {

	private Vector2D start;
	private Vector2D end;
	
	private boolean alive = true;
	
	private BufferedImage myImage;
	
	public ConnectionLine(Vector2D start, Vector2D end) {
		this.start = start;
		this.end = end;
	}
	
	private void createImage() {
		Vector2D connection = Vector2D.diffrence(end, start);
		int x = connection.getIntX();
		int y = connection.getIntY();	
		
		if(Math.abs(x) <= 2 && Math.abs(y) <= 0) alive = false;
		if(x <= 0) {
			x = 1;
		}
		if(y <= 0) {
			y = 1;
		}
		myImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) myImage.getGraphics();
		g2d.setColor(Color.RED);
		g2d.drawLine(0, 0, x, y);
		g2d.dispose();
	}
	
	@Override
	public int getX() {
		return start.getIntX();
	}
	
	@Override
	public int getY() {
		return start.getIntY();
	}
	
	@Override
	public Image getImage() {
		createImage();
		return myImage;
	}

	@Override
	public double getAngel() {
		return 0;
	}

	@Override
	public boolean isAlive() {
		return alive;
	}


}
