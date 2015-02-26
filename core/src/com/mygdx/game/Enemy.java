package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
	
	public Sprite image;
	public Rectangle bounds;
	public Circle Cbounds;
	public int health;
	public boolean normal;
	public boolean gold;
	public boolean strong;
	
	public Enemy(){
		image = Assets.enemy;
		Cbounds = new Circle(0, 0, (float)37.5);
		health = 1;
		normal = true;
		gold = false;
		strong = false;
	}
	
	public Enemy(int spawnX, int enemyType){
		if(enemyType == 1){
			image = Assets.gold_enemy;
			Cbounds = new Circle(spawnX, -70, (float) 70);
			normal = false;
			gold = true;
			strong = false;
		}
		else if(enemyType >= 2 && enemyType <= 4){
			image = Assets.strong_enemy;
			Cbounds = new Circle(spawnX, -100, (float) 100);
			health = 3;
			normal = false;
			gold = false;
			strong = true;
		}
		else{
			image = Assets.enemy;
			Cbounds = new Circle(spawnX, -38, (float) 37.5);
			normal = true;
			gold = false;
			strong = false;
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