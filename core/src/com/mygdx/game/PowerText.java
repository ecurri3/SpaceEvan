package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.gameData.GameData;

public class PowerText {

	public Sprite rapid, multi, shotgun, big, swift, auto;
	public Rectangle rBounds, mBounds, sBounds, bBounds, swBounds, aBounds;

	public PowerText() {
		rapid = Assets.power_rapid;
		rBounds = new Rectangle(1147, 960, 160, 35);
		multi = Assets.power_multishot;
		mBounds = new Rectangle(1147, 1030, 160, 35);
		shotgun = Assets.power_shotgun;
		sBounds = new Rectangle(1147, 995, 160, 35);
		big = Assets.power_big;
		bBounds = new Rectangle(987, 1030, 160, 35);
		swift = Assets.power_swiftness;
		swBounds = new Rectangle(987, 995, 160, 35);
		auto = Assets.power_auto;
		aBounds = new Rectangle(987, 960, 160, 35);
	}

	/*
	 * This function is responsible for notifying the player of what powerups
	 * they currently possess In the UI, it is located next to the right firing
	 * button and can currently hold 6 powerup messages
	 */
	public void draw(SpriteBatch batch, GameData gameData) {

		if (gameData.rapidFire)
			batch.draw(rapid, rBounds.x, rBounds.y);
		if (gameData.multiShot)
			batch.draw(multi, mBounds.x, mBounds.y);
		if (gameData.shotgun)
			batch.draw(shotgun, sBounds.x, sBounds.y);
		if (gameData.autoFire)
			batch.draw(auto, aBounds.x, aBounds.y);
		if (gameData.swiftnessPower)
			batch.draw(swift, swBounds.x, swBounds.y);
		if (gameData.bigAmmo)
			batch.draw(big, bBounds.x, bBounds.y);
	}

}
