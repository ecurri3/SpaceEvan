package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Explosion {
	
	public Rectangle bounds;
	public int timer;
	
	public Explosion(){
		bounds = new Rectangle(0, 0, 100, 100);
		timer = 0;
	}
	
	public Explosion(float x, float y){
		bounds = new Rectangle(x - 50, y - 50, 100, 100);
		timer = 0;
	}
	
	public boolean checkEnd(){
		if(timer > 10)
			return true;
		return false;
	}

}
