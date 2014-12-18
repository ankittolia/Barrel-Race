package com.barrel;

/**
 * @className CS 6301-022 User Interface Design
 * @author Ankit Tolia (abt140130)
 * @email-id 
 *           abt140130@utdallas.edu
 * @version 1.0
 * @started date 30 Nov 2014
 * @Description : Barrel Race Game for an Android Device
 * @Purpose : Class Assignment
 */

import com.barrel.io.FileIO;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Class Description: This class displays the score of user after s/he completes the game.
 * @Created by: Ankit Tolia
 * */
public class DisplayScoreActivity extends Activity {
	EditText name = null;
	TextView nameTxt = null, userScore = null, scoreTxt;
	Button btnsubmit = null;
	String score, showTime, askname;
	String[] timeSplit;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_completed); // set layout
		Bundle extras = getIntent().getExtras(); // get data passed from previous activity
		userScore = (TextView) findViewById(R.id.highscore);
		btnsubmit = (Button) findViewById(R.id.btnSubmit);
		scoreTxt = (TextView) findViewById(R.id.gamecompleteTxt);
		Typeface tf = Typeface.createFromAsset(getAssets(), "hand.ttf");
		scoreTxt.setTypeface(tf, Typeface.BOLD); // set text face

		// Check if user is among top scorers, if yes ask for his/her name
		if (extras != null) {
			score = extras.getString("score");
			showTime = extras.getString("showTime");
			timeSplit = showTime.split(":");

		}
		if (extras.containsKey("askname"))
			askname = extras.getString("askname");

		if (askname != null && askname.equalsIgnoreCase("true")) {
			name = (EditText) findViewById(R.id.entername);
			nameTxt = (TextView) findViewById(R.id.enternameTxt);
			nameTxt.setVisibility(View.VISIBLE);
			name.setVisibility(View.VISIBLE);
			btnsubmit.setVisibility(View.VISIBLE);
		}

		// This is code for displaying the last score

		if (score == null)
			Toast.makeText(this,
					getResources().getString(R.string.error_fetch_score),
					Toast.LENGTH_SHORT).show();
		else {
			/*
			 * Store the file names in the array so that they can be displayed
			 * as a list
			 */
			// String[] score_list = bldread.toString().split(":");
			userScore.setText(timeSplit[0] + " mins and " + timeSplit[1]
					+ " secs");
		}
		btnsubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int scoreSaved = FileIO.writeScores(name.getText().toString(),
						Long.parseLong(score)); // write scores to file
				if (scoreSaved == 1) // if file write is successfull
					Toast.makeText(DisplayScoreActivity.this,
							getResources().getString(R.string.scoresaved),
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(DisplayScoreActivity.this,
							getResources().getString(R.string.scoresavederr),
							Toast.LENGTH_SHORT).show();
				Intent myIntent = new Intent(DisplayScoreActivity.this,
						MainActivity.class);
				myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				DisplayScoreActivity.this.finish();

			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if back key of device is pressed
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (askname != null && askname.equalsIgnoreCase("true")) {
				int scoreSaved = FileIO.writeScores(name.getText().toString(),
						Long.parseLong(score)); // save scores to file
				if (scoreSaved == 1) // if write is successfull
					Toast.makeText(DisplayScoreActivity.this,
							getResources().getString(R.string.scoresaved),
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(DisplayScoreActivity.this,
							getResources().getString(R.string.scoresavederr),
							Toast.LENGTH_SHORT).show();
			}
			Intent myIntent = new Intent(DisplayScoreActivity.this,
					MainActivity.class);
			myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(myIntent);
			DisplayScoreActivity.this.finish();
		}

		return super.onKeyDown(keyCode, event);
	}
}
