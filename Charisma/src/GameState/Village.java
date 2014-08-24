package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import main.GamePanel;
import Entity.Player;
import TileMap.BackGround;
import TileMap.TileMap;


public class Village extends GameState {
	
	private TileMap tileMap;
	private BackGround bg;
	
	private Player player;
	
	public Village (GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	 
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/tilesets/VillageTiles.png");
		tileMap.loadMap("/maps/village.map");
		tileMap.setPosition(0, 0);
		tileMap.setBetween(1);
		
		bg = new BackGround("/Backgrounds/grass.png", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
	}
	
	public void update() {
		// update player
		player.update();
		tileMap.setPosition(GamePanel.WIDTH/ 2 - player.getx(), GamePanel.HEIGHT/2 - player.gety() );
	}
	public void draw (Graphics2D g) {
		
		// clear screen
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
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
