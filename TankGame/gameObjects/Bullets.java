package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/*
 * A class that implements functions of bullets. 
 * */
public class Bullets implements Drawable, Collidable {
	
	private BufferedImage img;
	private int x, y, vx, vy, angle;
	private boolean visible = true;
	private Rectangle r;
	
	private final int R = 3;
	
	public Bullets(BufferedImage img, int x, int y, int angle, boolean visible) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.visible = visible;
		this.r = new Rectangle(this.x, this.y, this.img.getWidth(), this.img.getHeight());
	}
	
	public void update() {
		vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        r.setBounds(this.x, this.y, this.img.getWidth(), this.img.getHeight());
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
		if (c instanceof Tank || c instanceof UnbreakableWall || c instanceof BreakableWall) {
			visible = false;
		}
	}
	
	@Override
	public void drawImage(Graphics g) {
		AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
		rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.img, x, y, null);
		update();
	}

}
