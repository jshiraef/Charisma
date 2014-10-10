package GameState;

import java.util.ArrayList;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUMGAMESTATES = 4;
	public static final int MENUSTATE = 0;
	public static final int VILLAGE = 1;
	public static final int FUNZONE = 2;
	public static final int GAMEOVER = 3;
	
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = MENUSTATE;
		loadState(currentState);

	}
	
	private void loadState(int state) {
		if(state == GAMEOVER)
			gameStates[state] = new GameOver(this);
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == VILLAGE)
			gameStates[state] = new Village(this);
		if(state == FUNZONE) 
			gameStates[state] = new FunZone(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
//		gameStates[currentState].init();
	}
	
	public void update() {
		
		if(!(gameStates[currentState] == null))
		gameStates[currentState].update();
		
	}
	
	public void draw(java.awt.Graphics2D g) {
		
		if(!(gameStates[currentState] == null))
			gameStates[currentState].draw(g);
			
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
}
