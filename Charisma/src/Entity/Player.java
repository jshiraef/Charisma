package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import Audio.AudioPlayer;
import TileMap.TileMap;

public class Player extends MapEntity{
	
	// player stuff
	private int health;
	private int maxHealth;
	private int charisma;
	private int maxCharisma;
	
	private boolean dead;
	private boolean blinking;
	private long blinkTimer;
	
	// orb
	private boolean firing; 
	private int charismaCost;
	private int charismaEffectiveness;
	private int orbDamage;
	
	private ArrayList<CharismaOrb> orbs;
	
	// swiping
	private boolean swiping; 
	private int swipeDamage;
	private int swipeRange;
	
	// hovering 
	private boolean hovering;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {2, 8, 1, 2, 4, 2, 5};
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int HOVERING = 4;
	private static final int FIRING = 5;
	private static final int SWIPING = 6;
	
	private HashMap<String, AudioPlayer> sfx;
	
	public Player (TileMap tm) {
		super (tm);
		width = 30;
		height = 30;
		collisionWidth = 20;
		collisionHeight = 20;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = maxHealth = 5;
		charisma = maxCharisma =  2500;
		
		charismaCost = 200;
		charismaEffectiveness = 5;
		orbs = new ArrayList<CharismaOrb>();
		
		
		swipeDamage = 8;
		swipeRange = 40;
		
		// load sprites
		 try {
			 
			 BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/Player/playerSprites2.png"));
			 
			 sprites = new ArrayList<BufferedImage[]>();
			 
			 for (int i = 0; i < 7; i++) {
				 BufferedImage[] bi = new BufferedImage[numFrames[i]];
				 
				 for (int j = 0; j < numFrames[i]; j++) {
					 
					 if(i != SWIPING) {
					 bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
					 }
					 else {  
						 bi[j] = spritesheet.getSubimage(j * width * 2, i * height, width * 2, height);
					 }
				 
				 	}
				 
			 sprites.add(bi);
			 
			 } 
		 
		 }
		  catch (Exception e) {
			  e.printStackTrace();
		  }
		 
		 animation = new Animation();
		 currentAction = IDLE;
		 animation.setFrames(sprites.get(IDLE));
		 animation.setDelay(400);
		 
		 sfx = new HashMap<String, AudioPlayer>();
		 sfx.put("jump", new AudioPlayer("/sound/sfx/jump.mp3"));
		 sfx.put("swipe", new AudioPlayer("/sound/sfx/swipe.mp3"));
		 sfx.put("shoot", new AudioPlayer("/sound/sfx/shoot.mp3"));
		 
	}
	
	
	
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getCharisma () {
		return charisma;
	}
	
	public int getMaxCharisma() {
		return maxCharisma;
	}
	
	public void setFiring() {
		firing = true;
	}
	
	public void setSwiping() {
		swiping = true;
	}
	
	public void setHovering(boolean b) {
		hovering = b;
	}
	
	public void checkAttack(ArrayList<Enemy> enemies) {
		
		
		// loop through enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			
			if(swiping) {
				if(facingRight) {
					if( e.getx() > x && e.getx() < x + swipeRange && e.gety() > y - height / 2 && e.gety() < y + height/ 2) {
						e.hit(swipeDamage);
					}
				}
				else {
					if( e.getx() > x && e.getx() < x + swipeRange && e.gety() > y - height / 2 && e.gety() < y + height/ 2) {
						e.hit(swipeDamage);
					}
				}
			}
		
			// orbs
			for(int j = 0; j < orbs.size(); j++) {
				if(orbs.get(j).intersects(e)) {
					e.hit(charismaEffectiveness);
					orbs.get(j).setHit();
					break;
				}
			}
		
			// check for enemy collision
			if(intersects(e)) {
				hit(e.getDamage());
			}
			
		}
		
	}
	
	private void hit(int damage) {
		if(blinking) 
			return;
		health -= damage;
		
		if(health < 0)
			health = 0;
		
		if(health == 0) 
			dead = true;
		
		blinking = true;
		blinkTimer = System.nanoTime();
	}
	
	private void getNextPosition() {
		// movement
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
		else {
			if (dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if (dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// cannot move while attacking except in air
		if((currentAction == SWIPING || currentAction == FIRING) && !(jumping || falling)) {
			dx = 0;
		}
		
		// jumping
		if(jumping && !falling) {
			sfx.get("jump").play();
			dy = jumpStart;
			falling = true;
		}
		
		if(falling) {
			if(dy > 0 && hovering) 
				dy += fallSpeed * 0.1;
			else dy += fallSpeed;
			
			if(dy > 0)
				jumping = false;
			if(dy < 0 && !jumping)
				dy += stopJumpSpeed;
			if(dy > maxFallSpeed) 
				dy = maxFallSpeed;
		}
		
	}
	
	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// Check if the attack has stopped
		if(currentAction == SWIPING) {
			if(animation.hasPlayedOnce())
				swiping = false;
		}
		
		
		if(currentAction == FIRING) {
			if(animation.hasPlayedOnce())
				firing = false;
		}
		
		// attack
		charisma += 1;
		if(charisma > maxCharisma)
			charisma = maxCharisma;
		if(firing && currentAction != FIRING) {
			if(charisma > charismaCost) {
				charisma -= charismaCost;
				CharismaOrb co = new CharismaOrb (tileMap, facingRight); 
				co.setPosition(x, y);
				orbs.add(co);
				
			}
		}
		
		// update orbs
		for (int i = 0; i < orbs.size(); i++) {
			orbs.get(i).update();
			if(orbs.get(i).shouldRemove()) {
				orbs.remove(i);
				i--;
			}
		}
		
		// check done blinking
		if(blinking) {
			long elapsed = (System.nanoTime() - blinkTimer) / 1000000;
			if(elapsed > 1000) {
				blinking = false;
			}
		}
		
		// set animation
		if(swiping) {
			if(currentAction != SWIPING) {
				sfx.get("swipe").play();
				currentAction = SWIPING;
				animation.setFrames(sprites.get(SWIPING));
				animation.setDelay(50);
				width = 60;
			}
		}
		else if(firing) {
			if(currentAction != FIRING) {
				currentAction = FIRING;
				sfx.get("shoot").play();
				animation.setFrames(sprites.get(FIRING));
				animation.setDelay(100);
				width = 30;
			}
		}
		else if(dy > 0) {
			if(hovering) {
				if(currentAction != HOVERING) {
					currentAction = HOVERING;
					animation.setFrames(sprites.get(HOVERING));
					animation.setDelay(100);
					width = 30;
				}
			}
			else if (currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 30;
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 30;
			}
		}
		else if (left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(100);
				width = 30;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 30;
			}
		}
		animation.update();
		
		// set direction
		if(currentAction != SWIPING && currentAction != FIRING) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		
		// draw orbs
		for (int i = 0; i < orbs.size(); i++) {
			orbs.get(i).draw(g);
			
		}
		
		 
		// draw Player
		
		if (blinking) {
			long elapsed = (System.nanoTime() - blinkTimer)/ 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
				
			}
		}
		
		super.draw(g);
	}


}
