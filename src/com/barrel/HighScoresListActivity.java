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

import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.barrel.adapter.CustomListViewAdapter;
import com.barrel.bean.HighScore;

/*
 * @Description : The objective of this class is to load the list of high score from file and then display it as per the ranking. 
 */
public class HighScoresListActivity extends ListActivity {
	// Intialize variables
	String bldStr = null;
	StringBuilder bldread = null;
	TextView scoreTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_score_list); // to set high score list
													// layout
		// to get TextView object and apply font on it using TypeFace.
		scoreTxt = (TextView) findViewById(R.id.scoreTxt);
		scoreTxt.setText(getResources().getString(R.string.highscores));
		Typeface tf = Typeface.createFromAsset(getAssets(), "hand.ttf");
		scoreTxt.setTypeface(tf, Typeface.BOLD);

		bldStr = getIntent().getStringExtra("scorebldr"); // To read high score
															// list.
		bldread = new StringBuilder(bldStr);

		String[] scoreArr = bldread.toString().split(
				System.getProperty("line.separator"));

		ArrayList<HighScore> scoreList = new ArrayList<HighScore>();
		for (int i = 1; i < scoreArr.length; i++) { // to store all the high
													// score in ArrayList.
			String[] recScore = scoreArr[i].split("\t");
			scoreList.add(new HighScore(recScore[0], Long
					.parseLong(recScore[1])));
		}

		Collections.sort(scoreList); // Sorting the list to give rank.
		for (int k = 0; k < scoreList.size(); k++) { // top 3 scorers
			if (k == 3)
				break;
			scoreList.get(k).setImage(1);
		}

		setListAdapter(new CustomListViewAdapter(this, R.layout.list_item,
				scoreList)); // sets the adapter for listview to display its elements
	}
}
