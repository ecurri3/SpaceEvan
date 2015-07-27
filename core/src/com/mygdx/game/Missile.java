package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Missile {

	public Sprite image;
	public Rectangle bounds;
	public boolean shotByPlayer;
	public boolean shotByEnemy;
	public boolean hitPlayer;

	public Missile() {
		image = Assets.missile;
		bounds = new Rectangle(0, 0, 4, 24);
	}

	public Missile(float x, float y) {
		image = Assets.missile;
		bounds = new Rectangle(x + 80, y + 18, 4, 24);
	}

	public Missile (float x, float y, float yOffset) {
		image = Assets.missile;
		bounds = new Rectangle(x, y + yOffset, 4, 24);
		shotByEnemy = true;
		shotByPlayer = false;
		hitPlayer = false;
	}

	public Missile(float x, float y, boolean big) {
		if (big) {
			image = Assets.bigmissile;
			bounds = new Rectangle(x + 80, y + 18, 12, 72);
		} else {
			image = Assets.missile;
			bounds = new Rectangle(x + 80, y + 18, 4, 24);
		}
		shotByPlayer = true;
		shotByEnemy = false;
		hitPlayer = false;
	}

	public boolean checkEnd() {
		return bounds.y <= 0;
	}

}
