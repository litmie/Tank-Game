package gameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.GameWorld;

/*
 * A class that implements functions of a tank.
 */
public class Tank implements Drawable, Collidable {
	
	private int health = 100;
	private int life = 3;
	
	private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    private int R = 1;
    private final int ROTATIONSPEED = 2;


    private BufferedImage img;
    private BufferedImage bullet;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private ArrayList<Bullets> b = new ArrayList<Bullets>(1);
    private Rectangle r;


    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, BufferedImage bullet) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.bullet = bullet;
        this.r = new Rectangle(this.x, this.y, this.img.getWidth(), this.img.getHeight());

    }
	
    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    
    public void toggleShootPressed() {
        this.ShootPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    
    public void unToggleShootPressed() {
        this.ShootPressed = false;
    }


    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed) {
        	addBullet();
        }
        r.setBounds(this.x, this.y, this.img.getWidth(), this.img.getHeight());
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    private void addBullet() {
    	if (b.isEmpty()) {
    		b.add(new Bullets(bullet, x, y, angle, true));
    	}
    }
    
    private void checkBorder() {
    	if (x < 40) {
            x = 40;
        }
        if (x >= GameWorld.SCREEN_WIDTH - 75) {
            x = GameWorld.SCREEN_WIDTH - 75;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameWorld.SCREEN_HEIGHT - 100) {
            y = GameWorld.SCREEN_HEIGHT - 100;
        }
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    public int getAngle() {
    	return angle;
    }
    
    public int getHealth() {
    	return health;
    }
    
    public int getLife() {
    	return life;
    }
    
    public ArrayList<Bullets> getBullets() {
    	return b;
    }
    
    public void emptyBullets() {
    	b.clear();
    }
    
    private void setR(int r) {
    	this.R = r;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }
    
    @Override
	public void drawImage(Graphics g) {
		AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
		rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.img, rotation, null);
		
		// health
		g.setColor(Color.GREEN);
		g.fillRect(x, y - 15, (this.img.getWidth()*health)/100, 7);
		
		// life
		g.setColor(Color.RED);
		for (int i = 0; i < life; i++) {
			int next = 10 * i;
			g.fillOval(x + next, this.img.getHeight() + y + 5, 10, 10);
		}
		
    }
	
    @Override
	public Rectangle getBounds() {
    	return r;
    }
    
    @Override
	public void handleCollision(Collidable c) {
    	if (c instanceof Bullets) {
    		if (!getBounds().contains(c.getBounds())) {
	    		if (health - 40 <= 0) {
	    			life -= 1;
	    			health = 100;
	    		} else {
	    		health -= 40;
	    		}
    		}
    	} else if (c instanceof PowerUp) {
    		setR(3);
    	} else if (c instanceof UnbreakableWall || c instanceof BreakableWall) {
    		if (UpPressed) {
	    		this.x -= vx;
	    		this.y -= vy;
    		} else if (DownPressed) {
    			this.x += vx;
	    		this.y += vy;
    		}
    	}
    }

}
