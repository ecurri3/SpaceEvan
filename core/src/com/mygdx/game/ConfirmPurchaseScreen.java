package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class ConfirmPurchaseScreen {
	
	public Sprite image;
	public Rectangle bounds;
	
	public ConfirmPurchaseScreen(){
		image = Assets.sprite_confirm_purchase;
		bounds = new Rectangle(510, 375, 900, 400);
	}

}
