package me.main.managers;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import me.util.Vector2D;

public class KeyManager implements KeyListener, MouseListener, MouseWheelListener {

	private Component myComponent;
	
	private boolean[] keymap;
	
	private Vector2D leftDragStart;
	private boolean leftButtonPressed;
	private boolean rightButtonPressed;
	private boolean leftButtonClicked;
	private boolean rightButtonClicked;
	
	private int mouseWheelNotches;
	
	
	
	public KeyManager(Component c) {
		
		this.myComponent = c;
		
		keymap = new boolean[4];
		for(int i = 0; i < 4; i++) {
			keymap[i] = false;
		}
		
		leftDragStart = new Vector2D(0, 0);
		leftButtonPressed = false;
		rightButtonPressed = false;

		mouseWheelNotches = 0;
	}
	
	/**
	 * reads if the key with the given key map mapping is currently pressed down
	 * @param p_key keymap index to check
	 * @return true if key is pressed down, false if key is released or the index is out of bounds
	 */
	public boolean getKey(int p_key) {
		try {
			return keymap[p_key];
		}catch(ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * reads if the key with the given key map mapping is currently pressed down and sets its state to released, allowing for further detections as pressed
	 * @param p_key keymap index to check
	 * @return true if the key is pressed, false if the key is released or the index is out of bounds
	 */
	public boolean readKey(int p_key) {
		try {
			if(keymap[p_key]) {
				keymap[p_key] = false;
				return true;
			}
		}catch(ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean isLeftButtonPressed() {
		return leftButtonPressed;
	}
	
	public boolean isRightButtonPressed() {
		return rightButtonPressed;
	}
	
	public boolean readLeftButtonClick() {
		if(!leftButtonClicked) return false;
		leftButtonClicked = false;
		return true;
	}
	
	public boolean readRightButtonClick() {
		if(!rightButtonClicked) return false;
		rightButtonClicked = false;
		return true;
	}
	
	public Vector2D getLeftDragStart() {
		return leftDragStart;
	}
	
	public int getMouseWheel() {
		return mouseWheelNotches;
	}
	
	/**
	 * reads the notches the mouse wheel moved since the last read and resets the notches to 0
	 * @return negative integer number of notches the wheel movedup, positive int number of notches the wheel moved down
	 */
	public int readMouseWheel() {
		int _ret = mouseWheelNotches;
		mouseWheelNotches = 0;
		return _ret;
	}
	
	public Vector2D getMousePositionRelativeToComponent() {
		Point _pos = myComponent.getMousePosition();
		if(_pos == null) return new Vector2D(0, 0);
		return new Vector2D(_pos.getX(), _pos.getY());
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		setKeys(evt, true);
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		setKeys(evt, false);
	}
	
	/**
	 * sets the key pressed state for the given key event keys
	 * @param evt keys
	 * @param p_state to set for the given keys
	 */
	private void setKeys(KeyEvent evt, boolean p_state) {
		if(evt.getKeyCode() == KeyEvent.VK_LEFT) keymap[0] = p_state;
		if(evt.getKeyCode() == KeyEvent.VK_RIGHT) keymap[1] = p_state;
		if(evt.getKeyCode() == KeyEvent.VK_UP) keymap[2] = p_state;
		if(evt.getKeyCode() == KeyEvent.VK_DOWN) keymap[3] = p_state;
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent evt) {
		switch(evt.getButton()) {
			case MouseEvent.BUTTON1:
				leftButtonClicked = true;
				break;
			case MouseEvent.BUTTON3:
				rightButtonClicked = true;
				break;
			default: break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		switch(evt.getButton()) {
			case MouseEvent.BUTTON1:
				if(!leftButtonPressed) {
					leftDragStart.set(evt.getX(), evt.getY());
				}
				leftButtonPressed = true;
				break;
			case MouseEvent.BUTTON3:
				rightButtonPressed = true;
				break;
			default: break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		switch(evt.getButton()) {
			case MouseEvent.BUTTON1:
				leftButtonPressed = false;
				break;
			case MouseEvent.BUTTON3:
				rightButtonPressed = false;
				break;
			default: break;
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent evt) {
		mouseWheelNotches += evt.getWheelRotation();
	}
	
}
