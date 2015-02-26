package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class StoreScreen extends ApplicationAdapter implements Screen {
	
	MyGame game;
	SpriteBatch batch;
	int currency = 0;
	String yourCurrency;

	StoreBackground storebackground;
	OrthographicCamera camera;
	boolean isTouched = false;
	boolean soundOn;
	boolean soundOff;
	Vector3 touch;
	
	public StoreScreen(MyGame game){
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1180);

		storebackground = new StoreBackground();

		batch = new SpriteBatch();
		touch = new Vector3();
	}

	@Override
	public void show() {

		if(GameScreen.prefs.getString("sound_option").equals("on")){
			soundOn = true;
			soundOff = false;
		}
		else{
			soundOn = false;
			soundOff = true;
		}
		
		currency = getCurrency();
		yourCurrency = "" + currency;
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
		// Updates the colors, without this, wherever sprites move will recolor
		// the area it moved over
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		
		batch.draw(storebackground.image, storebackground.bounds.x,
				storebackground.bounds.y);
		
		Assets.font48.setColor(Color.YELLOW);
		Assets.font48.draw(batch, "Space Coins: ", 1300, 50);
		Assets.font48.setColor(Color.WHITE);
		Assets.font48.draw(batch, yourCurrency, 1760, 50);
		Assets.font48.setColor(Color.valueOf("ffff00"));

		batch.end();
		
		//INPUTS
		
		for (int i = 0; i < 20; i++) {

			if (Gdx.input.isTouched(i)) {
				touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touch);
				// MISSILES BUTTON
				if ((touch.x >= 340 && touch.x <= 790)
						&& (touch.y >= 400 && touch.y <= 550)) {
					playSelectSound();
					sleep(300);

					game.store_screen.dispose();
					game.setScreen(game.store_missile_screen);
				}
				// POWERUPS BUTTON
				if ((touch.x >= 1130 && touch.x <= 1580)
						&& (touch.y >= 400 && touch.y <= 550)) {
					playSelectSound();
					sleep(300);

					game.store_screen.dispose();
					game.setScreen(game.store_power_screen);
				}
				// HEALTH BUTTON
				if ((touch.x >= 735 && touch.x <= 1185)
						&& (touch.y >= 700 && touch.y <= 850)) {
					playSelectSound();
					sleep(300);

					game.store_screen.dispose();
					game.setScreen(game.store_health_screen);
				}
				// BACK BUTTON
				if ((touch.x >= 0 && touch.x <= 350)
						&& (touch.y >= 0 && touch.y <= 140)) {
					playSelectSound();
					sleep(300);
					
					game.store_screen.dispose();
					game.setScreen(game.menu_screen);
				}

			}
		}
		
	}
	
	public void playSelectSound(){
		if(soundOn){
			
			Random rand = new Random();
			int randomNum = rand.nextInt(2) + 1;

			switch (randomNum) {
			case 1:
				Assets.select1.play();
				break;
			case 2:
				Assets.select2.play();
				break;
			}
		}
	}
	
	public void sleep(int val){
		try {
			Thread.sleep(val);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void setCurrency(int val) {
		int current = getCurrency();
		GameScreen.prefs.putInteger("currency", (current + val));
		GameScreen.prefs.flush();
	}

	public static int getCurrency() {
		return GameScreen.prefs.getInteger("currency");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
