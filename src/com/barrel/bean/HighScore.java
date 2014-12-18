package com.barrel.bean;

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
 * @Description : It is used to retrieve and write the high score . And it uses
 * comparable interface to show the sorted top score. It also implements setter
 * and getter methods for person name, person's score and image of rank.
 * 
 */

public class HighScore implements Comparable<HighScore> {

	private String name; // Name of the person
	private Long time; // Time required to complete the game by person.
	private int img; // To denote the rank image.

	public HighScore(String name, Long time) {
		super();
		this.name = name;
		this.time = time;
	}

	public String getName() { // getter method for person's name.
		return name;
	}

	public Long getTime() {// getter method for person's score.
		return time;
	}

	public void setName(String name) {// setter method to store person's name.
		this.name = name;
	}

	public void setTime(Long time) {// setter method to store person's score.
		this.time = time;

	}

	public int getImage() { // getter method for getting image of rank as per
							// relative order.
		return img;
	}

	public void setImage(int img) {// setter method to set image of rank as per
									// relative order.
		this.img = img;

	}

	@Override
	public String toString() { // it returns combined string of person's name
								// and score.
		return "HighScore [name=" + name + ", time=" + time + "]";
	}

	/*
	 * It is used to compare the score to determine relative order.
	 * 
	 * @parameter : HighScore o is high object.
	 * 
	 * @return int : a negative value if the value of this long is less than the
	 * value of object; 0 if the value of this long and the value of object are
	 * equal; a positive value if the value of this long is greater than the
	 * value of object.
	 * 
	 */
	public int compareTo(HighScore o) {
		return this.time.compareTo(o.time);
	}
}