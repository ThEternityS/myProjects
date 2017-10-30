package me.main.managers;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class WindowManager extends Canvas {
	private static final long serialVersionUID = 1L;

	private BufferedImage buffer;
	
	public WindowManager(int w, int h) {
		this.setPreferredSize(new Dimension(w, h));
		this.setIgnoreRepaint(true);
		
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				createBuffer();
			}
		});
	}
	
	public void initScreen() {
		createBuffer();
	}
	
	public void renderScreen() {
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		if(g2d == null) return;
		if(buffer == null || buffer.getWidth() != this.getWidth() || buffer.getHeight() != this.getHeight()) return;
		
		g2d.drawImage(buffer, 0, 0, null);
		g2d.dispose();
	}
	
	public Graphics2D getDrawGraphics() {
		return buffer.createGraphics();
	}
	
	private void createBuffer() {
		buffer = this.getGraphicsConfiguration().createCompatibleImage(this.getWidth(), this.getHeight());
	}
	
	public void resize() {
		createBuffer();
	}
}
