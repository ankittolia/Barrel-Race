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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.barrel.R;
import com.barrel.io.Preferences;

/*
 * @Description : The objective of this class object is to create the setting activity(setting page) and display from sharedPreference's object & store to SharedPreference's object selected values for different parameters shown in setting page. 
 */

public class SettingActivity extends Activity implements OnItemSelectedListener {

	public static String backgroudColor, barrelColor, horseColor; // to store
																	// the
																	// selected
																	// value of
																	// parameter
																	// as
																	// variable
																	// name
																	// signify.
	SharedPreferences sharedpreferences; // to create the object of shared
											// preference.
	String savedBgcolor, savedHorse, savedBarrelColor; // to store the values of
														// saved parameter as
														// the name signify.
	TextView headingSettings;// This variables the store the string to be
								// displayed as heading.

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
		setContentView(R.layout.settings); // setting the layout this activity.
		headingSettings = (TextView) findViewById(R.id.headingSettings);
		Typeface tf = Typeface.createFromAsset(getAssets(), "hand.ttf");
		headingSettings.setTypeface(tf);

		// Creating spinners for drop down lists to display available values and
		// select them.

		Spinner barrelSpinner = (Spinner) findViewById(R.id.barrel_spinner);
		Spinner horseSpinner = (Spinner) findViewById(R.id.speed_spinner);
		Spinner backgroundSpinner = (Spinner) findViewById(R.id.background_spinner);
		Button saveButton = (Button) findViewById(R.id.saveSetting);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout and binding the values of particular adapter to it's possible
		// values.
		ArrayAdapter<CharSequence> backgroundAdapter = ArrayAdapter
				.createFromResource(this, R.array.background_array,
						android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> barrelAdapter = ArrayAdapter
				.createFromResource(this, R.array.barrel_array,
						android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> horseAdapter = ArrayAdapter
				.createFromResource(this, R.array.horse_array,
						android.R.layout.simple_spinner_item);

		backgroundAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		barrelAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		horseAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Apply the adapter to the spinner
		backgroundSpinner.setAdapter(backgroundAdapter);
		barrelSpinner.setAdapter(barrelAdapter);
		horseSpinner.setAdapter(horseAdapter);

		// To show the selected values of different parameters from setting
		// page.
		sharedpreferences = getSharedPreferences("Setting",
				Context.MODE_PRIVATE);
		if (sharedpreferences != null) {
			savedBgcolor = sharedpreferences.getString("BackgroundColor",
					"brown");
			savedHorse = sharedpreferences.getString("HorseColor", "red");
			savedBarrelColor = sharedpreferences.getString("BarrelColor",
					"yellow");

			barrelSpinner
					.setSelection(getIndex(barrelSpinner, savedBarrelColor));
			horseSpinner.setSelection(getIndex(horseSpinner, savedHorse));
			backgroundSpinner.setSelection(getIndex(backgroundSpinner,
					savedBgcolor));
		}

		// Adding listener to background spinner(for setting background color)
		// which would get invoked when user selects any item from the drop down
		// lists. Purpose is to store
		// that value in SharedPreference.
		backgroundSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						// spin_val = gender[position];//saving the value
						// selected
						backgroudColor = (String) parent
								.getItemAtPosition(position);

					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});
		// Adding listener to barrel spinner(for setting Barrel color) which
		// would get invoked when user selects any item from the drop down
		// lists. Purpose is to store
		// that value in SharedPreference.
		barrelSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// spin_val = gender[position];//saving the value selected
				barrelColor = (String) parent.getItemAtPosition(position);

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		// Adding listener to horse spinner(for setting horse color) which would
		// get invoked when user selects any item from the drop down lists.
		// Purpose is to store
		// that value in SharedPreference.
		horseSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// spin_val = gender[position];//saving the value selected
				horseColor = (String) parent.getItemAtPosition(position);

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		// Adding on click listener to save button so that it can save the user
		// selected values for each parameters.
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { // Adding on click listener to save
											// button so that it can save the
											// user selected values for each
											// parameters.
				// Perform action on click
				new Preferences(SettingActivity.this);
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.saved_settings),
						Toast.LENGTH_SHORT).show();
				Intent mainIntent = new Intent(SettingActivity.this,
						MainActivity.class);
				mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // This flag
																	// is
																	// generally
																	// used by
																	// activities
																	// that want
																	// to
																	// present a
																	// "launcher"
																	// style
																	// behavior:
																	// they give
																	// the user
																	// a list of
																	// separate
																	// things
																	// that can
																	// be done,
																	// which
																	// otherwise
																	// run
																	// completely
																	// independently
																	// of the
																	// activity
																	// launching
																	// them.
				mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // If this
																		// flasg
																		// is
																		// set
																		// then
																		// the
																		// activity
																		// being
																		// launched
																		// is
																		// already
																		// running
																		// in
																		// the
																		// current
																		// task,
																		// then
																		// instead
																		// of
																		// launching
																		// a new
																		// instance
																		// of
																		// that
																		// activity,
																		// all
																		// of
																		// the
																		// other
																		// activities
																		// on
																		// top
																		// of it
																		// will
																		// be
																		// closed
																		// and
																		// this
																		// Intent
																		// will
																		// be
																		// delivered
																		// to
																		// the
																		// (now
																		// on
																		// top)
																		// old
																		// activity
																		// as a
																		// new
																		// Intent.
				startActivity(mainIntent); // To invoke new MainActivity.
				SettingActivity.this.finish();
			}
		});

	}

	/*
	 * getIndex method : The purpose of this function is to return the position
	 * of the string as compared to savedString in SharedPreferences.
	 * 
	 * @parameter : spinner is object for which we need to find the position of
	 * saved item.
	 * 
	 * @parameter : myString is the string to be searched from spinner object
	 * 
	 * @return int : returns the position of saved item.
	 * 
	 */
	private int getIndex(Spinner spinner, String myString) {
		int index = 0;

		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).toString()
					.equalsIgnoreCase(myString)) {
				index = i;
				i = spinner.getCount();// will stop the loop, kind of break, by
										// making condition false
			}
		}
		return index; // returns the position.
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
