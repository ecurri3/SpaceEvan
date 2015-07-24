package com.mygdx.game.ecurri3;

import android.content.Intent;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;

public class Switcher extends AndroidApplication implements Screen{
	
	public Switcher(){
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		startActivity( new Intent(Switcher.this, ActivityTest.class));
		onBackPressed();
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		startActivity( new Intent(Switcher.this, ActivityTest.class));
		this.dispose();
		onBackPressed();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
