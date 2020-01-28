package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.GameWorld;

/*
 * A class that implements functions of a power up. 
 * */
public class PowerUp implements Drawable, Collidable {
	
	private BufferedImage img;
	private int x , y;
	private boolean visible = true;
	private Rectangle r;
	
	public PowerUp(BufferedImage img, int x, int y) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.r = new Rectangle(this.x, this.y, this.img.getWidth(), this.img.getHeight());
	}
	
	public boolean getVisibility() {
		return visible;
	}
	
	@Override
	public Rectangle getBounds() {
		return r;
	}
	
	@Override
	public void handleCollision(Collidable c) {
		if (c instanceof Tank) {
			visible = false;
		}
	}

	@Override
	public void drawImage(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.img, GameWorld.SCREEN_WIDTH / 2 - this.img.getWidth() / 2, GameWorld.SCREEN_HEIGHT / 2 - this.img.getHeight() / 2, null);
	}

}
