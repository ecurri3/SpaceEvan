package com.mygdx.game;

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

public class StorePowerGeneralScreen extends ApplicationAdapter implements Screen{
	
	MyGame game;
	SpriteBatch batch;
	int currency = 0;
	int genUpDur;
	int genUpExtra;
	int cost;
	String yourCurrency;

	BitmapFont yourBitmapFontName;

	StoreTemplateTwo storetemplate2;
	ConfirmPurchaseScreen confirmPurchaseScreen;
	CantAffordScreen cantAffordScreen;
	AlreadyOwnedScreen alreadyOwnedScreen;
	Prerequisites doNotOwnPreReqs;
	Purchased purchased;

	OrthographicCamera camera;
	boolean isTouched = false;
	boolean durConfirmBool = false;
	boolean extraConfirmBool = false;
	boolean cantAfford = false;
	boolean alreadyOwned = false;
	boolean preReqs = false;
	Vector3 touch;

	public StorePowerGeneralScreen(MyGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1180);

		storetemplate2 = new StoreTemplateTwo();
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

		batch.draw(storetemplate2.image,
				storetemplate2.bounds.x,
				storetemplate2.bounds.y);
		
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
						purchaseHealth(genUpDur, cost);
						sleep(200);
						durConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						sleep(200);
						durConfirmBool = false;

					}
				}
			}
		}
		
		if (extraConfirmBool) {
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
						purchaseRegen(genUpExtra, cost);
						sleep(200);
						extraConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						sleep(200);
						extraConfirmBool = false;

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
						sleep(200);
						alreadyOwned = false;

					}
				}
			}
		}

		else {

			// INPUTS

			for (int i = 0; i < 20; i++) {

				if (Gdx.input.isTouched(i)) {
					touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touch);
					// Reload 1
					if ((touch.x >= 350 && touch.x <= 800)
							&& (touch.y >= 450 && touch.y <= 550)) {
						sleep(200);
						if (GameScreen.prefs.getInteger("genUpExtra") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "genUpExtra")){
								if (canAfford(500)) {
									genUpExtra = 1;
									cost = 500;
									extraConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}

					}
					// Reload 2
					if ((touch.x >= 350 && touch.x <= 800)
							&& (touch.y >= 565 && touch.y <= 665)) {
						sleep(200);
						if (GameScreen.prefs.getInteger("genUpExtra") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "genUpExtra")){
								if (canAfford(1500)) {
									genUpExtra = 2;
									cost = 1500;
									extraConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Reload 3
					if ((touch.x >= 350 && touch.x <= 800)
							&& (touch.y >= 680 && touch.y <= 780)) {
						sleep(200);
						if (GameScreen.prefs.getInteger("genUpExtra") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "genUpExtra")){
								if (canAfford(2000)) {
									genUpExtra = 3;
									cost = 2000;
									extraConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Reload 4
					if ((touch.x >= 350 && touch.x <= 800)
							&& (touch.y >= 795 && touch.y <= 895)) {
						sleep(200);
						if (GameScreen.prefs.getInteger("genUpExtra") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "genUpExtra")){
								if (canAfford(3000)) {
									genUpExtra = 4;
									cost = 3000;
									extraConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Reload 5
					if ((touch.x >= 350 && touch.x <= 800)
							&& (touch.y >= 910 && touch.y <= 1010)) {
						sleep(200);
						if (GameScreen.prefs.getInteger("genUpExtra") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "genUpExtra")){
								if (canAfford(3500)) {
									genUpExtra = 5;
									cost = 3500;
									extraConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Auto 1
					if ((touch.x >= 1130 && touch.x <= 1580)
							&& (touch.y >= 450 && touch.y <= 550)) {
						sleep(200);
						if (GameScreen.prefs.getInteger("genUpDur") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "genUpDur")){
								if (canAfford(750)) {
									genUpDur = 1;
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
					// Auto 2
					if ((touch.x >= 1130 && touch.x <= 1580)
							&& (touch.y >= 565 && touch.y <= 665)) {
						sleep(200);
						if (GameScreen.prefs.getInteger("genUpDur") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "genUpDur")){
								if (canAfford(1500)) {
									genUpDur = 2;
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
					// Auto 3
					if ((touch.x >= 1130 && touch.x <= 1580)
							&& (touch.y >= 680 && touch.y <= 780)) {
						sleep(200);
						if (GameScreen.prefs.getInteger("genUpDur") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "genUpDur")){
								if (canAfford(2250)) {
									genUpDur = 3;
									cost = 2250;
									durConfirmBool = true;
								}
								else
									cantAfford = true;
							}
							else
								preReqs = true;
						}
					}
					// Auto 4
					if ((touch.x >= 1130 && touch.x <= 1580)
							&& (touch.y >= 795 && touch.y <= 895)) {
						sleep(200);
						if (GameScreen.prefs.getInteger("genUpDur") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "genUpDur")){
								if (canAfford(3000)) {
									genUpDur = 4;
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
					// Auto 5
					if ((touch.x >= 1130 && touch.x <= 1580)
							&& (touch.y >= 910 && touch.y <= 1010)) {
						sleep(200);
						if (GameScreen.prefs.getInteger("genUpDur") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "genUpDur")){
								if (canAfford(3750)) {
									genUpDur = 5;
									cost = 3750;
									durConfirmBool = true;
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

						game.store_health_screen.dispose();
						game.setScreen(game.store_screen);
						sleep(200);
					}

				}
			}
		}

		batch.end();

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

	public void checkPurchases() {

		switch(GameScreen.prefs.getInteger("genUpExtra")) {
		case 1:
			batch.draw(purchased.image, 340, 450);
			break;
		case 2:
			batch.draw(purchased.image, 340, 450);
			batch.draw(purchased.image, 340, 565);
			break;
		case 3:
			batch.draw(purchased.image, 340, 450);
			batch.draw(purchased.image, 340, 565);
			batch.draw(purchased.image, 340, 680);
			break;
		case 4:
			batch.draw(purchased.image, 340, 450);
			batch.draw(purchased.image, 340, 565);
			batch.draw(purchased.image, 340, 680);
			batch.draw(purchased.image, 340, 795);
			break;
		case 5:
			batch.draw(purchased.image, 340, 450);
			batch.draw(purchased.image, 340, 565);
			batch.draw(purchased.image, 340, 680);
			batch.draw(purchased.image, 340, 795);
			batch.draw(purchased.image, 340, 910);
			break;
		}
		switch(GameScreen.prefs.getInteger("genUpDur")) {
		case 1:
			batch.draw(purchased.image, 1130, 450);
			break;
		case 2:
			batch.draw(purchased.image, 1130, 450);
			batch.draw(purchased.image, 1130, 565);
			break;
		case 3:
			batch.draw(purchased.image, 1130, 450);
			batch.draw(purchased.image, 1130, 565);
			batch.draw(purchased.image, 1130, 680);
			break;
		case 4:
			batch.draw(purchased.image, 1130, 450);
			batch.draw(purchased.image, 1130, 565);
			batch.draw(purchased.image, 1130, 680);
			batch.draw(purchased.image, 1130, 795);
			break;
		case 5:
			batch.draw(purchased.image, 1130, 450);
			batch.draw(purchased.image, 1130, 565);
			batch.draw(purchased.image, 1130, 680);
			batch.draw(purchased.image, 1130, 795);
			batch.draw(purchased.image, 1130, 910);
			break;
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
	
	public void purchaseHealth(int genUpDur, int cost){
		
		GameScreen.prefs.putInteger("genUpDur", genUpDur);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void purchaseRegen(int genUpExtra, int cost){
		
		GameScreen.prefs.putInteger("genUpExtra", genUpExtra);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void drawCosts(){
		
		Assets.font48.draw(batch, "500 SC", 115, 480);
		Assets.font48.draw(batch, "1500 SC", 80, 595);
		Assets.font48.draw(batch, "2000 SC", 80, 710);
		Assets.font48.draw(batch, "3000 SC", 80, 825);
		Assets.font48.draw(batch, "3500 SC", 80, 940);
		
		Assets.font48.draw(batch, "750 SC", 1600, 480);
		Assets.font48.draw(batch, "1500 SC", 1600, 595);
		Assets.font48.draw(batch, "2250 SC", 1600, 710);
		Assets.font48.draw(batch, "3000 SC", 1600, 825);
		Assets.font48.draw(batch, "3750 SC", 1600, 940);
		
	}
	
	public void drawText(){
		
		Assets.font48.setColor(Color.BLACK);
		Assets.font48.draw(batch, "10% Quicker", 365, 480);
		Assets.font48.draw(batch, "20% Quicker", 365, 595);
		Assets.font48.draw(batch, "30% Quicker", 365, 710);
		Assets.font48.draw(batch, "40% Quicker", 365, 825);
		Assets.font48.draw(batch, "50% Quicker", 365, 940);
		
		Assets.font48.draw(batch, "15% Chance", 1175, 480);
		Assets.font48.draw(batch, "30% Chance", 1175, 595);
		Assets.font48.draw(batch, "45% Chance", 1175, 710);
		Assets.font48.draw(batch, "60% Chance", 1175, 825);
		Assets.font48.draw(batch, "75% Chance", 1175, 940);
		
		Assets.font48.setColor(Color.YELLOW);
		Assets.font48.draw(batch, "Spawn Timer", 375, 400);
		Assets.font48.draw(batch, "Extra Powerup", 1130, 400);
		
		Assets.font96.setColor(Color.YELLOW);
		Assets.font96.draw(batch, "General", 725, 225);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
