package me.main.managers;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.entity.Displayable;
import me.entity.Entity;
import me.main.datahandling.ImageBuffer;
import me.particles.Particle;
import me.util.Vector2D;

public class DisplayManager implements Entity {

	private static final double ACCELARATION = 1;
	private static final double ZOOM_FACTOR_WIGHT = 1;
	private static final double FRICTION = 0.85;
	private static final double MAX_SCROLL = 10;
	private static final double STOP_VALUE = 0.5;
	
	//keep max zoom @1 to avoid scaling issues and render tree problems (disappearing entities)
	private static final double ZOOM_STEP_WIDTH = 0.05;
	private static final double MIN_ZOOM = 0.25;
	private static final double MAX_ZOOM = 1;
	
	private static final int ZOOM_TRANSITION = 3;
	
	private Component drawArea;
	
	private double zoomFactor = 1;
	private Vector2D scroll;
	private int posX = 0;
	private int posY = 0;
	
	//private int lastRotation = 0;
	
	private int playFieldWidth;
	private int playFieldHeight;
	
	public DisplayManager(Component p_drawArea, int w, int h) {
		this.drawArea = p_drawArea;
		this.playFieldWidth = w;
		this.playFieldHeight = h;
		scroll = new Vector2D(0, 0);
	}
	
	public void drawDisplayable(Graphics2D g2d, Displayable d) {
		if(d == null) return;
		int _entityXLocal = (int) (convertToLocalX(d.getX()));
		int _entityYLocal = (int) (convertToLocalY(d.getY()));
		
		g2d.drawImage(d.getImage(), _entityXLocal, _entityYLocal, null);
		//g2d.drawImage(e.getImage() , e.getX(), e.getY(), null);
	}
	public void drawDisplayableCentered(Graphics2D g2d, Displayable d) {
		if(d == null) return;
		int _entityXLocal = (int) (convertToLocalX(d.getX()+ d.getWidth() / 2));
		int _entityYLocal = (int) (convertToLocalY(d.getY()+ d.getHeight() / 2));
		
		AffineTransform at = new AffineTransform();
		at.translate( _entityXLocal, _entityYLocal);
		at.rotate(d.getAngel());
		g2d.setTransform(at);
		
		g2d.drawImage(ImageBuffer.getInstance().getLastScaledInstance(d, zoomFactor), -d.getWidth() / 2, -d.getHeight() / 2, null);
		
		at.rotate(-d.getAngel());
		at.translate(-_entityXLocal, -_entityYLocal);
		g2d.setTransform(at);
	}
	
	public void drawParticle(Graphics2D g2d, Particle p) {
		g2d.setColor(p.getColor());
		int _x = (int) convertToLocalX(p.getX());
		int _y = (int) convertToLocalY(p.getY());
		g2d.drawLine(_x, _y, _x, _y);
	}
	
	public Vector2D convertToLocal(Vector2D vec) {
		return new Vector2D(convertToLocalX(vec.getX()), convertToLocalY(vec.getY()));
	}
	public double convertToLocalX(double x) {
		return (x - posX) * zoomFactor;
	}
	public double convertToLocalY(double y) {
		return (y - posY) * zoomFactor;
	}
	
	public Vector2D convertToGlobal(Vector2D vec) {
		return new Vector2D(convertToGlobalX(vec.getX()), convertToGlobalY(vec.getY()));
	}
	public double convertToGlobalX(double x) {
		return x / zoomFactor + posX;
	}
	public double convertToGlobalY(double y) {
		return y / zoomFactor + posY;
	}

	/**
	 * zooms in/out the given number of steps
	 * @param p_zoomSteps steps to zoom in (if positive)/out (if negative)
	 */
	public void zoom(int p_zoomSteps, Vector2D p_focusPoint) {
		if(p_zoomSteps == 0) return;
		
		double _prev = zoomFactor;
		
		zoomFactor += p_zoomSteps * ZOOM_STEP_WIDTH;
		if(zoomFactor < MIN_ZOOM) zoomFactor = MIN_ZOOM;
		if(zoomFactor > MAX_ZOOM) zoomFactor = MAX_ZOOM;
		
		//do nothing if zoom factor didn't change
		if(_prev == zoomFactor) return;
		
		if(p_zoomSteps > 0) {
			Vector2D _direction = Vector2D.diffrence(p_focusPoint, calcScreenMid());
		
			_direction.minAndMax(ZOOM_TRANSITION);
			transitionView(_direction.getX(), _direction.getY());
		}
	}
	
	private Vector2D calcScreenMid() {
		return new Vector2D(drawArea.getWidth() / 2, drawArea.getHeight() / 2);
	}

	
	/**
	 * transitions the view on the play field
	 * @param moveX preferably only -1/0/1 for moving left/not/right
	 * @param moveY preferably only -1/0/1 for moving up/not/down
	 */
	public void transitionView(double moveX, double moveY) {
		if(moveX == 0 && moveY == 0) return;
		//accelerate scrolling
		scroll.add(moveX * ACCELARATION * ZOOM_FACTOR_WIGHT / zoomFactor, moveY * ACCELARATION * ZOOM_FACTOR_WIGHT / zoomFactor);		
	}
	
	private void applyFriction() {
		//slow scroll speed
		scroll.scale(FRICTION);
		//stop slow scrolling
		scroll.minAndMax(MAX_SCROLL);
		if(scroll.length() < STOP_VALUE) {
			scroll.set(0, 0);
		}
	}
	
	public void update() {
		//apply scrolling
		posX += scroll.getX() + 0.5;
		posY += scroll.getY() + 0.5;
		
		applyFriction();
		
		//check bounds
		int shownWidth = (int) (drawArea.getWidth() / zoomFactor) + 1;
		int shownHeight = (int) (drawArea.getHeight() / zoomFactor) + 1;
		if(posX + shownWidth > playFieldWidth) {
			posX = playFieldWidth - shownWidth;
			scroll.setX(0);
		}
		if(posY + shownHeight > playFieldHeight) {
			posY = playFieldHeight - shownHeight;
			scroll.setY(0);
		}
		//need to be at the end to ensure no errors while using resolution larger then the playfield
		if(posX < 0) {
			posX = 0;
			scroll.setX(0);
		}
		if(posY < 0) {
			posY = 0;
			scroll.setY(0);
		}
	}

	@Override
	public int getX() {
		return posX;
	}

	@Override
	public int getY() {
		return posY;
	}

	@Override
	public int getWidth() {
		return (int) ((drawArea.getWidth() / zoomFactor) + 1);
	}

	@Override
	public int getHeight() {
		return (int) ((drawArea.getHeight() / zoomFactor) + 1);
	}
}
