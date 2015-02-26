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

public class StorePowerRapidScreen extends ApplicationAdapter implements Screen {

	MyGame game;
	SpriteBatch batch;

	int currency = 0;
	int rapidUpDur;
	int rapidUpReload;
	int rapidUpRicochet;
	int cost;
	String yourCurrency;

	BitmapFont yourBitmapFontName;

	StorePowerRapidScreenBackground background;
	StoreMissileScreenBackground storemissilebackground;
	ConfirmPurchaseScreen confirmPurchaseScreen;
	CantAffordScreen cantAffordScreen;
	AlreadyOwnedScreen alreadyOwnedScreen;
	Prerequisites doNotOwnPreReqs;
	Purchased purchased;

	OrthographicCamera camera;

	boolean isTouched = false;
	
	boolean soundOn;
	boolean soundOff;
	
	boolean durConfirmBool = false;
	boolean reloadConfirmBool = false;
	boolean ricochetConfirmBool = false;
	boolean cantAfford = false;
	boolean alreadyOwned = false;
	boolean preReqs = false;

	Vector3 touch;

	public StorePowerRapidScreen(MyGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1180);

		background = new StorePowerRapidScreenBackground();
		confirmPurchaseScreen = new ConfirmPurchaseScreen();
		cantAffordScreen = new CantAffordScreen();
		alreadyOwnedScreen = new AlreadyOwnedScreen();
		doNotOwnPreReqs = new Prerequisites();
		purchased = new Purchased();

		yourBitmapFontName = new BitmapFont();
		yourBitmapFontName.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		yourBitmapFontName.setScale(2, -2);

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

		if (!GameScreen.prefs.contains("currency")) {
			GameScreen.prefs.putInteger("currency", 0);
		}

		currency = getCurrency();
		yourCurrency = "" + currency;

		sleep(200);

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
		
		Assets.font48.setColor(Color.WHITE);
		Assets.font48.draw(batch, yourCurrency, 1760, 50);
		Assets.font48.setColor(Color.valueOf("ffff00"));
		
		checkPurchases();

