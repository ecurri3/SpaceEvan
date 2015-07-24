package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class UserInterface {

	public Sprite image;
	public Rectangle bounds;
	public TextButton fireButton, leftButton, rightButton, pauseButton;
	public Skin fireButtonSkin, leftButtonSkin, rightButtonSkin,
			pauseButtonSkin;

	public UserInterface() {
		image = Assets.sprite_back;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}

	public UserInterface(Sprite background) {
		image = background;
		bounds = new Rectangle(0, 0, 1920, 1080);
	}
	
	public TextButton createButton(Stage stage, String downKey, String upKey, String downImage, String upImage,
			   BitmapFont font, int xPos, int yPos, int height, int width) {
		Skin buttonSkin = new Skin();
		buttonSkin.add(downKey, new Texture(downImage));
		buttonSkin.add(upKey, new Texture(upImage));
		
		TextButtonStyle style = new TextButtonStyle();
		
		style.font = font;
		style.down = buttonSkin.getDrawable(downKey);
		style.up = buttonSkin.getDrawable(upKey);
		
		TextButton button = new TextButton("", style);
		button.setPosition(xPos, yPos);
		button.setHeight(height);
		button.setWidth(width);
			
		return button;
	}

	public void draw(SpriteBatch batch) {
		batch.draw(image, bounds.x, bounds.y);
	}

}
