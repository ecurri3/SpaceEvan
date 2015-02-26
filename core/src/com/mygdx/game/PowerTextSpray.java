package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class PowerTextSpray {
	
	public Sprite image;
	public Rectangle bounds;
	
	public PowerTextSpray(){
		image = Assets.power_shotgun;
		bounds = new Rectangle(1147, 995, 160, 35);
	}

}
