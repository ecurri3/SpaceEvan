package com.mygdx.game.ecurri3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.gson.Gson;
import com.mygdx.gameData.GameData;
import com.mygdx.gameData.GameTimers;
import com.mygdx.gameData.PlayerData;
import com.mygdx.gameData.PurchaseData;
import com.mygdx.gameData.StoreData;

public class StoreCustom extends AndroidApplication {

	// ViewAnimator and Animation declarations
	ViewAnimator viewAnimator;
	Animation slide_in_left, slide_out_right;

	// Declare TextViews used in the second screen
	TextView name_view, desc_view, cost_view, tier_view, currency;

	// Declare linear layouts used in setup of first screen
	LinearLayout colLayout, col1, col2, col3;

	// Declare buttons used in confirm purchase screen
	Button exitButton, backButton, confirmButton;

	// Load Player and Store Data
	GameData gameData;
	GameTimers gameTimers;
	PlayerData playerData;
	StoreData storeData;

	Toast toast;

	// Variables used in second screen
	// Still deciding if these are needed or not, but could provide useful in
	// the future
	String purchaseName, purchaseDesc, purchaseKey;
	int purchaseCost, purchaseTier, ownedTier;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		initialize(listener); // Initialize listeners in order to add listeners
								// to buttons
		setContentView(R.layout.activity_store_custom);

		// Assign views
		viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);
		slide_in_left = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left);
		slide_out_right = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right);

		currency = (TextView) findViewById(R.id.currency);
		name_view = (TextView) findViewById(R.id.confirmName);
		desc_view = (TextView) findViewById(R.id.confirmDesc);
		cost_view = (TextView) findViewById(R.id.confirmCost);

		colLayout = (LinearLayout) findViewById(R.id.colLayout);
		col1 = (LinearLayout) findViewById(R.id.col1);
		col2 = (LinearLayout) findViewById(R.id.col2);
		col3 = (LinearLayout) findViewById(R.id.col3);

		exitButton = (Button) findViewById(R.id.exitButton);
		backButton = (Button) findViewById(R.id.backButton);
		confirmButton = (Button) findViewById(R.id.confirmButton);

		gameData = new GameData();
		gameTimers = new GameTimers();
		playerData = new PlayerData(gameData, gameTimers);
		playerData.checkPrefs(); // Initialize player preferences
		storeData = new StoreData();

		Bundle extras = getIntent().getExtras(); // Retrieve extras
		currency.setText("Space Coins: "
				+ playerData.prefs.getInteger("currency"));

		// Convert json string to array
		String json = extras.getString("arrayJSON");
		Gson gson = new Gson();
		final PurchaseData[] customData = gson.fromJson(json,
				PurchaseData[].class);

		// If preference three does exist, we need to adjust the layout
		// Set the total weight to 3 so the columns are evenly spaced
		// And adjust the weight of the third column so it is given space
		for (int i = 0; i < customData.length; i++) {
			if (customData[i].group == 3) {
				colLayout.setWeightSum(3);
				col3.setLayoutParams(new LinearLayout.LayoutParams(0,
						LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
			}
		}

		// Traverse through our custom array
		for (int i = 0; i < customData.length; i++) {
			Button button = new Button(this); // Add new button
			button.setText(customData[i].name); // Add name from our array

			// Set layout params
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(16, 0, 16, 0);

			button.setLayoutParams(params); // apply parameters

			final int x = i; // int must be declared final to be used within
								// listener function

			/*
			 * Check the group parameter of each array spot. This integer will
			 * tell us which column the button should be placed in and what
			 * preference it uses
			 * 
			 * From this, we can check the preferences against the tier
			 * parameter also assigned in the array to determine if the upgrade
			 * has already been purchased or not
			 */
			switch (customData[i].group) {
			case 1:
				col1.addView(button);
				break;
			case 2:
				col2.addView(button);
				break;
			case 3:
				col3.addView(button);
				break;
			}

			// Add listeners after checking if an upgrade has already been
			// purchased
			final int prefInt = playerData.prefs.getInteger(customData[i].key);
			if (customData[i].tier <= prefInt) {
				button.setAlpha(0.33f);
				button.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View v) {
						toast = Toast.makeText(getApplicationContext(),
								"Already Purchased", Toast.LENGTH_LONG);
						toast.show();
					}
				});
			} else {
				button.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View v) {
						setTask(customData[x].name, customData[x].description,
								customData[x].key, customData[x].cost,
								customData[x].tier, prefInt);
						viewAnimator.showNext();
					}
				});
			}
		}

		// Add listeners for back and confirm buttons
		exitButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(StoreCustom.this, Store.class));
			}
		});

		backButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				viewAnimator.showPrevious();
			}
		});

		confirmButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TO-DO
				if (!checkPreReq(ownedTier, purchaseTier)) {
					AlertDialog builder = new AlertDialog.Builder(getContext())
							.setTitle("Error")
							.setMessage("Do not own required prerequisites.")
							.setNeutralButton("Close", null).show();
				} else if (!checkCost(purchaseCost,
						playerData.prefs.getInteger("currency"))) {
					AlertDialog builder = new AlertDialog.Builder(getContext())
							.setTitle("Error")
							.setMessage("Do not own enough Space Coins.")
							.setNeutralButton("Close", null).show();
				} else {
					purchaseUpgrade(purchaseKey, purchaseTier, purchaseCost);
					// viewAnimator.showPrevious();
					recreate();
					AlertDialog builder = new AlertDialog.Builder(getContext())
							.setTitle("Success")
							.setMessage(
									"You have purchased " + purchaseDesc + ".")
							.setNeutralButton("Close", null).show();
				}
			}
		});

	}

	/*
	 * Assign variables and set TextView text to be used on the confirm purchase
	 * screen
	 */
	public void setTask(String name, String description, String key, int cost,
			int tier, int tier_owned) {
		purchaseName = name;
		purchaseDesc = description;
		purchaseKey = key;
		purchaseCost = cost;
		purchaseTier = tier;
		ownedTier = tier_owned;

		name_view.setText(name);
		desc_view.setText(description);
		cost_view.setText("" + cost);
	}

	public boolean checkPreReq(int ownedTier, int purchaseTier) {

		if (purchaseTier > ownedTier + 1) {
			return false;
		} else
			return true;
	}

	public boolean checkCost(int purchaseCost, int currency) {

		if (currency < purchaseCost)
			return false;
		else
			return true;
	}

	public void purchaseUpgrade(String key, int tier, int cost) {
		playerData.prefs.putInteger(key, tier);
		int current = playerData.prefs.getInteger("currency");
		playerData.prefs.putInteger("currency", (current - cost));
		playerData.prefs.flush();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(StoreCustom.this, Store.class));
	}

}
