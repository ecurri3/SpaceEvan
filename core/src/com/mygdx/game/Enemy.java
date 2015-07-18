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
		normal = false;
		gold = false;
		strong = false;
		
		if(enemyType == 1){
			image = Assets.gold_enemy;
			Cbounds = new Circle(spawnX, -70, (float) 70);
			health = 1;
			gold = true;
		}
		else if(enemyType >= 2 && enemyType <= 4){
			image = Assets.strong_enemy;
			Cbounds = new Circle(spawnX, -100, (float) 100);
			health = 3;
			strong = true;
		}
		else{
			image = Assets.enemy;
			Cbounds = new Circle(spawnX, -38, (float) 37.5);
			health = 1;
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