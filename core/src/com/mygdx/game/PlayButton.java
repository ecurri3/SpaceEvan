package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class PlayButton {
	
	public Sprite image;
	public Rectangle bounds;
	
	public PlayButton(){
		image = Assets.playbutton;
		bounds = new Rectangle(960-512, 540-128, 1024, 256);
	}
}
