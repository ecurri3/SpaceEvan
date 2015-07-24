package com.mygdx.gameData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PlayerData {

	public static Preferences prefs;
	public GameData gameData;
	public GameTimers gameTimers;

	public PlayerData(GameData data, GameTimers timers) {

		// Load current preferences
		prefs = Gdx.app.getPreferences("SpaceGame");
		gameData = data;
		gameTimers = timers;

	}

	public void checkPrefs() {

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

		if (!prefs.contains("rapidUpRicochet"))
			prefs.putInteger("rapidUpRicochet", 0);

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

		// AUTO FIRE
		if (!prefs.contains("autoUpDur"))
			prefs.putInteger("autoUpDur", 0);

		if (!prefs.contains("autoUpCannon"))
			prefs.putInteger("autoUpCannon", 0);

		// SWIFTNESS
		if (!prefs.contains("swiftnessUpDur"))
			prefs.putInteger("swiftnessUpDur", 0);

		if (!prefs.contains("swiftnessUpShield"))
			prefs.putInteger("swiftnessUpShield", 0);

		if (!prefs.contains("swiftnessUpTime"))
			prefs.putInteger("swiftnessUpTime", 0);

		// BIGAMMO
		if (!prefs.contains("bigUpDur"))
			prefs.putInteger("bigUpDur", 0);

		if (!prefs.contains("bigUpExplosion"))
			prefs.putInteger("bigUpExplosion", 0);

		// GENERAL
		if (!prefs.contains("generalUpDur"))
			prefs.putInteger("generalUpDur", 0);

		if (!prefs.contains("generalUpExtra"))
			prefs.putInteger("generalUpExtra", 0);

		// Save
		prefs.flush();

	}

	public void checkPowerUpPrefs() {

		// Reload Upgrade
		gameTimers.reloadDur = 50 - (2.5 * prefs.getInteger("mUpReload"));

		// Auto Shot Upgrade
		if (prefs.getInteger("mUpAuto") != 0) {
			gameData.autoShot = true;
			gameTimers.autoShotTime = 600 - (100 * prefs.getInteger("mUpAuto"));
		}

		// Health Regeneration
		if (prefs.getInteger("hUpRegen") != 0) {
			gameData.healthRegen = true;
			gameTimers.healthRegenTime = 2000 - (250 * prefs
					.getInteger("hUpRegen"));
		}

		// Extra Health
		if (prefs.getInteger("hUpHealth") != 0) {
			gameData.max_health = 5 + (1 * prefs.getInteger("hUpHealth"));
		}

		// Rapid Fire Duration
		gameTimers.rapidFireDuration = 1500 + (150 * prefs
				.getInteger("rapidUpDur"));

		// Rapid Fire Reload
		gameTimers.rapidFireDur = 10 - (0.5 * prefs.getInteger("rapidUpReload"));

		// Ricochet Upgrade
		if (prefs.getInteger("rapidUpRicochet") != 0) {
			gameData.ricochet = true;
			gameData.ricochetChance = 0 + (prefs.getInteger("rapidUpRicochet"));
		}

		// Multi Shot Duration
		gameTimers.multiShotDuration = (int) (2250 * (1 + (0.05 * prefs
				.getInteger("multiUpDur"))));

		// Multi Shot missile amount
		if (prefs.getInteger("multiUpAmount") != 0) {
			gameData.multi_extra = true;
			gameData.multi_extra_missiles = 3 + (1 * prefs
					.getInteger("multiUpAmount"));
		}

		// Mirror Image Upgrade
		if (prefs.getInteger("multiUpMirror") != 0) {
			gameData.mirror = true;
			gameData.mirrorChance = 0 + (20 * prefs.getInteger("multiUpMirror"));
		}

		// Shotgun Duration
		gameTimers.shotgunDuration = 1250 + (125 * prefs
				.getInteger("shotgunUpDur"));

		// Shotgun piercing chance
		if (prefs.getInteger("shotgunUpPierce") != 0) {
			gameData.shotgun_pierce = true;
			gameData.shotgun_pierce_chance = 0 + prefs
					.getInteger("shotgunUpPierce");
		}

		// Landmine upgrade
		if (prefs.getInteger("shotgunUpLandmine") != 0) {
			gameData.landmine = true;
			gameData.landmineChance = 0 + prefs.getInteger("shotgunUpLandmine");
		}

		// Auto Fire duration
		if (prefs.getInteger("autoUpDur") != 0) {
			gameTimers.autoDuration = 1500 + (150 * prefs
					.getInteger("autoUpDur"));
		}

		// Auto Fire Cannon
		if (prefs.getInteger("autoUpCannon") != 0) {
			gameData.autoCannon = true;
			gameData.autoCannonChance = 0 + prefs.getInteger("autoUpCannon");
		}

		// Swiftness duration
		if (prefs.getInteger("swiftnessUpDur") != 0) {
			gameTimers.swiftnessDuration = 1500 + (225 * prefs
					.getInteger("swiftnessUpDur"));
		}

		// Swiftness Shield
		if (prefs.getInteger("swiftnessUpShield") != 0) {
			gameData.swiftnessShield = true;
			gameData.swiftnessShieldChance = 20 + (15 * (prefs
					.getInteger("swiftnessUpShield") - 1));
		}

		// Swiftness Time
		if (prefs.getInteger("swiftnessUpTime") != 0) {
			gameData.slowTime = true;
			gameData.slowTimeAmount = 0 + (10 * prefs
					.getInteger("swiftnessUpTime"));
		}

		// Big Ammo duration
		if (prefs.getInteger("bigUpDur") != 0) {
			gameTimers.bigDuration = 2250 + (225 * prefs.getInteger("bigUpDur"));
		}

		// Big Ammo Explosion
		if (prefs.getInteger("bigUpExplosion") != 0) {
			gameData.bigExplosion = true;
			gameData.bigExplosionChance = 0 + (20 * prefs
					.getInteger("bigUpExplosion"));
		}

		// General powerup spawn timer
		gameTimers.powerup_spawn_timer = 1000 - (100 * prefs
				.getInteger("genUpDur"));

		// General chance to spawn extra powerup
		if (prefs.getInteger("genUpExtra") != 0) {
			gameData.extra_powerup = true;
			gameData.extra_powerup_chance = 0 + prefs.getInteger("genUpExtra");
		}
	}

	public static int getCurrency() {
		return prefs.getInteger("currency");
	}

	public void setCurrency(int value) {
		int current = getCurrency();
		prefs.putInteger("currency", (current + value));
		prefs.flush();
	}

}
