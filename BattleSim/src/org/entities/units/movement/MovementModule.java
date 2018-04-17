package org.entities.units.movement;

import org.util.Vector2D;

public interface MovementModule {
	public void moveHost();
	public Vector2D getVelocity();
}
