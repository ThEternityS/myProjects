package org.entities.effects;

import java.awt.Color;
import java.awt.Graphics2D;

import org.entities.Alive;
import org.entities.Updateable;

public class PingEffect extends Effect implements Alive, Updateable {

	private int ttl = 200;
	
	private int spread = 0;
	
	private int srcx;
	private int srcy;
	private Color color;
	
	public PingEffect(int x, int y, Color c) {
		this.srcx = x;
		this.srcy = y;
		this.color = c;
	}
	
	@Override
	public void render(Graphics2D g2d) {
		if(spread <= 5) return;
		g2d.setColor(color);
		if(spread > 5 && spread < 120) {
			g2d.drawOval(srcx - spread, srcy - spread, spread * 2, spread * 2);
		}
		if(spread > 10 && spread < 150) {
			int small = (int) (spread * 0.8);
			g2d.drawOval(srcx - small, srcy - small, small * 2, small * 2);
		}
		if(spread > 20 && spread < 180) {
			int smaller = (int) (spread * 0.4);
			g2d.drawOval(srcx - smaller, srcy - smaller, smaller * 2, smaller * 2);
		}
		if(spread > 30) {
			int smallest = (int) (spread * 0.2);
			g2d.drawOval(srcx - smallest, srcy - smallest, smallest * 2, smallest * 2);
		}
		
	}

	@Override
	public void update() {
		ttl--;
		spread += 1;
	}

	@Override
	public boolean isAlive() {
		if(ttl == 0) return false;
		return true;
	}

}
