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

public class StoreHealthScreen extends ApplicationAdapter implements Screen {
	
	MyGame game;
	SpriteBatch batch;
	int currency = 0;
	int hUpHealth;
	int hUpRegen;
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
	boolean healthConfirmBool = false;
	boolean regenConfirmBool = false;
	boolean cantAfford = false;
	boolean alreadyOwned = false;
	boolean preReqs = false;
	Vector3 touch;

	public StoreHealthScreen(MyGame game) {
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

		if (healthConfirmBool) {
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
						purchaseHealth(hUpHealth, cost);
						sleep(200);
						healthConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						sleep(200);
						healthConfirmBool = false;

					}
				}
			}
		}
		
		if (regenConfirmBool) {
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
						purchaseRegen(hUpRegen, cost);
						sleep(200);
						regenConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						sleep(200);
						regenConfirmBool = false;

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
						if (GameScreen.prefs.getInteger("hUpRegen") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "hUpRegen")){
								if (canAfford(500)) {
									hUpRegen = 1;
									cost = 500;
									regenConfirmBool = true;
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
						if (GameScreen.prefs.getInteger("hUpRegen") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "hUpRegen")){
								if (canAfford(1000)) {
									hUpRegen = 2;
									cost = 1000;
									regenConfirmBool = true;
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
						if (GameScreen.prefs.getInteger("hUpRegen") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "hUpRegen")){
								if (canAfford(1500)) {
									hUpRegen = 3;
									cost = 1500;
									regenConfirmBool = true;
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
						if (GameScreen.prefs.getInteger("hUpRegen") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "hUpRegen")){
								if (canAfford(2000)) {
									hUpRegen = 4;
									cost = 2000;
									regenConfirmBool = true;
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
						if (GameScreen.prefs.getInteger("hUpRegen") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "hUpRegen")){
								if (canAfford(2500)) {
									hUpRegen = 5;
									cost = 2500;
									regenConfirmBool = true;
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
						if (GameScreen.prefs.getInteger("hUpHealth") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "hUpHealth")){
								if (canAfford(500)) {
									hUpHealth = 1;
									cost = 500;
									healthConfirmBool = true;
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
						if (GameScreen.prefs.getInteger("hUpHealth") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "hUpHealth")){
								if (canAfford(875)) {
									hUpHealth = 2;
									cost = 875;
									healthConfirmBool = true;
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
						if (GameScreen.prefs.getInteger("hUpHealth") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "hUpHealth")){
								if (canAfford(1250)) {
									hUpHealth = 3;
									cost = 1250;
									healthConfirmBool = true;
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
						if (GameScreen.prefs.getInteger("hUpHealth") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "hUpHealth")){
								if (canAfford(1625)) {
									hUpHealth = 4;
									cost = 1625;
									healthConfirmBool = true;
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
						if (GameScreen.prefs.getInteger("hUpHealth") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "hUpHealth")){
								if (canAfford(2000)) {
									hUpHealth = 5;
									cost = 2000;
									healthConfirmBool = true;
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

		switch(GameScreen.prefs.getInteger("hUpRegen")) {
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
		switch(GameScreen.prefs.getInteger("hUpHealth")) {
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
	
	public void purchaseHealth(int hUpHealth, int cost){
		
		GameScreen.prefs.putInteger("hUpHealth", hUpHealth);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void purchaseRegen(int hUpRegen, int cost){
		
		GameScreen.prefs.putInteger("hUpRegen", hUpRegen);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void drawCosts(){
		
		Assets.font48.draw(batch, "500 SC", 115, 480);
		Assets.font48.draw(batch, "1000 SC", 80, 595);
		Assets.font48.draw(batch, "1500 SC", 80, 710);
		Assets.font48.draw(batch, "2000 SC", 80, 825);
		Assets.font48.draw(batch, "2500 SC", 80, 940);
		
		Assets.font48.draw(batch, "500 SC", 1600, 480);
		Assets.font48.draw(batch, "875 SC", 1600, 595);
		Assets.font48.draw(batch, "1250 SC", 1600, 710);
		Assets.font48.draw(batch, "1625 SC", 1600, 825);
		Assets.font48.draw(batch, "2000 SC", 1600, 940);
		
	}
	
	public void drawText(){
		
		Assets.font48.setColor(Color.BLACK);
		Assets.font48.draw(batch, "20 Seconds", 365, 480);
		Assets.font48.draw(batch, "17.5 Seconds", 365, 595);
		Assets.font48.draw(batch, "15 Seconds", 365, 710);
		Assets.font48.draw(batch, "12.5 Seconds", 365, 825);
		Assets.font48.draw(batch, "10 Seconds", 365, 940);
		
		Assets.font48.draw(batch, "1 Extra HP", 1175, 480);
		Assets.font48.draw(batch, "2 Extra HP", 1175, 595);
		Assets.font48.draw(batch, "3 Extra HP", 1175, 710);
		Assets.font48.draw(batch, "4 Extra HP", 1175, 825);
		Assets.font48.draw(batch, "5 Extra HP", 1175, 940);
		
		Assets.font48.setColor(Color.YELLOW);
		Assets.font48.draw(batch, "Regeneration", 355, 400);
		Assets.font48.draw(batch, "Extra Health", 1145, 400);
		
		Assets.font96.setColor(Color.YELLOW);
		Assets.font96.draw(batch, "Health", 760, 225);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
