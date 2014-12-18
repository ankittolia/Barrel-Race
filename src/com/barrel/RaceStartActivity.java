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
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.barrel.io.FileIO;
import com.barrel.view.BarrelView;

/*
 * @Description : The objective of this class is to draw the game screen and redraw the game surface at periodic intervals based on sensor readings.
 */

public class RaceStartActivity extends Activity implements SensorEventListener {
	// Initialize variables
	BarrelView mBallView = null; // Barrel View instance that draws the screen
									// contents
	Handler RedrawHandler = new Handler(); // redraw thread
	Timer mTmr = null; // timer task to redraw the game surface
	TimerTask mTsk = null; // Task that periodically redraws the game surface
	float mScrWidth, mScrHeight; // screen width and height
	android.graphics.PointF mBallPos, mBallSpd;
	private TextView textTimer, txtReady;
	private ImageButton startButton;
	private ImageButton pauseButton;
	private long startTime = 0L;
	private Handler myHandler = new Handler();
	long timeInMillies = 0L;
	long timeSwap = 0L; // time for hitting the wall
	long finalTime = 0L; // total time
	BarrelPositionCompute B;
	boolean finishFlag;
	boolean startFlag = false;
	long elapsedTime = 0;
	int x, y;
	// This section is used to limit the vibration to only once when circle is
	// completed
	public int vibeAfterLeft = 1; // Left Barrel Vibration
	public int vibeAfterRight = 1; // Right Barrel Vibration
	public int vibeAfterTop = 1; // Top Barrel Vibration
	public int vibeAfterLeft1 = 1;
	String totalTime = null;
	boolean paused = false;
	boolean afterStarting = false;
	float pausedy, pausedx;
	private SensorManager mSensorManager; // Sensor Manager for reading sensor
											// readings
	private Sensor mAccel;
	WindowManager.LayoutParams lparams;
	FrameLayout mainView;
	SharedPreferences sharedpreferences;
	String bgcolor;
	boolean scrLocked = false, vibrated = false;
	private CountDownTimer countDownTimer; // timer for resuming the app from
											// background
	final float alpha = 0.85f; // Used to set speed for the ball
	TextView startFinish = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.race_start);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mainView = (FrameLayout) findViewById(R.id.main_view);
		mainView.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.background));
		// Retrieve the user saved settings
		sharedpreferences = getSharedPreferences("Setting",
				Context.MODE_PRIVATE);
		bgcolor = sharedpreferences.getString("BackgroundColor", "brown");
		Typeface tf = Typeface.createFromAsset(getAssets(), "hand.ttf");

		startButton = (ImageButton) findViewById(R.id.btnStart);
		pauseButton = (ImageButton) findViewById(R.id.btnPause);
		textTimer = (TextView) findViewById(R.id.textTimer);
		txtReady = (TextView) findViewById(R.id.ready);
		txtReady.setTypeface(tf);
		startFinish = (TextView) findViewById(R.id.startfinish);
		textTimer.setEnabled(false);

		// On click of start button
		startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startFlag = true;
				afterStarting = true;
				startButton.setVisibility(View.GONE);
				// set background color according to user settings
				mainView.setBackgroundResource(R.drawable.custom_border);
				GradientDrawable drawable = (GradientDrawable) mainView
						.getBackground();
				if (bgcolor.equalsIgnoreCase("green"))
					drawable.setColor(Color.GREEN);
				else
					drawable.setColor(getResources().getColor(R.color.brown));
				startFinish.setVisibility(View.VISIBLE);

				textTimer.setVisibility(View.VISIBLE);
				mBallView.setVisibility(View.VISIBLE);
				pauseButton.setVisibility(View.VISIBLE);

				// start the game after three second countdown timer
				countDownTimer = new CountDownTimer(3000, 1000) {

					@Override
					public void onTick(long leftTimeInMilliseconds) {
						long seconds = leftTimeInMilliseconds / 1000;
						txtReady.setVisibility(View.VISIBLE);
						txtReady.setText(seconds + "");
					}

					@Override
					public void onFinish() {
						// this function will be called when the timecount is
						// finished
						txtReady.setVisibility(View.GONE);

						startTime = SystemClock.uptimeMillis(); // returns time
																// since start
																// of the game
						myHandler.postDelayed(updateTimerMethod, 0);

						mBallPos.x = mScrWidth / 2;
						mBallPos.y = mScrHeight - 20;
						if (RaceStartActivity.this != null
								&& !RaceStartActivity.this.isFinishing())
							mSensorManager.registerListener(
									RaceStartActivity.this, mAccel,
									SensorManager.SENSOR_DELAY_NORMAL);

					}
				}.start();

			}
		});

		// on click of pause button on game screen
		pauseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (paused) {
					// invoked when the game is already in pause mode
					startTime = SystemClock.uptimeMillis();
					if (RaceStartActivity.this != null
							&& !RaceStartActivity.this.isFinishing()) {
						mSensorManager.registerListener(RaceStartActivity.this,
								mAccel, SensorManager.SENSOR_DELAY_NORMAL); // register
																			// the
																			// sensor
																			// so
																			// that
																			// game
																			// is
																			// resumed

						pauseButton.setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.pause));
						timeSwap = 0;
						elapsedTime += finalTime;
						paused = false;
						myHandler.postDelayed(updateTimerMethod, 0);
						mBallPos.x = pausedx;
						mBallPos.y = pausedy;
					}
				} else {
					// invoked when the user clicks the pause button on game
					// screen
					mSensorManager.unregisterListener(RaceStartActivity.this,
							mAccel); // unregister the sensor so that ball
										// doesn't move
					pauseButton.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.start_game));

					pausedy = mBallPos.y;
					pausedx = mBallPos.x;
					mBallPos.x = pausedx;
					mBallPos.y = pausedy;
					mBallSpd.x = 0;
					mBallSpd.y = 0;
					paused = true;

				}
			}
		});

		Display display = getWindowManager().getDefaultDisplay();

		mScrWidth = display.getWidth() - 10;
		mScrHeight = display.getHeight() - 15;
		mBallPos = new android.graphics.PointF();
		mBallSpd = new android.graphics.PointF();

		// create variables for ball position and speed
		mBallPos.x = mScrWidth / 2;
		mBallPos.y = mScrHeight - 20;
		mBallSpd.x = 0;
		mBallSpd.y = 0;

		// create initial ball
		mBallView = new BarrelView(this, mBallPos.x, mBallPos.y, 5);

		mainView.addView(mBallView); // add ball to main screen

		mBallView.invalidate(); // call onDraw in BallView
		if (startFlag == false)
			mBallView.setVisibility(View.GONE);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		float a = alpha * mBallSpd.x + (1 - alpha) * event.values[0];
		float b = alpha * mBallSpd.y + (1 - alpha) * event.values[1];
		mBallSpd.x = -event.values[0] - a; // set ball speed
		mBallSpd.y = event.values[1] - b;

	}

	// listener for menu button on phone
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add("Exit"); // only one menu item
		return false;

	}

	// listener for menu item clicked
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		if (item.getTitle() == "Exit") // user clicked Exit
			finish(); // will call onPause
		return super.onOptionsItemSelected(item);
	}

	// invoked when the app goes in background
	@Override
	protected void onUserLeaveHint() { // TODO Auto-generated
		super.onUserLeaveHint();
		paused = false;
		pauseButton.performClick();
	}

	@Override
	public void onPause() // app moved to background, stop background threads
	{
		super.onPause();
		mTmr.cancel(); // kill\release timer (our only background thread)
		mTmr = null;
		mTsk = null;
	}

	@Override
	public void onResume() // app moved to foreground (also occurs at app
	// startup)
	{
		if (paused == true && !(startButton.getVisibility() == View.VISIBLE)) {
			// start the countdown timer once the app is resumed from background
			startTimer();
		}
		// create timer to move ball to new position
		Display display = getWindowManager().getDefaultDisplay();
		B = new BarrelPositionCompute();
		B.leftBarrelCentreX = (int) (display.getWidth() / 4);
		B.rightBarrelCentreX = (int) (display.getWidth() * 3 / 4);
		B.topBarrelCentreX = (int) (display.getWidth() / 2);
		B.leftBarrelCentreY = (int) display.getHeight() / 2;
		B.rightBarrelCentreY = (int) (display.getHeight() / 2);
		B.topBarrelCentreY = (int) (display.getHeight() / 4);

		// create a timer task that redraws the game surface periodically and
		// updates the ball position
		mTmr = new Timer();
		mTsk = new TimerTask() {

			public void run() {

				if (!paused) {
					
					Vibrator vibe = (Vibrator) getApplicationContext()
							.getSystemService(Context.VIBRATOR_SERVICE);

					// move ball based on current speed
					mBallPos.x += mBallSpd.x;
					mBallPos.y += mBallSpd.y;
					// if ball goes off screen, reposition to opposite side
					// of
					// screen
					if (mBallPos.x > mScrWidth - 10) {
						mBallPos.x = mScrWidth - 40;
						timeSwap += 5000;
						vibe.vibrate(50);
					}
					if (mBallPos.y > mScrHeight - 40) {
						mBallPos.y = mScrHeight - 50;
					}
					if (mBallPos.x < 10) {
						mBallPos.x = 40;
						timeSwap += 5000;
						vibe.vibrate(50);
					}
					if (mBallPos.y < 10) {
						mBallPos.y = 40;
						timeSwap += 5000;
						vibe.vibrate(50);
					}

					double a = (((mScrWidth / 4) + 2.5) - mBallPos.x);
					double b = (((mScrHeight / 2) + 4) - mBallPos.y);// left
					// ball
					// barrel
					// violation
					double d = (a * a) + (b * b);
					if (Math.sqrt(d) < 35 && !vibrated) {
						// Vibrate device once the barrel is touched
						vibe.vibrate(50);

						vibrated = true;
						RaceStartActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								// show a prompt letting the user to retry or
								// cancel the game
								createAlertDialog();
							}
						});

					}
					a = (((mScrWidth / 2) + 5) - mBallPos.x);
					b = (((mScrHeight / 4) + 2.5) - mBallPos.y);// top ball
					// circle
					// violation
					d = (a * a) + (b * b);
					if (Math.sqrt(d) < 35 && !vibrated) {
						vibe.vibrate(50);
						vibrated = true;
						RaceStartActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								// show a prompt letting the user to retry or
								// cancel the game
								createAlertDialog();
							}
						});

					}
					a = (((mScrWidth * 3 / 4) + 7.5) - mBallPos.x);
					b = (((mScrHeight / 2) + 7.5) - mBallPos.y); // right
					// ball
					// circle
					// violation
					d = (a * a) + (b * b);
					if (Math.sqrt(d) < 35 && !vibrated) {
						vibe.vibrate(50);

						vibrated = true;
						RaceStartActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								createAlertDialog();
							}
						});

					}

					// update ball class instance
					mBallView.mX = mBallPos.x;
					mBallView.mY = mBallPos.y;
					x = (int) mBallView.mX;
					y = (int) mBallView.mY;

					if (y < mScrHeight - 130 && y > mScrHeight - 140 && x > 0
							&& x < ((mScrWidth / 2) - ((mScrWidth / 2) - 150))) {
						mBallPos.y = mScrHeight - 130;
					}
					if (y < mScrHeight - 130 && y > mScrHeight - 140
							&& x < mScrWidth
							&& x > ((mScrWidth / 2) + ((mScrWidth / 2) - 180))) {
						mBallPos.y = mScrHeight - 130;
					}
					if (y < mScrHeight - 140 && y > mScrHeight - 160 && x > 0
							&& x < ((mScrWidth / 2) - ((mScrWidth / 2) - 150))) {
						mBallPos.y = mScrHeight - 180;
						timeSwap += 5000;
						vibe.vibrate(50);
					}
					if (y < mScrHeight - 140 && y > mScrHeight - 160
							&& x < mScrWidth
							&& x > ((mScrWidth / 2) + ((mScrWidth / 2) - 180))) {
						mBallPos.y = mScrHeight - 180;
						timeSwap += 5000;
						vibe.vibrate(50);
					}
					if (x < ((mScrWidth / 2) - ((mScrWidth / 2) - 190))
							&& y > mScrHeight - 150) {
						mBallPos.x = ((mScrWidth / 2) - ((mScrWidth / 2) - 190));
					}
					if (x > ((mScrWidth / 2) + ((mScrWidth / 2) - 190))
							&& y > mScrHeight - 150) {
						mBallPos.x = ((mScrWidth / 2) + ((mScrWidth / 2) - 190));
					}

					finishFlag = B.playLogic(x, y, mScrWidth, mScrHeight);

					if (B.flag == 1) { // changes the barrel color when flag is
										// updated
						if (B.leftBarrelRoundCompleted == true) {
							B.leftBottom = 2;
							B.leftTop = 2;
							B.leftRight = 2;
							B.leftLeft = 2;
							B.leftBarrelPositiveX = 2;
							B.leftBarrelNegativeX = 2;
							B.leftBarrelPositiveY = 2;
							B.leftBarrelNegativeY = 2;
							mBallView.paintLeft();
						}
						if (B.rightBarrelRoundCompleted == true) {
							B.rightRight = 2;
							B.rightleft = 2;
							B.rightBottom = 2;
							B.rightTop = 2;
							B.rightBarrelPositiveX = 2;
							B.rightBarrelNegativeX = 2;
							B.rightBarrelPositiveY = 2;
							B.rightBarrelNegativeY = 2;
							mBallView.paintRight();
						}
						if (B.topBarrelRoundCompleted == true) {
							B.topRight = 2;
							B.topLeft = 2;
							B.topBottom = 2;
							B.topBarrelPositiveX = 2;
							B.topBarrelNegativeX = 2;
							B.topBarrelPositiveY = 2;
							B.topBarrelNegativeY = 2;
							mBallView.paintTop();
						}
					} else if (B.flag == 2) {
						if (B.leftBarrelRoundCompleted == true) {
							B.leftBottom = 2;
							B.leftTop = 2;
							B.leftRight = 2;
							B.leftLeft = 2;
							B.leftBarrelPositiveX = 2;
							B.leftBarrelNegativeX = 2;
							B.leftBarrelPositiveY = 2;
							B.leftBarrelNegativeY = 2;
							mBallView.paintLeft();
						}
						if (B.rightBarrelRoundCompleted == true) {
							B.rightRight = 2;
							B.rightleft = 2;
							B.rightBottom = 2;
							B.rightTop = 2;
							B.rightBarrelPositiveX = 2;
							B.rightBarrelNegativeX = 2;
							B.rightBarrelPositiveY = 2;
							B.rightBarrelNegativeY = 2;
							mBallView.paintRight();
						}
						if (B.topBarrelRoundCompleted == true) {
							B.topRight = 2;
							B.topLeft = 2;
							B.topBottom = 2;
							B.topBarrelPositiveX = 2;
							B.topBarrelNegativeX = 2;
							B.topBarrelPositiveY = 2;
							B.topBarrelNegativeY = 2;
							mBallView.paintTop();
						}
					} else if (B.flag == 3) {
						if (B.leftBarrelRoundCompleted == true) {
							B.leftBottom = 2;
							B.leftTop = 2;
							B.leftRight = 2;
							B.leftLeft = 2;
							B.leftBarrelPositiveX = 2;
							B.leftBarrelNegativeX = 2;
							B.leftBarrelPositiveY = 2;
							B.leftBarrelNegativeY = 2;
							mBallView.paintLeft();
						}
						if (B.rightBarrelRoundCompleted == true) {
							B.rightRight = 2;
							B.rightleft = 2;
							B.rightBottom = 2;
							B.rightTop = 2;
							B.rightBarrelPositiveX = 2;
							B.rightBarrelNegativeX = 2;
							B.rightBarrelPositiveY = 2;
							B.rightBarrelNegativeY = 2;
							mBallView.paintRight();
						}
						if (B.topBarrelRoundCompleted == true) {
							B.topRight = 2;
							B.topLeft = 2;
							B.topBottom = 2;
							B.topBarrelPositiveX = 2;
							B.topBarrelNegativeX = 2;
							B.topBarrelPositiveY = 2;
							B.topBarrelNegativeY = 2;
							mBallView.paintTop();
						}
					}

					// if the horse completes rounds across all the circles
					if (finishFlag) {
						B.rightBarrelRoundCompleted = false;
						finishFlag = false;
						mBallView.paintRight();

						// Invoke DisplayScore Activity and pass the score
						// variable
						Intent myIntent = new Intent(getApplicationContext(),
								DisplayScoreActivity.class);
						Bundle extras = new Bundle();
						extras.putString("score", finalTime + "");
						extras.putString("showTime", totalTime + "");
						ArrayList<Long> topScrList = FileIO.getTopScores(); // get
																			// Scores
																			// from
																			// file
						if (topScrList != null && topScrList.size() > 0) {
							if (topScrList.size() < 10)
								extras.putString("askname", "true");
							else {
								Collections.sort(topScrList); // sort the top
																// scores from
																// file
								if (topScrList.get(topScrList.size() - 1) > finalTime) { // if
																							// user
																							// score
																							// is
																							// lower
																							// than
																							// the
																							// slowest
																							// time
																							// in
																							// file
																							// than
																							// insert
																							// user
																							// record
																							// into
																							// file
									extras.putString("askname", "true");
								} else
									extras.putString("askname", "false");
							}
						} else
							extras.putString("askname", "true");
						myIntent.putExtras(extras);

						startActivity(myIntent);
						RaceStartActivity.this.finish();
					}

					RedrawHandler.post(ballredraw); // redraw the view
				}

			}
		};

		mTmr.schedule(mTsk, 10, 20);

		super.onResume();
	}

	// Handle Screen Level Changes
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private Runnable ballredraw = new Runnable() {

		public void run() {
			mBallView.invalidate(); // redraw the game surface
		}
	};

	// Compute the total time elapsed since the game is started
	private Runnable updateTimerMethod = new Runnable() {

		public void run() {
			if (paused) // if game is in paused state, stop computing the time
				timeSwap = 0;
			else { // compute the time since the game is started along with the
					// time incurred due to hitting the wall(adding five
					// seconds)
				timeInMillies = SystemClock.uptimeMillis() - startTime;
				finalTime = timeSwap + timeInMillies;
			}
			// compute time in seconds and minutes
			int seconds = (int) ((finalTime + elapsedTime) / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			int milliseconds = (int) ((finalTime + elapsedTime) % 1000);
			textTimer.setText("" + minutes + ":"
					+ String.format("%02d", seconds) + ":"
					+ String.format("%03d", milliseconds));
			totalTime = "" + minutes + ":" + String.format("%02d", seconds);
			myHandler.postDelayed(this, 0);
		}

	};

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the back key of device is pressed, if yes ask for user
		// confirmation on exit
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& !(startButton.getVisibility() == View.VISIBLE)) {
			paused = false;
			pauseButton.performClick(); // pauses the app

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					RaceStartActivity.this); // displays an alert dialog

			// set title of alert dialog
			alertDialogBuilder.setTitle("Barrel Race");

			// set dialog message
			alertDialogBuilder
					.setMessage("Are you sure you want to quit the game?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, close
									// current activity
									mSensorManager = null;

									Intent myIntent = new Intent(
											RaceStartActivity.this,
											MainActivity.class);
									myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(myIntent);
									RaceStartActivity.this.finish();

								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
									pauseButton.performClick();
									paused = false;
								}
							});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();

		}

		return super.onKeyDown(keyCode, event);
	}

	/*
	 * resumes from background adding a five second delay Return: void
	 */
	private void startTimer() {
		// create the countdown timer object which runs for five seconds
		countDownTimer = new CountDownTimer(5000, 1000) {

			@Override
			public void onTick(long leftTimeInMilliseconds) {
				long seconds = leftTimeInMilliseconds / 1000;
				txtReady.setVisibility(View.VISIBLE);
				txtReady.setText(seconds + "");
			}

			@Override
			public void onFinish() {
				// this function will be called when the timecount is finished
				txtReady.setVisibility(View.GONE);
				pauseButton.performClick();
				paused = false;
			}
		}.start();
	}

	/*
	 * barrel is crashed. Return: Void
	 */
	private void createAlertDialog() {
		pauseButton.performClick();
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				RaceStartActivity.this);

		// set title
		alertDialogBuilder.setTitle("Barrel Race");

		// set dialog message
		alertDialogBuilder
				.setMessage("Whoa!! Barrel Crashed")
				.setCancelable(false)
				.setPositiveButton("Retry",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity and start a new one
								Intent myIntent1 = new Intent(
										RaceStartActivity.this,
										RaceStartActivity.class);
								myIntent1
										.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								myIntent1
										.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(myIntent1);
								RaceStartActivity.this.finish();

							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, just close
								// the dialog box and start main activity
								dialog.cancel();
								Intent myIntent1 = new Intent(
										RaceStartActivity.this,
										MainActivity.class);
								myIntent1
										.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								myIntent1
										.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(myIntent1);
								RaceStartActivity.this.finish();
							}
						});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}

}