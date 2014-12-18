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

/*
 * BarrelPosition class is used to check the position of the Horse and records
 * which Barrel is Circled completely by the Horse.
 * 
 * @Variable : leftBarrelCentreX, leftBarrelCentreY,
 * rightBarrelCentreX,rightBarrelCentreY, topBarrelCentreX, topBarrelCentreY All
 * these variables stores the X and Y Coordinates of Left, Right and Top Barrel.
 * 
 * @Variable : leftBarrelPositiveX,leftBarrelNegativeX
 * ,leftBarrelPositiveY,leftBarrelNegativeY
 * ,rightBarrelPositiveX,rightBarrelNegativeX
 * ,rightBarrelPositiveY,rightBarrelNegativeY
 * ,topBarrelPositiveX,topBarrelNegativeX ,topBarrelPositiveY,topBarrelNegativeY
 * are the Four Coordinates around each Barrel
 * 
 * @Variable :
 * topLeft,topRight,topBottom,rightLeft,rightRight,rightTop,rightBottom
 * ,leftLeft,leftRight,leftTop,leftBottom keeps the track that from which point
 * the Circle is started
 * 
 * @Variable :
 * leftBarrelRoundCompleted,rightBarrelRoundCompleted,topBarrelRoundCompleted
 * Keeps tracks which Barrel Circle is completed.
 * 
 * @Variable : flag keeps track of the number of Barrels whose Circle is
 * completed.
 * 
 * @Variable : finishFlag is true when the Horse will complete the path and else
 * it will be false
 */

public class BarrelPositionCompute {
	int leftBarrelCentreX, leftBarrelCentreY, rightBarrelCentreX,
			rightBarrelCentreY, topBarrelCentreX, topBarrelCentreY;
	int leftBarrelPositiveX = 0, leftBarrelNegativeX = 0,
			leftBarrelPositiveY = 0, leftBarrelNegativeY = 0,
			rightBarrelPositiveX = 0, rightBarrelNegativeX = 0,
			rightBarrelPositiveY = 0, rightBarrelNegativeY = 0,
			topBarrelPositiveX = 0, topBarrelNegativeX = 0,
			topBarrelPositiveY = 0, topBarrelNegativeY = 0;
	int topLeft = 0, topRight = 0, topBottom = 0;
	int rightleft = 0, rightRight = 0, rightTop = 0, rightBottom = 0;
	int leftLeft = 0, leftRight = 0, leftTop = 0, leftBottom = 0;
	boolean leftBarrelRoundCompleted;
	boolean rightBarrelRoundCompleted;
	boolean topBarrelRoundCompleted;
	int flag = 0;
	boolean finishFlag;

	/*
	 * playLogic method is implementing the Logic to check the position of the
	 * Horse and records which Barrel is Circled completely by the Horse.
	 * 
	 * @parameter : x is the the position of the Horse for x Axis
	 * 
	 * @parameter : y is the the position of the Horse for y Axis
	 * 
	 * @parameter : mScrWidth is the Width of the Screen
	 * 
	 * @parameter : mScrHeight is the Height of the Screen
	 * 
	 * @return boolean : returns the Finish Flag
	 * 
	 */

