package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class PowerTextMulti {
	
	public Sprite image;
	public Rectangle bounds;
	
	public PowerTextMulti(){
		image = Assets.power_multishot;
		bounds = new Rectangle(1147, 1030, 160, 35);
	}

}
