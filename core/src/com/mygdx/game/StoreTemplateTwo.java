package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class StoreTemplateTwo {
	
	public Sprite image;
	public Rectangle bounds;
	
	public StoreTemplateTwo(){
		image = Assets.sprite_store_template_two;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}

}
