package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static javax.imageio.ImageIO.read;
import gameObjects.*;

/*
 * Main class
 * */
public class GameWorld extends JPanel {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SCREEN_WIDTH = 960;
    public static final int SCREEN_HEIGHT = 960;
    private BufferedImage world;
	private Graphics2D buffer;
	private JFrame jf;
	private Tank t1;
	private Tank t2;
	private Tiles floor;
	private ArrayList<UnbreakableWall> unbreak_wall;
	private ArrayList<BreakableWall> break_wall;
	private PowerUp pow_up;
	private static ArrayList<Collidable> collidables = new ArrayList<>();
	
	private static boolean game_over = false;
	private int tank1_xbound;
	private int tank1_ybound;
	private int tank2_xbound;
	private int tank2_ybound;

	public static void main(String[] args) {
	    Thread x;
	    GameWorld gw = new GameWorld();
	    gw.init();
	    try {

	        while (!game_over) {
	        	gw.t1.update();
	           	gw.t2.update();
	           	
	           	
	           	if (!gw.t1.getBullets().isEmpty() && !collidables.contains(gw.t1.getBullets().get(0))) {
	           		collidables.add(gw.t1.getBullets().get(0));
	           	}
	           	if (!gw.t2.getBullets().isEmpty() && !collidables.contains(gw.t2.getBullets().get(0))) {
	           		collidables.add(gw.t2.getBullets().get(0));
	           	}
	           	
	    		for (int i = 0; i < collidables.size() - 1; i++) {
	    			for (int j = i + 1; j < collidables.size(); j++) {
	    				if (collidables.get(j) instanceof Bullets) {
	    					if (!gw.t1.getBullets().isEmpty()) {
	    						if (gw.t1.getBullets().get(0).getBounds().intersects(gw.t1.getBounds())) {
	    							continue;
	    						}
	    					}
	    					if (!gw.t2.getBullets().isEmpty()) {
	    						if (gw.t2.getBullets().get(0).getBounds().intersects(gw.t2.getBounds())) {
	    							continue;
	    						}
	    					}
	    				}
	    				if (collidables.get(i).getBounds().intersects(collidables.get(j).getBounds())) {
	    					collidables.get(i).handleCollision(collidables.get(j));
	    					collidables.get(j).handleCollision(collidables.get(i));
	    				}
	    			}
	    		}
	    		
	    		gw.repaint();
	            System.out.println(gw.t1);
	            System.out.println(gw.t2);
	            Thread.sleep(1000 / 144);
	        }
	    } catch (InterruptedException ignored) {

	    }

	}


	private void init() {
		this.jf = new JFrame("Tank Game");
	    this.world = new BufferedImage(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
	    BufferedImage t1img = null, t2img = null, floorimg = null, unbreakable_wall = null, breakable_wall = null, 
	    		bullet = null, pu = null;
	    try {
	        BufferedImage tmp;
	        System.out.println(System.getProperty("user.dir"));
	        t1img = read(new File("Resources/tank1.png"));
	        t2img = read(new File("Resources/tank2.png"));
	        floorimg = read(new File("Resources/Background.bmp"));
	        unbreakable_wall = read(new File("Resources/UnbreakableWall.gif"));
	        breakable_wall = read(new File("Resources/BreakableWall.gif"));
	        bullet = read(new File("Resources/bullet.png"));
	        pu = read(new File("Resources/Pickup.gif"));


	    } catch (IOException ex) {
	        System.out.println(ex.getMessage());
	    }
	    
	    t1 = new Tank(80, 80, 0, 0, 0, t1img, bullet);
	    t2 = new Tank(860, 860, 0, 0, 0, t2img, bullet);
	    floor = new Tiles(floorimg);
	    unbreak_wall = new ArrayList<UnbreakableWall>();
	    break_wall = new ArrayList<BreakableWall>();
	    pow_up = new PowerUp(pu, SCREEN_WIDTH / 2 - pu.getWidth() / 2, SCREEN_HEIGHT / 2 - pu.getHeight() / 2);
	    collidables.add(t1);
	    collidables.add(t2);
	    collidables.add(pow_up);
	    
	    int[][] map_layout = Map.getMap();
		int y_loc = 0;
	    for (int row = 0; row < Map.getRow(); row++) {
	    	int x_loc = 0;
	    	for (int col = 0; col < Map.getCol(); col++) {
	    		if (map_layout[row][col] == 9) {
	    			UnbreakableWall uWall = new UnbreakableWall(unbreakable_wall, x_loc, y_loc);
	    			unbreak_wall.add(uWall);
	    			collidables.add(uWall);
	    		}
	    		if (map_layout[row][col] == 1) {
	    			BreakableWall bWall = new BreakableWall(breakable_wall, x_loc, y_loc);
	    			break_wall.add(bWall);
	    			collidables.add(bWall);
	    		}
	    		x_loc += 32;
	    	}
	    	y_loc += 32;
	    }

	    TankControl tc2 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
	    TankControl tc1 = new TankControl(t2, KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_ENTER);
	    
	    this.jf.setLayout(new BorderLayout());
	    this.jf.add(this);


        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);


	    this.jf.getContentPane().setPreferredSize(new Dimension(SCREEN_WIDTH - 10, SCREEN_HEIGHT / 2 - 10));
	    this.jf.pack();
	    this.jf.setResizable(false);
	    jf.setLocationRelativeTo(null);

	    this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.jf.setVisible(true);


	}

