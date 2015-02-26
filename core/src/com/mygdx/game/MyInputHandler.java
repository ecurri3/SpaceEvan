package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;

public class MyInputHandler implements InputProcessor {

    public boolean keyPressed_LEFT;
    public boolean keyPressed_RIGHT;
    public boolean keyPressed_UP;
    public boolean keyPressed_DOWN;
    public boolean keyPressed_SPACE = false;
    public boolean isTouched = false;

	
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		isTouched = true;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		isTouched = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}