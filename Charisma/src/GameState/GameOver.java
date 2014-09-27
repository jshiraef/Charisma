package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import main.GamePanel;
import TileMap.BackGround;

public class GameOver extends GameState{
	
	private BackGround bg;
	private boolean destroy;
	
	private int currentChoice;
	
	private String[] options = {"Restart", "Menu", "Quit"};
	
	private Color titleColor;
	private Font titleFont;

	private Font font;
	
	
	
	public GameOver(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		
		titleColor = new Color(128, 0, 0);
		titleFont = new Font("Calibri", Font.ITALIC, 32);
		
		font = new Font("Times New Roman", Font.PLAIN, 14);
	}

	@Override
	public void init() {
		destroy = false;
		
		if(Village.FirstLevelConquered)
		bg = new BackGround("/Backgrounds/winscreen.png", 0.0);
		else
			bg = new BackGround("/Backgrounds/GameOver.png", 0.0);
		
	}

	@Override
	public void update() {
		bg.setPosition(0, 0);
	}

	@Override
	public void draw(Graphics2D g) {
		
		bg.draw(g);
		
		// draw menu options
				g.setFont(font);
				for(int i = 0; i < options.length; i++) {
					if(i == currentChoice) {
						g.setColor(Color.WHITE);
					}
					else {
						g.setColor(Color.RED);
					}
					g.drawString(options[i], 145, 140 + i * 15 );
				}
		
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.VILLAGE);
		}
		
		if (currentChoice == 1) {
			destroy = true;
			gsm.setState(GameStateManager.MENUSTATE);
		}
		
		if (currentChoice == 2) {
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER)	 {
			select();
		}
		
		if (k == KeyEvent.VK_UP) {
			currentChoice --;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		
		if (k == KeyEvent.VK_DOWN) {
			currentChoice ++;
			if(currentChoice == options.length)
				currentChoice = 0;
		}
		
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

}
