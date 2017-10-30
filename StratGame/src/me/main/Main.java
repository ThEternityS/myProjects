package me.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JFrame;

import me.entity.Displayable;
import me.entity.QuadTree;
import me.entity.Unit;
import me.gamestate.GameStateStack;
import me.gamestate.PossibleState;
import me.main.managers.DisplayManager;
import me.main.managers.KeyManager;
import me.main.managers.OverlayManager;
import me.main.managers.ParticleManager;
import me.main.managers.UnitManager;
import me.main.managers.WindowManager;
import me.particles.Particle;
import me.util.ImageCreator;

public class Main implements Runnable {

	private static final String		NAME		= "Strat";
	private static final int		WIDTH		= 800;
	private static final int		HEIGHT		= 600;
	private static final int		PLAY_WIDTH	= 6000;
	private static final int		PLAY_HEIGTH	= 3000;

	private static final int		LPS			= 120;

	private boolean					running		= false;

	private WindowManager			myWindowManager;
	private DisplayManager			myDisplayManager;
	private KeyManager				myKeyManager;
	private OverlayManager			myOverlayManager;
	private UnitManager				myUnitManager;
	private ParticleManager			myParticleManager;

	private GameStateStack			myGameStateStack;

	private QuadTree<Displayable>	renderTree;

	public Main() {
		myWindowManager = new WindowManager(WIDTH, HEIGHT);
		myDisplayManager = new DisplayManager(myWindowManager, PLAY_WIDTH, PLAY_HEIGTH);
		myKeyManager = new KeyManager(myWindowManager);
		myOverlayManager = new OverlayManager();
		myUnitManager = new UnitManager();
		myParticleManager = new ParticleManager();

		myGameStateStack = new GameStateStack(this);
		renderTree = new QuadTree<Displayable>(-200, -200, PLAY_WIDTH + 400, PLAY_HEIGTH + 400);
	}

	public void init() {
		initWindow();

		// set the image creator component to ensure proper loading of images
		ImageCreator.getInstance().setComponent(myWindowManager);

		// first game state
		myGameStateStack.push(PossibleState.IDLE_STATE);

		// units
		myUnitManager.addControllableUnit(new Unit(myUnitManager, 100, 100));
		myUnitManager.addControllableUnit(new Unit(myUnitManager, 100, 200));
		myUnitManager.addControllableUnit(new Unit(myUnitManager, 100, 300));
		myUnitManager.addControllableUnit(new Unit(myUnitManager, 100, 400));

		// overlay elements
		myOverlayManager.addPlayFieldBounds(PLAY_WIDTH, PLAY_HEIGTH);
	}

	private void initWindow() {

		JFrame f = new JFrame(NAME);
		f.add(myWindowManager);
		f.pack();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(true);
		f.setLocationRelativeTo(null);
		f.setVisible(true);

		initWindowManager();
	}
	
	private void initWindowManager() {
		myWindowManager.addKeyListener(myKeyManager);
		myWindowManager.addMouseWheelListener(myKeyManager);
		myWindowManager.addMouseListener(myKeyManager);

		myWindowManager.initScreen();
		myWindowManager.requestFocus();
	}

	public void start() {
		running = true;

		init();

		Thread t = new Thread(this);
		t.start();
	}

	public void stop() {
		running = false;
	}

	@Override
	public void run() {

		// ensure loading times
		try {
			Thread.sleep(50);
		}catch(InterruptedException ie) {
			ie.printStackTrace();
		}

		int fps = 0;
		int lps = 0;

		double msPerTick = (double) 1000 / LPS;
		int needUpdate = 0;
		boolean needRender = true;
		long endTime = 0;
		double timeBuffer = 0;
		long startTime = System.currentTimeMillis();
		long fpsTimer = startTime;

		while(running) {
			// update timers
			endTime = System.currentTimeMillis();
			timeBuffer += endTime - startTime;
			startTime = System.currentTimeMillis();

			needUpdate += (int) (timeBuffer / msPerTick);
			timeBuffer %= msPerTick;

			// update cycle
			while(needUpdate >= 1) {
				update();
				needUpdate--;
				lps++;
				needRender = true;
			}

			// fail save wait
			try {
				Thread.sleep(1);
			}catch(InterruptedException ie) {
				ie.printStackTrace();
			}

			// render cycle
			if(needRender) {
				render();
				fps++;
				needRender = false;
			}

			// counter
			if(System.currentTimeMillis() - fpsTimer >= 1000) {
				System.out.println("ticks: " + lps + " fps: " + fps);
				fpsTimer = System.currentTimeMillis();
				fps = 0;
				lps = 0;
			}

			// wait
			try {
				Thread.sleep(10);
			}catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	public void update() {
		updateView();
		
		myGameStateStack.update();

		myOverlayManager.update();

		myUnitManager.update();
		
		myParticleManager.update();
	}
	
	private void updateView() {
		int transX = 0;
		int transY = 0;
		if(myKeyManager.getKey(0)) transX--;
		if(myKeyManager.getKey(1)) transX++;
		if(myKeyManager.getKey(2)) transY--;
		if(myKeyManager.getKey(3)) transY++;

		myDisplayManager.zoom(-myKeyManager.readMouseWheel(), myKeyManager.getMousePositionRelativeToComponent());
		myDisplayManager.transitionView(transX, transY);
		myDisplayManager.update();
	}

	public void render() {
		Graphics2D g2d = myWindowManager.getDrawGraphics();

		if(g2d != null) {
			clearScreen(g2d);

			renderUnits(g2d);
			renderParticles(g2d);
			renderOverlay(g2d);

			g2d.dispose();
		}
		myWindowManager.renderScreen();
	}

	private void clearScreen(Graphics2D g2d) {
		g2d.setColor(Color.PINK);
		g2d.fillRect(0, 0, myWindowManager.getWidth(), myWindowManager.getHeight());
	}

	private void renderOverlay(Graphics2D g2d) {
		createRenderTree(myOverlayManager.getDisplayables());
		for(Displayable $d: renderTree.getAllContainedIn(myDisplayManager)) {
			myDisplayManager.drawDisplayable(g2d, $d);
		}
	}

	private void renderUnits(Graphics2D g2d) {
		for(Displayable $d: myUnitManager.getAllContainedIn(myDisplayManager)) {
			myDisplayManager.drawDisplayableCentered(g2d, $d);
		}
	}

	private void renderParticles(Graphics2D g2d) {
		for(Particle $p: myParticleManager.getAllParticles()) {
			myDisplayManager.drawParticle(g2d, $p);
		}
	}

	private void createRenderTree(List<? extends Displayable> list) {
		renderTree.clear();
		renderTree.insertAll(list);
	}

	public KeyManager getKeyManager() {
		return myKeyManager;
	}
	public DisplayManager getDisplayManager() {
		return myDisplayManager;
	}
	public OverlayManager getOverlayManager() {
		return myOverlayManager;
	}
	public UnitManager getUnitManager() {
		return myUnitManager;
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}

}
