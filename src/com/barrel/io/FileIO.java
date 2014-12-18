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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import android.os.Environment;

/*
 * FileIO Class Handled all the File reported operations, It is used to store High Scores
 * @Variable : filename is of String type which stores the name of the File
 */

public class FileIO {
	private static final String filename = "HighScores.txt";

	/*
	 * readScores Function to read score from file and save it in StringBuilder
	 * variable. O/P : returns the StringBuilder object with all person name and
	 * scores associated with him/her.
	 * 
	 * @ return : StringBuilder which is havoing all the scores.
	 * 
	 */

	public static StringBuilder readScores() {
		File file = new File(Environment.getExternalStorageDirectory(),
				"BarrelRacing/" + filename);
		if (!file.exists()) // If file doesn't exists return null.
			return null;
		BufferedReader reader = null;
		StringBuilder bldread = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			bldread = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) { // logic to read file
															// contain line by
															// line.
				bldread.append(line + System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close(); // closing the file
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bldread;
	}

	/*
	 * getTopScores Function to read top scores from file.
	 * 
	 * @ return : ArrayLst which is having all the high scores.
	 * 
	 */

	public static ArrayList<Long> getTopScores() {
		StringBuilder bldreadscore = null;
		bldreadscore = readScores();
		if (bldreadscore == null)
			return null;
		ArrayList<Long> scoreList = new ArrayList<Long>();
		String[] chkscores = bldreadscore.toString().split(
				System.getProperty("line.separator"));
		for (int i = 1; i < chkscores.length; i++) {
			String[] scrrecord = chkscores[i].split("\t");
			scoreList.add(Long.parseLong(scrrecord[1]));
		}
		return scoreList;
	}

	/*
	 * This function writes the score and his/her score into the file.
	 * 
	 * @parameter : Name is the name of the person with High Score
	 * 
	 * @ Paramtere : finishTime is the Time in which Path is covered
	 * 
	 * @ return : int 1 for Success and 0 for Unsuccess
	 * 
	 */

	public static int writeScores(String name, long finishTime) {
		try {
			File dir = new File(Environment.getExternalStorageDirectory(),
					"BarrelRacing");
			File file = new File(dir, filename);
			BufferedWriter bufferedWriter;
			bufferedWriter = new BufferedWriter(new FileWriter(file, true));
			ArrayList<Long> scrList = null;
			StringBuilder bldreadscore = null;
			bldreadscore = readScores();
			if (bldreadscore == null || bldreadscore.length() <= 0) {
				if (!dir.exists())
					dir.mkdirs();
				if (!file.exists())
					file.createNewFile();
				if (!file.canWrite()) {
					bufferedWriter.close();
					return 0;
				}
				bufferedWriter.write("Name" + "\t" + "Time Taken(ms)"
						+ System.getProperty("line.separator"));
			} else {
				String[] chkscores = bldreadscore.toString().split(
						System.getProperty("line.separator"));
				if (chkscores.length == 11) {
					scrList = getTopScores();
					Collections.sort(scrList, Collections.reverseOrder());
					/*
					 * Create a temporary file that will contain the records not
					 * to be deleted
					 */
					File tempFile = new File(dir, "BarrelTempFile.txt");
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							tempFile));
					writer.write("Name" + "\t" + "Time Taken(ms)"
							+ System.getProperty("line.separator"));

					for (int i = 1; i < chkscores.length; i++) {
						String[] scrrecord = chkscores[i].split("\t");
						if (Long.parseLong(scrrecord[1]) != scrList.get(0))
							writer.write(chkscores[i]
									+ System.getProperty("line.separator"));
					}
					writer.write(name == null ? "" : name + "\t" + finishTime
							+ System.getProperty("line.separator"));
					writer.close();
					file.delete(); // delete the original file
					/* rename the temporary file to original file name */
					boolean success = tempFile.renameTo(file);
					if (success) {
						return 1;
					} else
						return 0;
				}
			}
			bufferedWriter.write(name == null ? "" : name + "\t" + finishTime
					+ System.getProperty("line.separator"));
			bufferedWriter.flush();
			bufferedWriter.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}