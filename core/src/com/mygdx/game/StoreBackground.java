package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class StoreBackground {
	
	public Sprite image;
	public Rectangle bounds;
	
	public StoreBackground(){
		image = Assets.sprite_storeback;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}

}
