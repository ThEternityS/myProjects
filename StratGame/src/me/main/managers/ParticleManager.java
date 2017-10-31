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
			addImpact(new Vector2D(100, 100), 250, 3, 1, new Vector2D(10, 1));
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
	
	public void addImpact(Vector2D origin, int density, double force, double spread, Vector2D direction) {
		createCone(origin, (int) (2 * density / 3), force, spread, direction);
		
		Vector2D smallConeDir = new Vector2D(direction);
		smallConeDir.scale(-1);
		System.out.println(direction);
		System.out.println(smallConeDir);
		
		createCone(origin, (int) (density  / 3), force / 100, spread, smallConeDir);
		
	}
	
	private void createCone(Vector2D origin, int density, double force, double spread, Vector2D direction) {
		for(int i = 0; i < density; i++) {
			Vector2D _vel = new Vector2D(direction);
			_vel.scaleTo(force);
			_vel.add(new Vector2D((Math.random() - 0.5) * spread, (Math.random() - 0.5) * spread));
			_vel.scale(Math.random());
			
			allParticles.add(new Particle(origin, _vel, Color.CYAN, (int)(Math.random() * 300)));
		}
	}
	
	public List<Particle> getAllParticles() {
		return allParticles;
	}
	
}
