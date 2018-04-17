package org.entities;

import java.awt.Color;

import org.entities.effects.LineEffect;
import org.entities.effects.PingEffect;

public class EffectInitiator extends Initiator {

	private static EffectInitiator myInstance;
	
	private EffectInitiator() {}
	
	public static EffectInitiator getInstance() {
		if(myInstance == null) myInstance = new EffectInitiator();
		return myInstance;
	}

	public boolean createLineEffect(double x1, double y1, double x2, double y2) {
		if(this.getHolder() == null) return false;
		return this.getHolder().createEffect(new LineEffect((int) x1, (int) y1, (int) x2, (int) y2, Color.BLUE), 2);
	}
	
	public boolean createPingEffect(double x, double y) {
		if(this.getHolder() == null) return false;
		return this.getHolder().createEffect(new PingEffect((int) x, (int) y, Color.GRAY), 2);
	}
}
