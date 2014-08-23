package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import TileMap.TileMap;


public class Village extends GameState {
	
	private TileMap tileMap;
	
	public Village (GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	 
	public void init() {
		
		tileMap = new Tile(30);
		tileMap.loadTiles("/tilesets.grass.gif");
		tileMap.loadMap("maps/village.map");
		tileMap.setPosition(0, 0);
		
	}
	
	public void update() {
		
	}
	public void draw (Graphics2D g) {
		
		// clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		// draw tilemap
		tileMap.draw(g);
		
	}
	public void keyPressed(int k) {
		
	}
	public void keyReleased(int k) {
		
	}	
	
}
