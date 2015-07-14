package com.mygdx.gameData;

public class StoreData {
	
	public PurchaseData[] missileData;
	public PurchaseData[] rapidData;
	
	public StoreData(){
		
		missileData = loadMissileData();
		rapidData = loadRapidData();
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
	
	public PurchaseData[] loadRapidData(){
		PurchaseData[] table = new PurchaseData[15];
		
		table[0] = new PurchaseData("5% Faster Reload", "Rank 1 of Rapid Fire Reload", 250, 1, 1);
		table[1] = new PurchaseData("10% Faster Reload", "Rank 2 of Rapid Fire Reload", 250, 2, 1);
		table[2] = new PurchaseData("15% Faster Reload", "Rank 3 of Rapid Fire Reload", 250, 3, 1);
		table[3] = new PurchaseData("20% Faster Reload", "Rank 4 of Rapid Fire Reload", 250, 4, 1);
		table[4] = new PurchaseData("25% Faster Reload", "Rank 5 of Rapid Fire Reload", 250, 5, 1);
		table[5] = new PurchaseData("5% Longer Duration", "Rank 1 of Rapid Fire Duration", 250, 1, 2);
		table[6] = new PurchaseData("15% Longer Duration", "Rank 2 of Rapid Fire Duration", 250, 2, 2);
		table[7] = new PurchaseData("25% Longer Duration", "Rank 3 of Rapid Fire Duration", 250, 3, 2);
		table[8] = new PurchaseData("35% Longer Duration", "Rank 4 of Rapid Fire Duration", 250, 4, 2);
		table[9] = new PurchaseData("45% Longer Duration", "Rank 5 of Rapid Fire Duration", 250, 5, 2);
		table[10] = new PurchaseData("20% Chance of Ricochet", "Rank 1 of Ricochet", 250, 1, 3);
		table[11] = new PurchaseData("40% Chance of Ricochet", "Rank 2 of Ricochet", 250, 2, 3);
		table[12] = new PurchaseData("60% Chance of Ricochet", "Rank 3 of Ricochet", 250, 3, 3);
		table[13] = new PurchaseData("80% Chance of Ricochet", "Rank 4 of Ricochet", 250, 4, 3);
		table[14] = new PurchaseData("100% Chance of Ricochet", "Rank 5 of Ricochet", 250, 5, 3);
		
		return table;
	}
}
