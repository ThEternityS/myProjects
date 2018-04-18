package org.entities.initiator;

import org.main.EntityHolder;

public abstract class Initiator {
	
	private EntityHolder holder;
	
	public void setEntityHolderToUse(EntityHolder eh) {
		holder = eh;
	}
	
	public EntityHolder getHolder() {
		return holder;
	}
}
