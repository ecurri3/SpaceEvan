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

public class StorePowerShotgunScreen extends ApplicationAdapter implements Screen{
	
	MyGame game;
	SpriteBatch batch;
	int currency = 0;
	int shotgunUpDur;
	int shotgunUpPierce;
	int shotgunUpLandmine;
	int cost;
	String yourCurrency;

	BitmapFont yourBitmapFontName;

	StoreTemplateThree storetemplate3;
	ConfirmPurchaseScreen confirmPurchaseScreen;
	CantAffordScreen cantAffordScreen;
	AlreadyOwnedScreen alreadyOwnedScreen;
	Prerequisites doNotOwnPreReqs;
	Purchased purchased;

	OrthographicCamera camera;
	boolean isTouched = false;
	
	boolean durConfirmBool = false;
	boolean pierceConfirmBool = false;
	boolean landmineConfirmBool = false;
	
	boolean soundOn;
	boolean soundOff;
	
	boolean cantAfford = false;
	boolean alreadyOwned = false;
	boolean preReqs = false;
	Vector3 touch;
	
	public StorePowerShotgunScreen(MyGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1180);

		storetemplate3 = new StoreTemplateThree();
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

		batch.draw(storetemplate3.image, storetemplate3.bounds.x, storetemplate3.bounds.y);
		
		drawCosts();
		drawText();
		Assets.font48.setColor(Color.YELLOW);
		Assets.font48.draw(batch, "Space Coins: ", 1300, 50);
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
						purchaseDur(shotgunUpDur, cost);
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
		
