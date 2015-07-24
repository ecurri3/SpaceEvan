package com.mygdx.gameData;

public class GameTimers {

	// Used for easier calculations when determining timing of certain powerups,
	// reloads, etc
	public int nano = 10000000;

	// Keeps track of the time the game started and ended
	public long gameStart;

	// Auto Shot timer
	public long autoShotStart;
	public long autoShotEnd;
	public long autoShotDur;

	// Rapid Fire timer
	public long rapidFire_start;
	public long rapidFire_end;
	public long rapidFire_dur;

	// Shotgun timer
	public long shotgun_start;
	public long shotgun_end;
	public long shotgun_dur;

	// Multi Shot timer
	public long multiShot_start;
	public long multiShot_end;
	public long multiShot_dur;

	// Auto Fire timer
	public long autoFire_start;
	public long autoFire_end;
	public long autoFire_dur;

	// Swiftness timer
	public long swiftnessPower_start;
	public long swiftnessPower_end;
	public long swiftnessPower_dur;

	// Big Ammo timer
	public long bigAmmo_start;
	public long bigAmmo_end;
	public long bigAmmo_dur;

	// Reload timer
	public long startTime;
	public long endTime;
	public long duration;

	// Reload auto shot timer
	public long startTime_auto;
	public long endTime_auto;
	public long duration_auto;

	// Enemy spawn timer
	public long enemyStart;
	public long enemyEnd;
	public long enemyDur;

	// Power Up spawn timer
	public long powerStart = System.nanoTime() / nano;
	public long powerEnd;
	public long powerDur;

	// Keeps track of the reload
	public boolean recentlyFired = false;
	public boolean recentlyFired_auto = false;

	// Base reload duration, half a second
	public double reloadDur = 50;

	// Base reload duration for auto fire upgrade, half a second
	public double reloadDur_auto = 50;

	// Auto shot upgrade under the Missiles Store section
	public int autoShotTime = 500;

	// base Rapid Fire power up duration, 15 seconds
	public int rapidFireDuration = 1500;
	// base Rapid Fire reload duration, one tenth of a second
	public double rapidFireDur = 10;

	// base Multi Shot power up duration, 22.5 seconds
	public int multiShotDuration = 2250;

	// base Shotgun power up duration, 12.5 seconds
	public int shotgunDuration = 1250;
	
	public int autoDuration = 1500;
	
	public int swiftnessDuration = 1500;
	
	public int bigDuration = 2250;

	// powerup spawn timer
	public int powerup_spawn_timer = 1000;

	// pause duration
	public long pauseTime;

	public int healthRegenTime = 2000;
	public long healthRegen_start;
	public long healthRegen_end;
	public long healthRegen_dur;

	public GameTimers() {

		/* Begin Timers */
		gameStart = System.nanoTime() / nano;
		enemyStart = System.nanoTime() / nano;
		powerStart = System.nanoTime() / nano;
		autoShotStart = System.nanoTime() / nano;
		healthRegen_start = System.nanoTime() / nano;

	}
	
	public long getNanoTime(){
		return (System.nanoTime() / nano);
	}

}
