package org.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageCreator {

	public static Image createCircle(Color c, int radius) {
		
		int diameter = radius *2;
		
		BufferedImage img = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) img.createGraphics();
		g2d.setColor(c);
		g2d.fillOval(0, 0, diameter, diameter);
		g2d.dispose();
		
		return img;
	}
	
	public static Image creatLine(Color c, int x, int y) {
		BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) img.createGraphics();
		g2d.setColor(c);
		g2d.fillOval(0, 0, x, y);
		g2d.dispose();
		
		return img;
	}
	
}
