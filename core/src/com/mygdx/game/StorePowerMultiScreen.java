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

public class StorePowerMultiScreen extends ApplicationAdapter implements Screen {
	
	MyGame game;
	SpriteBatch batch;
	int currency = 0;
	int multiUpDur;
	int multiUpAmount;
	int multiUpMirror;
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
	boolean amountConfirmBool = false;
	boolean mirrorConfirmBool = false;
	
	boolean soundOn;
	boolean soundOff;
	
	boolean cantAfford = false;
	boolean alreadyOwned = false;
	boolean preReqs = false;
	Vector3 touch;
	
	public StorePowerMultiScreen(MyGame game) {
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
						purchaseDur(multiUpDur, cost);
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
		
		if (amountConfirmBool) {
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
						purchaseAmount(multiUpAmount, cost);
						sleep(200);
						amountConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						playSelectSound();
						sleep(200);
						amountConfirmBool = false;

					}
				}
			}
		}
		
		if (mirrorConfirmBool) {
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
						purchaseMirror(multiUpMirror, cost);
						playSelectSound();
						sleep(200);
						mirrorConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						playSelectSound();
						sleep(200);
						mirrorConfirmBool = false;

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
						if (PlayerData.prefs.getInteger("multiUpDur") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "multiUpDur")){
								if (canAfford(750)) {
									multiUpDur = 1;
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
						if (PlayerData.prefs.getInteger("multiUpDur") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "multiUpDur")){
								if (canAfford(1125)) {
									multiUpDur = 2;
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
						if (PlayerData.prefs.getInteger("multiUpDur") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "multiUpDur")){
								if (canAfford(1500)) {
									multiUpDur = 3;
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
						if (PlayerData.prefs.getInteger("multiUpDur") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "multiUpDur")){
								if (canAfford(2500)) {
									multiUpDur = 4;
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
						if (PlayerData.prefs.getInteger("multiUpDur") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "multiUpDur")){
								if (canAfford(3000)) {
									multiUpDur = 5;
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
						if (PlayerData.prefs.getInteger("multiUpAmount") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "multiUpAmount")){
								if (canAfford(500)) {
									multiUpAmount = 1;
									cost = 500;
									amountConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("multiUpAmount") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "multiUpAmount")){
								if (canAfford(1000)) {
									multiUpAmount = 2;
									cost = 1000;
									amountConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("multiUpAmount") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "multiUpAmount")){
								if (canAfford(1500)) {
									multiUpAmount = 3;
									cost = 1500;
									amountConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("multiUpAmount") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "multiUpAmount")){
								if (canAfford(2000)) {
									multiUpAmount = 4;
									cost = 2000;
									amountConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("multiUpAmount") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "multiUpAmount")){
								if (canAfford(2500)) {
									multiUpAmount = 5;
									cost = 2500;
									amountConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("multiUpMirror") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "multiUpMirror")){
								if (canAfford(500)) {
									multiUpMirror = 1;
									cost = 500;
									mirrorConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("multiUpMirror") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "multiUpMirror")){
								if (canAfford(1000)) {
									multiUpMirror = 2;
									cost = 1000;
									mirrorConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("multiUpMirror") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "multiUpMirror")){
								if (canAfford(1500)) {
									multiUpMirror = 3;
									cost = 1500;
									mirrorConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("multiUpMirror") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "multiUpMirror")){
								if (canAfford(2250)) {
									multiUpMirror = 4;
									cost = 2250;
									mirrorConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("multiUpMirror") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "multiUpMirror")){
								if (canAfford(3000)) {
									multiUpMirror = 5;
									cost = 3000;
									mirrorConfirmBool = true;
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

		switch(PlayerData.prefs.getInteger("multiUpDur")) {
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
		switch(PlayerData.prefs.getInteger("multiUpAmount")) {
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
		switch(PlayerData.prefs.getInteger("multiUpMirror")) {
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
	
	public void purchaseAmount(int multiUpAmount, int cost) {

		PlayerData.prefs.putInteger("multiUpAmount", multiUpAmount);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void purchaseDur(int multiUpDur, int cost) {

		PlayerData.prefs.putInteger("multiUpDur", multiUpDur);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void purchaseMirror(int multiUpMirror, int cost) {

		PlayerData.prefs.putInteger("multiUpMirror", multiUpMirror);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
public void drawCosts(){
		
		Assets.font36.draw(batch, "750", 50, 515);
		Assets.font36.draw(batch, "1125", 30, 630);
		Assets.font36.draw(batch, "1500", 30, 745);
		Assets.font36.draw(batch, "2000", 30, 860);
		Assets.font36.draw(batch, "3000", 30, 975);
		
		Assets.font36.draw(batch, "500", 645, 515);
		Assets.font36.draw(batch, "1000", 620, 630);
		Assets.font36.draw(batch, "1500", 620, 745);
		Assets.font36.draw(batch, "2000", 620, 860);
		Assets.font36.draw(batch, "2500", 620, 975);
		
		Assets.font36.draw(batch, "500", 1785, 515);
		Assets.font36.draw(batch, "1000", 1785, 630);
		Assets.font36.draw(batch, "1500", 1785, 745);
		Assets.font36.draw(batch, "2250", 1785, 860);
		Assets.font36.draw(batch, "3000", 1785, 975);
		
	}
	
	public void drawText(){
		
		Assets.font48.setColor(Color.BLACK);
		Assets.font48.draw(batch, "15% Longer", 190, 515);
		Assets.font48.draw(batch, "20% Longer", 190, 630);
		Assets.font48.draw(batch, "45% Longer", 190, 745);
		Assets.font48.draw(batch, "60% Longer", 190, 860);
		Assets.font48.draw(batch, "75% Longer", 190, 975);
		
		Assets.font48.draw(batch, "4 Missiles", 775, 515);
		Assets.font48.draw(batch, "5 Missiles", 775, 630);
		Assets.font48.draw(batch, "6 Missiles", 775, 745);
		Assets.font48.draw(batch, "7 Missiles", 775, 860);
		Assets.font48.draw(batch, "8 Missiles", 775, 975);
		
		Assets.font48.draw(batch, "20% Chance", 1370, 515);
		Assets.font48.draw(batch, "40% Chance", 1370, 630);
		Assets.font48.draw(batch, "60% Chance", 1370, 745);
		Assets.font48.draw(batch, "80% Chance", 1370, 860);
		Assets.font48.draw(batch, "100% Chance", 1370, 975);
		
		Assets.font48.setColor(Color.YELLOW);
		Assets.font48.draw(batch, "Duration", 210, 420);
		Assets.font48.draw(batch, "Extra Missiles", 715, 420);
		Assets.font48.draw(batch, "Mirror Image", 1350, 420);
		
		Assets.font96.setColor(Color.YELLOW);
		Assets.font96.draw(batch, "MULTI SHOT", 630, 225);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
