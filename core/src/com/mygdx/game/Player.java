package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	
	public Sprite image;
	public Rectangle bounds;
	
	public Player(){
		image = Assets.player;
		bounds = new Rectangle(880, 740, 160, 160);
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(image, bounds.x, bounds.y);
	}

}
