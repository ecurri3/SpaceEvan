package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class ExtraHealth {
	
	public Sprite image;
	public Rectangle bounds;
	
	public ExtraHealth(){
		image = Assets.extrahealth_1;
		bounds = new Rectangle(830, 982, 18, 90);
	}

}