	public boolean playLogic(int x, int y, float mScrWidth, float mScrHeight) {
		if (topBarrelRoundCompleted == false) { // If Circle Around top Barrel
												// is not completed
			if (topLeft == 0 // When Circle is started from bottom of Top Barrel
								// and approaches to Right
					&& topRight == 0
					&& (topBarrelPositiveY == 1 || (x < topBarrelCentreX + 20
							&& x > topBarrelCentreX - 20
							&& y >= topBarrelCentreY + 20 && y <= leftBarrelCentreY + 20))) {
				topBarrelPositiveY = 1; // Set Positive Y of Top Barrel as 1
										// when Horse is reached their
				topBottom = 1; // Set Bottom of Top BArrel as 1 which indicate
								// that the Circle has started from this point
				topLeft = 0; // Set Other points as 0 as it is starting from top
								// Bottom
				topRight = 0;
				if (topBarrelPositiveX == 1 // Set 1 if Horse reaches Right of
											// Top Barrel
						|| (x > topBarrelCentreX + 20
								&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
					topBarrelPositiveX = 1;
					if (leftBarrelRoundCompleted == false) { // Set all points
																// of other 2
																// barrels as 0
						leftBottom = 0;
						leftTop = 0;
						leftRight = 0;
						leftLeft = 0;
						leftBarrelPositiveX = 0;
						leftBarrelNegativeX = 0;
						leftBarrelPositiveY = 0;
						leftBarrelNegativeY = 0;
					}
					if (rightBarrelRoundCompleted == false) {
						rightRight = 0;
						rightleft = 0;
						rightBottom = 0;
						rightTop = 0;
						rightBarrelPositiveX = 0;
						rightBarrelNegativeX = 0;
						rightBarrelPositiveY = 0;
						rightBarrelNegativeY = 0;
					}
					if (topBarrelNegativeY == 1 // Set 1 if Horse reaches Top of
												// Top Barrel
							|| (y < topBarrelCentreY - 20
									&& x <= topBarrelCentreX + 25 && x >= topBarrelCentreX - 25)) {
						topBarrelNegativeY = 1;
						if (topBarrelNegativeX == 1 // Set 1 if Horse reaches
													// Left of Top Barrel
								|| (x < topBarrelCentreX - 20
										&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
							topBarrelNegativeX = 1;
							if (y > topBarrelCentreY + 20 // Set 1 if Horse
															// reaches Bottom of
															// Top Barrel again
									&& x <= topBarrelCentreX + 5
									&& x >= topBarrelCentreX - 5) {
								if (topBottom == 1) {
									topBarrelRoundCompleted = true; // Set that
																	// Top
																	// Barrel
																	// Circle is
																	// completed
									flag = 1; // set Flag 1
								}
							}
						}
					}
				}
			}
			if (topLeft == 0 // When Circle is started from bottom of Top Barrel
								// and approaches to Left
					&& topRight == 0
					&& (topBarrelPositiveY == 1 || (x < topBarrelCentreX + 20
							&& x > topBarrelCentreX - 20
							&& y >= topBarrelCentreY + 25 && y <= leftBarrelCentreY + 25))) {
				topBarrelPositiveY = 1;
				topBottom = 1;
				topLeft = 0;
				topRight = 0;
				if (topBarrelNegativeX == 1
						|| (x < topBarrelCentreX - 20
								&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
					topBarrelNegativeX = 1;
					if (leftBarrelRoundCompleted == false) {
						leftBottom = 0;
						leftTop = 0;
						leftRight = 0;
						leftLeft = 0;
						leftBarrelPositiveX = 0;
						leftBarrelNegativeX = 0;
						leftBarrelPositiveY = 0;
						leftBarrelNegativeY = 0;
					}
					if (rightBarrelRoundCompleted == false) {
						rightRight = 0;
						rightleft = 0;
						rightBottom = 0;
						rightTop = 0;
						rightBarrelPositiveX = 0;
						rightBarrelNegativeX = 0;
						rightBarrelPositiveY = 0;
						rightBarrelNegativeY = 0;
					}
					if (topBarrelNegativeY == 1
							|| (y < topBarrelCentreY - 20
									&& x <= topBarrelCentreX + 25 && x >= topBarrelCentreX - 25)) {
						topBarrelNegativeY = 1;
						if (topBarrelPositiveX == 1
								|| (x > topBarrelCentreX + 20
										&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
							topBarrelPositiveX = 1;
							if (y > topBarrelCentreY + 20
									&& x <= topBarrelCentreX + 5
									&& x >= topBarrelCentreX - 5) {
								if (topBottom == 1) {
									topBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (topLeft == 0 // When Circle is started from Right of Top Barrel
								// and approaches to Top
					&& topBottom == 0
					&& (topBarrelPositiveX == 1 || (x > topBarrelCentreX + 20
							&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25))) {
				topBarrelPositiveX = 1;
				topLeft = 0;
				topBottom = 0;
				topRight = 1;
				if (leftBarrelRoundCompleted == false) {
					leftBottom = 0;
					leftTop = 0;
					leftRight = 0;
					leftLeft = 0;
					leftBarrelPositiveX = 0;
					leftBarrelNegativeX = 0;
					leftBarrelPositiveY = 0;
					leftBarrelNegativeY = 0;
				}
				if (rightBarrelRoundCompleted == false) {
					rightRight = 0;
					rightleft = 0;
					rightBottom = 0;
					rightTop = 0;
					rightBarrelPositiveX = 0;
					rightBarrelNegativeX = 0;
					rightBarrelPositiveY = 0;
					rightBarrelNegativeY = 0;
				}
				if (topBarrelNegativeY == 1
						|| (y < topBarrelCentreY - 20
								&& x <= topBarrelCentreX + 25 && x >= topBarrelCentreX - 25)) {
					topBarrelNegativeY = 1;
					if (topBarrelNegativeX == 1
							|| (x < topBarrelCentreX - 20
									&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
						topBarrelNegativeX = 1;
						if (topBarrelPositiveY == 1
								|| (x < topBarrelCentreX + 20
										&& x > topBarrelCentreX - 20
										&& y <= rightBarrelCentreY && y >= topBarrelCentreY + 20)) {
							topBarrelPositiveY = 1;
							if (x > topBarrelCentreX + 20
									&& y >= topBarrelCentreY - 25
									&& y <= topBarrelCentreY + 25) {
								if (topRight == 1) {
									topBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (topLeft == 0 // When Circle is started from Right of Top Barrel
								// and approaches to Bottom
					&& topBottom == 0
					&& (topBarrelPositiveX == 1 || (x > topBarrelCentreX + 20
							&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25))) {
				topBarrelPositiveX = 1;
				topLeft = 0;
				topBottom = 0;
				topRight = 1;
				if (leftBarrelRoundCompleted == false) {
					leftBottom = 0;
					leftTop = 0;
					leftRight = 0;
					leftLeft = 0;
					leftBarrelPositiveX = 0;
					leftBarrelNegativeX = 0;
					leftBarrelPositiveY = 0;
					leftBarrelNegativeY = 0;
				}
				if (rightBarrelRoundCompleted == false) {
					rightRight = 0;
					rightleft = 0;
					rightBottom = 0;
					rightTop = 0;
					rightBarrelPositiveX = 0;
					rightBarrelNegativeX = 0;
					rightBarrelPositiveY = 0;
					rightBarrelNegativeY = 0;
				}
				if (topBarrelPositiveY == 1
						|| (x < topBarrelCentreX + 20
								&& x > topBarrelCentreX - 20
								&& y <= rightBarrelCentreY && y >= topBarrelCentreY + 20)) {
					topBarrelPositiveY = 1;
					if (topBarrelNegativeX == 1
							|| (x < topBarrelCentreX - 20
									&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
						topBarrelNegativeX = 1;
						if (topBarrelNegativeY == 1
								|| (y < topBarrelCentreY - 20
										&& x <= topBarrelCentreX + 25 && x >= topBarrelCentreX - 25)) {
							topBarrelNegativeY = 1;
							if ((x > topBarrelCentreX + 20
									&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
								if (topRight == 1) {
									topBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (topBottom == 0 // When Circle is started from Left of Top Barrel
								// and approaches to Bottom
					&& topRight == 0
					&& (topBarrelNegativeX == 1 || (x < topBarrelCentreX - 20
							&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25))) {
				topBarrelNegativeX = 1;
				topLeft = 1;
				topBottom = 0;
				topRight = 0;
				if (leftBarrelRoundCompleted == false) {
					leftBottom = 0;
					leftTop = 0;
					leftRight = 0;
					leftLeft = 0;
					leftBarrelPositiveX = 0;
					leftBarrelNegativeX = 0;
					leftBarrelPositiveY = 0;
					leftBarrelNegativeY = 0;
				}
				if (rightBarrelRoundCompleted == false) {
					rightRight = 0;
					rightleft = 0;
					rightBottom = 0;
					rightTop = 0;
					rightBarrelPositiveX = 0;
					rightBarrelNegativeX = 0;
					rightBarrelPositiveY = 0;
					rightBarrelNegativeY = 0;
				}
				if (topBarrelPositiveY == 1
						|| (x < topBarrelCentreX + 20
								&& x > topBarrelCentreX - 20
								&& y <= rightBarrelCentreY && y >= topBarrelCentreY + 20)) {
					topBarrelPositiveY = 1;
					if (topBarrelPositiveX == 1
							|| (x > topBarrelCentreX + 20
									&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
						topBarrelPositiveX = 1;
						if (topBarrelNegativeY == 1
								|| (y < topBarrelCentreY - 20
										&& x <= topBarrelCentreX + 25 && x >= topBarrelCentreX - 25)) {
							topBarrelNegativeY = 1;
							if ((x < topBarrelCentreX - 20
									&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
								if (topLeft == 1) {
									topBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (topBottom == 0 // When Circle is started from Left of Top Barrel
								// and approaches to Top
					&& topRight == 0
					&& (topBarrelNegativeX == 1 || (x < topBarrelCentreX - 20
							&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25))) {
				topBarrelNegativeX = 1;
				topLeft = 1;
				topBottom = 0;
				topRight = 0;
				if (leftBarrelRoundCompleted == false) {
					leftBottom = 0;
					leftTop = 0;
					leftRight = 0;
					leftLeft = 0;
					leftBarrelPositiveX = 0;
					leftBarrelNegativeX = 0;
					leftBarrelPositiveY = 0;
					leftBarrelNegativeY = 0;
				}
				if (rightBarrelRoundCompleted == false) {
					rightRight = 0;
					rightleft = 0;
					rightBottom = 0;
					rightTop = 0;
					rightBarrelPositiveX = 0;
					rightBarrelNegativeX = 0;
					rightBarrelPositiveY = 0;
					rightBarrelNegativeY = 0;
				}
				if (topBarrelNegativeY == 1
						|| (y < topBarrelCentreY - 20
								&& x <= topBarrelCentreX + 25 && x >= topBarrelCentreX - 25)) {
					topBarrelNegativeY = 1;
					if (topBarrelPositiveX == 1
							|| (x > topBarrelCentreX + 20
									&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
						topBarrelPositiveX = 1;
						if (topBarrelPositiveY == 1
								|| (x < topBarrelCentreX + 20
										&& x > topBarrelCentreX - 20
										&& y <= rightBarrelCentreY && y >= topBarrelCentreY + 20)) {
							topBarrelPositiveY = 1;
							if ((x < topBarrelCentreX - 20
									&& y >= topBarrelCentreY - 25 && y <= topBarrelCentreY + 25)) {
								if (topLeft == 1) {
									topBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
		}

		if (rightBarrelRoundCompleted == false) { // If Circle Around Right
													// Barrel is not completed
			if (rightRight == 0 // When Circle is started from Left of Right
								// Barrel and approaches to Top
					&& rightTop == 0
					&& rightBottom == 0
					&& (rightBarrelNegativeX == 1 || (x < rightBarrelCentreX - 20
							&& x > leftBarrelCentreX + 20
							&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25))) {
				rightBarrelNegativeX = 1;
				rightRight = 0;
				rightleft = 1;
				rightTop = 0;
				rightBottom = 0;
				if (rightBarrelNegativeY == 1
						|| (y < rightBarrelCentreY - 20
								&& y > topBarrelCentreY + 20
								&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25)) {
					rightBarrelNegativeY = 1;
					if (leftBarrelRoundCompleted == false) {
						leftBottom = 0;
						leftTop = 0;
						leftRight = 0;
						leftLeft = 0;
						leftBarrelPositiveX = 0;
						leftBarrelNegativeX = 0;
						leftBarrelPositiveY = 0;
						leftBarrelNegativeY = 0;
					}
					if (topBarrelRoundCompleted == false) {
						topRight = 0;
						topLeft = 0;
						topBottom = 0;
						topBarrelPositiveX = 0;
						topBarrelNegativeX = 0;
						topBarrelPositiveY = 0;
						topBarrelNegativeY = 0;
					}
					if (rightBarrelPositiveX == 1
							|| (x > rightBarrelCentreX + 20
									&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25)) {
						rightBarrelPositiveX = 1;
						if (rightBarrelPositiveY == 1
								|| (y > rightBarrelCentreY + 20
										&& y < mScrHeight - 170
										&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25)) {
							rightBarrelPositiveY = 1;
							if (x < rightBarrelCentreX - 20
									&& x > leftBarrelCentreX + 20
									&& y <= rightBarrelCentreY + 25
									&& y >= rightBarrelCentreY - 25) {
								if (rightleft == 1) {
									rightBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}

			if (rightRight == 0 // When Circle is started from Left of Right
								// Barrel and approaches to Bottom
					&& rightTop == 0
					&& rightBottom == 0
					&& (rightBarrelNegativeX == 1 || (x < rightBarrelCentreX - 20
							&& x > leftBarrelCentreX + 20
							&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25))) {
				rightBarrelNegativeX = 1;
				rightRight = 0;
				rightleft = 1;
				rightTop = 0;
				rightBottom = 0;
				if (rightBarrelPositiveY == 1
						|| (y > rightBarrelCentreY + 20 && y < mScrHeight - 170
								&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25)) {
					rightBarrelPositiveY = 1;
					if (leftBarrelRoundCompleted == false) {
						leftBottom = 0;
						leftTop = 0;
						leftRight = 0;
						leftLeft = 0;
						leftBarrelPositiveX = 0;
						leftBarrelNegativeX = 0;
						leftBarrelPositiveY = 0;
						leftBarrelNegativeY = 0;
					}
					if (topBarrelRoundCompleted == false) {
						topRight = 0;
						topLeft = 0;
						topBottom = 0;
						topBarrelPositiveX = 0;
						topBarrelNegativeX = 0;
						topBarrelPositiveY = 0;
						topBarrelNegativeY = 0;
					}
					if (rightBarrelPositiveX == 1
							|| (x > rightBarrelCentreX + 20
									&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25)) {
						rightBarrelPositiveX = 1;
						if (rightBarrelNegativeY == 1
								|| (y < rightBarrelCentreY - 20
										&& y > topBarrelCentreY + 20
										&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25)) {
							rightBarrelNegativeY = 1;
							if (x < rightBarrelCentreX - 20
									&& x > leftBarrelCentreX + 20
									&& y <= rightBarrelCentreY + 25
									&& y >= rightBarrelCentreY - 25) {
								if (rightleft == 1) {
									rightBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (rightRight == 0 // When Circle is started from Bottom of Right
								// Barrel and approaches to Right
					&& rightleft == 0
					&& rightTop == 0
					&& (rightBarrelPositiveY == 1 || (y > rightBarrelCentreY + 20
							&& y < mScrHeight - 170
							&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25))) {
				rightBarrelPositiveY = 1;
				rightRight = 0;
				rightleft = 0;
				rightTop = 0;
				rightBottom = 1;
				if (leftBarrelRoundCompleted == false) {
					leftBottom = 0;
					leftTop = 0;
					leftRight = 0;
					leftLeft = 0;
					leftBarrelPositiveX = 0;
					leftBarrelNegativeX = 0;
					leftBarrelPositiveY = 0;
					leftBarrelNegativeY = 0;
				}
				if (topBarrelRoundCompleted == false) {
					topRight = 0;
					topLeft = 0;
					topBottom = 0;
					topBarrelPositiveX = 0;
					topBarrelNegativeX = 0;
					topBarrelPositiveY = 0;
					topBarrelNegativeY = 0;
				}
				if (rightBarrelPositiveX == 1
						|| (x > rightBarrelCentreX + 20
								&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25)) {
					rightBarrelPositiveX = 1;
					if (rightBarrelNegativeY == 1
							|| (y < rightBarrelCentreY - 20
									&& y > topBarrelCentreY + 20
									&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25)) {
						rightBarrelNegativeY = 1;
						if (rightBarrelNegativeX == 1
								|| (x < rightBarrelCentreX - 20
										&& x > leftBarrelCentreX + 20
										&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25)) {
							rightBarrelNegativeX = 1;
							if ((y > rightBarrelCentreY + 20
									&& y < mScrHeight - 170
									&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25)) {
								if (rightBottom == 1) {
									rightBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (rightRight == 0 // When Circle is started from Bottom of Right
								// Barrel and approaches to Left
					&& rightleft == 0
					&& rightTop == 0
					&& (rightBarrelPositiveY == 1 || (y > rightBarrelCentreY + 20
							&& y < mScrHeight - 170
							&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25))) {
				rightBarrelPositiveY = 1;
				rightRight = 0;
				rightleft = 0;
				rightTop = 0;
				rightBottom = 1;
				if (leftBarrelRoundCompleted == false) {
					leftBottom = 0;
					leftTop = 0;
					leftRight = 0;
					leftLeft = 0;
					leftBarrelPositiveX = 0;
					leftBarrelNegativeX = 0;
					leftBarrelPositiveY = 0;
					leftBarrelNegativeY = 0;
				}
				if (topBarrelRoundCompleted == false) {
					topRight = 0;
					topLeft = 0;
					topBottom = 0;
					topBarrelPositiveX = 0;
					topBarrelNegativeX = 0;
					topBarrelPositiveY = 0;
					topBarrelNegativeY = 0;
				}
				if (rightBarrelNegativeX == 1
						|| (x < rightBarrelCentreX - 20
								&& x > leftBarrelCentreX + 20
								&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25)) {
					rightBarrelNegativeX = 1;
					if (rightBarrelNegativeY == 1
							|| (y < rightBarrelCentreY - 20
									&& y > topBarrelCentreY + 20
									&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25)) {
						rightBarrelNegativeY = 1;
						if (rightBarrelPositiveX == 1
								|| (x > rightBarrelCentreX + 20
										&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25)) {
							rightBarrelPositiveX = 1;

							if ((y > rightBarrelCentreY + 20
									&& y < mScrHeight - 170
									&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25)) {
								if (rightBottom == 1) {
									rightBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (rightRight == 0 // When Circle is started from Top of Right
								// Barrel and approaches to Right
					&& rightleft == 0
					&& rightBottom == 0
					&& (rightBarrelNegativeY == 1 || (y < rightBarrelCentreY - 20
							&& y > topBarrelCentreY + 20
							&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25))) {
				rightBarrelNegativeY = 1;
				rightRight = 0;
				rightleft = 0;
				rightTop = 1;
				rightBottom = 0;
				if (leftBarrelRoundCompleted == false) {
					leftBottom = 0;
					leftTop = 0;
					leftRight = 0;
					leftLeft = 0;
					leftBarrelPositiveX = 0;
					leftBarrelNegativeX = 0;
					leftBarrelPositiveY = 0;
					leftBarrelNegativeY = 0;
				}
				if (topBarrelRoundCompleted == false) {
					topRight = 0;
					topLeft = 0;
					topBottom = 0;
					topBarrelPositiveX = 0;
					topBarrelNegativeX = 0;
					topBarrelPositiveY = 0;
					topBarrelNegativeY = 0;
				}
				if (rightBarrelPositiveX == 1
						|| ((x > (rightBarrelCentreX + 20)) && (x < mScrWidth)
								&& (y <= (rightBarrelCentreY + 25)) && (y >= (rightBarrelCentreY - 25)))) {
					rightBarrelPositiveX = 1;
					if ((rightBarrelPositiveY == 1 || (y > rightBarrelCentreY + 20
							&& y < mScrHeight - 170
							&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25))) {
						rightBarrelPositiveY = 1;
						if (rightBarrelNegativeX == 1
								|| (x < rightBarrelCentreX - 20
										&& x > leftBarrelCentreX + 20
										&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25)) {
							rightBarrelNegativeX = 1;
							if (y < rightBarrelCentreY - 20
									&& y > topBarrelCentreY + 20
									&& x <= rightBarrelCentreX + 25
									&& x >= rightBarrelCentreX - 25) {
								if (rightTop == 1) {
									rightBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (rightRight == 0 // When Circle is started from Top of Right
								// Barrel and approaches to Left
					&& rightleft == 0
					&& rightBottom == 0
					&& (rightBarrelNegativeY == 1 || (y < rightBarrelCentreY - 20
							&& y > topBarrelCentreY + 20
							&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25))) {
				rightBarrelNegativeY = 1;
				rightRight = 0;
				rightleft = 0;
				rightTop = 1;
				rightBottom = 0;
				if (leftBarrelRoundCompleted == false) {
					leftBottom = 0;
					leftTop = 0;
					leftRight = 0;
					leftLeft = 0;
					leftBarrelPositiveX = 0;
					leftBarrelNegativeX = 0;
					leftBarrelPositiveY = 0;
					leftBarrelNegativeY = 0;
				}
				if (topBarrelRoundCompleted == false) {
					topRight = 0;
					topLeft = 0;
					topBottom = 0;
					topBarrelPositiveX = 0;
					topBarrelNegativeX = 0;
					topBarrelPositiveY = 0;
					topBarrelNegativeY = 0;
				}
				if (rightBarrelNegativeX == 1
						|| (x < rightBarrelCentreX - 20
								&& x > leftBarrelCentreX + 20
								&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25)) {
					rightBarrelNegativeX = 1;
					if ((rightBarrelPositiveY == 1 || (y > rightBarrelCentreY + 20
							&& y < mScrHeight - 170
							&& x <= rightBarrelCentreX + 25 && x >= rightBarrelCentreX - 25))) {
						rightBarrelPositiveY = 1;
						if (rightBarrelPositiveX == 1
								|| (x > rightBarrelCentreX + 20
										&& y <= rightBarrelCentreY + 25 && y >= rightBarrelCentreY - 25)) {
							rightBarrelPositiveX = 1;
							if (y < rightBarrelCentreY - 20
									&& y > topBarrelCentreY + 20
									&& x <= rightBarrelCentreX + 25
									&& x >= rightBarrelCentreX - 25) {
								if (rightTop == 1) {
									rightBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
		}

		if (leftBarrelRoundCompleted == false) { // If Circle Around Left Barrel
													// is not completed
			if (leftLeft == 0 // When Circle is started from Right of Left
								// Barrel and approaches to Top
					&& leftTop == 0
					&& leftBottom == 0
					&& (leftBarrelPositiveX == 1 || (x < rightBarrelCentreX - 20
							&& x > leftBarrelCentreX + 20
							&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25))) {
				leftBarrelPositiveX = 1;
				leftRight = 1;
				leftLeft = 0;
				leftTop = 0;
				leftBottom = 0;
				if (leftBarrelNegativeY == 1
						|| (y < leftBarrelCentreY - 20
								&& y > topBarrelCentreY + 20
								&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25)) {
					leftBarrelNegativeY = 1;
					if (topBarrelRoundCompleted == false) {
						topRight = 0;
						topLeft = 0;
						topBottom = 0;
						topBarrelPositiveX = 0;
						topBarrelNegativeX = 0;
						topBarrelPositiveY = 0;
						topBarrelNegativeY = 0;
					}
					if (rightBarrelRoundCompleted == false) {
						rightRight = 0;
						rightleft = 0;
						rightBottom = 0;
						rightTop = 0;
						rightBarrelPositiveX = 0;
						rightBarrelNegativeX = 0;
						rightBarrelPositiveY = 0;
						rightBarrelNegativeY = 0;
					}
					if (leftBarrelNegativeX == 1
							|| (x < leftBarrelCentreX - 20
									&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25)) {
						leftBarrelNegativeX = 1;

						if (leftBarrelPositiveY == 1
								|| (y > leftBarrelCentreY + 20
										&& y < mScrHeight - 170
										&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25)) {
							leftBarrelPositiveY = 1;
							if (x > leftBarrelCentreX + 20
									&& x < rightBarrelCentreX - 20
									&& y <= leftBarrelCentreY + 25
									&& y >= leftBarrelCentreY - 25) {
								if (leftRight == 1) {
									leftBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (leftLeft == 0 // When Circle is started from Right of Left
								// Barrel and approaches to Bottom
					&& leftTop == 0
					&& leftBottom == 0
					&& (leftBarrelPositiveX == 1 || (x < rightBarrelCentreX - 20
							&& x > leftBarrelCentreX + 20
							&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25))) {
				leftBarrelPositiveX = 1;
				leftRight = 1;
				leftLeft = 0;
				leftTop = 0;
				leftBottom = 0;
				if (leftBarrelPositiveY == 1
						|| (y > leftBarrelCentreY + 20 && y < mScrHeight - 170
								&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25)) {
					leftBarrelPositiveY = 1;
					if (topBarrelRoundCompleted == false) {
						topRight = 0;
						topLeft = 0;
						topBottom = 0;
						topBarrelPositiveX = 0;
						topBarrelNegativeX = 0;
						topBarrelPositiveY = 0;
						topBarrelNegativeY = 0;
					}
					if (rightBarrelRoundCompleted == false) {
						rightRight = 0;
						rightleft = 0;
						rightBottom = 0;
						rightTop = 0;
						rightBarrelPositiveX = 0;
						rightBarrelNegativeX = 0;
						rightBarrelPositiveY = 0;
						rightBarrelNegativeY = 0;
					}
					if (leftBarrelNegativeX == 1
							|| (x < leftBarrelCentreX - 20
									&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25)) {
						leftBarrelNegativeX = 1;

						if (leftBarrelNegativeY == 1
								|| (y < leftBarrelCentreY - 20
										&& y > topBarrelCentreY + 20
										&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25)) {
							leftBarrelNegativeY = 1;
							if (x > leftBarrelCentreX + 20
									&& x < rightBarrelCentreX - 20
									&& y <= leftBarrelCentreY + 25
									&& y >= leftBarrelCentreY - 25) {
								if (leftRight == 1) {
									leftBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (leftRight == 0 // When Circle is started from Bottom of Left
								// Barrel and approaches to Left
					&& leftLeft == 0
					&& leftTop == 0
					&& (leftBarrelPositiveY == 1 || (y > leftBarrelCentreY + 20
							&& y < mScrHeight - 170
							&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25))) {
				leftBarrelPositiveY = 1;
				leftRight = 0;
				leftLeft = 0;
				leftTop = 0;
				leftBottom = 1;
				if (leftBarrelNegativeX == 1
						|| (x < leftBarrelCentreX - 20
								&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25)) {
					leftBarrelNegativeX = 1;
					if (topBarrelRoundCompleted == false) {
						topRight = 0;
						topLeft = 0;
						topBottom = 0;
						topBarrelPositiveX = 0;
						topBarrelNegativeX = 0;
						topBarrelPositiveY = 0;
						topBarrelNegativeY = 0;
					}
					if (rightBarrelRoundCompleted == false) {
						rightRight = 0;
						rightleft = 0;
						rightBottom = 0;
						rightTop = 0;
						rightBarrelPositiveX = 0;
						rightBarrelNegativeX = 0;
						rightBarrelPositiveY = 0;
						rightBarrelNegativeY = 0;
					}
					if (leftBarrelNegativeY == 1
							|| (y < leftBarrelCentreY - 20
									&& y > topBarrelCentreY + 20
									&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25)) {
						leftBarrelNegativeY = 1;
						if (leftBarrelPositiveX == 1
								|| (x > leftBarrelCentreX + 20
										&& x < rightBarrelCentreX - 20
										&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25)) {
							leftBarrelPositiveX = 1;
							if (y > leftBarrelCentreY + 20
									&& y < mScrHeight - 170
									&& x >= leftBarrelCentreX - 25
									&& x <= leftBarrelCentreX + 25) {
								if (leftBottom == 1) {
									leftBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (leftRight == 0 // When Circle is started from Bottom of Left
								// Barrel and approaches to Right
					&& leftLeft == 0
					&& leftTop == 0
					&& (leftBarrelPositiveY == 1 || (y > leftBarrelCentreY + 20
							&& y < mScrHeight - 170
							&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25))) {
				leftBarrelPositiveY = 1;
				leftRight = 0;
				leftLeft = 0;
				leftTop = 0;
				leftBottom = 1;
				if (topBarrelRoundCompleted == false) {
					topRight = 0;
					topLeft = 0;
					topBottom = 0;
					topBarrelPositiveX = 0;
					topBarrelNegativeX = 0;
					topBarrelPositiveY = 0;
					topBarrelNegativeY = 0;
				}
				if (rightBarrelRoundCompleted == false) {
					rightRight = 0;
					rightleft = 0;
					rightBottom = 0;
					rightTop = 0;
					rightBarrelPositiveX = 0;
					rightBarrelNegativeX = 0;
					rightBarrelPositiveY = 0;
					rightBarrelNegativeY = 0;
				}
				if (leftBarrelPositiveX == 1
						|| (x > leftBarrelCentreX + 20
								&& x < rightBarrelCentreX - 20
								&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25)) {
					leftBarrelPositiveX = 1;
					if (leftBarrelNegativeY == 1
							|| (y < leftBarrelCentreY - 20
									&& y > topBarrelCentreY + 20
									&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25)) {
						leftBarrelNegativeY = 1;
						if (leftBarrelNegativeX == 1
								|| (x < leftBarrelCentreX - 20
										&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25)) {
							leftBarrelNegativeX = 1;
							if (y > leftBarrelCentreY + 20
									&& y < mScrHeight - 170
									&& x >= leftBarrelCentreX - 25
									&& x <= leftBarrelCentreX + 25) {
								if (leftBottom == 1) {
									leftBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (leftRight == 0 // When Circle is started from Top of Left Barrel
								// and approaches to Right
					&& leftLeft == 0
					&& leftBottom == 0
					&& (leftBarrelNegativeY == 1 || (y < leftBarrelCentreY - 20
							&& y > topBarrelCentreY + 20
							&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25))) {
				leftBarrelNegativeY = 1;
				leftRight = 0;
				leftLeft = 0;
				leftTop = 1;
				leftBottom = 0;
				if (topBarrelRoundCompleted == false) {
					topRight = 0;
					topLeft = 0;
					topBottom = 0;
					topBarrelPositiveX = 0;
					topBarrelNegativeX = 0;
					topBarrelPositiveY = 0;
					topBarrelNegativeY = 0;
				}
				if (rightBarrelRoundCompleted == false) {
					rightRight = 0;
					rightleft = 0;
					rightBottom = 0;
					rightTop = 0;
					rightBarrelPositiveX = 0;
					rightBarrelNegativeX = 0;
					rightBarrelPositiveY = 0;
					rightBarrelNegativeY = 0;
				}
				if (leftBarrelPositiveX == 1
						|| (x > leftBarrelCentreX + 20
								&& x < rightBarrelCentreX - 20
								&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25)) {
					leftBarrelPositiveX = 1;
					if (leftBarrelPositiveY == 1
							|| (y > leftBarrelCentreY + 20
									&& y < mScrHeight - 170
									&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25)) {
						leftBarrelPositiveY = 1;
						if (leftBarrelNegativeX == 1
								|| (x < leftBarrelCentreX - 20
										&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25)) {
							leftBarrelNegativeX = 1;

							if (y < leftBarrelCentreY - 20
									&& y > topBarrelCentreY + 20
									&& x >= leftBarrelCentreX - 25
									&& x <= leftBarrelCentreX + 25) {
								if (leftTop == 1) {
									leftBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
			if (leftRight == 0 // When Circle is started from Top of Left Barrel
								// and approaches to Left
					&& leftLeft == 0
					&& leftBottom == 0
					&& (leftBarrelNegativeY == 1 || (y < leftBarrelCentreY - 20
							&& y > topBarrelCentreY + 20
							&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25))) {
				leftBarrelNegativeY = 1;
				leftRight = 0;
				leftLeft = 0;
				leftTop = 1;
				leftBottom = 0;
				if (topBarrelRoundCompleted == false) {
					topRight = 0;
					topLeft = 0;
					topBottom = 0;
					topBarrelPositiveX = 0;
					topBarrelNegativeX = 0;
					topBarrelPositiveY = 0;
					topBarrelNegativeY = 0;
				}
				if (rightBarrelRoundCompleted == false) {
					rightRight = 0;
					rightleft = 0;
					rightBottom = 0;
					rightTop = 0;
					rightBarrelPositiveX = 0;
					rightBarrelNegativeX = 0;
					rightBarrelPositiveY = 0;
					rightBarrelNegativeY = 0;
				}
				if (leftBarrelNegativeX == 1
						|| (x < leftBarrelCentreX - 20
								&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25)) {
					leftBarrelNegativeX = 1;
					if (leftBarrelPositiveY == 1
							|| (y > leftBarrelCentreY + 20
									&& y < mScrHeight - 170
									&& x >= leftBarrelCentreX - 25 && x <= leftBarrelCentreX + 25)) {
						leftBarrelPositiveY = 1;
						if (leftBarrelPositiveX == 1
								|| (x > leftBarrelCentreX + 20
										&& x < rightBarrelCentreX - 20
										&& y <= leftBarrelCentreY + 25 && y >= leftBarrelCentreY - 25)) {
							leftBarrelPositiveX = 1;
							if (y < leftBarrelCentreY - 20
									&& y > topBarrelCentreY + 20
									&& x >= leftBarrelCentreX - 25
									&& x <= leftBarrelCentreX + 25) {
								if (leftTop == 1) {
									leftBarrelRoundCompleted = true;
									flag = 1;
								}
							}
						}
					}
				}
			}
		}
		if (rightBarrelRoundCompleted == true // If All the Barrels, Circle is
												// completed
				&& leftBarrelRoundCompleted == true
				&& topBarrelRoundCompleted == true) {
			if (y > mScrHeight - 140) // Go BAck to Start line
				finishFlag = true; // Below Start line
			else
				finishFlag = false; // Above Start Line
		} else
			finishFlag = false; // when Circles are not completed
		return finishFlag;
	}

}