package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameObjects.Tank;

public class TankControl implements KeyListener {
	
	private Tank t;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;
    
    public TankControl(Tank t, int up, int down, int left, int right, int shoot) {
        this.t = t;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    	
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.t.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.t.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.t.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.t.toggleRightPressed();
        }
        if (keyPressed == shoot) {
            this.t.toggleShootPressed();
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.t.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.t.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.t.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.t.unToggleRightPressed();
        }
        if (keyReleased  == shoot) {
            this.t.unToggleShootPressed();
        }
        
    }
}
