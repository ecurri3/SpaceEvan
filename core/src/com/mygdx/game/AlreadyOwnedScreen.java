package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class AlreadyOwnedScreen {
	
	public Sprite image;
	public Rectangle bounds;
	
	public AlreadyOwnedScreen(){
		image = Assets.sprite_already_owned;
		bounds = new Rectangle(510, 375, 900, 400);
	}

}
