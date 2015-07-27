package com.mygdx.game.ecurri3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.gson.Gson;
import com.mygdx.gameData.GameData;
import com.mygdx.gameData.GameTimers;
import com.mygdx.gameData.PlayerData;
import com.mygdx.gameData.StoreData;

public class Store extends AndroidApplication{
	
PlayerData playerData;
GameData gameData;
GameTimers gameTimers;
	
	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		initialize(listener);
		setContentView(R.layout.activity_store);
		
		gameData = new GameData();
		gameTimers = new GameTimers();
		playerData = new PlayerData(gameData, gameTimers);
		
		TextView title = (TextView) findViewById(R.id.title);
		SpannableString content = new SpannableString("Upgrades");
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		title.setText(content);
		title.setTextColor(Color.YELLOW);
		
		final StoreData storeData = new StoreData();
		
		Button missile = (Button) findViewById(R.id.missile);
		missile.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.missileData);
            	intent.putExtra("arrayJSON", json);
                startActivity(intent);
            }
        });
		
		Button health = (Button) findViewById(R.id.health);
		health.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.healthData);
            	intent.putExtra("arrayJSON", json);
                startActivity(intent);
            }
        });
		
		Button power = (Button) findViewById(R.id.power);
		power.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.powerData);
            	intent.putExtra("arrayJSON", json);
                startActivity(intent);
            }
        });
		
		Button rapid = (Button) findViewById(R.id.rapid);
		rapid.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.rapidData);
            	intent.putExtra("arrayJSON", json);
                startActivity(intent);
            }
        });
		
		Button multi = (Button) findViewById(R.id.multi);
		multi.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.multiData);
            	intent.putExtra("arrayJSON", json);
                startActivity(intent);
            }
        });
		
		Button shotgun = (Button) findViewById(R.id.shotgun);
		shotgun.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.shotgunData);
            	intent.putExtra("arrayJSON", json);
                startActivity(intent);
            }
        });
		
		Button auto = (Button) findViewById(R.id.auto);
		auto.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.autoData);
            	intent.putExtra("arrayJSON", json);
                startActivity(intent);
            }
        });
		
		Button swiftness = (Button) findViewById(R.id.swiftness);
		swiftness.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.swiftnessData);
            	intent.putExtra("arrayJSON", json);
                startActivity(intent);
            }
        });
		
		Button big = (Button) findViewById(R.id.big);
		big.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.bigData);
            	intent.putExtra("arrayJSON", json);
                startActivity(intent);
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
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(Store.this, ActivityTest.class));
	}

}
