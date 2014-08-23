package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import TileMap.BackGround;
import TileMap.TileMap;


public class Village extends GameState {
	
	private TileMap tileMap;
	private BackGround bg;
	
	public Village (GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	 
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/tilesets/VillageTiles.png");
		tileMap.loadMap("/maps/village.map");
		tileMap.setPosition(0, 0);
		
		bg = new BackGround("/Backgrounds/grass.png", 0.1);
		
	}
	
	public void update() {
		
	}
	public void draw (Graphics2D g) {
		
		// clear screen
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
	}
	public void keyPressed(int k) {
		
	}
	public void keyReleased(int k) {
		
	}	
	
}
