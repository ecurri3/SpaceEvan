package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class GameOver {
	
	public Sprite image;
	public Rectangle bounds;
	
	public GameOver(){
		image = Assets.gameover_screen;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}
}