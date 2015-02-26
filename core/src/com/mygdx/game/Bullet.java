package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
	
	public Sprite image;
	public Rectangle bounds;
	public int xUpdate = 0;
	public int yUpdate = 0;
	
	public Bullet(){
		image = Assets.bullet;
		bounds = new Rectangle(0, 0, 8, 8);
	}
	
	public Bullet(float x, float y, int xupdate, int yupdate) {
		image = Assets.bullet;
		bounds = new Rectangle(x + 80, y + 18, 8, 8);
		xUpdate = xupdate;
		yUpdate = yupdate;
	}
	
	public Bullet(float x, float y, int xupdate, int yupdate, boolean big){
		image = Assets.bigbullet;
		bounds = new Rectangle(x + 80, y + 18, 24, 24);
		xUpdate = xupdate;
		yUpdate = yupdate;
	}

	public boolean checkEnd() {
		return bounds.y <= 0 || bounds.y >= 1080 || bounds.x <= 0 || bounds.x >= 1920;
	}

}