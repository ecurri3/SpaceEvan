package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.gameData.GameData;

public class Health {

	public Sprite health;
	public Sprite extraHealth;
	public Rectangle hBounds;
	public Rectangle ehBounds;

	public Health() {
		health = Assets.health_5;
		hBounds = new Rectangle(610, 991, 210, 75);

		extraHealth = Assets.extrahealth_1;
		ehBounds = new Rectangle(830, 982, 18, 90);
	}

	public void setHealth(int h, GameData gameData) {
		if (gameData.player_health > 10)
			gameData.player_health = 10;

		health = Assets.health_5;
		switch (gameData.player_health) {
		case 10:
			extraHealth = Assets.extrahealth_5;
			break;
		case 9:
			extraHealth = Assets.extrahealth_4;
			break;
		case 8:
			extraHealth = Assets.extrahealth_3;
			break;
		case 7:
			extraHealth = Assets.extrahealth_2;
			break;
		case 6:
			extraHealth = Assets.extrahealth_1;
			break;
		case 5:
			health = Assets.health_5;
			break;
		case 4:
			health = Assets.health_4;
			break;
		case 3:
			health = Assets.health_3;
			break;
		case 2:
			health = Assets.health_2;
			break;
		case 1:
			health = Assets.health_1;
			break;
		}
	}

	public void draw(SpriteBatch batch, GameData gameData) {
		batch.draw(health, hBounds.x, hBounds.y);
		if (gameData.player_health > 5) {
			batch.draw(extraHealth, ehBounds.x, ehBounds.y);
		}
	}

}