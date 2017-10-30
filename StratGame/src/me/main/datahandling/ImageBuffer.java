package me.main.datahandling;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import me.entity.Displayable;

public class ImageBuffer {

	private static ImageBuffer myInstance;
	
	private Map<Displayable, TransformedImage> lastTransformedImage;
	
	
	private ImageBuffer() {
		lastTransformedImage = new HashMap<Displayable, TransformedImage>();
	}
	
	public static ImageBuffer getInstance() {
		if(myInstance == null) {
			myInstance = new ImageBuffer();
		}
		return myInstance;
	}
	
	public BufferedImage getLastScaledInstance(Displayable d, double p_zoom) {
		if(isAppropriatelyScaled(d, p_zoom)) {
			return (lastTransformedImage.get(d)).getImage();
		}else {
			return saveScaledInstance(d, p_zoom);
		}
	}
	
	private BufferedImage saveScaledInstance(Displayable d, double p_zoom) {
		TransformedImage _newImg = new TransformedImage(d.getImage(), p_zoom, d.getAngel());
		lastTransformedImage.put(d, _newImg);
		return _newImg.getImage();
	}
	
	private boolean isAppropriatelyScaled(Displayable d, double p_zoom) {
		if(lastTransformedImage.get(d) == null) return false;
		return (lastTransformedImage.get(d).hasSameProperties(p_zoom, d.getAngel()));
	}
}
