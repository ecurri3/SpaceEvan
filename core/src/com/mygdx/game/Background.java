package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Background {
	
	public Sprite image;
	public Rectangle bounds;
	
	public Background(){
		image = Assets.sprite_back;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}
	
	public Background(Sprite background){
		image = background;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}

}
