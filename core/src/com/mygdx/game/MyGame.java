package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGame extends Game{
	
	public GameScreen game_screen;
	public MenuScreen menu_screen;
	public GameOverScreen gameOver_screen;
	//TO-DO:
	public OptionScreen options_screen;
	public StoreScreen store_screen;
	public StoreMissileScreen store_missile_screen;
	public StorePowerScreen store_power_screen;
	public StorePowerRapidScreen store_power_rapid_screen;
	public StorePowerMultiScreen store_power_multi_screen;
	public StorePowerShotgunScreen store_power_shotgun_screen;
	public StorePowerGeneralScreen store_power_general_screen;
//	public StoreBigScreen store_big_screen;
//	public StoreSwiftnessScreen store_swiftness_screen;
//	public StoreAutoScreen store_auto_screen;
	public StoreHealthScreen store_health_screen;

	@Override
	public void create() {
		Assets.load();
		game_screen = new GameScreen(this);
		menu_screen = new MenuScreen(this);
		options_screen =  new OptionScreen(this);
		gameOver_screen = new GameOverScreen(this);
		store_screen = new StoreScreen(this);
		store_missile_screen = new StoreMissileScreen(this);
		store_power_screen = new StorePowerScreen(this);
		store_power_rapid_screen = new StorePowerRapidScreen(this);
		store_power_multi_screen = new StorePowerMultiScreen(this);
		store_power_shotgun_screen =  new StorePowerShotgunScreen(this);
		store_health_screen = new StoreHealthScreen(this);
		store_power_general_screen = new StorePowerGeneralScreen(this);
		
		setScreen(game_screen);
		setScreen(menu_screen);
		
	}

}
