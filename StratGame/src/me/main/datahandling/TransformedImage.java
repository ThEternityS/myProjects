package me.main.datahandling;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class TransformedImage {

	private BufferedImage myImage;
	private double zoom;
	private double angle;
	
	public TransformedImage(Image p_img, double p_zoom, double p_angle) {
		this.zoom = p_zoom;
		this.angle = p_angle;
		
		//create scaled instance of img
		int _newWidth = (int) Math.ceil(p_img.getWidth(null) * p_zoom );
		int _newHeight = (int) Math.ceil(p_img.getHeight(null) * p_zoom);
		
		//ensure proper loading of unloaded images
		if(_newWidth <= 0 || _newHeight <= 0) {
			this.zoom = 0;
			if(_newWidth <= 0) _newWidth = 1;
			if(_newHeight <= 0) _newHeight = 1;
		}
		
		myImage = new BufferedImage(_newWidth, _newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) myImage.getGraphics();
		g2d.drawRect(0, 0, _newWidth - 1, _newHeight - 1);
		g2d.drawImage(p_img, 0, 0, _newWidth, _newHeight, null);
		g2d.dispose();
	}
	
	public boolean hasSameProperties(double p_zoom, double p_angle) {
		return (zoom == p_zoom && angle == p_angle);
	}
	
	public BufferedImage getImage() {
		return myImage;
	}
}
