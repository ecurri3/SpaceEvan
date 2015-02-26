package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class StorePowerScreenBackground {
	
	public Sprite image;
	public Rectangle bounds;
	
	public StorePowerScreenBackground(){
		image = Assets.sprite_power_screen_back;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}

}