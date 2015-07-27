package com.mygdx.game.ecurri3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewAnimator;

import com.mygdx.gameData.*;
import com.badlogic.gdx.backends.android.AndroidApplication;

public class ActivityTest extends AndroidApplication {

	PlayerData playerData;

	// ViewAnimator and Animation declarations
	ViewAnimator viewAnimator;
	Animation slide_in_left, slide_out_right;
	
	//Button declarations
	Button startButton, storeButton, normalMode, expertMode, backButton;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		initialize(listener);
		setContentView(R.layout.activity_test);

		// Assign views
		viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);
		slide_in_left = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left);
		slide_out_right = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right);

		startButton = (Button) findViewById(R.id.startButton);
		normalMode = (Button) findViewById(R.id.normalMode);
		expertMode = (Button) findViewById(R.id.expertMode);
		backButton = (Button) findViewById(R.id.backButton);
		storeButton = (Button) findViewById(R.id.storeButton);
		
		startButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				viewAnimator.showNext();
			}
		});
		
		normalMode.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				Intent intent = new Intent(ActivityTest.this, AndroidLauncher.class);
				intent.putExtra("expertMode", false);
				startActivity(intent);
			}
		});
		
		expertMode.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				Intent intent = new Intent(ActivityTest.this, AndroidLauncher.class);
				intent.putExtra("expertMode", true);
				startActivity(intent);
			}
		});
		
		backButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				viewAnimator.showPrevious();
			}
		});

		storeButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(ActivityTest.this, Store.class));
			}
		});

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
