package com.mygdx.gameData;

public class StoreData {

	public PurchaseData[] missileData, rapidData, healthData, powerData,
			multiData, shotgunData, autoData, swiftnessData, bigData;

	public StoreData() {

		missileData = loadMissileData();
		healthData = loadHealthData();
		powerData = loadPowerData();
		rapidData = loadRapidData();
		multiData = loadMultiData();
		shotgunData = loadShotgunData();
		autoData = loadAutoData();
		swiftnessData = loadSwiftnessData();
		bigData = loadBigData();
	}

	public PurchaseData[] loadMissileData() {
		PurchaseData[] table = new PurchaseData[10];

		for (int i = 0; i < 5; i++) {
			table[i] = new PurchaseData("Every " + (5 - i) + " Seconds",
					"Rank " + (1 + i) + " of Auto Shot Upgrade", "mUpAuto",
					250 + (i * 175), i + 1, 1);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 5] = new PurchaseData((5 + i * 5) + "% Faster Reload",
					"Rank " + (1 + i) + " of Reload Upgrade", "mUpReload",
					500 + (i * 500), i + 1, 2);
		}

		return table;
	}

	public PurchaseData[] loadHealthData() {
		PurchaseData[] table = new PurchaseData[10];

		for (int i = 0; i < 5; i++) {
			table[i] = new PurchaseData((12.5 + i * 12.5)
					+ "% Faster Regeneration", "Rank " + (1 + i)
					+ " of Health Regeneration", "hUpRegen", 500 + (i * 500),
					i + 1, 1);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 5] = new PurchaseData((1 + i) + " Extra Health", "Rank "
					+ (1 + i) + " of Extra Health Upgrade", "hUpHealth",
					500 + (i * 375), i + 1, 2);
		}

		return table;
	}

	public PurchaseData[] loadPowerData() {
		PurchaseData[] table = new PurchaseData[10];

		for (int i = 0; i < 5; i++) {
			table[i] = new PurchaseData((10 + i * 10) + "% Quicker Spawn Time",
					"Rank " + (1 + i) + " of Powerup Spawn Timer", "genUpDur",
					500 + (i * 750), i + 1, 1);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 5] = new PurchaseData((20 + i * 20)
					+ "% Chance for Extra Powerup", "Rank " + (1 + i)
					+ " of Extra Powerup", "genUpExtra", 750 + (i * 1000),
					i + 1, 2);
		}

		return table;
	}

	public PurchaseData[] loadRapidData() {
		PurchaseData[] table = new PurchaseData[15];

		for (int i = 0; i < 5; i++) {
			table[i] = new PurchaseData((5 + i * 5) + "% Faster Reload",
					"Rank " + (1 + i) + " of Rapid Fire Reload",
					"rapidUpReload", 500 + (i * 375), i + 1, 1);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 5] = new PurchaseData((5 + i * 10) + "% Longer Duration",
					"Rank " + (1 + i) + " of Rapid Fire Duration",
					"rapidUpDur", 750 + (i * 500), i + 1, 2);
		}
		// Explicitly defined so cost formula could work for those ahead of it
		table[10] = new PurchaseData("10% Chance of Ricochet",
				"Rank 1 of Ricochet", "rapidUpRicochet", 250, 1, 3);
		for (int i = 1; i < 5; i++) {
			table[i + 10] = new PurchaseData((10 + i * 10)
					+ "% Chance of Ricochet", "Rank " + (1 + i)
					+ " of Ricochet", "rapidUpRicochet",
					(table[(i + 10) - 1].cost * 2), i + 1, 3);
		}

		return table;
	}

	public PurchaseData[] loadMultiData() {
		PurchaseData[] table = new PurchaseData[15];

		for (int i = 0; i < 5; i++) {
			table[i] = new PurchaseData((5 + i * 5) + "% Longer Duration",
					"Rank " + (1 + i) + " of Multi Shot Duration",
					"multiUpDur", 750 + (i * 375), i + 1, 1);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 5] = new PurchaseData((1 + i) + " Extra Missiles",
					"Rank " + (1 + i) + " of Extra Missiles", "multiUpAmount",
					500 + (i * 500), i + 1, 2);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 10] = new PurchaseData((20 + i * 20)
					+ "% Chance of Multi Ships", "Rank " + (1 + i)
					+ " of Multi Ships", "multiUpMirror", 1000 + (i * 500),
					i + 1, 3);
		}

		return table;
	}

	public PurchaseData[] loadShotgunData() {
		PurchaseData[] table = new PurchaseData[15];

		for (int i = 0; i < 5; i++) {
			table[i] = new PurchaseData((10 + i * 10) + "% Longer Duration",
					"Rank " + (1 + i) + " of Shotgun Duration", "shotgunUpDur",
					750 + (i * 750), i + 1, 1);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 5] = new PurchaseData((20 + i * 20)
					+ "% Chance to Pierce Enemies", "Rank " + (1 + i)
					+ " of Shotgun Pierce", "shotgunUpPierce",
					1000 + (i * 750), i + 1, 2);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 10] = new PurchaseData((10 + i * 5)
					+ "% Chance to Spawn Landmine", "Rank " + (1 + i)
					+ " of Shotgun Duration", "shotgunUpLandmine",
					1000 + (i * 1000), i + 1, 3);
		}

		return table;
	}

	public PurchaseData[] loadAutoData() {
		PurchaseData[] table = new PurchaseData[10];

		for (int i = 0; i < 5; i++) {
			table[i] = new PurchaseData((10 + i * 10) + "% Longer Duration",
					"Rank " + (1 + i) + " of Auto Fire Duration", "autoUpDur",
					750 + (i * 750), i + 1, 1);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 5] = new PurchaseData((20 + i * 20)
					+ "% Chance to Spawn Cannons", "Rank " + (1 + i)
					+ " of Auto Cannons", "autoUpCannon", 1000 + (i * 1250),
					i + 1, 2);
		}

		return table;
	}

	public PurchaseData[] loadSwiftnessData() {
		PurchaseData[] table = new PurchaseData[15];

		for (int i = 0; i < 5; i++) {
			table[i] = new PurchaseData((15 + i * 15) + "% Longer Duration",
					"Rank " + (1 + i) + " of Swiftness Duration",
					"swiftnessUpDur", 1000 + (i * 750), i + 1, 1);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 5] = new PurchaseData((20 + i * 15)
					+ "% Chance to Spawn Static Shield", "Rank " + (1 + i)
					+ " of Static Shield", "swiftnessUpShield",
					1500 + (i * 1000), i + 1, 2);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 10] = new PurchaseData((10 + i * 10) + "% Slower Time",
					"Rank " + (1 + i) + " of Slow Time", "swiftnessUpTime",
					1000 + (i * 1000), i + 1, 3);
		}

		return table;
	}

	public PurchaseData[] loadBigData() {
		PurchaseData[] table = new PurchaseData[10];

		for (int i = 0; i < 5; i++) {
			table[i] = new PurchaseData((10 + i * 10) + "% Longer Duration",
					"Rank " + (1 + i) + " of Big Ammo Duration", "bigUpDur",
					750 + (i * 750), i + 1, 1);
		}
		for (int i = 0; i < 5; i++) {
			table[i + 5] = new PurchaseData((20 + i * 20)
					+ "% Chance to cause Big Explosion", "Rank " + (1 + i)
					+ " of Big Explosion", "bigUpExplosion", 500 + (i * 750),
					i + 1, 2);
		}

		return table;
	}
}
