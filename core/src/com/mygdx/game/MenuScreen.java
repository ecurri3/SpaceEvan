package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.gameData.PlayerData;

public class MenuScreen extends ApplicationAdapter implements Screen {

	MyGame game;
	SpriteBatch batch;
	int currency = 0;
	String yourCurrency;

	BitmapFont yourBitmapFontName;

	MenuBackground menubackground;
	OrthographicCamera camera;
	boolean isTouched = false;
	boolean soundOn;
	boolean soundOff;
	Vector3 touch;

	public MenuScreen(MyGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		// 934, 590 was the dimensions of the background created on Paint
		camera.setToOrtho(true, 1920, 1180);

		menubackground = new MenuBackground();

		yourBitmapFontName = new BitmapFont();
		yourBitmapFontName.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		yourBitmapFontName.setScale(2, -2);

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

		batch.draw(menubackground.image, menubackground.bounds.x,
				menubackground.bounds.y);
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		Assets.font36.draw(batch, "SPACE COINS: " + yourCurrency, 700, 560);

		batch.end();

		for (int i = 0; i < 20; i++) {

			if (Gdx.input.isTouched(i)) {
				touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touch);
				// PLAY BUTTON
				if ((touch.x >= 620 && touch.x <= 1295)
						&& (touch.y >= 380 && touch.y <= 530)) {
					playSelectSound();
					sleep(300);

					game.menu_screen.dispose();
					game.setScreen(game.game_screen);

				}
				// STORE BUTTON
				if ((touch.x >= 620 && touch.x <= 1295)
						&& (touch.y >= 600 && touch.y <= 750)) {
					playSelectSound();
					sleep(300);
					
					game.menu_screen.dispose();
					game.setScreen(game.store_screen);
				}
				// OPTIONS BUTTON
				if ((touch.x >= 620 && touch.x <= 1295)
						&& (touch.y >= 820 && touch.y <= 970)) {
					playSelectSound();
					sleep(300);

					game.menu_screen.dispose();
					game.setScreen(game.options_screen);
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
		PlayerData.prefs.putInteger("currency", (current + val));
		PlayerData.prefs.flush();
	}

	public static int getCurrency() {
		return PlayerData.prefs.getInteger("currency");
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
