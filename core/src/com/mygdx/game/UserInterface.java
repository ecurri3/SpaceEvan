package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class UserInterface {
	
	public Sprite image;
	public Rectangle bounds;
	
	public UserInterface(){
		image = Assets.sprite_back;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}
	
	public UserInterface(Sprite background){
		image = background;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(image, bounds.x, bounds.y);
	}

}
