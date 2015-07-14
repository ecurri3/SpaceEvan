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
		gameData.player_health = 5 + (1 * prefs.getInteger("hUpHealth"));

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
		switch (prefs.getInteger("multiUpDur")) {
		case 1:
			gameTimers.multiShotDuration = 2587;
			break;
		case 2:
			gameTimers.multiShotDuration = 2925;
			break;
		case 3:
			gameTimers.multiShotDuration = 3262;
			break;
		case 4:
			gameTimers.multiShotDuration = 3600;
			break;
		case 5:
			gameTimers.multiShotDuration = 3937;
			break;
		default:
			gameTimers.multiShotDuration = 2250;
			break;
		}

		// Multi Shot missile amount
		if (prefs.getInteger("multiUpAmount") != 0) {
			gameData.multi_extra = true;
			gameData.multi_extra_missiles = 3 + (1 * prefs
					.getInteger("multiUpAmount"));
		}

		// Mirror Image Upgrade
		if (prefs.getInteger("multiUpMirror") != 0) {
			gameData.mirror = true;
			gameData.mirrorChance = 0 + (1 * prefs.getInteger("multiUpMirror"));
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

		// General powerup spawn timer
		gameTimers.powerup_spawn_timer = 1000 - (100 * prefs
				.getInteger("genUpDur"));

		// General chance to spawn extra powerup
		if (prefs.getInteger("genUpExtra") != 0) {
			gameData.extra_powerup = true;
			gameData.extra_powerup_chance = 0 + prefs.getInteger("genUpExtra");
		}
	}

}
