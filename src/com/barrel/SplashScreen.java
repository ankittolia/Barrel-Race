package com.barrel;

/**
 * @className CS 6301-022 User Interface Design
 * @author Ankit Tolia (abt140130)
 * @email-id abt140130@utdallas.edu
 * @version 1.0
 * @started date 30 Nov 2014
 * @Description : Barrel Race Game for an Android Device
 * @Purpose : Class Assignment
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/*
 * 
 * @Description : The objective of this class is to create splash screen  for about 2 secs then call the 
 main page of game.
 */
public class SplashScreen extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 2000; // set the timeout for the screen
	TextView appName;

	/*
	 * onCreate function is where you initialize your activity. Most
	 * importantly, here you will usually call setContentView(int) with a layout
	 * resource defining your UI, and using findViewById(int) to retrieve the
	 * widgets in that UI that you need to interact with program.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		appName=(TextView) findViewById(R.id.myImageViewText);
		Typeface tf = Typeface.createFromAsset(getAssets(), "hand.ttf");
		appName.setTypeface(tf);
		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer.
			 * 
			 * @Paremeter : None
			 * 
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}
}
