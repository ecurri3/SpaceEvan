package com.mygdx.gameData;

public class GameData {

	public boolean extra_powerup = false;
	public int extra_powerup_chance = 0;

	// Determines if an explosion animation is being played.
	// Set to true when an enemy is destroyed by the player
	public boolean explosionBool = false;

	/*
	 * Store Upgrade Boolean Variables
	 */
	public boolean healthRegen;
	public boolean autoShot;
	public boolean mirror;
	public boolean mirrorTwins;
	public boolean ricochet;
	public boolean multi_extra;

	// mirror chance is the chance to spawn Mirror Image
	public int mirrorChance = 0;

	// ricochet chance is the chance to spawn a ricochet explosion
	public int ricochetChance = 0;

	// multi shot extra missiles upgrade
	public int multi_extra_missiles = 3;

	// shotgun piercing upgrade
	public boolean shotgun_pierce = false;
	public int shotgun_pierce_chance = 0;

	// shotgun landmine upgrade
	public boolean landmine = false;
	public int landmineChance = 0;

	/*
	 * Power-Up Booleans
	 */
	public boolean rapidFire;
	public boolean shotgun;
	public boolean multiShot;
	public boolean autoFire;
	public boolean swiftnessPower;
	public boolean bigAmmo;

	public int player_health = 5;

	public GameData() {

		healthRegen = false;
		autoShot = false;
		mirror = false;
		mirrorTwins = false;
		ricochet = false;
		multi_extra = false;
		rapidFire = false;
		shotgun = false;
		multiShot = false;
		autoFire = false;
		swiftnessPower = false;
		bigAmmo = false;

	}

}
