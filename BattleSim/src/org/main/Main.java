package org.main;

import javax.swing.JFrame;

import org.entities.initiator.EffectInitiator;
import org.entities.initiator.UnitInitiator;

public class Main extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private Battleground myBattleground;
	private EntityHolder myEntities;
	
	public Main() {
		super("BattleSim");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIgnoreRepaint(true);
		this.setResizable(false);
		this.setVisible(true);
		
		//order sensitive
		initBattleground();
		initEntities();
		initInitiators();
		
		initTestData();
		
		this.pack();
	}
	
	private void initBattleground() {
		myBattleground = new Battleground(WIDTH, HEIGHT);
		this.add(myBattleground);
		this.validate();
	}
	private void initEntities() {
		myEntities = new EntityHolder();
	}
	
	/**
	 * creates the initiator singleton instances for the first time
	 * sets initiator holder to use
	 */
	private void initInitiators() {
		(EffectInitiator.getInstance()).setEntityHolderToUse(myEntities);;
		(UnitInitiator.getInstance()).setEntityHolderToUse(myEntities);;
	}
	
	private void initTestData() {
		
		UnitInitiator ui = UnitInitiator.getInstance();
		
		
		
		ui.createEnemyStagnantUnit(400, 100);
		ui.createEnemyStagnantUnit(495, 95);
		ui.createEnemyStagnantUnit(483, 100);
		ui.createEnemyStagnantUnit(180, 200);
		ui.createEnemyStagnantUnit(400, 180);
		//create individual units
		for(int i = 0; i < 1; ++i) {
			ui.createAlliedIntelligentUnit(200 + 30 * i, 200);
		}
		
		ui.createEnemyStagnantUnit(140, 400);
		ui.createEnemyStagnantUnit(200, 400);
		ui.createAlliedStagnantUnit(170, 400);
		
		//EffectInitiator.getInstance().createPingEffect(100, 100);
	}
	
	private void update() {
		myEntities.update();
	}
	
	private void render() {
		myBattleground.addDrawUnits(myEntities.getRenderEntities());
		myBattleground.render();
	}
	
	@Override
	public void run() {
		while(true) {
			
			update();
			render();
			
			try {
				Thread.sleep(10);
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(new Main());
		t.start();
	}
}
