package me.util;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ImageCreator {

	private static ImageCreator myInstance = null;
	private MediaTracker myMediaTracker;
	private int id;
	
	private ImageCreator() {
		id = 0;
	}
	
	public static ImageCreator getInstance() {
		if(myInstance == null) myInstance = new ImageCreator();
		return myInstance;
	}
	
	public void setComponent(Component p_component) {
		myMediaTracker = new MediaTracker(p_component);
		id = 0;
	}
	
	
	public Image loadImage(String path) {
		Image _img = Toolkit.getDefaultToolkit().getImage(path);
		if(myMediaTracker != null) {
			myMediaTracker.addImage(_img, id);
			try {
				myMediaTracker.waitForID(id);
			}catch(InterruptedException ie) {
				ie.printStackTrace();
			}
			id++;
		}
		return _img;
	}

	public BufferedImage createBufferedImage(Image img) {
		if(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) return null;
		BufferedImage _img = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = (Graphics2D) _img.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();
		return _img;
	}
}
