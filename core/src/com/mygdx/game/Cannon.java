package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Cannon {
	
	public Sprite cannon;
	public Rectangle bounds;
	public float degree;
	
	public Cannon(float x, float y){
		cannon = Assets.cannon;
		bounds = new Rectangle(x, y, 100, 100);
		degree = 0;
	}
	
	public void drawR(SpriteBatch batch, double x, double y){
		float d = (float) Math.toDegrees(Math.atan2(x, y));
		degree = -d;
		
		batch.draw(cannon, bounds.x, bounds.y, cannon.getOriginX(),
				cannon.getOriginY(), 100, 100, 1, 1, degree);
	}

}
