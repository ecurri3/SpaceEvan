package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {

	// Game preferences, used to save options, currency, upgrades owned, and so
	// on
	public static Preferences prefs;

	// Used when in tilt mode, computes the accelerometer y position to
	// determine player movement
	float accelY = 0;

	// Determines if user set a new highscore at the end of the game
	// set in the restart() function
	static boolean newHighScore = false;

	// Sets the users score to this variable, used in the game over screen
	static int score = 0;
	int highScore = 0;
	String yourHighScore;
	String yourScoreName;

	// BitmapFont used for drawing text/numbers to screen
	BitmapFont font;

	private MyGame game;
	OrthographicCamera camera;
	SpriteBatch batch;
	Vector3 touch;

	float stateTime;

	Random rand;

	// Used for easier calculations when determining timing of certain powerups,
	// reloads, etc
	int nano = 10000000;

	// Base enemy spawn rate
	int enemySpawnRate;

	// Determines if an explosion animation is being played.
	// Set to true when an enemy is destroyed by the player
	boolean explosionBool = false;

	/*
	 * Store Upgrade Boolean Variables
	 */
	boolean healthRegen = false;
	boolean autoShot = false;
	boolean mirror = false;
	boolean mirrorTwins = false;
	boolean ricochet = false;
	boolean multi_extra = false;

	int healthRegenTime = 2000;
	long healthRegen_start;
	long healthRegen_end;
	long healthRegen_dur = (healthRegen_end - healthRegen_start);

	// Base reload duration, half a second
	double reloadDur = 50;

	// Base reload duration for auto fire upgrade, half a second
	double reloadDur_auto = 50;

	// Auto shot upgrade under the Missiles Store section
	int autoShotTime = 500;

	// base Rapid Fire power up duration, 15 seconds
	int rapidFireDuration = 1500;
	// base Rapid Fire reload duration, one tenth of a second
	double rapidFireDur = 10;

	// base Multi Shot power up duration, 22.5 seconds
	int multiShotDuration = 2250;

	// base Shotgun power up duration, 12.5 seconds
	int shotgunDuration = 1250;

	// mirror chance is the chance to spawn Mirror Image
	int mirrorChance = 0;

	// ricochet chance is the chance to spawn a ricochet explosion
	int ricochetChance = 0;

	// multi shot extra missiles upgrade
	int multi_extra_missiles = 3;

	// shotgun piercing upgrade
	boolean shotgun_pierce = false;
	int shotgun_pierce_chance = 0;

	// shotgun landmine upgrade
	boolean landmine = false;
	int landmineChance = 0;
	
	//powerup spawn timer
	int powerup_spawn_timer = 1000;
	
	//powerup extra spawn
	boolean extra_powerup = false;
	int extra_powerup_chance = 0;

	int player_health = 5;

	/*
	 * Static variables shared between game over screen and the game screen
	 */
	static int enemiesKilled = 0;
	static int coinsEarned = 0;
	static int timeElapsed;
	static int timesFired = 0;
	static int healthUsed = 0;

	// Keeps track of the x acceleration of the player
	float accelerationX = 0;

	// Sets the players current acceleration when the game is paused
	// players saved acceleration is used when the game is resumed
	float pauseX;

	/*
	 * Class declarations for essential game functions
	 */
	Player player;
	Missile missile;
	Background background;
	AnimatedBackground anim_background;
	Health health;
	ExtraHealth extrahealth;
	/*
	 * Classes used for power up text in bottom right corner of screen
	 */
	PowerTextRapid powertextrapid;
	PowerTextSpray powertextspray;
	PowerTextMulti powertextmulti;
	PowerTextAuto powertextauto;
	PowerTextSwiftness powertextswiftness;
	PowerTextBig powertextbig;

	// Determines whether the game is paused or active
	boolean paused;

	// Keeps track of the reload
	boolean recentlyFired = false;
	boolean recentlyFired_auto = false;

	// Keeps track of which way the player is currently accelerating
	boolean accelLeft;
	boolean accelRight;

	/*
	 * Preference Booleans
	 */
	boolean control_tilt;
	boolean control_button;
	boolean sound_on;
	boolean sound_off;

	/*
	 * Power-Up Booleans
	 */
	boolean rapidFire = false;
	boolean shotgun = false;
	boolean multiShot = false;
	boolean autoFire = false;
	boolean swiftnessPower = false;
	boolean bigAmmo = false;

	// Keeps track of the time the game started and ended
	long gameStart;

	// Auto Shot timer
	long autoShotStart;
	long autoShotEnd;
	long autoShotDur = (autoShotEnd - autoShotStart);

	// Rapid Fire timer
	long rapidFire_start;
	long rapidFire_end;
	long rapidFire_dur = (rapidFire_end - rapidFire_start);

	// Shotgun timer
	long shotgun_start;
	long shotgun_end;
	long shotgun_dur = (shotgun_end - shotgun_start);

	// Multi Shot timer
	long multiShot_start;
	long multiShot_end;
	long multiShot_dur = (multiShot_end - multiShot_start);

	// Auto Fire timer
	long autoFire_start;
	long autoFire_end;
	long autoFire_dur = (autoFire_end - autoFire_start);

	// Swiftness timer
	long swiftnessPower_start;
	long swiftnessPower_end;
	long swiftnessPower_dur = (swiftnessPower_end - swiftnessPower_start);

	// Big Ammo timer
	long bigAmmo_start;
	long bigAmmo_end;
	long bigAmmo_dur = (bigAmmo_end - bigAmmo_start);

	// Reload timer
	long startTime;
	long endTime;
	long duration = (endTime - startTime);

	// Reload auto shot timer
	long startTime_auto;
	long endTime_auto;
	long duration_auto = (endTime_auto - startTime_auto);

	// Enemy spawn timer
	long enemyStart;
	long enemyEnd;
	long enemyDur = (enemyEnd - enemyStart);

	// Power Up spawn timer
	long powerStart = System.nanoTime() / nano;
	long powerEnd;
	long powerDur = (powerEnd - powerStart);

	// pause duration
	long pauseTime;

	/*
	 * Arrays to keep track of individual enemies, missiles, and so on
	 */
	Array<Missile> missiles = new Array<Missile>();
	Array<Enemy> enemies = new Array<Enemy>();
	Array<Powerup> powerups = new Array<Powerup>();
	Array<Bullet> bullets = new Array<Bullet>();
	Array<Explosion> explosions = new Array<Explosion>();
	Array<Landmine> landmines = new Array<Landmine>();

	// Checks if accelerometer is available on current device
	boolean available = Gdx.input
			.isPeripheralAvailable(Peripheral.Accelerometer);

	/*
	 * GameScreen constructor
	 */
	public GameScreen(MyGame game) {

		this.game = game;

		camera = new OrthographicCamera();
		// Dimensions of the screen
		// Extra 100 is for banner ad
		camera.setToOrtho(true, 1920, 1180);

		/*
		 * Initialize classes
		 */
		batch = new SpriteBatch();
		touch = new Vector3();
		player = new Player();
		missile = new Missile();
		health = new Health();
		extrahealth = new ExtraHealth();
		powertextrapid = new PowerTextRapid();
		powertextspray = new PowerTextSpray();
		powertextmulti = new PowerTextMulti();
		powertextauto = new PowerTextAuto();
		powertextswiftness = new PowerTextSwiftness();
		powertextbig = new PowerTextBig();

		score = 0;
		yourScoreName = "0";
		font = new BitmapFont();
		font.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font.setScale(2, -2);

		stateTime = 0F;

		rand = new Random();
	}

	@Override
	public void show() {
		
		anim_background = new AnimatedBackground();

		/*
		 * Begin timers
		 */
		gameStart = System.nanoTime() / nano;
		enemyStart = System.nanoTime() / nano;
		powerStart = System.nanoTime() / nano;
		autoShotStart = System.nanoTime() / nano;
		healthRegen_start = System.nanoTime() / nano;
		score = 0;
		newHighScore = false;

		// Load current preferences
		prefs = Gdx.app.getPreferences("SpaceGame");

		/*
		 * Check if preferences are yet initialized Used if it is the very first
		 * time starting a game
		 */
		if (!prefs.contains("currency"))
			prefs.putInteger("currency", 0);

		if (!prefs.contains("highScore"))
			prefs.putInteger("highScore", 0);

		// OPTIONS
		if (!prefs.contains("sound_option"))
			prefs.putString("sound_option", "on");

		if (!prefs.contains("control_option"))
			prefs.putString("control_option", "tilt");

		// MISSILES
		if (!prefs.contains("mUpReload"))
			prefs.putInteger("mUpReload", 0);

		if (!prefs.contains("mUpAuto"))
			prefs.putInteger("mUpAuto", 0);

		// HEALTH
		if (!prefs.contains("hUpRegen"))
			prefs.putInteger("hUpRegen", 0);

		if (!prefs.contains("hUpHealth"))
			prefs.putInteger("hUpHealth", 0);

		// RAPID FIRE
		if (!prefs.contains("rapidUpDur"))
			prefs.putInteger("rapidUpDur", 0);

		if (!prefs.contains("rapidUpReload"))
			prefs.putInteger("rapidUpReload", 0);

		if (!prefs.contains("rapidUpReload"))
			prefs.putInteger("rapidUpReload", 0);

		// MULTI SHOT
		if (!prefs.contains("multiUpDur"))
			prefs.putInteger("multiUpDur", 0);

		if (!prefs.contains("multiUpAmount"))
			prefs.putInteger("multiUpAmount", 0);

		if (!prefs.contains("multiUpMirror"))
			prefs.putInteger("multiUpMirror", 0);

		// SHOTGUN
		if (!prefs.contains("shotgunUpDur"))
			prefs.putInteger("shotgunUpDur", 0);

		if (!prefs.contains("shotgunUpPierce"))
			prefs.putInteger("shotgunUpPierce", 0);

		if (!prefs.contains("shotgunUpLandmine"))
			prefs.putInteger("shotgunUpLandmine", 0);

		// GENERAL
		if (!prefs.contains("generalUpDur"))
			prefs.putInteger("generalUpDur", 0);

		if (!prefs.contains("generalUpExtra"))
			prefs.putInteger("generalUpExtra", 0);

		// Save
		prefs.flush();

		// Control Scheme Options
		if (prefs.getString("control_option").equals("tilt")) {
			control_tilt = true;
			control_button = false;
			background = new Background(Assets.sprite_back);
		} else {
			control_tilt = false;
			control_button = true;
			background = new Background(Assets.sprite_control_button_back);
		}

		// Load current high score
		highScore = getHighScore();
		yourHighScore = "" + highScore;

		// Reload Upgrade
		switch (prefs.getInteger("mUpReload")) {
		case 1:
			reloadDur = 47.5;
			break;
		case 2:
			reloadDur = 45;
			break;
		case 3:
			reloadDur = 42.5;
			break;
		case 4:
			reloadDur = 39;
			break;
		case 5:
			reloadDur = 35;
			break;
		}

		// Auto Shot Upgrade
		switch (prefs.getInteger("mUpAuto")) {
		case 1:
			autoShot = true;
			autoShotTime = 500;
			break;
		case 2:
			autoShot = true;
			autoShotTime = 400;
			break;
		case 3:
			autoShot = true;
			autoShotTime = 300;
			break;
		case 4:
			autoShot = true;
			autoShotTime = 200;
			break;
		case 5:
			autoShot = true;
			autoShotTime = 100;
			break;
		}

		// Health Regeneration
		switch (prefs.getInteger("hUpRegen")) {
		case 1:
			healthRegen = true;
			healthRegenTime = 2000;
			break;
		case 2:
			healthRegen = true;
			healthRegenTime = 1750;
			break;
		case 3:
			healthRegen = true;
			healthRegenTime = 1500;
			break;
		case 4:
			healthRegen = true;
			healthRegenTime = 1250;
			break;
		case 5:
			healthRegen = true;
			healthRegenTime = 1000;
			break;
		}

		// Extra Health
		switch (prefs.getInteger("hUpHealth")) {
		case 1:
			player_health = 6;
			break;
		case 2:
			player_health = 7;
			break;
		case 3:
			player_health = 8;
			break;
		case 4:
			player_health = 9;
			break;
		case 5:
			player_health = 10;
			break;
		}

		// Rapid Fire Duration
		switch (prefs.getInteger("rapidUpDur")) {
		case 1:
			rapidFireDuration = 1650;
			break;
		case 2:
			rapidFireDuration = 1800;
			break;
		case 3:
			rapidFireDuration = 1950;
			break;
		case 4:
			rapidFireDuration = 2100;
			break;
		case 5:
			rapidFireDuration = 2250;
			break;
		default:
			rapidFireDuration = 1500;
			break;
		}

		// Rapid Fire Reload
		switch (prefs.getInteger("rapidUpReload")) {
		case 1:
			rapidFireDur = 9.5;
			break;
		case 2:
			rapidFireDur = 9;
			break;
		case 3:
			rapidFireDur = 8.5;
			break;
		case 4:
			rapidFireDur = 8;
			break;
		case 5:
			rapidFireDur = 7.5;
			break;
		}

		// Ricochet Upgrade
		switch (prefs.getInteger("rapidUpRicochet")) {
		case 1:
			ricochet = true;
			ricochetChance = 1;
			break;
		case 2:
			ricochet = true;
			ricochetChance = 2;
			break;
		case 3:
			ricochet = true;
			ricochetChance = 3;
			break;
		case 4:
			ricochet = true;
			ricochetChance = 4;
			break;
		case 5:
			ricochet = true;
			ricochetChance = 5;
			break;
		}

		// Multi Shot Duration
		switch (prefs.getInteger("multiUpDur")) {
		case 1:
			multiShotDuration = 2587;
			break;
		case 2:
			multiShotDuration = 2925;
			break;
		case 3:
			multiShotDuration = 3262;
			break;
		case 4:
			multiShotDuration = 3600;
			break;
		case 5:
			multiShotDuration = 3937;
			break;
		default:
			multiShotDuration = 2250;
			break;
		}

		// Multi Shot missile amount
		switch (prefs.getInteger("multiUpAmount")) {
		case 1:
			multi_extra = true;
			multi_extra_missiles = 4;
			break;
		case 2:
			multi_extra = true;
			multi_extra_missiles = 5;
			break;
		case 3:
			multi_extra = true;
			multi_extra_missiles = 6;
			break;
		case 4:
			multi_extra = true;
			multi_extra_missiles = 7;
			break;
		case 5:
			multi_extra = true;
			multi_extra_missiles = 8;
			break;
		}

		// Mirror Image Upgrade
		switch (prefs.getInteger("multiUpMirror")) {
		case 1:
			mirror = true;
			mirrorChance = 1;
			break;
		case 2:
			mirror = true;
			mirrorChance = 2;
			break;
		case 3:
			mirror = true;
			mirrorChance = 3;
			break;
		case 4:
			mirror = true;
			mirrorChance = 4;
			break;
		case 5:
			mirror = true;
			mirrorChance = 5;
			break;
		}

		// Shotgun Duration
		switch (prefs.getInteger("shotgunUpDur")) {
		case 1:
			shotgunDuration = 1375;
			break;
		case 2:
			shotgunDuration = 1500;
			break;
		case 3:
			shotgunDuration = 1625;
			break;
		case 4:
			shotgunDuration = 1750;
			break;
		case 5:
			shotgunDuration = 1875;
			break;
		default:
			shotgunDuration = 1250;
			break;
		}

		// Shotgun piercing chance
		switch (prefs.getInteger("shotgunUpPierce")) {
		case 1:
			shotgun_pierce = true;
			shotgun_pierce_chance = 1;
			break;
		case 2:
			shotgun_pierce = true;
			shotgun_pierce_chance = 2;
			break;
		case 3:
			shotgun_pierce = true;
			shotgun_pierce_chance = 3;
			break;
		case 4:
			shotgun_pierce = true;
			shotgun_pierce_chance = 4;
			break;
		case 5:
			shotgun_pierce = true;
			shotgun_pierce_chance = 5;
			break;
		}

		// Landmine upgrade
		switch (prefs.getInteger("shotgunUpLandmine")) {
		case 1:
			landmine = true;
			landmineChance = 1;
			break;
		case 2:
			landmine = true;
			landmineChance = 2;
			break;
		case 3:
			landmine = true;
			landmineChance = 3;
			break;
		case 4:
			landmine = true;
			landmineChance = 4;
			break;
		case 5:
			landmine = true;
			landmineChance = 5;
			break;
		}

		// General powerup spawn timer
		switch (prefs.getInteger("genUpDur")) {
		case 1:
			powerup_spawn_timer = 900;
			break;
		case 2:
			powerup_spawn_timer = 800;
			break;
		case 3:
			powerup_spawn_timer = 700;
			break;
		case 4:
			powerup_spawn_timer = 600;
			break;
		case 5:
			powerup_spawn_timer = 500;
			break;
		default:
			powerup_spawn_timer = 1000;
			break;
		}

		// General chance to spawn extra powerup
		switch (prefs.getInteger("genUpExtra")) {
		case 1:
			extra_powerup = true;
			extra_powerup_chance = 1;
			break;
		case 2:
			extra_powerup = true;
			extra_powerup_chance = 2;
			break;
		case 3:
			extra_powerup = true;
			extra_powerup_chance = 3;
			break;
		case 4:
			extra_powerup = true;
			extra_powerup_chance = 4;
			break;
		case 5:
			extra_powerup = true;
			extra_powerup_chance = 5;
			break;
		default:
			extra_powerup = false;
			extra_powerup_chance = 0;
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		updateHealthBar();

		// Check if player has been killed
		if (player_health <= 0) {
			restart();
			game.game_screen.dispose();
			game.setScreen(game.gameOver_screen);
		}

		// If game is paused, enter this statement
		if (paused) {
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				resumeGame();
			}
			if (Gdx.input.isTouched()) {
				touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				// RESUME
				if ((touch.x >= 735 && touch.x <= 1185)
						&& (touch.y >= 315 && touch.y <= 415)) {
					resumeGame();
				}
				// EXIT
				if ((touch.x >= 735 && touch.x <= 1185)
						&& (touch.y >= 480 && touch.y <= 580)) {
					restart();
					paused = false;
					game.game_screen.dispose();
					game.setScreen(game.gameOver_screen);
				}
			}
		} else {

			// Update game differently depending on current device
			switch (Gdx.app.getType()) {
			case Android:
				generalUpdateAndroid(touch, camera);
				break;
			case Desktop:
				generalUpdate();
				break;
			case Applet:
				break;
			case HeadlessDesktop:
				break;
			case WebGL:
				break;
			case iOS:
				break;
			default:
				break;
			}
			// Check for any offscreen sprites
			checkOffScreen();
			// Check for any collisions between sprites
			checkCollision();
		}

		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		batch.draw(anim_background.image, anim_background.bounds.x, anim_background.bounds.y);
		//anim_background.bounds.y += 1;
		batch.draw(background.image, background.bounds.x, background.bounds.y);

		// Red Dotted line
		if (autoFire)
			batch.draw(Assets.sprite_auto_aim, player.bounds.x + 80,
					player.bounds.y - 1080);

		// Player
		batch.draw(player.image, player.bounds.x, player.bounds.y);

		// Mirror Image Side Ships
		if (mirrorTwins) {
			batch.draw(Assets.missile_twin, player.bounds.x - 150,
					player.bounds.y + 60);
			batch.draw(Assets.bullet_twin, player.bounds.x + 210,
					player.bounds.y + 60);
		}

		// Health Bar
		batch.draw(health.image, health.bounds.x, health.bounds.y);
		// Extra Health Bar
		if (player_health > 5)
			batch.draw(extrahealth.image, extrahealth.bounds.x,
					extrahealth.bounds.y);

		// Display Power-Up Text
		displayPowerText();
		// Display Score
		displayScore();
		drawAll();

		// If game is paused, draw sprites underneath the pause display
		if (paused) {

			drawAll();
			batch.draw(Assets.pause, 260, 80);
		}

		batch.end();

	}

	/*
	 * Function for updating game functions on desktop device
	 */
	public void generalUpdate() {

		checkDurations();

		checkAcceleration();
		updatePlayerSprite();
		checkDesktopInput();

		checkPlayerBounds();
		updatePlayerMovement();

	}

	/*
	 * Function for updating game functions on android device
	 */
	public void generalUpdateAndroid(Vector3 touch, OrthographicCamera camera) {

		checkDurations();
		checkAcceleration();

		updatePlayerSprite();

		if (control_tilt) {
			accelY = Gdx.input.getAccelerometerY();
			computeMovement(accelY);
		}
		if (control_button) {
			for (int i = 0; i < 4; i++) {
				if (Gdx.input.isTouched(i)) {
					touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touch);

					// MOVE LEFT
					if ((touch.x >= 20 && touch.x <= 308)
							&& (touch.y >= 915 && touch.y <= 1065)) {
						if (swiftnessPower)
							accelerationX -= 1.75;
						else
							accelerationX -= 1.5;
					}
					// MOVE RIGHT
					if ((touch.x >= 309 && touch.x <= 600)
							&& (touch.y >= 915 && touch.y <= 1066)) {
						if (swiftnessPower)
							accelerationX += 1.75;
						else
							accelerationX += 1.5;
					}
				}
			}
			updatePlayerMovement();
		}

		if (autoFire) {
			if (recentlyFired_auto) {
			} else {

				if (shotgun)
					bulletSound();
				else
					missileSound();

				startTime_auto = System.nanoTime() / nano;

				if (shotgun) {
					timesFired++;
					spawnBullets();
				} else {
					timesFired++;
					spawnMissiles();
				}

			}
		}

		for (int i = 0; i < 20; i++) {

			if (Gdx.input.isTouched(i)) {
				touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touch);
				// FIRE BUTTON
				if (control_tilt) {

					if (((touch.x >= 20 && touch.x <= 600) && (touch.y >= 915 && touch.y <= 1065))
							|| ((touch.x >= 1320 && touch.x <= 1900) && (touch.y >= 915 && touch.y <= 1065))) {
						if (recentlyFired) {
						} else {

							if (shotgun)
								bulletSound();
							else
								missileSound();

							startTime = System.nanoTime() / nano;

							if (shotgun) {
								timesFired++;
								spawnBullets();
							} else {
								timesFired++;
								spawnMissiles();
							}
						}
					}
				}
				if (control_button) {

					if (((touch.x >= 1320 && touch.x <= 1900) && (touch.y >= 915 && touch.y <= 1065))) {
						if (recentlyFired) {
						} else {

							if (shotgun)
								bulletSound();
							else
								missileSound();

							startTime = System.nanoTime() / nano;

							if (shotgun) {
								timesFired++;
								spawnBullets();
							} else {
								timesFired++;
								spawnMissiles();
							}
						}
					}
				}
				if ((touch.x >= 824 && touch.x <= 979)
						&& (touch.y >= 906 && touch.y <= 984)) {
					pauseGame();
				}
			}
		}

		checkPlayerBounds();

	}

	/*
	 * Checks all sprites if they are offscreen
	 */
	public void checkOffScreen() {

		for (Landmine l : getLandmines()) {
			landmineUpdate(l);
		}
		for (Missile m : getMissiles()) {
			if (m.checkEnd()) {
				missiles.removeValue(m, false);
			}
			missileUpdate(m);
		}
		for (Bullet b : getBullets()) {
			if (b.checkEnd()) {
				bullets.removeValue(b, false);
			}
			bulletUpdate(b);
		}
		for (Enemy e : getEnemies()) {
			if (e.checkEnd()) {
				enemies.removeValue(e, false);
				healthUsed++;
				player_health--;
			}
			enemyUpdate(e);
		}
		for (Powerup p : getPowerups()) {
			if (p.checkEnd()) {
				powerups.removeValue(p, false);
			}
			powerUpdate(p);
		}
		if (explosionBool) {
			for (Explosion e : explosions) {
				updateExplosions(e.bounds.x, e.bounds.y);
				e.timer++;
				if (e.checkEnd())
					explosions.removeValue(e, false);
			}
		}
	}

	/*
	 * Checks for all collisions between all sprites
	 */
	public void checkCollision() {
		for (Missile m : missiles) {
			for (Enemy e : enemies) {
				if (Intersector.overlaps(e.Cbounds, m.bounds)) {

					if (e.normal) {
						score++;
						yourScoreName = "" + score;
						explosionSound(m.bounds.x, m.bounds.y);
						missiles.removeValue(m, false);
						enemies.removeValue(e, false);
					}
					if (e.gold) {
						score += 15;
						yourScoreName = "" + score;
						enemiesKilled++;
						explosionSound(m.bounds.x, m.bounds.y);
						missiles.removeValue(m, false);
						enemies.removeValue(e, false);
					}
					if (e.strong) {
						e.health--;
						explosionSound(m.bounds.x, m.bounds.y);
						missiles.removeValue(m, false);
						if (e.health <= 0) {
							score += 8;
							yourScoreName = "" + score;
							enemiesKilled++;
							explosionSound(m.bounds.x, m.bounds.y);
							enemies.removeValue(e, false);
						}
					}

					// If ricochet is active
					if (rapidFire) {
						if (ricochet) {
							int chance = rand.nextInt(100) + 1;

							switch (ricochetChance) {
							case 1:
								if (chance <= 10)
									spawnRicochet(m.bounds);
								break;
							case 2:
								if (chance <= 20)
									spawnRicochet(m.bounds);
								break;
							case 3:
								if (chance <= 30)
									spawnRicochet(m.bounds);
								break;
							case 4:
								if (chance <= 40)
									spawnRicochet(m.bounds);
								break;
							case 5:
								if (chance <= 50)
									spawnRicochet(m.bounds);
								break;
							}
						}
					}
				}
			}
			for (Powerup p : powerups) {
				if (Intersector.overlaps(p.Cbounds, m.bounds)) {
					// POWERUP
					if (p.image == Assets.powerup_rapid) {
						Assets.powerup1.play();
						rapidFire = true;
						rapidFire_start = System.nanoTime() / nano;

						missiles.removeValue(m, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_spray) {
						Assets.powerup2.play();
						shotgun = true;
						shotgun_start = System.nanoTime() / nano;

						missiles.removeValue(m, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_multi) {
						Assets.powerup4.play();
						multiShot = true;
						if (mirror) {
							int chance = rand.nextInt(100) + 1;

							switch (mirrorChance) {
							case 1:
								if (chance <= 20)
									mirrorTwins = true;
								break;
							case 2:
								if (chance <= 40)
									mirrorTwins = true;
								break;
							case 3:
								if (chance <= 60)
									mirrorTwins = true;
								break;
							case 4:
								if (chance <= 80)
									mirrorTwins = true;
								break;
							case 5:
								mirrorTwins = true;
							}
						}
						multiShot_start = System.nanoTime() / nano;

						missiles.removeValue(m, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_life) {
						Assets.heal1.play();
						player_health++;

						missiles.removeValue(m, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_auto) {
						Assets.powerup5.play();

						autoFire = true;
						autoFire_start = System.nanoTime() / nano;

						missiles.removeValue(m, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_swiftness) {
						Assets.powerup6.play();

						swiftnessPower = true;
						swiftnessPower_start = System.nanoTime() / nano;

						missiles.removeValue(m, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_big) {
						Assets.powerup7.play();

						bigAmmo = true;
						bigAmmo_start = System.nanoTime() / nano;

						missiles.removeValue(m, false);
						powerups.removeValue(p, false);
					}
				}
				if (Intersector.overlaps(p.Cbounds, player.bounds)) {
					// POWERUP
					if (p.image == Assets.powerup_rapid) {
						Assets.powerup1.play();
						rapidFire = true;
						rapidFire_start = System.nanoTime() / nano;

						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_spray) {
						Assets.powerup2.play();
						shotgun = true;
						shotgun_start = System.nanoTime() / nano;

						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_multi) {
						Assets.powerup4.play();
						multiShot = true;
						multiShot_start = System.nanoTime() / nano;

						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_life) {
						Assets.heal1.play();
						player_health++;

						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_auto) {
						Assets.powerup5.play();

						autoFire = true;
						autoFire_start = System.nanoTime() / nano;

						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_swiftness) {
						Assets.powerup6.play();

						swiftnessPower = true;
						swiftnessPower_start = System.nanoTime() / nano;

						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_big) {
						Assets.powerup7.play();

						bigAmmo = true;
						bigAmmo_start = System.nanoTime() / nano;

						powerups.removeValue(p, false);
					}
				}
			}
		}
		for (Bullet b : bullets) {
			for (Enemy e : enemies) {
				if (Intersector.overlaps(e.Cbounds, b.bounds)) {

					if (e.normal) {
						score++;
						yourScoreName = "" + score;
						enemiesKilled++;
						explosionSound(b.bounds.x, b.bounds.y);
						if (shotgun_pierce) {
							int chance = rand.nextInt(100) + 1;
							switch (shotgun_pierce_chance) {
							case 5:
								break;
							case 4:
								if (chance <= 20)
									bullets.removeValue(b, false);
								break;
							case 3:
								if (chance <= 40)
									bullets.removeValue(b, false);
								break;
							case 2:
								if (chance <= 60)
									bullets.removeValue(b, false);
								break;
							case 1:
								if (chance <= 80)
									bullets.removeValue(b, false);
								break;
							}

						} else
							bullets.removeValue(b, false);
						enemies.removeValue(e, false);
						if (landmine) {
							int chance = rand.nextInt(100) + 1;
							switch (landmineChance) {
							case 1:
								if (chance <= 10)
									landmines.add(new Landmine(b.bounds.x,
											b.bounds.y));
								break;
							case 2:
								if (chance <= 20)
									landmines.add(new Landmine(b.bounds.x,
											b.bounds.y));
								break;
							case 3:
								if (chance <= 25)
									landmines.add(new Landmine(b.bounds.x,
											b.bounds.y));
								break;
							case 4:
								if (chance <= 30)
									landmines.add(new Landmine(b.bounds.x,
											b.bounds.y));
								break;
							case 5:
								if (chance <= 35)
									landmines.add(new Landmine(b.bounds.x,
											b.bounds.y));
								break;
							}

						}
					}
					if (e.gold) {
						score += 15;
						yourScoreName = "" + score;
						enemiesKilled++;
						explosionSound(b.bounds.x, b.bounds.y);
						if (shotgun_pierce) {
							int chance = rand.nextInt(100) + 1;
							switch (shotgun_pierce_chance) {
							case 5:
								break;
							case 4:
								if (chance <= 20)
									bullets.removeValue(b, false);
								break;
							case 3:
								if (chance <= 40)
									bullets.removeValue(b, false);
								break;
							case 2:
								if (chance <= 60)
									bullets.removeValue(b, false);
								break;
							case 1:
								if (chance <= 80)
									bullets.removeValue(b, false);
								break;
							}

						} else
							bullets.removeValue(b, false);
						enemies.removeValue(e, false);
						if (landmine) {
							int chance = rand.nextInt(100) + 1;
							switch (landmineChance) {
							case 1:
								if (chance <= 10)
									landmines.add(new Landmine(b.bounds.x,
											b.bounds.y));
								break;
							case 2:
								if (chance <= 20)
									landmines.add(new Landmine(b.bounds.x,
											b.bounds.y));
								break;
							case 3:
								if (chance <= 25)
									landmines.add(new Landmine(b.bounds.x,
											b.bounds.y));
								break;
							case 4:
								if (chance <= 30)
									landmines.add(new Landmine(b.bounds.x,
											b.bounds.y));
								break;
							case 5:
								if (chance <= 35)
									landmines.add(new Landmine(b.bounds.x,
											b.bounds.y));
								break;
							}

						}
					}
					if (e.strong) {
						e.health--;
						explosionSound(b.bounds.x, b.bounds.y);
						if (shotgun_pierce) {
							int chance = rand.nextInt(100) + 1;
							switch (shotgun_pierce_chance) {
							case 5:
								break;
							case 4:
								if (chance <= 20)
									bullets.removeValue(b, false);
								break;
							case 3:
								if (chance <= 40)
									bullets.removeValue(b, false);
								break;
							case 2:
								if (chance <= 60)
									bullets.removeValue(b, false);
								break;
							case 1:
								if (chance <= 80)
									bullets.removeValue(b, false);
								break;
							}

						} else
							bullets.removeValue(b, false);
						if (e.health <= 0) {
							score += 8;
							yourScoreName = "" + score;
							enemiesKilled++;
							explosionSound(b.bounds.x, b.bounds.y);
							enemies.removeValue(e, false);
							if (landmine) {
								int chance = rand.nextInt(100) + 1;
								switch (landmineChance) {
								case 1:
									if (chance <= 10)
										landmines.add(new Landmine(b.bounds.x,
												b.bounds.y));
									break;
								case 2:
									if (chance <= 20)
										landmines.add(new Landmine(b.bounds.x,
												b.bounds.y));
									break;
								case 3:
									if (chance <= 25)
										landmines.add(new Landmine(b.bounds.x,
												b.bounds.y));
									break;
								case 4:
									if (chance <= 30)
										landmines.add(new Landmine(b.bounds.x,
												b.bounds.y));
									break;
								case 5:
									if (chance <= 35)
										landmines.add(new Landmine(b.bounds.x,
												b.bounds.y));
									break;
								}

							}
						}
					}

					if (rapidFire) {
						if (ricochet) {
							int chance = rand.nextInt(100) + 1;

							switch (ricochetChance) {
							case 1:
								if (chance <= 10)
									spawnRicochet(b.bounds);
								break;
							case 2:
								if (chance <= 20)
									spawnRicochet(b.bounds);
								break;
							case 3:
								if (chance <= 30)
									spawnRicochet(b.bounds);
								break;
							case 4:
								if (chance <= 40)
									spawnRicochet(b.bounds);
								break;
							case 5:
								if (chance <= 50)
									spawnRicochet(b.bounds);
								break;
							}
						}
					}
				}
			}
			for (Powerup p : powerups) {
				if (Intersector.overlaps(p.Cbounds, b.bounds)) {
					// POWERUP
					if (p.image == Assets.powerup_rapid) {
						Assets.powerup1.play();
						rapidFire = true;
						rapidFire_start = System.nanoTime() / nano;

						bullets.removeValue(b, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_spray) {
						Assets.powerup2.play();
						shotgun = true;
						shotgun_start = System.nanoTime() / nano;

						bullets.removeValue(b, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_multi) {
						Assets.powerup4.play();
						multiShot = true;
						multiShot_start = System.nanoTime() / nano;

						bullets.removeValue(b, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_life) {
						Assets.heal1.play();
						player_health++;

						bullets.removeValue(b, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_auto) {
						Assets.powerup5.play();
						autoFire = true;
						autoFire_start = System.nanoTime() / nano;

						bullets.removeValue(b, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_swiftness) {
						Assets.powerup6.play();
						swiftnessPower = true;
						swiftnessPower_start = System.nanoTime() / nano;

						bullets.removeValue(b, false);
						powerups.removeValue(p, false);
					}
					if (p.image == Assets.powerup_big) {
						Assets.powerup7.play();
						bigAmmo = true;
						bigAmmo_start = System.nanoTime() / nano;

						bullets.removeValue(b, false);
						powerups.removeValue(p, false);
					}
				}
			}
		}
		for (Landmine l : landmines) {
			for (Enemy e : enemies) {
				if (Intersector.overlaps(e.Cbounds, l.Cbounds)) {

					if (e.normal) {
						score++;
						yourScoreName = "" + score;
						explosionSound(l.Cbounds.x, l.Cbounds.y);
						landmines.removeValue(l, false);
						enemies.removeValue(e, false);
					}
					if (e.gold) {
						score += 15;
						yourScoreName = "" + score;
						enemiesKilled++;
						explosionSound(l.Cbounds.x, l.Cbounds.y);
						landmines.removeValue(l, false);
						enemies.removeValue(e, false);
					}
					if (e.strong) {
						e.health--;
						explosionSound(l.Cbounds.x, l.Cbounds.y);
						landmines.removeValue(l, false);
						if (e.health <= 0) {
							score += 8;
							yourScoreName = "" + score;
							enemiesKilled++;
							explosionSound(l.Cbounds.x, l.Cbounds.y);
							enemies.removeValue(e, false);
						}
					}
				}
			}
		}
	}

	/*
	 * Updates movement of missiles
	 */
	public void missileUpdate(Missile missile) {
		missile.bounds.y -= 15;

		batch.begin();

		batch.draw(missile.image, missile.bounds.x, missile.bounds.y);

		batch.end();
	}

	/*
	 * Updates movement of bullets
	 */
	public void bulletUpdate(Bullet bullet) {
		bullet.bounds.y -= bullet.yUpdate;
		bullet.bounds.x -= bullet.xUpdate;

		batch.begin();

		batch.draw(bullet.image, bullet.bounds.x, bullet.bounds.y);

		batch.end();
	}

	/*
	 * Updates movement of enemies
	 */
	public void enemyUpdate(Enemy enemy) {
		batch.begin();

		if (enemy.normal) {
			enemy.Cbounds.y += 3.25;
			batch.draw(enemy.image, enemy.Cbounds.x - (float) 37.5,
					enemy.Cbounds.y - (float) 37.5);
		}
		if (enemy.gold) {
			enemy.Cbounds.y += 5;
			batch.draw(enemy.image, enemy.Cbounds.x - (float) 70,
					enemy.Cbounds.y - (float) 70);
		}
		if (enemy.strong) {
			enemy.Cbounds.y += 2;
			batch.draw(enemy.image, enemy.Cbounds.x - (float) 100,
					enemy.Cbounds.y - (float) 100);
		}

		batch.end();
	}

	/*
	 * Updates movement of the powerups
	 */
	public void powerUpdate(Powerup powerup) {
		powerup.Cbounds.y += 2.5;

		batch.begin();

		batch.draw(powerup.image, powerup.Cbounds.x - 75,
				powerup.Cbounds.y - 85);

		batch.end();
	}

	public void landmineUpdate(Landmine landmine) {
		batch.begin();

		batch.draw(landmine.image, landmine.Cbounds.x - 30,
				landmine.Cbounds.y - 30);

		batch.end();
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

	private Array<Missile> getMissiles() {
		Array<Missile> ret = new Array<Missile>();
		for (Missile m : missiles)
			if (m instanceof Missile)
				ret.add((Missile) m);
		return ret;
	}

	private Array<Bullet> getBullets() {
		Array<Bullet> ret = new Array<Bullet>();
		for (Bullet b : bullets)
			if (b instanceof Bullet)
				ret.add((Bullet) b);
		return ret;
	}

	private Array<Enemy> getEnemies() {
		Array<Enemy> ret = new Array<Enemy>();
		for (Enemy e : enemies)
			if (e instanceof Enemy)
				ret.add((Enemy) e);
		return ret;
	}

	private Array<Powerup> getPowerups() {
		Array<Powerup> ret = new Array<Powerup>();
		for (Powerup p : powerups)
			if (p instanceof Powerup)
				ret.add((Powerup) p);
		return ret;
	}

	private Array<Landmine> getLandmines() {
		Array<Landmine> ret = new Array<Landmine>();
		for (Landmine l : landmines)
			if (l instanceof Landmine)
				ret.add((Landmine) l);
		return ret;
	}

	/*
	 * Draws all missiles, bullets, enemies, and powerups
	 */
	public void drawAll() {

		for (Missile m : missiles) {
			batch.draw(m.image, m.bounds.x, m.bounds.y);
		}
		for (Bullet b : bullets) {
			batch.draw(b.image, b.bounds.x, b.bounds.y);
		}
		for (Enemy e : enemies) {
			if (e.normal)
				batch.draw(e.image, e.Cbounds.x - (float) 37.5, e.Cbounds.y
						- (float) 37.5);
			if (e.gold)
				batch.draw(e.image, e.Cbounds.x - (float) 70, e.Cbounds.y
						- (float) 70);
			if (e.strong)
				batch.draw(e.image, e.Cbounds.x - (float) 100, e.Cbounds.y
						- (float) 100);
		}
		for (Powerup p : powerups) {
			batch.draw(p.image, p.Cbounds.x - 75, p.Cbounds.y - 85);
		}
		for (Landmine l : landmines) {
			batch.draw(l.image, l.Cbounds.x - 30, l.Cbounds.y - 30);
		}
	}

	/*
	 * Updates player health with the correct image
	 */
	public void updateHealthBar() {

		if (player_health >= 10)
			player_health = 10;
		switch (player_health) {
		case 10:
			extrahealth.image = Assets.extrahealth_5;
			health.image = Assets.health_5;
			break;
		case 9:
			extrahealth.image = Assets.extrahealth_4;
			health.image = Assets.health_5;
			break;
		case 8:
			extrahealth.image = Assets.extrahealth_3;
			health.image = Assets.health_5;
			break;
		case 7:
			extrahealth.image = Assets.extrahealth_2;
			health.image = Assets.health_5;
			break;
		case 6:
			extrahealth.image = Assets.extrahealth_1;
			health.image = Assets.health_5;
		case 5:
			health.image = Assets.health_5;
			break;
		case 4:
			health.image = Assets.health_4;
			break;
		case 3:
			health.image = Assets.health_3;
			break;
		case 2:
			health.image = Assets.health_2;
			break;
		case 1:
			health.image = Assets.health_1;
			break;
		}
	}

	/*
	 * Updates player sprite depending on their acceleration
	 * 
	 * Meant to give a turning animation for the player
	 */
	public void updatePlayerSprite() {

		if (accelerationX >= 0 && accelerationX <= 2)
			player.image = Assets.sprite_0normal;
		if (accelerationX <= 0 && accelerationX >= -2)
			player.image = Assets.sprite_0normal;

		else if (accelerationX < -2 && accelerationX >= -4)
			player.image = Assets.sprite_9left;
		else if (accelerationX < -4 && accelerationX >= -6)
			player.image = Assets.sprite_18left;
		else if (accelerationX < -6 && accelerationX >= -8)
			player.image = Assets.sprite_27left;
		else if (accelerationX < -8 && accelerationX >= -10)
			player.image = Assets.sprite_36left;
		else if (accelerationX < -10)
			player.image = Assets.sprite_45left;

		else if (accelerationX > 2 && accelerationX <= 4)
			player.image = Assets.sprite_9right;
		else if (accelerationX > 4 && accelerationX <= 6)
			player.image = Assets.sprite_18right;
		else if (accelerationX > 6 && accelerationX <= 8)
			player.image = Assets.sprite_27right;
		else if (accelerationX > 8 && accelerationX <= 10)
			player.image = Assets.sprite_36right;
		else if (accelerationX > 10)
			player.image = Assets.sprite_45right;
	}

	// Receives an integer and maps it to the String highScore in prefs
	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	// Retrieves the current high score
	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	/*
	 * Restarts the game
	 * 
	 * Called when the player manually exits or loses all of their health
	 */
	public void restart() {

		if (score > getHighScore()) {
			newHighScore = true;
			setHighScore(score);
		}

		MenuScreen.setCurrency(score * 2);

		coinsEarned = score * 2;
		long gameEnd = System.nanoTime() / nano;
		timeElapsed = (int) (gameEnd - gameStart) / 100;

		player_health = 5;
		enemySpawnRate = 150;
		accelerationX = 0;
		yourScoreName = "0";

		rapidFire = false;
		shotgun = false;
		multiShot = false;
		autoFire = false;
		swiftnessPower = false;
		bigAmmo = false;

		player.bounds.x = 880;
		player.bounds.y = 740;

		for (Missile m : getMissiles()) {
			missiles.removeValue(m, false);
		}
		for (Bullet b : getBullets()) {
			bullets.removeValue(b, false);
		}
		for (Enemy e : getEnemies()) {
			enemies.removeValue(e, false);
		}
		for (Powerup p : getPowerups()) {
			powerups.removeValue(p, false);
		}
		for (Landmine l : getLandmines()) {
			landmines.removeValue(l, false);
		}

	}

	/*
	 * Checks all ongoing timers and looks for an timers that have gone over
	 * their alloted limit and sets the corresponding booleans to false
	 */
	public void checkDurations() {

		/*
		 * Adjust the spawn rate of enemies as the score gets higher
		 */
		if (score < 50)
			enemySpawnRate = 150;
		else if (score >= 50 && score < 100)
			enemySpawnRate = 130;
		else if (score >= 100 && score < 125)
			enemySpawnRate = 110;
		else if (score >= 125 && score < 150)
			enemySpawnRate = 100;
		else if (score >= 150 && score < 200)
			enemySpawnRate = 95;
		else if (score >= 200 && score < 250)
			enemySpawnRate = 90;
		else if (score >= 250 && score < 300)
			enemySpawnRate = 85;
		else if (score >= 300 && score < 350)
			enemySpawnRate = 80;
		else if (score >= 350 && score < 400)
			enemySpawnRate = 75;
		else if (score >= 400 && score < 450)
			enemySpawnRate = 70;
		else if (score >= 450 && score < 500)
			enemySpawnRate = 65;
		else if (score >= 500)
			enemySpawnRate = 60;

		if (healthRegen) {
			healthRegen_end = System.nanoTime() / nano;
			healthRegen_dur = (healthRegen_end - healthRegen_start);
			if (healthRegen_dur >= healthRegenTime) {
				player_health++;
				Assets.heal1.play();
				healthRegen_start = healthRegen_end;
			}
		}

		autoShotEnd = System.nanoTime() / nano;
		autoShotDur = (autoShotEnd - autoShotStart);
		if (autoShotDur >= autoShotTime) {
			if (autoShot) {
				if (shotgun)
					spawnBullets();
				else
					spawnMissiles();
			}
			autoShotStart = autoShotEnd;
		}

		rapidFire_end = System.nanoTime() / nano;
		rapidFire_dur = (rapidFire_end - rapidFire_start);
		if (rapidFire_dur >= rapidFireDuration) {
			rapidFire = false;
		}

		shotgun_end = System.nanoTime() / nano;
		shotgun_dur = (shotgun_end - shotgun_start);
		if (shotgun_dur >= shotgunDuration) {
			shotgun = false;
		}

		multiShot_end = System.nanoTime() / nano;
		multiShot_dur = (multiShot_end - multiShot_start);
		if (multiShot_dur >= multiShotDuration) {
			multiShot = false;
			mirrorTwins = false;
		}

		autoFire_end = System.nanoTime() / nano;
		autoFire_dur = (autoFire_end - autoFire_start);
		if (autoFire_dur >= 1500) {
			autoFire = false;
		}

		swiftnessPower_end = System.nanoTime() / nano;
		swiftnessPower_dur = (swiftnessPower_end - swiftnessPower_start);
		if (swiftnessPower_dur >= 1500) {
			swiftnessPower = false;
		}

		bigAmmo_end = System.nanoTime() / nano;
		bigAmmo_dur = (bigAmmo_end - bigAmmo_start);
		if (bigAmmo_dur >= 2250) {
			bigAmmo = false;
		}

		powerEnd = System.nanoTime() / nano;
		powerDur = powerEnd - powerStart;
		// Spawn new powerup
		if (powerDur >= powerup_spawn_timer) {
			int randomNum = rand.nextInt(1752) + 52;
			int randomPower = rand.nextInt(7) + 1;
			powerups.add(new Powerup(randomNum, randomPower));
			if (extra_powerup) {
					int chance = rand.nextInt(100) + 1;
					switch (extra_powerup_chance) {
					case 1:
						if (chance <= 15){
							randomNum = rand.nextInt(1752) + 52;
							randomPower = rand.nextInt(7) + 1;
							powerups.add(new Powerup(randomNum, randomPower));
						}
						break;
					case 2:
						if (chance <= 30){
							randomNum = rand.nextInt(1752) + 52;
							randomPower = rand.nextInt(7) + 1;
							powerups.add(new Powerup(randomNum, randomPower));
						}
						break;
					case 3:
						if (chance <= 45){
							randomNum = rand.nextInt(1752) + 52;
							randomPower = rand.nextInt(7) + 1;
							powerups.add(new Powerup(randomNum, randomPower));
						}
						break;
					case 4:
						if (chance <= 60){
							randomNum = rand.nextInt(1752) + 52;
							randomPower = rand.nextInt(7) + 1;
							powerups.add(new Powerup(randomNum, randomPower));
						}
						break;
					case 5:
						if (chance <= 75){
							randomNum = rand.nextInt(1752) + 52;
							randomPower = rand.nextInt(7) + 1;
							powerups.add(new Powerup(randomNum, randomPower));
						}
						break;
					}
				}
			powerStart = powerEnd;
		}

		enemyEnd = System.nanoTime() / nano;
		enemyDur = enemyEnd - enemyStart;
		// Spawn new enemy
		if (enemyDur >= enemySpawnRate) {
			int spawnArea = rand.nextInt(1752) + 52;
			int chooseEnemy = rand.nextInt(20) + 1;
			enemies.add(new Enemy(spawnArea, chooseEnemy));
			enemyStart = enemyEnd;
		}

		endTime = System.nanoTime() / nano;
		duration = endTime - startTime;
		if (rapidFire) {
			if (duration >= rapidFireDur)
				recentlyFired = false;
			else
				recentlyFired = true;
		} else if (duration >= reloadDur) {
			recentlyFired = false;
		} else if (duration < reloadDur) {
			recentlyFired = true;
		}

		endTime_auto = System.nanoTime() / nano;
		duration_auto = endTime_auto - startTime_auto;
		if (rapidFire) {
			if (duration_auto >= rapidFireDur)
				recentlyFired_auto = false;
			else
				recentlyFired_auto = true;
		} else if (duration_auto >= reloadDur_auto) {
			recentlyFired_auto = false;
		} else if (duration_auto < reloadDur_auto) {
			recentlyFired_auto = true;
		}
	}

	/*
	 * This function handles all input for the Desktop version of the game
	 */
	public void checkDesktopInput() {

		if (Gdx.input.isKeyPressed(Keys.A)) {
			// player.bounds.x -= accelerationX;
			accelerationX -= 1.5;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			// player.bounds.x += accelerationX;
			accelerationX += 1.5;
		}

		if (autoFire) {
			if (recentlyFired_auto) {
			} else {

				if (shotgun)
					bulletSound();
				else
					missileSound();

				startTime_auto = System.nanoTime() / nano;

				if (shotgun) {
					spawnBullets();
				} else {
					spawnMissiles();
				}

			}
		}
		if (Gdx.input.isKeyPressed(Keys.F)
				|| Gdx.input.isButtonPressed(Buttons.LEFT)) {
			if (recentlyFired) {
			} else {

				if (shotgun)
					bulletSound();
				else
					missileSound();

				startTime = System.nanoTime() / nano;

				if (shotgun) {
					spawnBullets();
				} else {
					spawnMissiles();
				}

			}
		}

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			pauseGame();
		}
	}

	/*
	 * This function will check the x and y acceleration of the player and set
	 * booleans accordingly
	 * 
	 * I cant remember if this function is even needed anymore and havent gotten
	 * around to commenting it out and seeing if it still runs ok Oh well
	 */
	public void checkAcceleration() {

		if (accelerationX > 0.0) {
			accelRight = true;
			accelLeft = false;
		}
		if (accelerationX < 0.0) {
			accelRight = false;
			accelLeft = true;
		}
		if (accelerationX == 0.0) {
			accelRight = false;
			accelLeft = false;
		}

	}

	/*
	 * This function handles the spawning of bullets, which occur during a
	 * SHOTGUN powerup
	 */
	public void spawnBullets() {

		if (bigAmmo) {
			if (multiShot) {
				if (multi_extra) {
					switch (multi_extra_missiles) {
					case 4:
						bullets.add(new Bullet(player.bounds.x + 13,
								player.bounds.y + 32, -5, 7, true));
						break;
					case 5:
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -5, 7, true));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -1, 13, true));
						break;
					case 6:
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -5, 7, true));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -1, 13, true));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 1, 13, true));
						break;
					case 7:
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -5, 7, true));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -1, 13, true));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 1, 13, true));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 5, 7, true));
						break;
					case 8:
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -5, 7, true));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -1, 13, true));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 1, 13, true));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 5, 7, true));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 5, 8, true));
						break;
					}
				}
				bullets.add(new Bullet(player.bounds.x + 27,
						player.bounds.y + 32, -6, 5, true));
				bullets.add(new Bullet(player.bounds.x + 27,
						player.bounds.y + 32, -3, 10, true));
				bullets.add(new Bullet(player.bounds.x + 27,
						player.bounds.y + 32, 0, 15, true));
				bullets.add(new Bullet(player.bounds.x + 27,
						player.bounds.y + 32, 3, 10, true));
				bullets.add(new Bullet(player.bounds.x + 27,
						player.bounds.y + 32, 6, 5, true));

				bullets.add(new Bullet(player.bounds.x - 27,
						player.bounds.y + 32, -6, 5, true));
				bullets.add(new Bullet(player.bounds.x - 27,
						player.bounds.y + 32, -3, 10, true));
				bullets.add(new Bullet(player.bounds.x - 27,
						player.bounds.y + 32, 0, 15, true));
				bullets.add(new Bullet(player.bounds.x - 27,
						player.bounds.y + 32, 3, 10, true));
				bullets.add(new Bullet(player.bounds.x - 27,
						player.bounds.y + 32, 6, 5, true));
			}
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, -6, 5,
					true));
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, -3, 10,
					true));
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, 0, 15,
					true));
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, 3, 10,
					true));
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, 6, 5, true));
		} else {

			if (multiShot) {
				if (multi_extra) {
					switch (multi_extra_missiles) {
					case 4:
						bullets.add(new Bullet(player.bounds.x + 13,
								player.bounds.y + 32, -5, 7));
						break;
					case 5:
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -5, 7));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -1, 13));
						break;
					case 6:
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -5, 7));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -1, 13));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 1, 13));
						break;
					case 7:
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -5, 7));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -1, 13));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 1, 13));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 5, 7));
						break;
					case 8:
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -5, 7));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, -1, 13));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 1, 13));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 5, 7));
						bullets.add(new Bullet(player.bounds.x + 27,
								player.bounds.y + 32, 5, 8));
						break;
					}
				}
				bullets.add(new Bullet(player.bounds.x + 27,
						player.bounds.y + 32, -6, 5));
				bullets.add(new Bullet(player.bounds.x + 27,
						player.bounds.y + 32, -3, 10));
				bullets.add(new Bullet(player.bounds.x + 27,
						player.bounds.y + 32, 0, 15));
				bullets.add(new Bullet(player.bounds.x + 27,
						player.bounds.y + 32, 3, 10));
				bullets.add(new Bullet(player.bounds.x + 27,
						player.bounds.y + 32, 6, 5));

				bullets.add(new Bullet(player.bounds.x - 27,
						player.bounds.y + 32, -6, 5));
				bullets.add(new Bullet(player.bounds.x - 27,
						player.bounds.y + 32, -3, 10));
				bullets.add(new Bullet(player.bounds.x - 27,
						player.bounds.y + 32, 0, 15));
				bullets.add(new Bullet(player.bounds.x - 27,
						player.bounds.y + 32, 3, 10));
				bullets.add(new Bullet(player.bounds.x - 27,
						player.bounds.y + 32, 6, 5));
			}
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, -6, 5));
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, -3, 10));
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, 0, 15));
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, 3, 10));
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, 6, 5));
		}

		if (mirrorTwins) {

			missiles.add(new Missile(player.bounds.x - 160,
					player.bounds.y + 80));
			missiles.add(new Missile(player.bounds.x - 200,
					player.bounds.y + 80));
			missiles.add(new Missile(player.bounds.x - 180,
					player.bounds.y + 60));

			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 70,
					-6, 5));
			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 70,
					-3, 10));
			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 63,
					0, 15));
			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 70,
					3, 10));
			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 70,
					6, 5));
		}
	}

	/*
	 * This function will spawn missiles at the correct location in relation to
	 * the sprite This function is called whenever the user fires, or whenever
	 * the fire action may be called
	 */
	public void spawnMissiles() {

		if (bigAmmo) {
			if (multiShot) {
				if (multi_extra) {
					switch (multi_extra_missiles) {
					case 4:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 32, true));
						break;
					case 5:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 32, true));
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 32, true));
						break;
					case 6:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 32, true));
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 32, true));
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 32, true));
						break;
					case 7:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 32, true));
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 32, true));
						missiles.add(new Missile(player.bounds.x + 50,
								player.bounds.y + 32, true));
						missiles.add(new Missile(player.bounds.x - 63,
								player.bounds.y + 32, true));
						break;
					case 8:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 32, true));
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 32, true));
						missiles.add(new Missile(player.bounds.x + 50,
								player.bounds.y + 32, true));
						missiles.add(new Missile(player.bounds.x - 63,
								player.bounds.y + 32, true));
						missiles.add(new Missile(player.bounds.x - 63,
								player.bounds.y + 32, true));
						break;
					}
				}
				missiles.add(new Missile(player.bounds.x + 27,
						player.bounds.y + 32, true));
				missiles.add(new Missile(player.bounds.x - 27,
						player.bounds.y + 32, true));
			}
			missiles.add(new Missile(player.bounds.x, player.bounds.y, true));
		} else {

			if (multiShot) {
				if (multi_extra) {
					switch (multi_extra_missiles) {
					case 4:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 18));
						break;
					case 5:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 18));
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 62));
						break;
					case 6:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 18));
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 62));
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 62));
						break;
					case 7:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 18));
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 62));
						missiles.add(new Missile(player.bounds.x + 50,
								player.bounds.y + 62));
						missiles.add(new Missile(player.bounds.x - 63,
								player.bounds.y + 95));
						break;
					case 8:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 18));
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 62));
						missiles.add(new Missile(player.bounds.x + 50,
								player.bounds.y + 62));
						missiles.add(new Missile(player.bounds.x - 63,
								player.bounds.y + 95));
						missiles.add(new Missile(player.bounds.x - 63,
								player.bounds.y + 95));
						break;
					}
				}
				missiles.add(new Missile(player.bounds.x + 27,
						player.bounds.y + 30));
				missiles.add(new Missile(player.bounds.x - 27,
						player.bounds.y + 30));
			}
			missiles.add(new Missile(player.bounds.x, player.bounds.y));
		}

		if (mirrorTwins) {

			missiles.add(new Missile(player.bounds.x - 160,
					player.bounds.y + 80));
			missiles.add(new Missile(player.bounds.x - 200,
					player.bounds.y + 80));
			missiles.add(new Missile(player.bounds.x - 180,
					player.bounds.y + 60));

			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 70,
					-6, 5));
			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 70,
					-3, 10));
			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 63,
					0, 15));
			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 70,
					3, 10));
			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 70,
					6, 5));
		}
	}

	/*
	 * Spawns a ricochet explosion
	 */
	public void spawnRicochet(Rectangle bounds) {

		bullets.add(new Bullet(bounds.x, bounds.y, -8, -8));
		bullets.add(new Bullet(bounds.x, bounds.y, 8, -8));
		bullets.add(new Bullet(bounds.x, bounds.y, 8, 8));
		bullets.add(new Bullet(bounds.x, bounds.y, -8, 8));

		bullets.add(new Bullet(bounds.x, bounds.y, 0, -12));
		bullets.add(new Bullet(bounds.x, bounds.y, 0, -12));
		bullets.add(new Bullet(bounds.x, bounds.y, 12, 0));
		bullets.add(new Bullet(bounds.x, bounds.y, -12, 0));

		bullets.add(new Bullet(bounds.x, bounds.y, -4, -10));
		bullets.add(new Bullet(bounds.x, bounds.y, 4, -10));
		bullets.add(new Bullet(bounds.x, bounds.y, 10, 4));
		bullets.add(new Bullet(bounds.x, bounds.y, -10, 4));

		bullets.add(new Bullet(bounds.x, bounds.y, -10, -4));
		bullets.add(new Bullet(bounds.x, bounds.y, 10, -4));
		bullets.add(new Bullet(bounds.x, bounds.y, 4, 10));
		bullets.add(new Bullet(bounds.x, bounds.y, -4, 10));
	}

	/*
	 * This function is responsible for pausing the game and handling all
	 * variables that are important and would otherwise be affected by a pause
	 * Specifically, durations of spawn timers and powerups need to be handled
	 */
	public void pauseGame() {

		paused = true;
		accelLeft = false;
		accelRight = false;
		pauseX = accelerationX;
		accelerationX = 0;
		touch.x = 0;
		touch.y = 0;

		pauseTime = System.nanoTime() / nano;
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This function is responsible for handling all variables when the player
	 * decides to resume the game from a paused state Specifically, the
	 * durations of spawn timers and powerups need to be handled so the player
	 * is not overwhelmed upon resuming and so powerups do not run out during
	 * the pause
	 */
	public void resumeGame() {

		paused = false;

		accelerationX = pauseX;

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long resumeTime = System.nanoTime() / nano;
		long pauseDiff = resumeTime - pauseTime;
		rapidFire_start += pauseDiff;
		shotgun_start += pauseDiff;
		multiShot_start += pauseDiff;
		autoFire_start += pauseDiff;
		swiftnessPower_start += pauseDiff;
		bigAmmo_start += pauseDiff;
		startTime += pauseDiff;
		enemyStart += pauseDiff;
		powerStart += pauseDiff;
		autoShotStart += pauseDiff;
		gameStart += pauseDiff;
	}

	/*
	 * This function is responsible for notifying the player of what powerups
	 * they currently possess In the UI, it is located next to the right firing
	 * button and can currently hold 6 powerup messages
	 */
	public void displayPowerText() {

		if (rapidFire)
			batch.draw(powertextrapid.image, powertextrapid.bounds.x,
					powertextrapid.bounds.y);
		if (shotgun)
			batch.draw(powertextspray.image, powertextspray.bounds.x,
					powertextspray.bounds.y);
		if (multiShot)
			batch.draw(powertextmulti.image, powertextmulti.bounds.x,
					powertextmulti.bounds.y);
		if (autoFire)
			batch.draw(powertextauto.image, powertextauto.bounds.x,
					powertextauto.bounds.y);
		if (swiftnessPower)
			batch.draw(powertextswiftness.image, powertextswiftness.bounds.x,
					powertextswiftness.bounds.y);
		if (bigAmmo)
			batch.draw(powertextbig.image, powertextbig.bounds.x,
					powertextbig.bounds.y);
	}

	/*
	 * Displays both the score of the current session and the overall highscore
	 * the player has achieved
	 */
	public void displayScore() {

		Assets.font24.setColor(Color.WHITE);
		Assets.font24.draw(batch, yourScoreName, 693, 910);
		Assets.font24.draw(batch, yourHighScore, 743, 940);
	}

	/*
	 * This function will check if the player is off screen Essentially, this
	 * creates a boundary around the screen so the player cannot leave for any
	 * reason All vertices are checked even though the player can only move left
	 * and right in order to fully ensure his entrapment
	 */
	public void checkPlayerBounds() {

		if (player.bounds.getX() < 0.0) {
			if (accelerationX < 0)
				accelerationX = 0;
			player.bounds.setPosition(0, player.bounds.getY());
		}
		if (player.bounds.getX() > 1760.0) {
			if (accelerationX > 0)
				accelerationX = 0;
			player.bounds.setPosition(1760, player.bounds.getY());
		}
	}

	/*
	 * Updates player movement when using the button-control option
	 */
	public void updatePlayerMovement() {

		player.bounds.x += accelerationX;

		if (swiftnessPower) {
			if (accelRight)
				accelerationX -= 0.33;
			if (accelLeft)
				accelerationX += 0.33;
		} else {
			if (accelRight)
				accelerationX -= 0.5;
			if (accelLeft)
				accelerationX += 0.5;
		}
	}

	/*
	 * Computes the movement of the player when using the tilt-control option
	 */
	public void computeMovement(float Y) {

		double factor = 1;
		if (swiftnessPower)
			factor = 1.25;

		if (Y > 0 && Y <= 0.25) {
			player.bounds.x += 0.9 * factor;
		}
		if (Y > 0.25 && Y <= 0.50) {
			player.bounds.x += 1.9 * factor;
		}
		if (Y > 0.50 && Y <= 0.75) {
			player.bounds.x += 2.9 * factor;
		}
		if (Y > 0.75 && Y <= 1.00) {
			player.bounds.x += 3.9 * factor;
		}
		if (Y > 1.00 && Y <= 1.25) {
			player.bounds.x += 4.9 * factor;
		}
		if (Y > 1.25 && Y <= 1.50) {
			player.bounds.x += 5.9 * factor;
		}
		if (Y > 1.50 && Y <= 1.75) {
			player.bounds.x += 6.4 * factor;
		}
		if (Y > 1.75 && Y <= 2.00) {
			player.bounds.x += 6.9 * factor;
		}
		if (Y > 2.00 && Y <= 2.25) {
			player.bounds.x += 7.4 * factor;
		}
		if (Y > 2.25 && Y <= 2.50) {
			player.bounds.x += 7.9 * factor;
		}
		if (Y > 2.50 && Y <= 2.75) {
			player.bounds.x += 8.4 * factor;
		}
		if (Y > 2.75 && Y <= 3.00) {
			player.bounds.x += 15.9 * factor;
		}
		if (Y > 3.00 && Y <= 3.25) {
			player.bounds.x += 18.9 * factor;
		}
		if (Y > 3.25) {
			player.bounds.x += 25.9 * factor;
		}

		if (Y < 0 && Y >= -0.25) {
			player.bounds.x -= 0.9 * factor;
		}
		if (Y < -0.25 && Y >= -0.50) {
			player.bounds.x -= 1.9 * factor;
		}
		if (Y < -0.50 && Y >= -0.75) {
			player.bounds.x -= 2.9 * factor;
		}
		if (Y < -0.75 && Y >= -1.00) {
			player.bounds.x -= 3.9 * factor;
		}
		if (Y < -1.00 && Y >= -1.25) {
			player.bounds.x -= 4.9 * factor;
		}
		if (Y < -1.25 && Y >= -1.50) {
			player.bounds.x -= 5.9 * factor;
		}
		if (Y < -1.50 && Y >= -1.75) {
			player.bounds.x -= 6.4 * factor;
		}
		if (Y < -1.75 && Y >= -2.00) {
			player.bounds.x -= 6.9 * factor;
		}
		if (Y < -2.00 && Y >= -2.25) {
			player.bounds.x -= 7.4 * factor;
		}
		if (Y < -2.25 && Y >= -2.50) {
			player.bounds.x -= 7.9 * factor;
		}
		if (Y < -2.50 && Y >= -2.75) {
			player.bounds.x -= 8.4 * factor;
		}
		if (Y < -2.75 && Y >= -3.00) {
			player.bounds.x -= 15.9 * factor;
		}
		if (Y < -3.00 && Y >= -3.25) {
			player.bounds.x -= 19.9 * factor;
		}
		if (Y < -3.25) {
			player.bounds.x -= 25.9 * factor;
		}

	}

	/*
	 * Plays a random explosion sound
	 */
	public void explosionSound(float x, float y) {

		explosionBool = true;
		explosions.add(new Explosion(x, y));

		int randomNum = rand.nextInt(6) + 1;

		switch (randomNum) {
		case 1:
			Assets.explosion1.play();
			break;
		case 2:
			Assets.explosion2.play();
			break;
		case 3:
			Assets.explosion3.play();
			break;
		case 4:
			Assets.explosion4.play();
			break;
		case 5:
			Assets.explosion5.play();
			break;
		case 6:
			Assets.explosion6.play();
			break;
		}

	}

	/*
	 * Plays a random bullet firing sound
	 */
	public void bulletSound() {

		int randomNum = rand.nextInt(6) + 1;

		switch (randomNum) {
		case 1:
			Assets.bullet4.play();
			break;
		case 2:
			Assets.bullet5.play();
			break;
		case 3:
			Assets.bullet6.play();
			break;
		case 4:
			Assets.bullet7.play();
			break;
		case 5:
			Assets.bullet8.play();
			break;
		case 6:
			Assets.bullet9.play();
			break;
		}

	}

	/*
	 * Plays a random missile firing sound
	 */
	public void missileSound() {

		int randomNum = rand.nextInt(6) + 1;

		switch (randomNum) {
		case 1:
			Assets.fire1.play();
			break;
		case 2:
			Assets.fire2.play();
			break;
		case 3:
			Assets.fire3.play();
			break;
		case 4:
			Assets.fire4.play();
			break;
		case 5:
			Assets.fire5.play();
			break;
		case 6:
			Assets.fire6.play();
			break;
		}
	}

	/*
	 * Updates all ongoing explosion aniamtions
	 */
	public void updateExplosions(float x, float y) {

		stateTime += Gdx.graphics.getDeltaTime();
		Assets.current_frame = Assets.explosion_animation.getKeyFrame(
				stateTime, true);
		batch.begin();

		batch.draw(Assets.current_frame, x, y);

		batch.end();

	}

}
