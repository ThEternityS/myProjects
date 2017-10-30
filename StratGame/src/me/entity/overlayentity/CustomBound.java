package me.entity.overlayentity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.entity.BaseEntity;
import me.entity.Displayable;

public class CustomBound extends BaseEntity implements Displayable {

	private BufferedImage myImage;
	
	public CustomBound(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		createBoundImage(width, height);
	}
	
	private void createBoundImage(int w, int h) {
		myImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) myImage.getGraphics();
		g2d.setColor(Color.BLUE);
		g2d.fillRect(0, 0, w, h);
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
	
}
