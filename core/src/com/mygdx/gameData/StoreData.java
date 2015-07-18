package com.mygdx.gameData;

public class StoreData {
	
	public PurchaseData[] missileData;
	public PurchaseData[] rapidData;
	public PurchaseData[] healthData;
	public PurchaseData[] multiData;
	
	public StoreData(){
		
		missileData = loadMissileData();
		rapidData = loadRapidData();
		healthData = loadHealthData();
		multiData = loadMultiData();
	}

	public PurchaseData[] loadMissileData(){
		PurchaseData[] table = new PurchaseData[10];
		
		table[0] = new PurchaseData("Every 5 Seconds", "Rank 1 of Auto Shot Upgrade", 250, 1, 1);
		table[1] = new PurchaseData("Every 4 Seconds", "Rank 2 of Auto Shot Upgrade", 375, 2, 1);
		table[2] = new PurchaseData("Every 3 Seconds", "Rank 3 of Auto Shot Upgrade", 550, 3, 1);
		table[3] = new PurchaseData("Every 2 Seconds", "Rank 4 of Auto Shot Upgrade", 750, 4, 1);
		table[4] = new PurchaseData("Every 1 Seconds", "Rank 5 of Auto Shot Upgrade", 1000, 5, 1);
		table[5] = new PurchaseData("5% Faster Reload", "Rank 1 of Reload Upgrade", 500, 1, 2);
		table[6] = new PurchaseData("10% Faster Reload", "Rank 2 of Reload Upgrade", 1000, 2, 2);
		table[7] = new PurchaseData("15% Faster Reload", "Rank 3 of Reload Upgrade", 1500, 3, 2);
		table[8] = new PurchaseData("20% Faster Reload", "Rank 4 of Reload Upgrade", 2000, 4, 2);
		table[9] = new PurchaseData("25% Faster Reload", "Rank 5 of Reload Upgrade", 2500, 5, 2);
		
		return table;
	}
	
	public PurchaseData[] loadHealthData(){
		PurchaseData[] table = new PurchaseData[10];
		
		table[0] = new PurchaseData("12.5% Faster Regeneration", "Rank 1 of Health Regeneration", 500, 1, 1);
		table[1] = new PurchaseData("25% Faster Regeneration", "Rank 2 of Health Regeneration", 1000, 2, 1);
		table[2] = new PurchaseData("37.5% Faster Regeneration", "Rank 3 of Health Regeneration", 1500, 3, 1);
		table[3] = new PurchaseData("50% Faster Regeneration", "Rank 4 of Health Regeneration", 2000, 4, 1);
		table[4] = new PurchaseData("62.5% Faster Regeneration", "Rank 5 of Health Regeneration", 2500, 5, 1);
		table[5] = new PurchaseData("1 Extra Health", "Rank 1 of Extra Health Upgrade", 500, 1, 2);
		table[6] = new PurchaseData("2 Extra Health", "Rank 2 of Extra Health Upgrade", 875, 2, 2);
		table[7] = new PurchaseData("3 Extra Health", "Rank 3 of Extra Health Upgrade", 1250, 3, 2);
		table[8] = new PurchaseData("4 Extra Health", "Rank 4 of Extra Health Upgrade", 1625, 4, 2);
		table[9] = new PurchaseData("5 Extra Health", "Rank 5 of Extra Health Upgrade", 2000, 5, 2);
		
		return table;
	}
	
	public PurchaseData[] loadRapidData(){
		PurchaseData[] table = new PurchaseData[15];
		
		table[0] = new PurchaseData("5% Faster Reload", "Rank 1 of Rapid Fire Reload", 500, 1, 1);
		table[1] = new PurchaseData("10% Faster Reload", "Rank 2 of Rapid Fire Reload", 750, 2, 1);
		table[2] = new PurchaseData("15% Faster Reload", "Rank 3 of Rapid Fire Reload", 1125, 3, 1);
		table[3] = new PurchaseData("20% Faster Reload", "Rank 4 of Rapid Fire Reload", 1500, 4, 1);
		table[4] = new PurchaseData("25% Faster Reload", "Rank 5 of Rapid Fire Reload", 2000, 5, 1);
		table[5] = new PurchaseData("5% Longer Duration", "Rank 1 of Rapid Fire Duration", 750, 1, 2);
		table[6] = new PurchaseData("15% Longer Duration", "Rank 2 of Rapid Fire Duration", 1125, 2, 2);
		table[7] = new PurchaseData("25% Longer Duration", "Rank 3 of Rapid Fire Duration", 1750, 3, 2);
		table[8] = new PurchaseData("35% Longer Duration", "Rank 4 of Rapid Fire Duration", 2500, 4, 2);
		table[9] = new PurchaseData("45% Longer Duration", "Rank 5 of Rapid Fire Duration", 3000, 5, 2);
		table[10] = new PurchaseData("20% Chance of Ricochet", "Rank 1 of Ricochet", 250, 1, 3);
		table[11] = new PurchaseData("40% Chance of Ricochet", "Rank 2 of Ricochet", 500, 2, 3);
		table[12] = new PurchaseData("60% Chance of Ricochet", "Rank 3 of Ricochet", 1000, 3, 3);
		table[13] = new PurchaseData("80% Chance of Ricochet", "Rank 4 of Ricochet", 2000, 4, 3);
		table[14] = new PurchaseData("100% Chance of Ricochet", "Rank 5 of Ricochet", 4000, 5, 3);
		
		return table;
	}
	
	public PurchaseData[] loadMultiData(){
		PurchaseData[] table = new PurchaseData[15];
		
		table[0] = new PurchaseData("5% Longer Duration", "Rank 1 of Multi Shot Duration", 750, 1, 1);
		table[1] = new PurchaseData("10% Longer Duration", "Rank 2 of Multi Shot Duration", 1125, 2, 1);
		table[2] = new PurchaseData("15% Longer Duration", "Rank 3 of Multi Shot Duration", 1500, 3, 1);
		table[3] = new PurchaseData("20% Longer Duration", "Rank 4 of Multi Shot Duration", 2000, 4, 1);
		table[4] = new PurchaseData("25% Longer Duration", "Rank 5 of Multi Shot Duration", 2500, 5, 1);
		table[5] = new PurchaseData("1 Extra Missile", "Rank 1 of Extra Missiles", 500, 1, 2);
		table[6] = new PurchaseData("1 Extra Missile", "Rank 2 of Extra Missiles", 1000, 2, 2);
		table[7] = new PurchaseData("1 Extra Missile", "Rank 3 of Extra Missiles", 1500, 3, 2);
		table[8] = new PurchaseData("1 Extra Missile", "Rank 4 of Extra Missiles", 2000, 4, 2);
		table[9] = new PurchaseData("1 Extra Missile", "Rank 5 of Extra Missiles", 2500, 5, 2);
		table[10] = new PurchaseData("20% Chance of Multi Ships", "Rank 1 of Multi Ships", 500, 1, 3);
		table[11] = new PurchaseData("40% Chance of Multi Ships", "Rank 2 of Multi Ships", 1000, 2, 3);
		table[12] = new PurchaseData("60% Chance of Multi Ships", "Rank 3 of Multi Ships", 1500, 3, 3);
		table[13] = new PurchaseData("80% Chance of Multi Ships", "Rank 4 of Multi Ships", 2250, 4, 3);
		table[14] = new PurchaseData("100% Chance of Multi Ships", "Rank 5 of Multi Ships", 3000, 5, 3);
		
		return table;
	}
}
