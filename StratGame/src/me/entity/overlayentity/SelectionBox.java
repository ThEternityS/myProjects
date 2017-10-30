package me.entity.overlayentity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.entity.Alive;
import me.entity.Displayable;
import me.entity.BaseEntity;


public class SelectionBox extends BaseEntity implements Displayable, Alive {

	private static final Color MY_COLOR = Color.CYAN;
	private static final float ALPHA = 0.35f;
	
	private BufferedImage myImage;
	
	private boolean alive = true;
	
	public SelectionBox(int p_x, int p_y, int p_w, int p_h) {
		this.x = p_x;
		this.y = p_y;
		if(p_w < 0) this.x += p_w;
		if(p_h < 0) this.y += p_h;
		this.width = Math.abs(p_w);
		this.height = Math.abs(p_h);
		createImage();
	}
	
	private void createImage() {
		myImage = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) myImage.getGraphics();
		g2d.setColor(MY_COLOR);
		g2d.drawRect(0, 0, width, height);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
		g2d.fillRect(0, 0, width, height);
		g2d.dispose();
	}

	@Override
	public BufferedImage getImage() {
		return myImage;
	}

	@Override
	public double getAngel() {
		return 0;
	}
	
	@Override
	public boolean isAlive() {
		if(alive == true) {
			alive = false;
			return true;
		}
		return false;
	}
}
