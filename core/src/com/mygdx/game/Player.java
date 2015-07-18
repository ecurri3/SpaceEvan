package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.gameData.GameData;

public class Player {

	public Sprite player;
	public Sprite missileShip;
	public Sprite bulletShip;
	public Rectangle bounds;

	public Player() {
		player = Assets.player;
		missileShip = Assets.missile_twin;
		bulletShip = Assets.bullet_twin;
		bounds = new Rectangle(880, 740, 160, 160);
	}

	public void draw(SpriteBatch batch) {
		batch.draw(player, bounds.x, bounds.y);
	}

	public void drawR(SpriteBatch batch, float d, GameData gameData) {
		if (d > 90)
			d = 90;
		else if (d < -90)
			d = -90;

		batch.draw(player, bounds.x, bounds.y, player.getOriginX(),
				player.getOriginY(), 160, 160, 1, 1, d);
		
		if (gameData.mirrorTwins) {
			batch.draw(missileShip, bounds.x - 150, bounds.y + 60,
					missileShip.getOriginX(), missileShip.getOriginY(),
					missileShip.getHeight(), missileShip.getWidth(), 1, 1, d);
			batch.draw(bulletShip, bounds.x + 210, bounds.y + 60,
					bulletShip.getOriginX(), bulletShip.getOriginY(),
					bulletShip.getHeight(), bulletShip.getWidth(), 1, 1, d);
		}
	}

}
