package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Health {
	
	public Sprite image;
	public Rectangle bounds;
	
	public Health(){
		image = Assets.health_5;
		bounds = new Rectangle(610, 991, 210, 75);
	}

}