		if (durConfirmBool) {
			batch.draw(confirmPurchaseScreen.image,
					confirmPurchaseScreen.bounds.x,
					confirmPurchaseScreen.bounds.y);

			for (int i = 0; i < 20; i++) {

				if (Gdx.input.isTouched(i)) {
					touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touch);
					// YES
					if ((touch.x >= 620 && touch.x <= 880)
							&& (touch.y >= 615 && touch.y <= 705)) {
						purchaseDur(rapidUpDur, cost);
						playSelectSound();
						sleep(200);
						durConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						playSelectSound();
						sleep(200);
						durConfirmBool = false;

					}
				}
			}
		}
		
		if (reloadConfirmBool) {
			batch.draw(confirmPurchaseScreen.image,
					confirmPurchaseScreen.bounds.x,
					confirmPurchaseScreen.bounds.y);

			for (int i = 0; i < 20; i++) {

				if (Gdx.input.isTouched(i)) {
					touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touch);
					// YES
					if ((touch.x >= 620 && touch.x <= 880)
							&& (touch.y >= 615 && touch.y <= 705)) {
						playSelectSound();
						purchaseReload(rapidUpReload, cost);
						sleep(200);
						reloadConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						playSelectSound();
						sleep(200);
						reloadConfirmBool = false;

					}
				}
			}
		}
		
		if (ricochetConfirmBool) {
			batch.draw(confirmPurchaseScreen.image,
					confirmPurchaseScreen.bounds.x,
					confirmPurchaseScreen.bounds.y);

			for (int i = 0; i < 20; i++) {

				if (Gdx.input.isTouched(i)) {
					touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touch);
					// YES
					if ((touch.x >= 620 && touch.x <= 880)
							&& (touch.y >= 615 && touch.y <= 705)) {
						purchaseRicochet(rapidUpRicochet, cost);
						playSelectSound();
						sleep(200);
						ricochetConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						playSelectSound();
						sleep(200);
						ricochetConfirmBool = false;

					}
				}
			}
		}
		
		if(preReqs){
			batch.draw(doNotOwnPreReqs.image, doNotOwnPreReqs.bounds.x,
					doNotOwnPreReqs.bounds.y);

			for (int i = 0; i < 20; i++) {

				if (Gdx.input.isTouched(i)) {
					touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touch);
					// OK
					if ((touch.x >= 830 && touch.x <= 1090)
							&& (touch.y >= 620 && touch.y <= 720)) {
						playSelectSound();
						sleep(200);
						preReqs = false;

					}
				}
			}
		}

		if (cantAfford) {

			batch.draw(cantAffordScreen.image, cantAffordScreen.bounds.x,
					cantAffordScreen.bounds.y);

			for (int i = 0; i < 20; i++) {

				if (Gdx.input.isTouched(i)) {
					touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touch);
					// OK
					if ((touch.x >= 830 && touch.x <= 1090)
							&& (touch.y >= 620 && touch.y <= 720)) {
						playSelectSound();
						sleep(200);
						cantAfford = false;

					}
				}
			}
		}
		if (alreadyOwned) {

			batch.draw(alreadyOwnedScreen.image, alreadyOwnedScreen.bounds.x, alreadyOwnedScreen.bounds.y);

			for (int i = 0; i < 20; i++) {

				if (Gdx.input.isTouched(i)) {
					touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touch);
					// OK
					if ((touch.x >= 830 && touch.x <= 1090)
							&& (touch.y >= 620 && touch.y <= 720)) {
						playSelectSound();
						sleep(200);
						alreadyOwned = false;
					}
				}
			}
		}
		
		// INPUTS
		else{
			
			for (int i = 0; i < 20; i++) {
			
				if (Gdx.input.isTouched(i)) {
					touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touch);
					
					// Duration 1
					if ((touch.x >= 142 && touch.x <= 592)
							&& (touch.y >= 480 && touch.y <= 580)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpDur") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "rapidUpDur")){
								if (canAfford(750)) {
									rapidUpDur = 1;
									cost = 750;
									durConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}

					}
					// Duration 2
					if ((touch.x >= 142 && touch.x <= 592)
							&& (touch.y >= 596 && touch.y <= 696)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpDur") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "rapidUpDur")){
								if (canAfford(1125)) {
									rapidUpDur = 2;
									cost = 1125;
									durConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Duration 3
					if ((touch.x >= 142 && touch.x <= 592)
							&& (touch.y >= 712 && touch.y <= 812)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpDur") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "rapidUpDur")){
								if (canAfford(1750)) {
									rapidUpDur = 3;
									cost = 1750;
									durConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Duration 4
					if ((touch.x >= 142 && touch.x <= 592)
							&& (touch.y >= 828 && touch.y <= 928)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpDur") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "rapidUpDur")){
								if (canAfford(2500)) {
									rapidUpDur = 4;
									cost = 2500;
									durConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Duration 5
					if ((touch.x >= 142 && touch.x <= 592)
							&& (touch.y >= 944 && touch.y <= 1044)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpDur") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "rapidUpDur")){
								if (canAfford(3000)) {
									rapidUpDur = 5;
									cost = 3000;
									durConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Reload 1
					if ((touch.x >= 734 && touch.x <= 1184)
							&& (touch.y >= 480 && touch.y <= 580)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpReload") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "rapidUpReload")){
								if (canAfford(500)) {
									rapidUpReload = 1;
									cost = 500;
									reloadConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}

					}
					// Reload 2
					if ((touch.x >= 734 && touch.x <= 1184)
							&& (touch.y >= 596 && touch.y <= 696)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpReload") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "rapidUpReload")){
								if (canAfford(750)) {
									rapidUpReload = 2;
									cost = 750;
									reloadConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Reload 3
					if ((touch.x >= 734 && touch.x <= 1184)
							&& (touch.y >= 712 && touch.y <= 812)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpReload") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "rapidUpReload")){
								if (canAfford(1125)) {
									rapidUpReload = 3;
									cost = 1125;
									reloadConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Reload 4
					if ((touch.x >= 734 && touch.x <= 1184)
							&& (touch.y >= 828 && touch.y <= 928)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpReload") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "rapidUpReload")){
								if (canAfford(1500)) {
									rapidUpReload = 4;
									cost = 1500;
									reloadConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Reload 5
					if ((touch.x >= 734 && touch.x <= 1184)
							&& (touch.y >= 944 && touch.y <= 1044)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpReload") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "rapidUpReload")){
								if (canAfford(2000)) {
									rapidUpReload = 5;
									cost = 2000;
									reloadConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Ricochet 1
					if ((touch.x >= 1326 && touch.x <= 1776)
							&& (touch.y >= 480 && touch.y <= 580)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpRicochet") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "rapidUpRicochet")){
								if (canAfford(250)) {
									rapidUpRicochet = 1;
									cost = 250;
									ricochetConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}

					}
					// Ricochet 2
					if ((touch.x >= 1326 && touch.x <= 1776)
							&& (touch.y >= 596 && touch.y <= 696)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpRicochet") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "rapidUpRicochet")){
								if (canAfford(500)) {
									rapidUpRicochet = 2;
									cost = 500;
									ricochetConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Ricochet 3
					if ((touch.x >= 1326 && touch.x <= 1776)
							&& (touch.y >= 712 && touch.y <= 812)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpRicochet") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "rapidUpRicochet")){
								if (canAfford(1000)) {
									rapidUpRicochet = 3;
									cost = 1000;
									ricochetConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Ricochet 4
					if ((touch.x >= 1326 && touch.x <= 1776)
							&& (touch.y >= 828 && touch.y <= 928)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpRicochet") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "rapidUpRicochet")){
								if (canAfford(2000)) {
									rapidUpRicochet = 4;
									cost = 2000;
									ricochetConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Ricochet 5
					if ((touch.x >= 1326 && touch.x <= 1776)
							&& (touch.y >= 944 && touch.y <= 1044)) {
						playSelectSound();
						sleep(200);
						if (GameScreen.prefs.getInteger("rapidUpRicochet") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "rapidUpRicochet")){
								if (canAfford(4000)) {
									rapidUpRicochet = 5;
									cost = 4000;
									ricochetConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// BACK BUTTON
					if ((touch.x >= 0 && touch.x <= 350)
							&& (touch.y >= 0 && touch.y <= 140)) {
						playSelectSound();
						sleep(200);
						
						game.store_power_rapid_screen.dispose();
						game.setScreen(game.store_power_screen);
					}
				}
			}
		}

		batch.end();

	}
	
	public static void setCurrency(int val) {
		int current = getCurrency();
		GameScreen.prefs.putInteger("currency", (current + val));
		GameScreen.prefs.flush();
	}

	public static int getCurrency() {
		return GameScreen.prefs.getInteger("currency");
	}
	
	public void checkPurchases() {

		switch(GameScreen.prefs.getInteger("rapidUpDur")) {
		case 1:
			batch.draw(purchased.image, 142, 480);
			break;
		case 2:
			batch.draw(purchased.image, 142, 480);
			batch.draw(purchased.image, 142, 596);
			break;
		case 3:
			batch.draw(purchased.image, 142, 480);
			batch.draw(purchased.image, 142, 596);
			batch.draw(purchased.image, 142, 712);
			break;
		case 4:
			batch.draw(purchased.image, 142, 480);
			batch.draw(purchased.image, 142, 596);
			batch.draw(purchased.image, 142, 712);
			batch.draw(purchased.image, 142, 828);
			break;
		case 5:
			batch.draw(purchased.image, 142, 480);
			batch.draw(purchased.image, 142, 596);
			batch.draw(purchased.image, 142, 712);
			batch.draw(purchased.image, 142, 828);
			batch.draw(purchased.image, 142, 944);
			break;
		}
		switch(GameScreen.prefs.getInteger("rapidUpReload")) {
		case 1:
			batch.draw(purchased.image, 734, 480);
			break;
		case 2:
			batch.draw(purchased.image, 734, 480);
			batch.draw(purchased.image, 734, 596);
			break;
		case 3:
			batch.draw(purchased.image, 734, 480);
			batch.draw(purchased.image, 734, 596);
			batch.draw(purchased.image, 734, 712);
			break;
		case 4:
			batch.draw(purchased.image, 734, 480);
			batch.draw(purchased.image, 734, 596);
			batch.draw(purchased.image, 734, 712);
			batch.draw(purchased.image, 734, 828);
			break;
		case 5:
			batch.draw(purchased.image, 734, 480);
			batch.draw(purchased.image, 734, 596);
			batch.draw(purchased.image, 734, 712);
			batch.draw(purchased.image, 734, 828);
			batch.draw(purchased.image, 734, 944);
			break;
		}
		switch(GameScreen.prefs.getInteger("rapidUpRicochet")) {
		case 1:
			batch.draw(purchased.image, 1326, 480);
			break;
		case 2:
			batch.draw(purchased.image, 1326, 480);
			batch.draw(purchased.image, 1326, 596);
			break;
		case 3:
			batch.draw(purchased.image, 1326, 480);
			batch.draw(purchased.image, 1326, 596);
			batch.draw(purchased.image, 1326, 712);
			break;
		case 4:
			batch.draw(purchased.image, 1326, 480);
			batch.draw(purchased.image, 1326, 596);
			batch.draw(purchased.image, 1326, 712);
			batch.draw(purchased.image, 1326, 828);
			break;
		case 5:
			batch.draw(purchased.image, 1326, 480);
			batch.draw(purchased.image, 1326, 596);
			batch.draw(purchased.image, 1326, 712);
			batch.draw(purchased.image, 1326, 828);
			batch.draw(purchased.image, 1326, 944);
			break;
		}
	}
	
	public void sleep(int val){
		try {
			Thread.sleep(val);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
	
	public boolean canAfford(int cost) {

		int currentCoins = getCurrency();
		if (currentCoins >= cost)
			return true;
		return false;
	}
	
	public boolean havePreReq(int level, String pref){
		
		int levelOwned = GameScreen.prefs.getInteger(pref);
		int levelReq = level - 1;
		
		if(level == 1)
			return true;
		else if(levelReq == levelOwned)
			return true;
		
		return false;
		
	}

	public void purchaseReload(int rapidUpReload, int cost) {

		GameScreen.prefs.putInteger("rapidUpReload", rapidUpReload);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void purchaseDur(int rapidUpDur, int cost) {

		GameScreen.prefs.putInteger("rapidUpDur", rapidUpDur);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void purchaseRicochet(int rapidUpRichochet, int cost) {

		GameScreen.prefs.putInteger("rapidUpRicochet", rapidUpRichochet);
		cost = cost * (-1);
		setCurrency(cost);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
