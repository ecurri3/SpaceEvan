package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.gameData.GameData;

public class Player {

	public Sprite player;
	public Sprite missileShip;
	public Sprite bulletShip;
	public Sprite shield, flame;
	public Rectangle bounds;
	public Circle shield_bounds;
	public TextureRegion current_frame, current_frame_flame;
	public Animation static_animation;
	public Animation flame_animation;
	public float rotation;

	public Player() {
		player = Assets.player;
		missileShip = Assets.missile_twin;
		bulletShip = Assets.bullet_twin;
		bounds = new Rectangle(880, 740, 160, 160);
		shield_bounds = new Circle(bounds.x + 80f, bounds.y + 80f, 100f);
		static_animation = Assets.static_animation;
		flame_animation = Assets.flame_animation;
		rotation = 0;
	}

	public void draw(SpriteBatch batch) {
		batch.draw(player, bounds.x, bounds.y);
	}

	public void drawR(SpriteBatch batch, float d, float time, GameData gameData) {
		if (d > 90)
			d = 90;
		else if (d < -90)
			d = -90;

		current_frame_flame = flame_animation.getKeyFrame(time, true);
		flame = new Sprite(current_frame_flame);

		batch.draw(player, bounds.x, bounds.y, player.getOriginX(),
				player.getOriginY(), 160, 160, 1, 1, d);

		batch.draw(current_frame_flame, bounds.x, bounds.y,
				player.getOriginX(), player.getOriginY(), 160, 160, 1, 1, d);

		if (gameData.mirrorTwins) {
			batch.draw(missileShip, bounds.x - 150, bounds.y + 60,
					missileShip.getOriginX(), missileShip.getOriginY(),
					missileShip.getHeight(), missileShip.getWidth(), 1, 1, d);
			batch.draw(bulletShip, bounds.x + 210, bounds.y + 60,
					bulletShip.getOriginX(), bulletShip.getOriginY(),
					bulletShip.getHeight(), bulletShip.getWidth(), 1, 1, d);
		}
	}

	public void drawShield(SpriteBatch batch, float time, boolean paused) {

		shield_bounds.set(bounds.x + 80f, bounds.y + 80f, 100f);

		rotation++;
		if (rotation > 360)
			rotation = 0;

		current_frame = static_animation.getKeyFrame(time, true);
		shield = new Sprite(current_frame);

		if (!paused) {
			batch.draw(current_frame, shield_bounds.x - 100f,
					shield_bounds.y - 100f, shield.getOriginX(),
					shield.getOriginY(), shield.getWidth(), shield.getHeight(),
					1, 1, rotation);
		}
	}

}
