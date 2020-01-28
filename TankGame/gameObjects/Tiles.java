package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.GameWorld;

/*
 * A class that implements function of tiles. 
 * */
public class Tiles implements Drawable{

	private BufferedImage img;
	
	public Tiles(BufferedImage img) {
		this.img = img;
	}

	@Override
	public void drawImage(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int width = 0; width < GameWorld.SCREEN_WIDTH; width+=320) {
			for (int height = 0; height < GameWorld.SCREEN_HEIGHT; height+=240) {
				g2d.drawImage(this.img, width, height, null);
			}
		}
	}
}
