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
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/*
 * @Description : The object of this class on invoked from main activity displays the activity with instructions on How to play the game.
 */
public class InstructionsActivity extends Activity {
	TextView heading; // Heading of the activity.

	// First function to be called after creating the activity.
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // To
																			// make
																			// window
																			// full
																			// screen.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.how_to_play); // To set layout.
		heading = (TextView) findViewById(R.id.howtoheading); // to get TextView
																// object to
																// display
																// heading.
		Typeface tf = Typeface.createFromAsset(getAssets(), "hand.ttf"); // Declare
																			// the
																			// fout
																			// to
																			// be
																			// used
		heading.setTypeface(tf); // Setting the font of heading.

	}
}
