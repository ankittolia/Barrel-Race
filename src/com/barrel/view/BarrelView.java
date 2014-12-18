package com.barrel.view;

/**
 * @className CS 6301-022 User Interface Design
 * @author Ankit Tolia (abt140130)
 * @email-id abt140130@utdallas.edu
 * @version 1.0
 * @started date 30 Nov 2014
 * @Description : Barrel Race Game for an Android Device
 * @Purpose : Class Assignment
 */

import com.barrel.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/*
 * BarrelView Class is used to create the game screen which includes Coloring and Drawing
 * @variable : mx,my are the X and Y coordinates.
 * @variable height and Width are the dimensions of the screen
 * @variable mpaint,mpaintHorse,mPaintLeft,mpaintcenter,mpaintright are used to paint left, right, top and Horse
 */

public class BarrelView extends View {

	public float mX;
	public float mY;
	public float height, width;
	private final int mR;
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint mPaintHorse = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint mPaintLeft = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint mPaintRight = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint mPaintTop = new Paint(Paint.ANTI_ALIAS_FLAG);
	private static final int BALL_RADIUS = 15;
	public static final int BARREL_RADIUS = 35;
	public static int BOTTOM_PADDING;
	Display display;
	SharedPreferences sharedPreferences;

	// construct new ball object
	public BarrelView(Context context, float x, float y, int r) {
		super(context);
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();
		// color hex is [transparency][red][green][blue]
		sharedPreferences = getContext().getSharedPreferences("Setting",
				Context.MODE_PRIVATE);
		String horseColor = sharedPreferences.getString("HorseColor", "red");
		if (horseColor.equalsIgnoreCase("red")) {
			mPaintHorse.setColor(getResources().getColor(R.color.red));
		} else {
			mPaintHorse.setColor(getResources().getColor(R.color.black));
		}
		mPaint.setColor(getResources().getColor(R.color.white)); // not
																	// transparent.
																	// color is
																	// green

		String barrelColor = sharedPreferences.getString("BarrelColor",
				"yellow");

		if (barrelColor.equalsIgnoreCase("skyblue")) {
			mPaintLeft.setColor(getResources().getColor(R.color.skyblue));
			mPaintRight.setColor(getResources().getColor(R.color.skyblue));
			mPaintTop.setColor(getResources().getColor(R.color.skyblue));
		} else {
			mPaintLeft.setColor(getResources().getColor(R.color.yellow));
			mPaintRight.setColor(getResources().getColor(R.color.yellow));
			mPaintTop.setColor(getResources().getColor(R.color.yellow));

		}

		this.mX = x;
		this.mY = y;
		this.mR = r; // radius
	}

	/*
	 * paintLeft is used when Circle is completed around Left Barrel then barrel
	 * color is changed.
	 */

	public void paintLeft() {
		mPaintLeft.setColor(Color.WHITE);
	}

	public void paintRight() {
		mPaintRight.setColor(Color.WHITE);
	}

	public void paintTop() {
		mPaintTop.setColor(Color.WHITE);
	}

	// called by invalidate()
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.drawColor(getResources().getColor(R.color.lightred),
		// Mode.DST);
		/*
		 * BitmapDrawable ball = (BitmapDrawable) getContext().getResources()
		 * .getDrawable(R.drawable.horse);
		 */
		canvas.drawCircle(mX, mY, BALL_RADIUS, mPaintHorse);
		float xdiv, ydiv;
		width = display.getWidth();
		height = display.getHeight();
		xdiv = 4.0f / width;
		ydiv = 4.0f / height;
		BOTTOM_PADDING = 40 * 2 + 80;
		canvas.drawCircle(width / 4, height / 2, 20, mPaintLeft); // left ball
		canvas.drawCircle((float) (width * 0.75), height / 2, 20, mPaintRight); // right
		// ball
		canvas.drawCircle(width / 2, height / 4, 20, mPaintTop); // top ball

		canvas.drawLine(0, height - BOTTOM_PADDING, width / 2 - BOTTOM_PADDING
				/ 2, height - BOTTOM_PADDING, mPaint);
		canvas.drawLine(width / 2 + BOTTOM_PADDING / 2,
				height - BOTTOM_PADDING, width, height - BOTTOM_PADDING, mPaint);

	}
}
