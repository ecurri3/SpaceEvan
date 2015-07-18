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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.gameData.*;

public class GameScreen implements Screen {

	GameData gameData;
	GameTimers gameTimers;
	PlayerData playerData;

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

	private MyGame game;
	OrthographicCamera camera;
	SpriteBatch batch;
	Vector3 touch;

	// EXPERIMENTAL
	private Stage stage;
	private TextureAtlas buttonsAtlas; // ** image of buttons **//
	private Skin buttonSkin; // ** images are used as skins of the button **//
	private TextButton fireButton, leftButton, rightButton, pauseButton; // **
																			// the
																			// button
																			// -
																			// the
																			// only
																			// actor
																			// in
																			// program
	// **//
	boolean fireButtonBool, leftButtonBool, rightButtonBool, pauseButtonBool = false;

	float rotation;

	float stateTime;

	Random rand;

	// Base enemy spawn rate
	int enemySpawnRate;
	int collisionCounter = 0;

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
	UserInterface userInterface;
	AnimatedBackground anim_background;
	Health health;
	ExtraHealth extrahealth;

	/*
	 * Classes used for power up text in bottom right corner of screen
	 */
	PowerText powerText;

	// Determines whether the game is paused or active
	boolean paused;

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
		powerText = new PowerText();

		score = 0;
		yourScoreName = "0";

		stateTime = 0F;

