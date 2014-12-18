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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.barrel.io.FileIO;

/*
 * 
 * @Description : The objective of this class is to allow user to select different options for the game.
 */

public class MainActivity extends Activity {
	TextView txtplay = null, txthighscore = null, txthow = null,
			txtSetting = null, txtExit = null; // Represents TextView object of
												// each label on Main screen.
	StringBuilder bldread = null;

	/*
	 * onCreate method : This is first method to be called when this activity is
	 * created.
	 * 
	 * @parameter : Bundle savedInstanceState is object for a mapping from
	 * String values to various parcelable types.
	 * 
	 * @return void : returns nothing.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); // to set main layout.
		Typeface tf = Typeface.createFromAsset(getAssets(), "hand.ttf"); // to
																			// set
																			// the
																			// font
																			// to
																			// be
																			// displayed.

		// to get TextView object.
		txtplay = (TextView) findViewById(R.id.playgame);
		txthighscore = (TextView) findViewById(R.id.highscores);
		txthow = (TextView) findViewById(R.id.instruct);
		txtSetting = (TextView) findViewById(R.id.setting);
		txtExit = (TextView) findViewById(R.id.exit);

		// Each text view is set with TypeInterface font.
		txtplay.setTypeface(tf);
		txthighscore.setTypeface(tf);
		txthow.setTypeface(tf);
		txtSetting.setTypeface(tf);
		txtExit.setTypeface(tf);

		// Adding on click listener to play text view which on pressed invokes
		// RaceStartActivity to start game.
		txtplay.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				txtplay.setTextColor(getResources().getColor(R.color.blue));
				Intent raceIntent = new Intent(MainActivity.this,
						RaceStartActivity.class);
				startActivity(raceIntent);
			}
		});

		// Adding on click listener to high score which invokes to
		// HighScoresListActivity to display the list of high scores.
		txthighscore.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				txthighscore
						.setTextColor(getResources().getColor(R.color.blue));

				bldread = FileIO.readScores(); // Reading the file to get the
												// list of high scores.

				if (bldread == null || bldread.length() <= 1) // to check if
																// list is
																// empty.
					Toast.makeText(
							MainActivity.this,
							getResources().getString(
									R.string.high_scores_not_avail),
							Toast.LENGTH_SHORT).show();
				else { // if high score list is not empty, invoke high score
						// activity and display the list.
					Intent scoreIntent = new Intent(getApplicationContext(),
							HighScoresListActivity.class);
					scoreIntent.putExtra("scorebldr", bldread.toString());
					startActivity(scoreIntent);
				}
			}
		});

		// Adding on click listener on How to play button to display the
		// information regarding the instructions on How to play the game.
		txthow.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				txthow.setTextColor(getResources().getColor(R.color.blue));

				Intent intructIntent = new Intent(MainActivity.this,
						InstructionsActivity.class);
				startActivity(intructIntent);
			}
		});

		// Adding on click listener to setting to invoke SettingActivity.
		txtSetting.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				txtSetting.setTextColor(getResources().getColor(R.color.blue));
				Intent settingIntent = new Intent(MainActivity.this,
						SettingActivity.class);
				startActivity(settingIntent);

			}
		});
		// Adding on click listener to setting to invoke SettingActivity.
		txtExit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						MainActivity.this);

				// set title
				alertDialogBuilder.setTitle("Barrel Race");

				// set dialog message
				alertDialogBuilder
						.setMessage("Do you wish to exit game?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										MainActivity.this.finish();

									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();

			}
		});

	}

	/*
	 * onResume method : This methods is called when we resume from
	 * inactive/pause state. Overridden to set the color to Text.
	 * 
	 * @parameter : None
	 * 
	 * @return void : None
	 * 
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		txtplay.setTextColor(getResources().getColor(R.color.black));
		txthighscore.setTextColor(getResources().getColor(R.color.black));
		txthow.setTextColor(getResources().getColor(R.color.black));
		txtSetting.setTextColor(getResources().getColor(R.color.black));
		txtExit.setTextColor(getResources().getColor(R.color.black));

	}

	//
	/*
	 * OnKeyDown method : This method is called when soft is pressed. Overridden
	 * to overwrite on back key press so that back key press invokes the
	 * MainActivity.
	 * 
	 * @parameter : keyCode is unique code of key pressed.
	 * 
	 * @parameter : keyEvent is object used to report key and button events.
	 * 
	 * @return boolean : Return true to prevent this event from being propagated
	 * further, or false to indicate that you have not handled this event and it
	 * should continue to be propagated.
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MainActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
