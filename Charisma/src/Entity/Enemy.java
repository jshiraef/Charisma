package Entity;

import TileMap.TileMap;

public class Enemy extends MapEntity {
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	protected boolean blinking;
	protected long blinkTimer;
	
	
	public Enemy(TileMap tm) {
		super(tm);
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void hit(int damage) {
		
		if(dead || blinking)
			return;
		
		health -= damage;
		if(health == 0)
			dead = true;
		
		blinking = true;
		blinkTimer = System.nanoTime();
		
	}
	
	public void update() {
		
	}

}
