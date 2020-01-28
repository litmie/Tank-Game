package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/*
 * A class that implements functions of unbreakable walls. 
 * */
public class UnbreakableWall implements Drawable, Collidable {
	
	private BufferedImage img;
	private int x, y;
	private Rectangle r;
	
	public UnbreakableWall(BufferedImage img, int x, int y) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.r = new Rectangle(this.x, this.y, this.img.getWidth(), this.img.getHeight());
	}
	
	@Override
	public void drawImage(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	    g2d.drawImage(this.img, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return r;
	}

	@Override
	public void handleCollision(Collidable c) {}


}
