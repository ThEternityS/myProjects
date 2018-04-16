package org.entities.effects;

import java.awt.Color;
import java.awt.Graphics2D;

import org.entities.Alive;

public class LineEffect extends Effect implements Alive {

	private boolean living = true;
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Color color;
	
	public LineEffect(int x1, int y1, int x2, int y2, Color c) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = c;
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.drawLine(x1, y1, x2, y2);
	}

	/*
	 *	Effect is alive for one cycle only 
	 */
	@Override
	public boolean isAlive() {
		if(living) {
			living = false;
			return true;
		}
		return false;
	}

}
