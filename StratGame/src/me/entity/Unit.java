package me.entity;
import java.awt.Image;

import me.main.managers.UnitManager;
import me.util.ImageCreator;
import me.util.Vector2D;

public class Unit implements Updateable, Displayable, Alive {

	private Vector2D position;
	
	private UnitMovementManager myMovementManager;
	private int health;
	
	private Vector2D destination;
	
	private static Image myImage = ImageCreator.getInstance().loadImage("entityR.png");
	
	public Unit(UnitManager um) {
		this(um, 0, 0);
	}
	
	public Unit(UnitManager um, int x, int y) {
		myMovementManager = new UnitMovementManager(um, this);
		position = new Vector2D(x, y);
		destination = new Vector2D(position);
	}
	
	public void moveTo(Vector2D p_destination) {
		this.destination = p_destination;
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	@Override
	public void update() {
		if(destination != null) {
			myMovementManager.moveTo(destination);
		}
		myMovementManager.collisionAvoidance();
		myMovementManager.update();
	}
	
	@Override
	public int getX() {
		return position.getIntX() - getWidth() / 2;
	}
	@Override
	public int getY() {
		return position.getIntY() - getHeight() / 2;
	}
	@Override
	public int getWidth() {
		if(myImage == null) return 0;
		return myImage.getWidth(null);
	}
	@Override
	public int getHeight() {
		if(myImage == null) return 0;
		return myImage.getHeight(null);
	}
	@Override
	public Image getImage() {
		return myImage;
	}
	@Override
	public double getAngel() {
		return myMovementManager.getVelocity().calcAngle();
	}

	@Override
	public boolean isAlive() {
		return (health > 0);
	}

	

}
