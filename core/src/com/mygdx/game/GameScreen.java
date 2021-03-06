package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
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
	private TextButton fireButton, leftButton, rightButton, pauseButton;
	// **//
	boolean fireButtonBool, leftButtonBool, rightButtonBool,
			pauseButtonBool = false;

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

	/*
	 * Classes used for power up text in bottom right corner of screen
	 */
	PowerText powerText;

	// Determines whether the game is paused or active
	boolean paused = false;

	// Keeps track of which way the player is currently accelerating
	boolean accelLeft;
	boolean accelRight;

	/*
	 * Preference Booleans
	 */
	boolean control_tilt;
	boolean control_button;

	boolean expertMode;

	/*
	 * Arrays to keep track of individual enemies, missiles, and so on
	 */
	Array<Missile> missiles = new Array<Missile>();
	Array<Enemy> enemies = new Array<Enemy>();
	Array<Powerup> powerups = new Array<Powerup>();
	Array<Bullet> bullets = new Array<Bullet>();
	Array<Explosion> explosions = new Array<Explosion>();
	Array<Landmine> landmines = new Array<Landmine>();
	Array<Cannon> cannons = new Array<Cannon>();

	// Checks if accelerometer is available on current device
	boolean available = Gdx.input
			.isPeripheralAvailable(Peripheral.Accelerometer);

	/*
	 * GameScreen constructor
	 */
	public GameScreen(MyGame game, boolean expert) {

		this.game = game;
		expertMode = expert;

		camera = new OrthographicCamera();
		// Dimensions of the screen
		// Extra 100 is for banner ad
		camera.setToOrtho(true, 1920, 1080);

		/*
		 * Initialize classes
		 */
		batch = new SpriteBatch();
		touch = new Vector3();
		player = new Player();
		missile = new Missile();
		health = new Health();
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

		control_tilt = false;
		control_button = true;
		userInterface = new UserInterface(Assets.sprite_control_button_back);

		// Load current high score
		highScore = getHighScore();
		yourHighScore = "" + highScore;

		playerData.checkPrefs();
		playerData.checkPowerUpPrefs();

		stage = new Stage(new StretchViewport(1920, 1080)); // ** window is
															// stage **//
		stage.clear();
		Gdx.input.setInputProcessor(stage); // ** stage is responsive **//

		fireButton = userInterface
				.createButton(stage, "fireButtonDown", "fireButtonUp",
						"images/interface/FB_down.png",
						"images/interface/FB_up.png", Assets.font12, 1315, 15,
						150, 590);
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

		leftButton = userInterface.createButton(stage, "leftButtonDown",
				"leftButtonUp", "images/interface/LB_down.png",
				"images/interface/LB_up.png", Assets.font12, 20, 20, 140, 290);
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

		rightButton = userInterface.createButton(stage, "rightButtonDown",
				"rightButtonUp", "images/interface/RB_down.png",
				"images/interface/RB_up.png", Assets.font12, 310, 20, 140, 290);
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

		pauseButton = userInterface.createButton(stage, "pauseButtonDown",
				"pauseButtonUp", "images/interface/PB_down.png",
				"images/interface/PB_up.png", Assets.font12, 825, 100, 70, 155);
		pauseButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				pauseGame();
				return true;
			}
		});
		stage.addActor(pauseButton);

		if (cannons.size != 4) {
			cannons.add(new Cannon(100, 830));
			cannons.add(new Cannon(600, 830));
			cannons.add(new Cannon(1220, 830));
			cannons.add(new Cannon(1720, 830));
		}
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

		// Order of drawing
		/*
		 * 1. Background 2. Auto-fire Reticle 3. Cannons 4. Player 5. Static
		 * Shield 6. Missile 7. Bullet 8. Enemy 9. Landmine 10. Powerup 11. User
		 * Interface 12. Health 13. Powerup Text 14. Score
		 */

		// If game is paused, enter this statement
		if (!paused) {

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
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							checkCollision();
						}
					});
				}
			}).start();
		}

		drawAll();

		// If game is paused, draw sprites underneath the pause display
		if (paused) {
			batch.draw(Assets.pause, 260, 80);
			displayScore();
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
		}

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
		if (gameData.swiftnessPower)
			factor = 1.15;

		if (control_button) {
			if (leftButtonBool)
				accelerationX -= 1.5 * factor;
			if (rightButtonBool)
				accelerationX += 1.5 * factor;
			updatePlayerMovement();
		}

		if (gameData.autoFire) {
			if (!gameTimers.recentlyFired_auto) {
				gameTimers.startTime_auto = gameTimers.getNanoTime();
				fireProjectile();
				if (gameData.cannons) {
					for (Cannon c : cannons) {
						fireCannons(c);
					}
				}
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
	}

	/*
	 * Checks for all collisions between all sprites
	 */
	public void checkCollision() {

		for (Enemy e : enemies) {
			// Check Missiles
			for (Missile m : missiles) {
				if (Intersector.overlaps(e.Cbounds, m.bounds)) {
					checkStatus(e);
					missiles.removeValue(m, false);
					explosionAdd(m.bounds.x, m.bounds.y);
					checkBigAmmo(m.bounds.x, m.bounds.y);
					checkRapidFire(m.bounds.x, m.bounds.y);
				}
			}
			// Check Bullets
			for (Bullet b : bullets) {
				if (Intersector.overlaps(e.Cbounds, b.bounds)) {

					checkStatus(e);
					explosionAdd(b.bounds.x, b.bounds.y);

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

					checkBigAmmo(b.bounds.x, b.bounds.y);
					checkRapidFire(b.bounds.x, b.bounds.y);
				}
			}
			// Check Landmines
			for (Landmine l : landmines) {
				if (Intersector.overlaps(e.Cbounds, l.Cbounds)) {
					checkStatus(e);
					spawnBigExplosion(l.Cbounds.x, l.Cbounds.y);
					landmines.removeValue(l, false);
				}
			}
			// Check Player in Expert Mode
			if (expertMode) {
				if (Intersector.overlaps(e.Cbounds, player.bounds)) {
					if (!e.hitPlayer) {
						gameData.player_health--;
						healthUsed++;
						e.hitPlayer = true;
					}
				}
			}
			// Check Big Explosions
			for (Explosion x : explosions) {
				if (x.big) {
					if (Intersector.overlaps(x.cBounds, e.Cbounds)) {
						if (!e.hitByExplosion) {
							e.health--; // Do extra damage
							checkStatus(e);
							e.hitByExplosion = true;
						}
					}
				}
			}
			// Check Static Shield
			if (gameData.static_shield) {
				if (Intersector.overlaps(e.Cbounds, player.shield_bounds)) {
					if (Intersector.overlaps(e.Cbounds, player.shield_bounds)) {
						if (!e.hitByShield) {
							checkStatus(e);
							explosionAdd(-100, -100);
							e.hitByShield = true;
						}
					}
				}
			}
		}
		for (Powerup p : powerups) {
			for (Missile m : missiles) {
				if (Intersector.overlaps(p.Cbounds, m.bounds)) {
					// POWERUP
					p.executePowerUp(gameData, gameTimers, p.numType);
					missiles.removeValue(m, false);
					powerups.removeValue(p, false);
				}
			}
			for (Bullet b : bullets) {
				if (Intersector.overlaps(p.Cbounds, b.bounds)) {
					// POWERUP
					p.executePowerUp(gameData, gameTimers, p.numType);
					bullets.removeValue(b, false);
					powerups.removeValue(p, false);
				}
			}
			if (Intersector.overlaps(p.Cbounds, player.bounds)) {
				// POWERUP
				p.executePowerUp(gameData, gameTimers, p.numType);
				powerups.removeValue(p, false);
			}
		}
		if (expertMode) {
			for (Missile m : missiles) {
				if (m.shotByEnemy) {
					if (!m.hitPlayer) {
						if (Intersector.overlaps(player.bounds, m.bounds)) {
							gameData.player_health--;
							healthUsed++;
							explosionAdd(m.bounds.x - 48, m.bounds.y);
							missiles.removeValue(m, false);
						}
					}
				}
			}
		}
	}

	public void checkStatus(Enemy e) {
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
	}

	public void checkBigAmmo(float x, float y) {
		if (gameData.bigAmmo) {
			if (gameData.bigExplosion) {
				int chance = rand.nextInt(100) + 1;
				if (chance <= (gameData.bigExplosionChance)) {
					spawnBigExplosion(x, y);
				}
			}
		}
	}

	public void checkRapidFire(float x, float y) {
		if (gameData.rapidFire) {
			if (gameData.ricochet) {
				int chance = rand.nextInt(100) + 1;
				if (chance <= (gameData.ricochetChance * 10))
					spawnRicochet(x, y);
			}
		}
	}

	/*
	 * Updates movement of missiles
	 */
	public void missileUpdate(Missile missile) {
		if (expertMode) {
			if (missile.shotByEnemy)
				missile.bounds.y += 17.5;
			else
				missile.bounds.y -= 25;
		} else
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

		double factor = 1.0;
		if (expertMode)
			factor = 1.15;

		if (gameData.slow_time)
			factor = factor
					- (0.1 * PlayerData.prefs.getInteger("swiftnessUpTime"));

		if (enemy.normal) {
			enemy.Cbounds.y += 3.25 * factor;
			batch.draw(enemy.image, enemy.Cbounds.x - (float) 37.5,
					enemy.Cbounds.y - (float) 37.5);
		}
		if (enemy.gold) {
			enemy.Cbounds.y += 5 * factor;
			batch.draw(enemy.image, enemy.Cbounds.x - (float) 70,
					enemy.Cbounds.y - (float) 70);
		}
		if (enemy.strong) {
			enemy.Cbounds.y += 2 * factor;
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

		anim_background.draw(batch, paused);

		// Red Dotted line
		if (gameData.autoFire) {
			batch.draw(Assets.sprite_auto_aim, player.bounds.x + 80,
					player.bounds.y - 1080);
		}

		if (gameData.cannons) {
			for (Cannon c : cannons)
				batch.draw(c.cannon, c.bounds.x, c.bounds.y,
						c.cannon.getOriginX(), c.cannon.getOriginY(), 100, 100,
						1, 1, c.degree);
		}

		// Player
		stateTime += Gdx.graphics.getDeltaTime();
		player.drawR(batch, rotation, stateTime, gameData);

		if (gameData.static_shield) {
			stateTime += Gdx.graphics.getDeltaTime();
			player.drawShield(batch, stateTime, paused);
		}

		if (gameData.explosionBool) {
			for (Explosion e : explosions) {
				if (e.big)
					updateExplosions(e.cBounds.x, e.cBounds.y, e);
				else
					updateExplosions(e.bounds.x, e.bounds.y, e);
				e.timer++;
				if (e.checkEnd())
					explosions.removeValue(e, false);
			}
		}

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

		userInterface.draw(batch);

		// Health Bar
		health.draw(batch, gameData);

		// Display Power-Up Text
		powerText.draw(batch, gameData);
		// Display Score
		displayScore();
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

		if (expertMode) {
			playerData.setCurrency(score * 4);
			coinsEarned = score * 4;
		} else {
			playerData.setCurrency(score * 2);
			coinsEarned = score * 2;
		}

		long gameEnd = gameTimers.getNanoTime();
		timeElapsed = (int) (gameEnd - gameTimers.gameStart) / 100;

		gameData.player_health = 5;
		enemySpawnRate = 200;
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
		if (expertMode) {
			enemySpawnRate = 100 - ((score / 15) * 5);
			if (enemySpawnRate < 15)
				enemySpawnRate = 15;
		} else {
			enemySpawnRate = 200 - ((score / 25) * 5);
			if (enemySpawnRate < 50)
				enemySpawnRate = 50;
		}

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
		if (gameTimers.autoFire_dur >= gameTimers.autoDuration) {
			gameData.autoFire = false;
			gameData.cannons = false;
		}

		gameTimers.swiftnessPower_end = gameTimers.getNanoTime();
		gameTimers.swiftnessPower_dur = (gameTimers.swiftnessPower_end - gameTimers.swiftnessPower_start);
		if (gameTimers.swiftnessPower_dur >= gameTimers.swiftnessDuration) {
			gameData.swiftnessPower = false;
			gameData.slow_time = false;
			gameData.static_shield = false;
		}

		gameTimers.bigAmmo_end = gameTimers.getNanoTime();
		gameTimers.bigAmmo_dur = (gameTimers.bigAmmo_end - gameTimers.bigAmmo_start);
		if (gameTimers.bigAmmo_dur >= gameTimers.bigDuration) {
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
				if (chance <= (gameData.extra_powerup_chance * 20)) {
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
			enemies.add(new Enemy(spawnArea, chooseEnemy, expertMode));
			gameTimers.enemyStart = gameTimers.enemyEnd;
		}

		/*
		 * This function will add the ability for enemy spaceships to shoot
		 * missiles This is to be used in Expert Mode only
		 * 
		 * The reason it is disabled is because, in its current form, feels to
		 * difficult to properly play against
		 */
		
		/*
		if (expertMode) {
			for (Enemy e : enemies) {
				e.reloadEnd = gameTimers.getNanoTime();
				e.reloadDur = e.reloadEnd - e.reloadStart;
				if (e.reloadDur >= gameTimers.enemyReloadTime) {
					missiles.add(new Missile(e.Cbounds.x, e.Cbounds.y,
							e.yOffset));
					e.reloadStart = e.reloadEnd;
				}
			}
		}
		*/

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
		if (gameData.swiftnessPower)
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

	public void fireCannons(Cannon cannon) {
		int x = rand.nextInt(20) - 10;
		int y = rand.nextInt(5) + 20;
		bullets.add(new Bullet(cannon.bounds.x - 50, cannon.bounds.y, x, y,
				true));
		cannon.drawR(batch, x, y);
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
	public void spawnRicochet(float x, float y) {

		bullets.add(new Bullet(x, y, -16, -16));
		bullets.add(new Bullet(x, y, 16, -16));
		bullets.add(new Bullet(x, y, 16, 16));
		bullets.add(new Bullet(x, y, -16, 16));

		bullets.add(new Bullet(x, y, 0, -24));
		bullets.add(new Bullet(x, y, 0, -24));
		bullets.add(new Bullet(x, y, 24, 0));
		bullets.add(new Bullet(x, y, -24, 0));

		bullets.add(new Bullet(x, y, -8, -20));
		bullets.add(new Bullet(x, y, 8, -20));
		bullets.add(new Bullet(x, y, 20, 8));
		bullets.add(new Bullet(x, y, -20, 8));

		bullets.add(new Bullet(x, y, -20, -8));
		bullets.add(new Bullet(x, y, 20, -8));
		bullets.add(new Bullet(x, y, 8, 20));
		bullets.add(new Bullet(x, y, -8, 20));
	}

	public void spawnBigExplosion(float x, float y) {
		explosions.add(new Explosion(x, y, true));
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
	public void explosionAdd(float x, float y) {

		gameData.explosionBool = true;
		explosions.add(new Explosion(x, y, false));

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
	public void updateExplosions(float x, float y, Explosion e) {

		stateTime += Gdx.graphics.getDeltaTime();

		e.current_frame = e.explosion_animation.getKeyFrame(stateTime, true);
		if (e.big)
			batch.draw(e.current_frame, x - 300f, y - 300f);
		else
			batch.draw(e.current_frame, x, y);

	}

}
