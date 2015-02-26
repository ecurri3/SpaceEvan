package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class MenuBackground {
	
	public Sprite image;
	public Rectangle bounds;
	
	public MenuBackground(){
		image = Assets.sprite_menuback;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}

}