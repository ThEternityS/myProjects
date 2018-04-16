package org.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import org.entities.Renderable;

public class Battleground extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final Color CLEAR_COLOR = Color.CYAN;

	private BufferedImage buffer;
	
	private List<Renderable> renderObjects;
	
	public Battleground(int width, int height) {
		super();
		
		this.setIgnoreRepaint(true);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLUE);
		
		renderObjects = new ArrayList<Renderable>();
	}
	
	private void createBuffer() {
		buffer = this.getGraphicsConfiguration().createCompatibleImage(this.getWidth(), this.getHeight());
	}
	public void addDrawUnits(Collection<? extends Renderable> r) {
		renderObjects.addAll(r);
	}
	
	public void render() {
		
		renderOnBuffer();
		renderScreen();
		
		//clear the references to the objects to render in this cycle
		renderObjects.clear();
	}
	
	/**
	 * creates graphics of the buffer
	 * clears the previously buffered image
	 * call method to render objects onto the buffer
	 */
	private void renderOnBuffer() {
		//create graphics object
		if(buffer == null || buffer.getWidth() != this.getWidth() || buffer.getHeight() != this.getHeight()) createBuffer();
		Graphics2D g2d = (Graphics2D) buffer.createGraphics();

		clearBuffer(g2d);
		renderObjectsOnBuffer(g2d);
		
		g2d.dispose();
	}
	
	/**
	 * renders the current renderObjects to the given graphics object
	 * @param g2d graphics object to draw on
	 */
	private void renderObjectsOnBuffer(Graphics2D g2d) {
		//draw render objects
		for(Renderable r: renderObjects) {
			r.render(g2d);
		}
	}

	private void clearBuffer(Graphics2D g2d) {
		g2d.setColor(CLEAR_COLOR);
		g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
	}
	
	private void renderScreen() {
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		g2d.drawImage(buffer, 0, 0, null);
	}
}
