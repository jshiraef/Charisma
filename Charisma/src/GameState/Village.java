package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Audio.AudioPlayer;

import main.GamePanel;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.PixelThug;
import Entity.Player;
import TileMap.BackGround;
import TileMap.TileMap;


public class Village extends GameState {
	
	private TileMap tileMap;
	private BackGround bg;
	
	private AudioPlayer bgMusic;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	
	public Village (GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	 
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/tilesets/VillageTiles.png");
		tileMap.loadMap("/maps/village.map");
		tileMap.setPosition(0, 0);
		tileMap.setCameraTweaker(0.07);
		
		bg = new BackGround("/Backgrounds/grass.png", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
		populateEnemies();
		
		
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/sound/music/village.mp3");
		bgMusic.play();
		bgMusic.setVolume(-20.0f); // Reduce Volume by 10 decibels
		
	}
	
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		
		PixelThug pt;
		Point[] points = new Point[] {new Point(200, 100), new Point(860, 200), new Point(1525, 200), new Point(1680, 200), new Point(1800, 200), new Point(2700, 200), new Point(2720, 200), new Point(2740, 200)};
		
		for(int i = 0; i < points.length; i++) {
			pt = new PixelThug(tileMap);
			pt.setPosition(points[i].x, points[i].y);
			enemies.add(pt);
		}
		
		pt = new PixelThug(tileMap);
		pt.setPosition(860, 200);
		
		
	}
	
	public void update() {
		// update player
		player.update();
		tileMap.setPosition(GamePanel.WIDTH/ 2 - player.getx(), GamePanel.HEIGHT/2 - player.gety() );
		
		// background setup
		
		bg.setPosition(tileMap.getX(), tileMap.getY());
		
		// attacking
		player.checkAttack(enemies);
		
		// enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		
		// explosion
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}

	}
	public void draw (Graphics2D g) {
		
		// clear screen
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		// enemies
		for(int i = 0; i < enemies.size(); i++ ) {
			enemies.get(i).draw(g);
		}
		
		// explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tileMap.getX(), (int)tileMap.getY());
			explosions.get(i).draw(g);
		}
		
		// hud
		hud.draw(g);
		
	}
	public void keyPressed(int k) {
		
		if(k == KeyEvent.VK_LEFT) {
			player.setLeft(true);
		}
		if(k == KeyEvent.VK_RIGHT) {
			player.setRight(true);
		}
		if(k == KeyEvent.VK_UP) {
			player.setUp(true);
		}
		if(k == KeyEvent.VK_DOWN) {
			player.setDown(true);
		}
		if(k == KeyEvent.VK_W) {
			player.setJumping(true);
		}
		if(k == KeyEvent.VK_E) {
			player.setHovering(true);
		}
		if(k == KeyEvent.VK_R) {
			player.setSwiping();
		}
		if(k == KeyEvent.VK_F) {
			player.setFiring();
		}
	}
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) {
			player.setLeft(false);
		}
		if(k == KeyEvent.VK_RIGHT) {
			player.setRight(false);
		}
		if(k == KeyEvent.VK_UP) {
			player.setUp(false);
		}
		if(k == KeyEvent.VK_DOWN) {
			player.setDown(false);
		}
		if(k == KeyEvent.VK_W) {
			player.setJumping(false);
		}
		if(k == KeyEvent.VK_E) {
			player.setHovering(false);
		}
		
	}	
	
}
