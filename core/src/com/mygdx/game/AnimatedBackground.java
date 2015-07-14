package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class AnimatedBackground {
	
	public Sprite image;
	public Sprite image2;
	public Sprite image3;
	public Rectangle bounds;
	public Rectangle bounds2;
	public Rectangle bounds3;
	
	public AnimatedBackground(){
		image = Assets.sprite_animback;
		image2 = Assets.sprite_animback2;
		image3 = Assets.sprite_animback3;
		bounds = new Rectangle(0, -1080, 1920, 2160);
		bounds2 = new Rectangle(0, -3238, 1920, 2160);
		bounds3 = new Rectangle(0, -5396, 1920, 2160);
	}

	public void draw(SpriteBatch batch) {
		if(bounds.y <= 1080){
			batch.draw(image, bounds.x, bounds.y);
		}
		if(bounds2.y <= 1080){
			batch.draw(image2, bounds2.x, bounds2.y);
		}
		if(bounds3.y <= 1080){
			batch.draw(image3, bounds3.x, bounds3.y);
		}
		if(bounds3.y > 0){
			//reset all backgrounds
			bounds.y = -1080;
			bounds2.y = -3238;
			bounds3.y = -5396;
		}
		bounds.y += 1;
		bounds2.y += 1;
		bounds3.y += 1;
		
	}

}
