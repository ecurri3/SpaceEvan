package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Powerup {
	
	public Sprite image;
	public Rectangle bounds;
	public Circle Cbounds;
	
	public Powerup(){
		image = Assets.powerup_rapid;
		Cbounds = new Circle(0, 0, 75);
	}
	
	public Powerup(int spawnX, int power){
		switch(power){
		case 1:
			image = Assets.powerup_rapid;
			break;
		case 2:
			image = Assets.powerup_spray;
			break;
		case 3:
			image = Assets.powerup_multi;
			break;
		case 4:
			image = Assets.powerup_life;
			break;
		case 5:
			image = Assets.powerup_auto;
			break;
		case 6:
			image = Assets.powerup_swiftness;
			break;
		case 7:
			image = Assets.powerup_big;
			break;
		default:
			image = Assets.powerup_rapid;
		}
		Cbounds = new Circle(spawnX, 0, 75);
	}
	
	public boolean checkEnd() {
		return Cbounds.y >= 1080;
	}

}
