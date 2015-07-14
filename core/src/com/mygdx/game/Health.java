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
	
	public Health(){
		health = Assets.health_5;
		hBounds = new Rectangle(610, 991, 210, 75);
		
		extraHealth = Assets.extrahealth_1;
		ehBounds = new Rectangle(830, 982, 18, 90);
	}
	
	public void draw(SpriteBatch batch, GameData gameData){
		batch.draw(health, hBounds.x, hBounds.y);
		if(gameData.player_health > 5){
			batch.draw(extraHealth, ehBounds.x, ehBounds.y);
		}
	}

}