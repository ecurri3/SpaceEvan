package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.gameData.GameTimers;

public class Enemy {
	
	public Sprite image;
	public Rectangle bounds;
	public Circle Cbounds;
	public int health;
	
	public float xOffset;
	public float yOffset;
	
	public boolean normal;
	public boolean gold;
	public boolean strong;
	
	public boolean hitPlayer;
	public boolean hitByExplosion;
	public boolean hitByShield;
	
	public long reloadDur;
	public long reloadStart;
	public long reloadEnd;
	
	public Enemy(){
		image = Assets.enemy;
		Cbounds = new Circle(0, 0, (float)37.5);
		health = 1;
		normal = true;
		gold = false;
		strong = false;
		hitPlayer = false;
		hitByExplosion = false;
		hitByShield = false;
		
		reloadStart = System.nanoTime() / GameTimers.nano;
	}
	
	public Enemy(int spawnX, int enemyType, boolean expertMode){
		normal = false;
		gold = false;
		strong = false;
		int factor = 1;
		if(expertMode)
			factor = 2;
		
		if(enemyType == 1){
			image = Assets.gold_enemy;
			xOffset = 70f;
			yOffset = 140f;
			Cbounds = new Circle(spawnX, -70, (float) 70);
			health = 1 * factor;
			gold = true;
		}
		else if(enemyType >= 2 && enemyType <= 4){
			image = Assets.strong_enemy;
			xOffset = 100f;
			yOffset = 200f;
			Cbounds = new Circle(spawnX, -100, (float) 100);
			health = 3 * factor;
			strong = true;
		}
		else{
			image = Assets.enemy;
			xOffset = 37.5f;
			yOffset = 70f;
			Cbounds = new Circle(spawnX, -38, (float) 37.5);
			health = 1 * factor;
			normal = true;
		}
	}
	
	public Enemy(int spawnX){
		image = Assets.enemy;
		Cbounds = new Circle(spawnX, 0, (float) 37.5);
	}
	
	public boolean checkEnd() {
		return Cbounds.y >= 1080;
	}

}