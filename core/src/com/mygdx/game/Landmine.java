package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

public class Landmine {
	
	public Sprite image;
	public Circle Cbounds;
	
	public Landmine(){
		image = Assets.landmine;
		Cbounds = new Circle(0, 0, (float)30);
	}
	
	public Landmine(float x, float y){
		image = Assets.landmine;
		Cbounds = new Circle(x, y, (float)30);
	}

}
