package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class PlayAgain {
	
	public Sprite image;
	public Rectangle bounds;
	
	public PlayAgain(){
		image = Assets.playagain;
		bounds = new Rectangle(960-512, 540-128, 1024, 256);
	}
}
