package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import TileMap.BackGround;

public class MenuState extends GameState	{
	
	private BackGround bg;
	
	private int currentChoice;
	
	private String[] options = {"Start", "Help", "Quit"	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		
		try{
			bg = new BackGround("/Background/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			
		titleColor = new Color(128, 0, 0);
		titleFont = new Font("Calibri", Font.ITALIC, 32);
		
		font = new Font("Times New Roman", Font.PLAIN, 14);
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {
		
	}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		// draw background
		bg.draw(g);
		
		// draw Title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Charisma", 80, 70);
		
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
			gsm.setState(GameStateManager(Level1State));
		}
		
		if (currentChoice == 1) {
			// help
		}
		
		if (currentChoice == 2) {
			System.exit(0);
		}
	}
	
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
	
	public void keyReleased(int k) {
		
	}

}
