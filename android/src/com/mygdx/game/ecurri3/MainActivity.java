package com.mygdx.game.ecurri3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyGame;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AndroidApplication {

	private static final String AD_UNIT_ID = "ca-app-pub-2698355908852800/1713416173";
	protected AdView adView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

		//initialize(new MyGame(), cfg);

		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libgdx View
		View gameView = initializeForView(new MyGame(), cfg);

//		// Create and setup the AdMob view
//		AdView adView = createAdView(); // Put in your secret key here
//		AdRequest adRequest = new AdRequest.Builder().build();
//		adView.loadAd(adRequest);

		// Add the libgdx view
		layout.addView(gameView);

//		// Add the AdMob view
//		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT);
//		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//
//		layout.addView(adView, adParams);
//
//		// Hook it all up
		setContentView(layout);
//		startAdvertising(adView);
	}

	private AdView createAdView() {
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(AD_UNIT_ID);
		adView.setId(12345); // this is an arbitrary id, allows for relative
								// positioning in createGameView()
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		adView.setLayoutParams(params);
		adView.setBackgroundColor(Color.BLACK);
		return adView;
	}
	
	private void startAdvertising(AdView adView) {
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
	  }

}
