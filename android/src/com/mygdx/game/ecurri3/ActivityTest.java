package com.mygdx.game.ecurri3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.mygdx.gameData.*;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class ActivityTest extends AndroidApplication{
	
	PlayerData playerData;
	
	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		initialize(listener);
		setContentView(R.layout.activity_test);
		
		Button startButton = (Button) findViewById(R.id.startButton);
		Button storeButton = (Button) findViewById(R.id.storeButton);
		startButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ActivityTest.this, AndroidLauncher.class));
            }
        });
		
		storeButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ActivityTest.this, Store.class));
            }
        });
		
	}

	@Override
	public void onPause () {
		super.onPause();
	}

	@Override
	public void onDestroy () {
		super.onDestroy();
	}

}
