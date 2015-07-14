package com.mygdx.game.ecurri3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.gson.Gson;
import com.mygdx.gameData.PlayerData;
import com.mygdx.gameData.StoreData;

public class Store extends AndroidApplication{
	
PlayerData playerData;
	
	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		initialize(listener);
		setContentView(R.layout.activity_store);
		
		final StoreData storeData = new StoreData();
		
		Button missile = (Button) findViewById(R.id.missile);
		missile.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	intent.putExtra("pref1",  "mUpAuto");
            	intent.putExtra("pref2", "mUpReload");
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.missileData);
            	intent.putExtra("arrayJSON", json);
                startActivity(intent);
            }
        });
		
		Button rapid = (Button) findViewById(R.id.rapid);
		rapid.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Store.this, StoreCustom.class);
            	intent.putExtra("pref1",  "rapidUpDur");
            	intent.putExtra("pref2", "rapidUpReload");
            	intent.putExtra("pref3", "rapidUpRicochet");
            	Gson gson = new Gson();
            	String json = gson.toJson(storeData.rapidData);
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

}
