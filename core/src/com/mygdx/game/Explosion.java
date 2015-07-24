package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Explosion {

	public Rectangle bounds;
	public Circle cBounds;
	public int timer;
	public boolean big;
	
	public TextureRegion current_frame;
	public Animation explosion_animation;

	public Explosion() {
		bounds = new Rectangle(0, 0, 100, 100);
		timer = 0;
	}

	public Explosion(float x, float y, boolean big) {
		this.big = false;
		if (big) {
			this.big = true;
			cBounds = new Circle(x, y, 300f);
			timer = 0;
			explosion_animation = Assets.big_explosion_animation;
		} else {
			bounds = new Rectangle(x - 50, y - 50, 100, 100);
			timer = 0;
			explosion_animation = Assets.explosion_animation;
		}
	}

	public boolean checkEnd() {
		if (timer > 10)
			return true;
		return false;
	}

}
