package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class AnimatedBackground {
	
	public Sprite image;
	public Rectangle bounds;
	
	public AnimatedBackground(){
		image = Assets.sprite_animback;
		bounds = new Rectangle(0, -9720, 1920, 10800);
	}

}