		if (pierceConfirmBool) {
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
						purchaseAmount(shotgunUpPierce, cost);
						sleep(200);
						pierceConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						playSelectSound();
						sleep(200);
						pierceConfirmBool = false;

					}
				}
			}
		}
		
		if (landmineConfirmBool) {
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
						purchaseMirror(shotgunUpLandmine, cost);
						playSelectSound();
						sleep(200);
						landmineConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						playSelectSound();
						sleep(200);
						landmineConfirmBool = false;

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
						if (PlayerData.prefs.getInteger("shotgunUpDur") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "shotgunUpDur")){
								if (canAfford(750)) {
									shotgunUpDur = 1;
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
						if (PlayerData.prefs.getInteger("shotgunUpDur") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "shotgunUpDur")){
								if (canAfford(1125)) {
									shotgunUpDur = 2;
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
						if (PlayerData.prefs.getInteger("shotgunUpDur") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "shotgunUpDur")){
								if (canAfford(1500)) {
									shotgunUpDur = 3;
									cost = 1500;
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
						if (PlayerData.prefs.getInteger("shotgunUpDur") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "shotgunUpDur")){
								if (canAfford(2500)) {
									shotgunUpDur = 4;
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
						if (PlayerData.prefs.getInteger("shotgunUpDur") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "shotgunUpDur")){
								if (canAfford(3000)) {
									shotgunUpDur = 5;
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
						if (PlayerData.prefs.getInteger("shotgunUpPierce") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "shotgunUpPierce")){
								if (canAfford(750)) {
									shotgunUpPierce = 1;
									cost = 750;
									pierceConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("shotgunUpPierce") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "shotgunUpPierce")){
								if (canAfford(1250)) {
									shotgunUpPierce = 2;
									cost = 1250;
									pierceConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("shotgunUpPierce") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "shotgunUpPierce")){
								if (canAfford(1750)) {
									shotgunUpPierce = 3;
									cost = 1750;
									pierceConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("shotgunUpPierce") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "shotgunUpPierce")){
								if (canAfford(2250)) {
									shotgunUpPierce = 4;
									cost = 2250;
									pierceConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("shotgunUpPierce") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "shotgunUpPierce")){
								if (canAfford(2750)) {
									shotgunUpPierce = 5;
									cost = 2750;
									pierceConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("shotgunUpLandmine") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "shotgunUpLandmine")){
								if (canAfford(1000)) {
									shotgunUpLandmine = 1;
									cost = 1000;
									landmineConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("shotgunUpLandmine") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "shotgunUpLandmine")){
								if (canAfford(2000)) {
									shotgunUpLandmine = 2;
									cost = 2000;
									landmineConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("shotgunUpLandmine") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3000, "shotgunUpLandmine")){
								if (canAfford(3000)) {
									shotgunUpLandmine = 3;
									cost = 3;
									landmineConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("shotgunUpLandmine") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "shotgunUpLandmine")){
								if (canAfford(4000)) {
									shotgunUpLandmine = 4;
									cost = 4000;
									landmineConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("shotgunUpLandmine") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "shotgunUpLandmine")){
								if (canAfford(5000)) {
									shotgunUpLandmine = 5;
									cost = 5000;
									landmineConfirmBool = true;
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
	
	public void checkPurchases() {

		switch(PlayerData.prefs.getInteger("shotgunUpDur")) {
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
		switch(PlayerData.prefs.getInteger("shotgunUpPierce")) {
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
		switch(PlayerData.prefs.getInteger("shotgunUpLandmine")) {
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
	
	public boolean canAfford(int cost) {

		int currentCoins = getCurrency();
		if (currentCoins >= cost)
			return true;
		return false;
	}
	
	public boolean havePreReq(int level, String pref){
		
		int levelOwned = PlayerData.prefs.getInteger(pref);
		int levelReq = level - 1;
		
		if(level == 1)
			return true;
		else if(levelReq == levelOwned)
			return true;
		
		return false;
		
	}
	
	public void purchaseAmount(int shotgunUpPierce, int cost) {

		PlayerData.prefs.putInteger("shotgunUpPierce", shotgunUpPierce);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void purchaseDur(int shotgunUpDur, int cost) {

		PlayerData.prefs.putInteger("shotgunUpDur", shotgunUpDur);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void purchaseMirror(int shotgunUpLandmine, int cost) {

		PlayerData.prefs.putInteger("shotgunUpLandmine", shotgunUpLandmine);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
public void drawCosts(){
		
		Assets.font36.draw(batch, "750", 50, 515);
		Assets.font36.draw(batch, "1125", 30, 630);
		Assets.font36.draw(batch, "1500", 30, 745);
		Assets.font36.draw(batch, "2000", 30, 860);
		Assets.font36.draw(batch, "3000", 30, 975);
		
		Assets.font36.draw(batch, "750", 645, 515);
		Assets.font36.draw(batch, "1250", 620, 630);
		Assets.font36.draw(batch, "1750", 620, 745);
		Assets.font36.draw(batch, "2250", 620, 860);
		Assets.font36.draw(batch, "2750", 620, 975);
		
		Assets.font36.draw(batch, "1000", 1785, 515);
		Assets.font36.draw(batch, "2000", 1785, 630);
		Assets.font36.draw(batch, "3000", 1785, 745);
		Assets.font36.draw(batch, "4000", 1785, 860);
		Assets.font36.draw(batch, "5000", 1785, 975);
		
	}
	
	public void drawText(){
		
		Assets.font48.setColor(Color.BLACK);
		Assets.font48.draw(batch, "10% Longer", 190, 515);
		Assets.font48.draw(batch, "20% Longer", 190, 630);
		Assets.font48.draw(batch, "30% Longer", 190, 745);
		Assets.font48.draw(batch, "40% Longer", 190, 860);
		Assets.font48.draw(batch, "50% Longer", 190, 975);
		
		Assets.font48.draw(batch, "20% chance", 775, 515);
		Assets.font48.draw(batch, "40% chance", 775, 630);
		Assets.font48.draw(batch, "60% chance", 775, 745);
		Assets.font48.draw(batch, "80% chance", 775, 860);
		Assets.font48.draw(batch, "100% chance", 775, 975);
		
		Assets.font48.draw(batch, "10% Chance", 1370, 515);
		Assets.font48.draw(batch, "20% Chance", 1370, 630);
		Assets.font48.draw(batch, "25% Chance", 1370, 745);
		Assets.font48.draw(batch, "30% Chance", 1370, 860);
		Assets.font48.draw(batch, "35% Chance", 1370, 975);
		
		Assets.font48.setColor(Color.YELLOW);
		Assets.font48.draw(batch, "Duration", 225, 420);
		Assets.font48.draw(batch, "Piercing", 805, 420);
		Assets.font48.draw(batch, "Landmines", 1400, 420);
		
		Assets.font96.setColor(Color.YELLOW);
		Assets.font96.draw(batch, "SHOTGUN", 715, 225);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
