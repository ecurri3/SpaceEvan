package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class OptionScreenBackground {
	
	public Sprite image;
	public Rectangle bounds;
	
	public OptionScreenBackground(){
		image = Assets.sprite_option_screen_back;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}

}
