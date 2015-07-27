package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class MyGame extends Game{
	
	public boolean expertMode;
	public GameScreen game_screen;
	public GameOverScreen gameOver_screen;
	public Screen switcher;
	
	public MyGame(boolean difficulty){
		expertMode = difficulty;
	}

	@Override
	public void create() {
		Assets.load();
		game_screen = new GameScreen(this, expertMode);
		gameOver_screen = new GameOverScreen(this);
		
		setScreen(game_screen);
	}

}
