package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class MyGame extends Game{
	
	public GameScreen game_screen;
	public GameOverScreen gameOver_screen;
	public Screen switcher;

	@Override
	public void create() {
		Assets.load();
		game_screen = new GameScreen(this);
		gameOver_screen = new GameOverScreen(this);
		
		setScreen(game_screen);
	}

}
