package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class StoreMissileScreenBackground {
	
	public Sprite image;
	public Rectangle bounds;
	
	public StoreMissileScreenBackground(){
		image = Assets.sprite_missile_screen_back;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}

}