		rand = new Random();
	}

	@Override
	public void show() {

		gameData = new GameData();
		gameTimers = new GameTimers();
		playerData = new PlayerData(gameData, gameTimers);

		anim_background = new AnimatedBackground();

		score = 0;
		newHighScore = false;

		// Control Scheme Options
		if (PlayerData.prefs.getString("control_option").equals("tilt")) {
			control_tilt = true;
			control_button = false;
			userInterface = new UserInterface(Assets.sprite_back);
		} else {
			control_tilt = false;
			control_button = true;
			userInterface = new UserInterface(Assets.sprite_control_button_back);
		}

		// Load current high score
		highScore = getHighScore();
		yourHighScore = "" + highScore;

		playerData.checkPrefs();
		playerData.checkPowerUpPrefs();

		stage = new Stage(new StretchViewport(1920, 1080)); // ** window is
															// stage **//
		stage.clear();
		Gdx.input.setInputProcessor(stage); // ** stage is responsive **//

		fireButton = userInterface.createButton(stage, "fireButtonDown", "fireButtonUp", "FB_down.png", "FB_up.png", Assets.font12, 1315, 100, 150, 590);
		fireButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				fireButtonBool = true;
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				fireButtonBool = false;
			}
		});
		stage.addActor(fireButton);
		
		leftButton = userInterface.createButton(stage, "leftButtonDown", "leftButtonUp", "LB_down.png", "LB_up.png", Assets.font12, 20, 105, 140, 290);
		leftButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				leftButtonBool = true;
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				leftButtonBool = false;
			}
		});
		stage.addActor(leftButton);
		
		rightButton = userInterface.createButton(stage, "rightButtonDown", "rightButtonUp", "RB_down.png", "RB_up.png", Assets.font12, 310, 105, 140, 290);
		rightButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				rightButtonBool = true;
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				rightButtonBool = false;
			}
		});
		stage.addActor(rightButton);
		
		pauseButton = userInterface.createButton(stage, "pauseButtonDown", "pauseButtonUp", "PB_down.png", "PB_up.png", Assets.font12, 825, 180, 70, 155);
		pauseButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				pauseGame();
				return true;
			}
		});
		stage.addActor(pauseButton);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		updateHealthBar();

		// Check if player has been killed
		if (gameData.player_health <= 0) {
			restart();
			game.game_screen.dispose();
			game.setScreen(game.gameOver_screen);
		}

		batch.setProjectionMatrix(camera.combined);

		stage.act();
		batch.begin();

		anim_background.draw(batch);

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
			default:
				break;
			}
			// Check for any offscreen sprites
			checkOffScreen();
			// Check for any collisions between sprites
			new Thread(new Runnable() {
				@Override
				public void run() {
					// time to post on the main thread!
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							// this will be run on the application listener
							// thread
							// before the next call to
							// ApplicationListener#render()
							checkCollision();
						}
					});
				}
			}).start();
		}

		// Red Dotted line
		if (gameData.autoFire)
			batch.draw(Assets.sprite_auto_aim, player.bounds.x + 80,
					player.bounds.y - 1080);

		// Player
		player.drawR(batch, rotation, gameData);

		drawAll();

		// If game is paused, draw sprites underneath the pause display
		if (paused) {
			batch.draw(Assets.pause, 260, 80);
			displayScore();
		}

		userInterface.draw(batch);

		// Health Bar
		health.draw(batch, gameData);

		// Display Power-Up Text
		powerText.draw(batch, gameData);
		// Display Score
		displayScore();

		stage.draw();
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
		
		double factor = 1.0;
		if(gameData.swiftnessPower)
			factor = 1.15;

		if (control_tilt) {
			accelY = Gdx.input.getAccelerometerY();
			computeMovement(accelY);
		}
		if (control_button) {
			if(leftButtonBool)
				accelerationX -= 1.5 * factor;
			if(rightButtonBool)
				accelerationX += 1.5 * factor;
			updatePlayerMovement();
		}

		if (gameData.autoFire) {
			if (!gameTimers.recentlyFired_auto) {
				gameTimers.startTime_auto = gameTimers.getNanoTime();
				fireProjectile();
			}
		}

		if (fireButtonBool) {
			if (!gameTimers.recentlyFired) {
				gameTimers.startTime = gameTimers.getNanoTime();
				gameTimers.recentlyFired = true;
				fireProjectile();
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
				gameData.player_health--;
			}
			enemyUpdate(e);
		}
		for (Powerup p : getPowerups()) {
			if (p.checkEnd()) {
				powerups.removeValue(p, false);
			}
			powerUpdate(p);
		}
		if (gameData.explosionBool) {
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

					e.health--;
					if (e.health <= 0) {
						if (e.normal)
							score++;
						if (e.gold)
							score += 15;
						if (e.strong)
							score += 8;
						enemies.removeValue(e, false);
					}
					missiles.removeValue(m, false);
					explosionSound(m.bounds.x, m.bounds.y);

					// If ricochet is active
					if (gameData.rapidFire) {
						if (gameData.ricochet) {
							int chance = rand.nextInt(100) + 1;
							if (chance <= (gameData.ricochetChance * 10))
								spawnRicochet(m.bounds);
						}
					}
				}
			}
			for (Powerup p : powerups) {
				if (Intersector.overlaps(p.Cbounds, m.bounds)) {
					// POWERUP
					p.executePowerUp(gameData, gameTimers, p.numType);
					missiles.removeValue(m, false);
					powerups.removeValue(p, false);
				}
				if (Intersector.overlaps(p.Cbounds, player.bounds)) {
					// POWERUP
					p.executePowerUp(gameData, gameTimers, p.numType);
				}
			}
		}
		for (Bullet b : bullets) {
			for (Enemy e : enemies) {
				if (Intersector.overlaps(e.Cbounds, b.bounds)) {

					e.health--;
					if (e.health <= 0) {
						if (e.normal)
							score++;
						if (e.gold)
							score += 15;
						if (e.strong)
							score += 8;
						enemies.removeValue(e, false);
					}
					explosionSound(b.bounds.x, b.bounds.y);

					if (gameData.shotgun_pierce) {
						int chance = rand.nextInt(100) + 1;
						if (chance <= (100 - (20 * gameData.shotgun_pierce_chance)))
							bullets.removeValue(b, false);

					} else
						bullets.removeValue(b, false);

					if (gameData.landmine) {
						int chance = rand.nextInt(100) + 1;
						if (chance <= (10 + (gameData.landmineChance * 5)))
							landmines.add(new Landmine(b.bounds.x, b.bounds.y));
					}

					if (gameData.rapidFire) {
						if (gameData.ricochet) {
							int chance = rand.nextInt(100) + 1;
							if (chance <= (gameData.ricochetChance * 10))
								spawnRicochet(b.bounds);
						}
					}
				}
			}
			for (Powerup p : powerups) {
				if (Intersector.overlaps(p.Cbounds, b.bounds)) {
					// POWERUP
					p.executePowerUp(gameData, gameTimers, p.numType);
					bullets.removeValue(b, false);
					powerups.removeValue(p, false);
				}
			}
		}
		for (Landmine l : landmines) {
			for (Enemy e : enemies) {
				if (Intersector.overlaps(e.Cbounds, l.Cbounds)) {

					e.health--;
					if (e.health <= 0) {
						if (e.normal)
							score++;
						if (e.gold)
							score += 15;
						if (e.strong)
							score += 8;
						enemies.removeValue(e, false);
					}
					explosionSound(l.Cbounds.x, l.Cbounds.y);
					landmines.removeValue(l, false);

				}
			}
		}
	}

	/*
	 * Updates movement of missiles
	 */
	public void missileUpdate(Missile missile) {
		missile.bounds.y -= 25;

		batch.draw(missile.image, missile.bounds.x, missile.bounds.y);
	}

	/*
	 * Updates movement of bullets
	 */
	public void bulletUpdate(Bullet bullet) {
		bullet.bounds.y -= bullet.yUpdate;
		bullet.bounds.x -= bullet.xUpdate;

		batch.draw(bullet.image, bullet.bounds.x, bullet.bounds.y);
	}

	/*
	 * Updates movement of enemies
	 */
	public void enemyUpdate(Enemy enemy) {

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
	}

	/*
	 * Updates movement of the powerups
	 */
	public void powerUpdate(Powerup powerup) {
		powerup.Cbounds.y += 2.5;

		batch.draw(powerup.image, powerup.Cbounds.x - 75,
				powerup.Cbounds.y - 85);
	}

	public void landmineUpdate(Landmine landmine) {

		batch.draw(landmine.image, landmine.Cbounds.x - 30,
				landmine.Cbounds.y - 30);
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
		health.setHealth(gameData.player_health, gameData);
	}

	/*
	 * Updates player sprite depending on their acceleration
	 * 
	 * Meant to give a turning animation for the player
	 */
	public void updatePlayerSprite() {
		rotation = (accelerationX) * 4.5f;
	}

	// Receives an integer and maps it to the String highScore in prefs
	public static void setHighScore(int val) {
		PlayerData.prefs.putInteger("highScore", val);
		PlayerData.prefs.flush();
	}

	// Retrieves the current high score
	public static int getHighScore() {
		return PlayerData.prefs.getInteger("highScore");
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
		long gameEnd = gameTimers.getNanoTime();
		timeElapsed = (int) (gameEnd - gameTimers.gameStart) / 100;

		gameData.player_health = 5;
		enemySpawnRate = 150;
		accelerationX = 0;
		yourScoreName = "0";

		gameData.rapidFire = false;
		gameData.shotgun = false;
		gameData.multiShot = false;
		gameData.autoFire = false;
		gameData.swiftnessPower = false;
		gameData.bigAmmo = false;

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
		enemySpawnRate = 200 - ((score / 25) * 5);
		if (enemySpawnRate < 50)
			enemySpawnRate = 50;

		// USE THIS MAYBE
		// enemySpawnRate = ((200 - ((score/25)*5)) >= 50) ? 200 -
		// ((score/25)*5) : 50;

		if (gameData.healthRegen) {
			gameTimers.healthRegen_end = gameTimers.getNanoTime();
			gameTimers.healthRegen_dur = (gameTimers.healthRegen_end - gameTimers.healthRegen_start);
			if (gameTimers.healthRegen_dur >= gameTimers.healthRegenTime) {
				gameData.player_health++;
				Assets.heal1.play();
				gameTimers.healthRegen_start = gameTimers.healthRegen_end;
			}
		}

		gameTimers.autoShotEnd = gameTimers.getNanoTime();
		gameTimers.autoShotDur = (gameTimers.autoShotEnd - gameTimers.autoShotStart);
		if (gameTimers.autoShotDur >= gameTimers.autoShotTime) {
			if (gameData.autoShot) {
				if (gameData.shotgun)
					spawnBullets();
				else
					spawnMissiles();
			}
			gameTimers.autoShotStart = gameTimers.autoShotEnd;
		}

		gameTimers.rapidFire_end = gameTimers.getNanoTime();
		gameTimers.rapidFire_dur = (gameTimers.rapidFire_end - gameTimers.rapidFire_start);
		if (gameTimers.rapidFire_dur >= gameTimers.rapidFireDuration) {
			gameData.rapidFire = false;
		}

		gameTimers.shotgun_end = gameTimers.getNanoTime();
		gameTimers.shotgun_dur = (gameTimers.shotgun_end - gameTimers.shotgun_start);
		if (gameTimers.shotgun_dur >= gameTimers.shotgunDuration) {
			gameData.shotgun = false;
		}

		gameTimers.multiShot_end = gameTimers.getNanoTime();
		gameTimers.multiShot_dur = (gameTimers.multiShot_end - gameTimers.multiShot_start);
		if (gameTimers.multiShot_dur >= gameTimers.multiShotDuration) {
			gameData.multiShot = false;
			gameData.mirrorTwins = false;
		}

		gameTimers.autoFire_end = gameTimers.getNanoTime();
		gameTimers.autoFire_dur = (gameTimers.autoFire_end - gameTimers.autoFire_start);
		if (gameTimers.autoFire_dur >= 1500) {
			gameData.autoFire = false;
		}

		gameTimers.swiftnessPower_end = gameTimers.getNanoTime();
		gameTimers.swiftnessPower_dur = (gameTimers.swiftnessPower_end - gameTimers.swiftnessPower_start);
		if (gameTimers.swiftnessPower_dur >= 1500) {
			gameData.swiftnessPower = false;
		}

		gameTimers.bigAmmo_end = gameTimers.getNanoTime();
		gameTimers.bigAmmo_dur = (gameTimers.bigAmmo_end - gameTimers.bigAmmo_start);
		if (gameTimers.bigAmmo_dur >= 2250) {
			gameData.bigAmmo = false;
		}

		gameTimers.powerEnd = gameTimers.getNanoTime();
		gameTimers.powerDur = gameTimers.powerEnd - gameTimers.powerStart;
		// Spawn new powerup
		if (gameTimers.powerDur >= gameTimers.powerup_spawn_timer) {
			int randomNum = rand.nextInt(1752) + 52;
			int randomPower = rand.nextInt(7) + 1;
			powerups.add(new Powerup(randomNum, randomPower));
			if (gameData.extra_powerup) {
				int chance = rand.nextInt(100) + 1;
				if (chance <= (gameData.extra_powerup_chance * 15)) {
					randomNum = rand.nextInt(1752) + 52;
					randomPower = rand.nextInt(7) + 1;
					powerups.add(new Powerup(randomNum, randomPower));
				}
			}
			gameTimers.powerStart = gameTimers.powerEnd;
		}

		gameTimers.enemyEnd = gameTimers.getNanoTime();
		gameTimers.enemyDur = gameTimers.enemyEnd - gameTimers.enemyStart;
		// Spawn new enemy
		if (gameTimers.enemyDur >= enemySpawnRate) {
			int spawnArea = rand.nextInt(1752) + 52;
			int chooseEnemy = rand.nextInt(20) + 1;
			enemies.add(new Enemy(spawnArea, chooseEnemy));
			gameTimers.enemyStart = gameTimers.enemyEnd;
		}

		gameTimers.endTime = gameTimers.getNanoTime();
		gameTimers.duration = gameTimers.endTime - gameTimers.startTime;
		if (gameData.rapidFire) {
			if (gameTimers.duration >= gameTimers.rapidFireDur)
				gameTimers.recentlyFired = false;
			else
				gameTimers.recentlyFired = true;
		} else if (gameTimers.duration >= gameTimers.reloadDur) {
			gameTimers.recentlyFired = false;
		} else if (gameTimers.duration < gameTimers.reloadDur) {
			gameTimers.recentlyFired = true;
		}

		gameTimers.endTime_auto = gameTimers.getNanoTime();
		gameTimers.duration_auto = gameTimers.endTime_auto
				- gameTimers.startTime_auto;
		if (gameData.rapidFire) {
			if (gameTimers.duration_auto >= gameTimers.rapidFireDur)
				gameTimers.recentlyFired_auto = false;
			else
				gameTimers.recentlyFired_auto = true;
		} else if (gameTimers.duration_auto >= gameTimers.reloadDur_auto) {
			gameTimers.recentlyFired_auto = false;
		} else if (gameTimers.duration_auto < gameTimers.reloadDur_auto) {
			gameTimers.recentlyFired_auto = true;
		}
	}

	/*
	 * This function handles all input for the Desktop version of the game
	 */
	public void checkDesktopInput() {
		
		double factor = 1.0;
		if(gameData.swiftnessPower)
			factor = 1.5;

		if (Gdx.input.isKeyPressed(Keys.A)) {
			accelerationX -= 1.5 * factor;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			accelerationX += 1.5 * factor;
		}

		if (gameData.autoFire) {
			if (gameTimers.recentlyFired_auto) {
			} else {
				gameTimers.startTime_auto = gameTimers.getNanoTime();
				fireProjectile();

			}
		}
		if (Gdx.input.isKeyPressed(Keys.F)
				|| Gdx.input.isButtonPressed(Buttons.LEFT)) {
			if (gameTimers.recentlyFired) {
			} else {
				gameTimers.startTime = gameTimers.getNanoTime();
				gameTimers.recentlyFired = true;
				fireProjectile();

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

		accelRight = false;
		accelLeft = false;

		if (accelerationX > 0.0)
			accelRight = true;
		else if (accelerationX < 0.0)
			accelLeft = true;

	}

	public void fireProjectile() {

		timesFired++;
		if (gameData.shotgun)
			spawnBullets();
		else
			spawnMissiles();

		if (gameData.mirrorTwins) {
			missiles.add(new Missile(player.bounds.x - 160,
					player.bounds.y + 80, false));
			missiles.add(new Missile(player.bounds.x - 200,
					player.bounds.y + 80, false));
			missiles.add(new Missile(player.bounds.x - 180,
					player.bounds.y + 60, false));

			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 70,
					-6, 20, false));
			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 63,
					0, 30, false));
			bullets.add(new Bullet(player.bounds.x + 180, player.bounds.y + 70,
					6, 20, false));
		}
	}

	/*
	 * This function handles the spawning of bullets, which occur during a
	 * SHOTGUN powerup
	 */
	public void spawnBullets() {

		// check for big ammo, if true, use big bullets
		// spawn 3 bullets
		// check for multi shot, if true, spawn 2 more
		bulletSound();
		boolean bigBullets = false;

		if (gameData.bigAmmo)
			bigBullets = true;

		bullets.add(new Bullet(player.bounds.x, player.bounds.y, -6, 20,
				bigBullets));
		bullets.add(new Bullet(player.bounds.x, player.bounds.y, 0, 30,
				bigBullets));
		bullets.add(new Bullet(player.bounds.x, player.bounds.y, 6, 20,
				bigBullets));

		if (gameData.multiShot) {
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, -12, 10,
					bigBullets));
			bullets.add(new Bullet(player.bounds.x, player.bounds.y, 12, 10,
					bigBullets));
		}
	}

	/*
	 * This function will spawn missiles at the correct location in relation to
	 * the sprite This function is called whenever the user fires, or whenever
	 * the fire action may be called
	 */
	public void spawnMissiles() {

		missileSound();
		boolean bigMissile = false;

		if (gameData.bigAmmo)
			bigMissile = true;

		missiles.add(new Missile(player.bounds.x, player.bounds.y, bigMissile));

		if (gameData.multiShot) {

			missiles.add(new Missile(player.bounds.x + 27,
					player.bounds.y + 32, bigMissile));
			missiles.add(new Missile(player.bounds.x - 27,
					player.bounds.y + 32, bigMissile));

			if (gameData.multi_extra) {

				for (int i = 4; i < gameData.multi_extra_missiles; i++) {
					switch (i) {
					case 4:
						missiles.add(new Missile(player.bounds.x + 13,
								player.bounds.y + 32, bigMissile));
						break;
					case 5:
						missiles.add(new Missile(player.bounds.x - 50,
								player.bounds.y + 32, bigMissile));
						break;
					case 6:
						missiles.add(new Missile(player.bounds.x + 50,
								player.bounds.y + 32, bigMissile));
						break;
					case 7:
						missiles.add(new Missile(player.bounds.x - 63,
								player.bounds.y + 32, bigMissile));
						break;
					case 8:
						missiles.add(new Missile(player.bounds.x + 63,
								player.bounds.y + 32, bigMissile));
						break;
					}
				}
			}
		}
	}

	/*
	 * Spawns a ricochet explosion
	 */
	public void spawnRicochet(Rectangle bounds) {

		bullets.add(new Bullet(bounds.x, bounds.y, -16, -16));
		bullets.add(new Bullet(bounds.x, bounds.y, 16, -16));
		bullets.add(new Bullet(bounds.x, bounds.y, 16, 16));
		bullets.add(new Bullet(bounds.x, bounds.y, -16, 16));

		bullets.add(new Bullet(bounds.x, bounds.y, 0, -24));
		bullets.add(new Bullet(bounds.x, bounds.y, 0, -24));
		bullets.add(new Bullet(bounds.x, bounds.y, 24, 0));
		bullets.add(new Bullet(bounds.x, bounds.y, -24, 0));

		bullets.add(new Bullet(bounds.x, bounds.y, -8, -20));
		bullets.add(new Bullet(bounds.x, bounds.y, 8, -20));
		bullets.add(new Bullet(bounds.x, bounds.y, 20, 8));
		bullets.add(new Bullet(bounds.x, bounds.y, -20, 8));

		bullets.add(new Bullet(bounds.x, bounds.y, -20, -8));
		bullets.add(new Bullet(bounds.x, bounds.y, 20, -8));
		bullets.add(new Bullet(bounds.x, bounds.y, 8, 20));
		bullets.add(new Bullet(bounds.x, bounds.y, -8, 20));
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

		gameTimers.pauseTime = gameTimers.getNanoTime();
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
		long resumeTime = gameTimers.getNanoTime();
		long pauseDiff = resumeTime - gameTimers.pauseTime;
		gameTimers.rapidFire_start += pauseDiff;
		gameTimers.shotgun_start += pauseDiff;
		gameTimers.multiShot_start += pauseDiff;
		gameTimers.autoFire_start += pauseDiff;
		gameTimers.swiftnessPower_start += pauseDiff;
		gameTimers.bigAmmo_start += pauseDiff;
		gameTimers.startTime += pauseDiff;
		gameTimers.enemyStart += pauseDiff;
		gameTimers.powerStart += pauseDiff;
		gameTimers.autoShotStart += pauseDiff;
		gameTimers.gameStart += pauseDiff;
	}

	/*
	 * Displays both the score of the current session and the overall highscore
	 * the player has achieved
	 */
	public void displayScore() {

		yourScoreName = "" + score;
		Assets.font24.setColor(Color.WHITE);
		Assets.font36.setColor(Color.WHITE);
		Assets.font24.draw(batch, yourScoreName, 693, 910);
		Assets.font36.draw(batch, yourHighScore, 743, 940);
		Assets.font48.draw(batch, "poop", 2000, 325);
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
		double factor = 1;
		if (gameData.swiftnessPower)
			factor = 1.50;

		if (accelRight)
			accelerationX -= 0.33 * factor;
		if (accelLeft)
			accelerationX += 0.33 * factor;
	}

	/*
	 * Computes the movement of the player when using the tilt-control option
	 */
	public void computeMovement(float Y) {

		double factor = 1;
		if (gameData.swiftnessPower)
			factor = 1.25;

		player.bounds.x += 0.9 + ((Y / 0.25) * factor);

	}

	/*
	 * Plays a random explosion sound
	 */
	public void explosionSound(float x, float y) {

		gameData.explosionBool = true;
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

		batch.draw(Assets.current_frame, x, y);

	}

}
