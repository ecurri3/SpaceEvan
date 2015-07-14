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
import com.mygdx.gameData.PlayerData;

public class StoreMissileScreen extends ApplicationAdapter implements Screen {

	MyGame game;
	SpriteBatch batch;
	int currency = 0;
	int mUpReload;
	int mUpAuto;
	int cost;
	String yourCurrency;

	BitmapFont yourBitmapFontName;

	StoreMissileScreenBackground storemissilebackground;
	ConfirmPurchaseScreen confirmPurchaseScreen;
	CantAffordScreen cantAffordScreen;
	AlreadyOwnedScreen alreadyOwnedScreen;
	Prerequisites doNotOwnPreReqs;
	Purchased purchased;

	OrthographicCamera camera;
	boolean isTouched = false;
	boolean confirmBool = false;
	boolean sizeConfirmBool = false;
	boolean cantAfford = false;
	boolean alreadyOwned = false;
	boolean preReqs = false;
	Vector3 touch;

	public StoreMissileScreen(MyGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1180);

		storemissilebackground = new StoreMissileScreenBackground();
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

		if (!PlayerData.prefs.contains("currency")) {
			PlayerData.prefs.putInteger("currency", 0);
		}

		currency = getCurrency();
		yourCurrency = "" + currency;

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
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

		batch.draw(storemissilebackground.image,
				storemissilebackground.bounds.x,
				storemissilebackground.bounds.y);
		Assets.font48.setColor(Color.WHITE);
		Assets.font48.draw(batch, yourCurrency, 1760, 50);
		Assets.font48.setColor(Color.valueOf("ffff00"));
		
		checkPurchases();

		if (confirmBool) {
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
						purchaseReload(mUpReload, cost);
						confirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						confirmBool = false;
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
			}
		}
		
		if (sizeConfirmBool) {
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
						purchaseSize(mUpAuto, cost);
						sizeConfirmBool = false;
						currency = getCurrency();
						yourCurrency = "" + currency;
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
					// NO
					if ((touch.x >= 1030 && touch.x <= 1290)
							&& (touch.y >= 615 && touch.y <= 705)) {
						sizeConfirmBool = false;
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

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
						if (PlayerData.prefs.getInteger("mUpReload") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "mUpReload")){
								if (canAfford(500)) {
									mUpReload = 1;
									cost = 500;
									confirmBool = true;
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
						if (PlayerData.prefs.getInteger("mUpReload") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "mUpReload")){
								if (canAfford(750)) {
									mUpReload = 2;
									cost = 750;
									confirmBool = true;
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
						if (PlayerData.prefs.getInteger("mUpReload") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "mUpReload")){
								if (canAfford(1125)) {
									mUpReload = 3;
									cost = 1125;
									confirmBool = true;
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
						if (PlayerData.prefs.getInteger("mUpReload") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "mUpReload")){
								if (canAfford(1500)) {
									mUpReload = 4;
									cost = 1500;
									confirmBool = true;
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
						if (PlayerData.prefs.getInteger("mUpReload") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "mUpReload")){
								if (canAfford(2000)) {
									mUpReload = 5;
									cost = 2000;
									confirmBool = true;
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
						if (PlayerData.prefs.getInteger("mUpAuto") >= 1) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(1, "mUpAuto")){
								if (canAfford(250)) {
									mUpAuto = 1;
									cost = 250;
									sizeConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("mUpAuto") >= 2) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(2, "mUpAuto")){
								if (canAfford(375)) {
									mUpAuto = 2;
									cost = 375;
									sizeConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("mUpAuto") >= 3) {
							alreadyOwned = true;
						} else {
							if(havePreReq(3, "mUpAuto")){
								if (canAfford(550)) {
									mUpAuto = 3;
									cost = 550;
									sizeConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("mUpAuto") >= 4) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(4, "mUpAuto")){
								if (canAfford(750)) {
									mUpAuto = 4;
									cost = 750;
									sizeConfirmBool = true;
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
						if (PlayerData.prefs.getInteger("mUpAuto") >= 5) {
							alreadyOwned = true;
						} else {
							// CONFIRMATION DISPLAY
							if(havePreReq(5, "mUpAuto")){
								if (canAfford(1000)) {
									mUpAuto = 5;
									cost = 1000;
									sizeConfirmBool = true;
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

						game.store_missile_screen.dispose();
						game.setScreen(game.store_screen);
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			}
		}

		batch.end();

	}

	public static void setCurrency(int val) {
		int current = getCurrency();
		PlayerData.prefs.putInteger("currency", (current + val));
		PlayerData.prefs.flush();
	}

	public static int getCurrency() {
		return PlayerData.prefs.getInteger("currency");
	}

	public void checkPurchases() {

		switch(PlayerData.prefs.getInteger("mUpReload")) {
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
		switch(PlayerData.prefs.getInteger("mUpAuto")) {
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
		
		int levelOwned = PlayerData.prefs.getInteger(pref);
		int levelReq = level - 1;
		
		if(level == 1)
			return true;
		else if(levelReq == levelOwned)
			return true;
		
		return false;
		
	}

	public void purchaseReload(int mUpReload, int cost) {

		PlayerData.prefs.putInteger("mUpReload", mUpReload);
		cost = cost * (-1);
		setCurrency(cost);
	}
	
	public void purchaseSize(int mUpAuto, int cost) {

		PlayerData.prefs.putInteger("mUpAuto", mUpAuto);
		cost = cost * (-1);
		setCurrency(cost);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
