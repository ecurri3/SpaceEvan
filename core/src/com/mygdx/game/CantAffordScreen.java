package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class CantAffordScreen {
	
	public Sprite image;
	public Rectangle bounds;
	
	public CantAffordScreen(){
		image = Assets.sprite_cant_afford;
		bounds = new Rectangle(510, 375, 900, 400);
	}

}
