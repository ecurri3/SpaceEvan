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

public class StorePowerScreen extends ApplicationAdapter implements Screen {

	MyGame game;
	SpriteBatch batch;
	int currency = 0;
	String yourCurrency;

	StorePowerScreenBackground background;
	OrthographicCamera camera;
	boolean isTouched = false;
	boolean soundOn;
	boolean soundOff;
	Vector3 touch;

	public StorePowerScreen(MyGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1180);

		background = new StorePowerScreenBackground();

		batch = new SpriteBatch();
		touch = new Vector3();
	}

	@Override
	public void show() {

		sleep(300);
		
		if(GameScreen.prefs.getString("sound_option").equals("on")){
			soundOn = true;
			soundOff = false;
		}
		else{
			soundOn = false;
			soundOff = true;
		}

		if (!GameScreen.prefs.contains("currency")) {
			GameScreen.prefs.putInteger("currency", 0);
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

		batch.draw(background.image, background.bounds.x, background.bounds.y);
		
		Assets.font48.setColor(Color.YELLOW);
		Assets.font48.draw(batch, "Space Coins: ", 1300, 50);
		Assets.font48.setColor(Color.WHITE);
		Assets.font48.draw(batch, yourCurrency, 1760, 50);
		Assets.font48.setColor(Color.valueOf("ffff00"));

		batch.end();

		// INPUTS

		for (int i = 0; i < 7; i++) {

			if (Gdx.input.isTouched(i)) {
				touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touch);
				// RAPID FIRE BUTTON
				if ((touch.x >= 340 && touch.x <= 790)
						&& (touch.y >= 440 && touch.y <= 540)) {
					playSelectSound();
					sleep(300);

					game.store_power_screen.dispose();
					game.setScreen(game.store_power_rapid_screen);

				}
				// MULTI SHOT BUTTON
				if ((touch.x >= 340 && touch.x <= 790)
						&& (touch.y >= 575 && touch.y <= 675)) {
					playSelectSound();
					sleep(300);
					
					game.store_power_screen.dispose();
					game.setScreen(game.store_power_multi_screen);

				}
				// SHOTGUN BUTTON
				if ((touch.x >= 340 && touch.x <= 790)
						&& (touch.y >= 710 && touch.y <= 810)) {
					playSelectSound();
					sleep(300);
					
					game.store_power_screen.dispose();
					game.setScreen(game.store_power_shotgun_screen);

				}
				// BIG AMMO BUTTON
				if ((touch.x >= 1130 && touch.x <= 1580)
						&& (touch.y >= 440 && touch.y <= 540)) {
					playSelectSound();
					sleep(300);
					
					//game.store_power_screen.dispose();
					//game.setScreen(game.store_power_big_screen);

				}
				// SWIFTNESS BUTTON
				if ((touch.x >= 1130 && touch.x <= 1580)
						&& (touch.y >= 575 && touch.y <= 675)) {
					playSelectSound();
					sleep(300);
					
					//game.store_power_screen.dispose();
					//game.setScreen(game.store_power_swiftness_screen);

				}
				// AUTO FIRE BUTTON
				if ((touch.x >= 1130 && touch.x <= 1580)
						&& (touch.y >= 710 && touch.y <= 810)) {
					playSelectSound();
					sleep(300);
					
					//game.store_power_screen.dispose();
					//game.setScreen(game.store_power_auto_screen);

				}
				// GENERAL BUTTON
				if ((touch.x >= 735 && touch.x <= 1185)
						&& (touch.y >= 840 && touch.y <= 940)) {
					playSelectSound();
					sleep(300);
					
					game.store_power_screen.dispose();
					game.setScreen(game.store_power_general_screen);

				}
				// BACK BUTTON
				if ((touch.x >= 0 && touch.x <= 350)
						&& (touch.y >= 0 && touch.y <= 140)) {
					playSelectSound();
					
					sleep(300);
					game.store_power_screen.dispose();
					game.setScreen(game.store_screen);
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
