package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.gameData.PlayerData;

public class OptionScreen extends ApplicationAdapter implements Screen {
	
	MyGame game;
	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;
	
	boolean soundOn;
	boolean soundOff;
	
	OptionScreenBackground optionScreenBackground;

	public OptionScreen(MyGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1180);
		
		optionScreenBackground = new OptionScreenBackground();

		batch = new SpriteBatch();
		touch = new Vector3();
	}
	
	@Override
	public void show() {
		
		sleep(300);
		
		if(PlayerData.prefs.getString("sound_option").equals("on")){
			soundOn = true;
			soundOff = false;
		}
		else{
			soundOn = false;
			soundOff = true;
		}

		if (!PlayerData.prefs.contains("currency")) {
			PlayerData.prefs.putInteger("currency", 0);
		}
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
		
		batch.draw(optionScreenBackground.image,
				optionScreenBackground.bounds.x,
				optionScreenBackground.bounds.y);
		
		drawSettings();
		
		batch.end();
		
		for (int i = 0; i < 20; i++) {

			if (Gdx.input.isTouched(i)) {
				touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touch);
				//SOUND
				if ((touch.x >= 500 && touch.x <= 950)
						&& (touch.y >= 380 && touch.y <= 480)) {
					if(PlayerData.prefs.getString("sound_option").equals("on"))
						PlayerData.prefs.putString("sound_option", "off");
					else
						PlayerData.prefs.putString("sound_option", "on");
					PlayerData.prefs.flush();
					playSelectSound();
					sleep(300);
				}
				//CONTROL
				if ((touch.x >= 500 && touch.x <= 950)
						&& (touch.y >= 505 && touch.y <= 605)) {
					if(PlayerData.prefs.getString("control_option").equals("tilt"))
						PlayerData.prefs.putString("control_option", "buttons");
					else
						PlayerData.prefs.putString("control_option", "tilt");
					PlayerData.prefs.flush();
					playSelectSound();
					sleep(300);
				}
				
				// BACK BUTTON
				if ((touch.x >= 0 && touch.x <= 350)
						&& (touch.y >= 0 && touch.y <= 140)) {
					
					game.options_screen.dispose();
					game.setScreen(game.menu_screen);
					playSelectSound();
					sleep(300);
				}

			}
		}
		
	}
	
	public void drawSettings(){
		
		if(PlayerData.prefs.getString("sound_option").equals("on")){
			//DISPLAY ON
			batch.draw(Assets.sprite_option_on, 970, 380);
		}
		else{
			//DISPLAY OFF
			batch.draw(Assets.sprite_option_off, 970, 380);
		}
		
		if(PlayerData.prefs.getString("control_option").equals("tilt")){
			//DISPLAY TILT
			batch.draw(Assets.sprite_option_tilt, 970, 505);
		}
		else{
			//DISPLAY BUTTONS
			batch.draw(Assets.sprite_option_buttons, 970, 505);
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

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
