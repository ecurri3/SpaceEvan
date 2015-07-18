package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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

	public TextButton createFireButton(Stage stage, String downKey, String upKey, String downImage, String upImage,
									   BitmapFont font, int xPos, int yPos, int height, int width) {
		fireButtonSkin = new Skin();
		fireButtonSkin.add("fireButtonDown", new Texture("FB_down.png"));
		fireButtonSkin.add("fireButtonUp", new Texture("FB_up.png"));
		TextButtonStyle style = new TextButtonStyle(); // ** Button properties
		// **//
		style.font = Assets.font12;
		style.up = fireButtonSkin.getDrawable("fireButtonUp");
		style.down = fireButtonSkin.getDrawable("fireButtonDown");
		fireButton = new TextButton("", style); // ** Button text and style **//
		fireButton.setPosition(1315, 100); // ** Button location **//
		fireButton.setHeight(150); // ** Button Height **//
		fireButton.setWidth(590); // ** Button Width **//
		
		return fireButton;

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