	@Override
    public void paintComponent(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
	    world = new BufferedImage(world.getWidth(), world.getHeight(), world.getType());
        buffer = world.createGraphics();
	    super.paintComponent(g2);

	    this.floor.drawImage(buffer); 
	    this.t1.drawImage(buffer);
	    this.t2.drawImage(buffer);
	    
		for (int i = 0; i < unbreak_wall.size(); i++) {
			this.unbreak_wall.get(i).drawImage(buffer);
		}
		for (int i = 0; i < break_wall.size(); i++) {
			if (this.break_wall.get(i).getVisibility())
				this.break_wall.get(i).drawImage(buffer);
			else {
				collidables.remove(break_wall.get(i));
				this.break_wall.remove(break_wall.get(i));
			}
	    }
		
		if (!t1.getBullets().isEmpty()) {
			if (t1.getBullets().get(0).getVisibility())
				t1.getBullets().get(0).drawImage(buffer);
			else {
				collidables.remove(t1.getBullets().get(0));
				t1.emptyBullets();
			}
		}
		if (!t2.getBullets().isEmpty()) {
			if (t2.getBullets().get(0).getVisibility())
				t2.getBullets().get(0).drawImage(buffer);
			else {
				collidables.remove(t2.getBullets().get(0));
				t2.emptyBullets();
			}
		}
		
		if (pow_up.getVisibility()) {
	    	this.pow_up.drawImage(buffer);
	    }
	    
	    // moving camera
	    tank1_xbound = t1.getX();
	    tank1_ybound = t1.getY();
	    tank2_xbound = t2.getX();
	    tank2_ybound = t2.getY();
	    
	    if (tank1_xbound < SCREEN_WIDTH / 4) {
	    	tank1_xbound = 0;
	    } else if (SCREEN_WIDTH - tank1_xbound < SCREEN_WIDTH / 4) {
	    	tank1_xbound = SCREEN_WIDTH / 2;
	    } else {
	    	tank1_xbound = tank1_xbound - SCREEN_WIDTH / 4;
	    }
	    
	    if (tank1_ybound < SCREEN_HEIGHT / 4) {
	    	tank1_ybound = 0;
	    } else if (SCREEN_HEIGHT - tank1_ybound < SCREEN_HEIGHT / 4) {
	    	tank1_ybound = SCREEN_HEIGHT / 2;
	    } else {
	    	tank1_ybound = tank1_ybound - SCREEN_HEIGHT / 4;
	    }
	    
	    if (tank2_xbound < SCREEN_WIDTH / 4) {
	    	tank2_xbound = 0;
	    } else if (SCREEN_WIDTH - tank2_xbound < SCREEN_WIDTH / 4) {
	    	tank2_xbound = SCREEN_WIDTH / 2;
	    } else {
	    	tank2_xbound = tank2_xbound - SCREEN_WIDTH / 4;
	    }
	    
	    if (tank2_ybound < SCREEN_HEIGHT / 4) {
	    	tank2_ybound = 0;
	    } else if (SCREEN_HEIGHT - tank2_ybound < SCREEN_HEIGHT / 4) {
	    	tank2_ybound = SCREEN_HEIGHT / 2;
	    } else {
	    	tank2_ybound = tank2_ybound - SCREEN_HEIGHT / 4;
	    }
	    
	    // split screen and mini-map
        g2.drawImage(world.getSubimage(tank1_xbound, tank1_ybound, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2),0,0,null);
        g2.drawImage(world.getSubimage(tank2_xbound, tank2_ybound, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2),SCREEN_WIDTH / 2,0,null);
        g2.drawImage(world.getSubimage(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT), SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2 - 150, 200, 150, null);
        
        // end of game
        g.setColor(Color.BLUE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 80));
        if (t1.getLife() == 0) {
        	g.drawString("Tank 2 Won", SCREEN_WIDTH / 4, tank2_ybound);
        	game_over = true;
        }
        if (t2.getLife() == 0) {
        	g.drawString("Tank 1 Won", SCREEN_WIDTH / 4, tank1_ybound);
        	game_over = true;
        }
	}
	    
}
