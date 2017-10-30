package me.main.managers;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import me.particles.Particle;
import me.util.Vector2D;

public class ParticleManager {

	List<Particle> allParticles;
	
	public ParticleManager() {
		allParticles = new LinkedList<Particle>();
	}
	
	public void update() {
		
		if(allParticles.size() == 0) {
			System.out.println("new Explosion");
			//addExplosion(new Vector2D(100, 100), 250, 0.6);
			addImpact(new Vector2D(100, 100), 250, 0.9, new Vector2D(12, 5));
		}
		
		List<Particle> _dead = new LinkedList<Particle>();
		
		//update particles and find dead ones
		for(Particle $p: allParticles) {
			if($p.isAlive()) {
				$p.update();
			}else {
				_dead.add($p);
			}
		}
		
		//remove all dead particles
		allParticles.removeAll(_dead);
	}
	
	public void addExplosion(Vector2D origin, int density, double force) {
		for(int i = density; i > 0; i--) {
			Vector2D _vel = new Vector2D((Math.random() - 0.5), (Math.random() - 0.5));
			_vel.scaleTo(force * Math.random());
			
			allParticles.add(new Particle(origin, _vel, Color.BLACK, (int) (250 * Math.random() * force + 100)));
		}
	}
	
	public void addCircqueplosion(Vector2D origin, int density, double force) {
		createCircle(origin, (int) density / 3, force, Color.BLACK, 250);
		createCircle(origin, (int) density / 3, 2*force / 3, Color.RED, 300);
		createCircle(origin, (int) density / 3, force / 3, Color.YELLOW, 350);
	}
	
	private void createCircle(Vector2D origin, int density, double force, Color col, int ttl) {
		for(int i = density; i > 0; i--) {
			Vector2D _vel = new Vector2D((Math.random() - 0.5), (Math.random() - 0.5));
			_vel.scaleTo(force);
			
			allParticles.add(new Particle(origin, _vel, col, (int)(ttl * Math.random())));
		}
	}
	
	public void addImpact(Vector2D origin, int density, double force, Vector2D direction) {
		for(int i = density; i > 0; i--) {
			Vector2D _vel = new Vector2D(direction);
			_vel.scaleTo(force);
			double _xAdd = Math.random() > 0.5 ? force * Math.random(): -force * Math.random();
			double _yAdd = Math.random() > 0.5 ? force * Math.random(): -force * Math.random();
			
			_vel.add(_xAdd, _yAdd);
			allParticles.add(new Particle(origin, _vel, Color.CYAN, 100 + (int) (300 * Math.random())));
		}
	}
	
	public List<Particle> getAllParticles() {
		return allParticles;
	}
	
}
