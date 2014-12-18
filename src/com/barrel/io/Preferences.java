package com.barrel.io;

/**
 * @className CS 6301-022 User Interface Design
 * @author Ankit Tolia (abt140130)
 * @email-id abt140130@utdallas.edu
 * @version 1.0
 * @started date 30 Nov 2014
 * @Description : Barrel Race Game for an Android Device
 * @Purpose : Class Assignment
 */

import com.barrel.SettingActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PorterDuff.Mode;
import android.widget.HorizontalScrollView;

/*
 * Preferences Class is used to store the TEmperary setting of the App
 * @Variable : context is required to get Activity references
 * @Variable : backgroundColor, barrelColor takes the settings
 */

public class Preferences {
	// SharedPreferences.Editor editor;
	Context context;
	String backgroundColor, barrelColor;

	/*
	 * Constructor to Initialize the Value.
	 */

	public Preferences(Context context) { // Constructor initializes context
											// which is then used to get
											// SharedPreferences object.
		this.context = context;
		setSettings();
	}

	/*
	 * This method stores the values of the different parameters from setting
	 * page such as barrel color, background color and horse color. return void
	 * 
	 */

	public void setSettings() {
		Editor editor = context.getSharedPreferences("Setting",
				context.MODE_PRIVATE).edit(); // MODE_PRIVATE mode prohibits
												// this SharedPreferences to be
												// accessed by other apps.
		// to store the values of different paremeters in SharedPreference.
		editor.putString("BackgroundColor", SettingActivity.backgroudColor);
		editor.putString("BarrelColor", SettingActivity.barrelColor);
		editor.putString("HorseColor", SettingActivity.horseColor);
		editor.commit(); // to commit those changes in SharedPrefernce.
	}
}
