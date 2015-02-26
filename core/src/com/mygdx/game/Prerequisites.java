package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Prerequisites {
	
	public Sprite image;
	public Rectangle bounds;
	
	public Prerequisites(){
		image = Assets.sprite_prerequisites;
		bounds = new Rectangle(510, 375, 900, 400);
	}

}
