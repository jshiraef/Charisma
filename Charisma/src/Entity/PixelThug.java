package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class PixelThug extends Enemy{
	
	private BufferedImage[] sprites;
	
	public PixelThug(TileMap tm) {
		super (tm);
		
		moveSpeed = 0.3;
		maxSpeed = 0.3;
		fallSpeed = 0.3;
		maxFallSpeed = 10;
		
		width = 30;
		height = 30;
		collisionWidth = 20;
		collisionHeight = 20;
		
		health = maxHealth = 2;
		damage = 1;
		
		// load Sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemies/pixelThug.png"));
			
			sprites = new BufferedImage[3];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		}
		catch(Exception e)  {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
		
	}
	
	private void getNextPosition() {
		
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		
		}
		
		if(falling) {
			dy += fallSpeed;
			
		}
		
	}
	
	public void update() {
		 
		 
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check blinking
		if(blinking) {
			long elapsed = (System.nanoTime() - blinkTimer) / 1000000;
			if(elapsed > 400) {
				blinking = false;
			}
		}
		
		// if hits wall change direction
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		
		// animation
		animation.update();
			
		
	}
	
	public void draw(Graphics2D g) {
		
//		if(notOnScreen())
//			return;
		
		setMapPosition();
		
		super.draw(g);
	}

}
