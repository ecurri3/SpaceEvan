package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.mygdx.gameData.GameData;
import com.mygdx.gameData.GameTimers;

public class Powerup {

	public Sprite image;
	public Circle Cbounds;
	public int numType;
	Random rand = new Random();

	public Powerup() {
		image = Assets.powerup_rapid;
		Cbounds = new Circle(0, 0, 75);
		numType = 1;
	}

	public Powerup(int spawnX, int power) {
		switch (power) {
		case 1:
			image = Assets.powerup_rapid;
			numType = 1;
			break;
		case 2:
			image = Assets.powerup_multi;
			numType = 2;
			break;
		case 3:
			image = Assets.powerup_spray;
			numType = 3;
			break;
		case 4:
			image = Assets.powerup_life;
			numType = 4;
			break;
		case 5:
			image = Assets.powerup_auto;
			numType = 5;
			break;
		case 6:
			image = Assets.powerup_swiftness;
			numType = 6;
			break;
		case 7:
			image = Assets.powerup_big;
			numType = 7;
			break;
		}
		Cbounds = new Circle(spawnX, 0, 75);
	}

	public void executePowerUp(GameData gameData, GameTimers gameTimers, int n) {
		switch (n) {
		case 1:
			Assets.powerup1.play();
			gameData.rapidFire = true;
			gameTimers.rapidFire_start = gameTimers.getNanoTime();
			break;
		case 2:
			Assets.powerup4.play();
			gameData.multiShot = true;
			gameTimers.multiShot_start = gameTimers.getNanoTime();
			if (gameData.mirror) {
				int chance = rand.nextInt(100) + 1;
				if (chance <= gameData.mirrorChance)
					gameData.mirrorTwins = true;
			}
			break;
		case 3:
			Assets.powerup2.play();
			gameData.shotgun = true;
			gameTimers.shotgun_start = gameTimers.getNanoTime();
			break;
		case 4:
			Assets.heal1.play();
			gameData.player_health++;
			break;
		case 5:
			Assets.powerup5.play();
			gameData.autoFire = true;
			gameTimers.autoFire_start = gameTimers.getNanoTime();
			if (gameData.autoCannon) {
				int chance = rand.nextInt(100) + 1;
				if (chance <= gameData.autoCannonChance * 20)
					gameData.cannons = true;
			}
			break;
		case 6:
			Assets.powerup6.play();
			gameData.swiftnessPower = true;
			gameTimers.swiftnessPower_start = gameTimers.getNanoTime();
			if (gameData.slowTime)
				gameData.slow_time = true;
			if (gameData.swiftnessShield) {
				int chance = rand.nextInt(100) + 1;
				if (chance <= gameData.swiftnessShieldChance)
					gameData.static_shield = true;
			}
			break;
		case 7:
			Assets.powerup7.play();
			gameData.bigAmmo = true;
			gameTimers.bigAmmo_start = gameTimers.getNanoTime();
			break;
		}
	}

	public boolean checkEnd() {
		return Cbounds.y >= 1080;
	}

}
