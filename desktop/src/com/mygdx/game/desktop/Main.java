package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGame;

public class Main {
	
	public static void main(String args[]){
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "My Game";
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new MyGame(), cfg);
	}

}
