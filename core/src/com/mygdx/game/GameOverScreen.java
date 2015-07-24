package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.gameData.PlayerData;

public class GameOverScreen extends ApplicationAdapter implements Screen {

	MyGame game;
	SpriteBatch batch;
	GameOver gameover;
	OrthographicCamera camera;
	
	boolean first;
	boolean soundOn;
	boolean soundOff;

	Vector3 touch;

	BitmapFont font48;
	BitmapFont font36;

	String enemiesKilled;
	String coinsEarned;
	String timeElapsed;
	String timesFired;
	String healthUsed;
	String score;

	int coinsEarned_int;
	int previousCurrency;
	int newCurrency;

	boolean isTouched = false;

	public GameOverScreen(MyGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		// 934, 590 was the dimensions of the background created on Paint
		camera.setToOrtho(true, 1920, 1080);

		font48 = new BitmapFont();
		font48.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font48.setScale(2, -2);

		font36 = new BitmapFont();
		font36.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font36.setScale(2, -2);

		enemiesKilled = "" + GameScreen.enemiesKilled;
		coinsEarned = "" + GameScreen.coinsEarned;
		timeElapsed = "" + GameScreen.timeElapsed;
		timesFired = "" + GameScreen.timesFired;
		healthUsed = "" + GameScreen.healthUsed;
		score = "" + GameScreen.score;

		batch = new SpriteBatch();
		touch = new Vector3();
		gameover = new GameOver();
	}

	@Override
	public void show() {
		
		if(PlayerData.prefs.getString("sound_option").equals("on")){
			soundOn = true;
			soundOff = false;
		}
		else{
			soundOn = false;
			soundOff = true;
		}

		enemiesKilled = "" + GameScreen.enemiesKilled;
		coinsEarned = "" + GameScreen.coinsEarned;
		timeElapsed = "" + GameScreen.timeElapsed + " seconds";
		timesFired = "" + GameScreen.timesFired;
		healthUsed = "" + GameScreen.healthUsed;
		score = "" + GameScreen.score;

		coinsEarned_int = GameScreen.coinsEarned;
		newCurrency = PlayerData.getCurrency();
		previousCurrency = newCurrency - coinsEarned_int;

		font48 = Assets.font48;
		font36 = Assets.font36;
		
		first = true;

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

		batch.draw(gameover.image, gameover.bounds.x, gameover.bounds.y);

		font48.setColor(Color.YELLOW);
		font36.setColor(Color.GREEN);
		font48.draw(batch, "Enemies Killed: " + enemiesKilled, 75, 250);
		if (newHighScore())
			font36.draw(
					batch,
					"NEW HIGH SCORE!!!: "
							+ PlayerData.prefs.getInteger("highScore"), 150,
					300);
		else
			font36.draw(batch, "Score: " + score, 150, 300);
		font48.draw(batch, "Space Coins Earned: " + coinsEarned, 75, 375);
		font36.draw(batch, "Previous Amount: " + previousCurrency, 150, 450);
		font36.draw(batch, "New Amount:      " + newCurrency, 150, 525);
		font48.draw(batch, "Total Time Elapsed: " + timeElapsed, 75, 600);
		font48.draw(batch, "Times Fired: " + timesFired, 75, 675);
		font48.draw(batch, "Health Used: " + healthUsed, 75, 750);

		batch.end();

		if (Gdx.input.isTouched()) {
			for (int i = 0; i < 20; i++) {

				if (Gdx.input.isTouched(i)) {
					touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touch);

					// Play Again
					if ((touch.x >= 170 && touch.x <= 870)
							&& (touch.y >= 900 && touch.y <= 1040)) {
						playSelectSound();
						sleep(2500);
						GameScreen.enemiesKilled = 0;
						GameScreen.coinsEarned = 0;
						GameScreen.timeElapsed = 0;
						GameScreen.timesFired = 0;
						GameScreen.healthUsed = 0;
						coinsEarned_int = 0;
						newCurrency = 0;
						previousCurrency = 0;
						
						font36.setColor(Color.YELLOW);
						
						game.gameOver_screen.dispose();
						game.setScreen(game.game_screen);
					}

					// Menu
					if ((touch.x >= 1040 && touch.x <= 1740)
							&& (touch.y >= 900 && touch.y <= 1040)) {
						playSelectSound();
						sleep(2500);
						GameScreen.enemiesKilled = 0;
						GameScreen.coinsEarned = 0;
						GameScreen.timeElapsed = 0;
						GameScreen.timesFired = 0;
						GameScreen.healthUsed = 0;
						coinsEarned_int = 0;
						newCurrency = 0;
						previousCurrency = 0;
						
						font36.setColor(Color.YELLOW);
						
						game.gameOver_screen.dispose();
						game.gameOver_screen.hide();
						game.setScreen(game.switcher);
						System.exit(0);
					}
				}
			}
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

	public boolean newHighScore() {
		if (GameScreen.newHighScore)
			return true;

		return false;
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
