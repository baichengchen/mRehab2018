package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.example.matt2929.strokeappdec2017.AmazonCloud.UploadToAmazonBucket;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by matt2929 on 1/3/18.
 */

public class SaveWorkoutJSON {
	ArrayList<WorkoutJSON> workoutJSONS = new ArrayList<>();
	Context context;
	UploadToAmazonBucket uploadToAmazonBucket;

	public SaveWorkoutJSON(Context context) {
		this.context = context;
		uploadToAmazonBucket = new UploadToAmazonBucket(context);
	}


	public ArrayList<WorkoutJSON> getWorkouts() {
		File fileParent = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "RehabApplicationJSON2019");
		ArrayList<File> Files = new ArrayList<>(getAllFiles(fileParent));
		workoutJSONS.clear();
		for (File f : Files) {
			BufferedReader br = null;
			String fileText = "";
			try {
				br = new BufferedReader(new FileReader(f));
				String line = "";
				while ((line = br.readLine()) != null) {
					fileText += line;
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			workoutJSONS.add(new WorkoutJSON(fileText));
		}
		return workoutJSONS;
	}
/*
	public void addNewWorkout(String WorkoutName, String Hand, float Duration, float Accuracy, ArrayList<Float> scores, int Reps) {
		Calendar currentCalendar = Calendar.getInstance();
		String fileName = WorkoutData.UserName + "_" + "FullVersion" + "_" + WorkoutName + "_" + currentCalendar.get(Calendar.YEAR) + "~" +
				(currentCalendar.get(Calendar.MONTH)+1) + "~" + currentCalendar.get(Calendar.DAY_OF_MONTH) + "_[" + currentCalendar.get(Calendar.HOUR_OF_DAY) + "h~" + currentCalendar.get(Calendar.MINUTE) + "m" + currentCalendar.get(Calendar.SECOND) + "]_JSON.json";
		//Below is from baseline
		//String fileName = WorkoutData.UserName + "_" + WorkoutName + "_" + currentCalendar.get(Calendar.YEAR) + "~"
		// + (currentCalendar.get(Calendar.MONTH)+1) + "~" + currentCalendar.get(Calendar.DAY_OF_MONTH) + "_[" + currentCalendar.get(Calendar.HOUR_OF_DAY) + "h~" + currentCalendar.get(Calendar.MINUTE) + "m" + currentCalendar.get(Calendar.SECOND) + "]_JSON.json";
		String output = "";
		WorkoutJSON newWorkoutJSON = new WorkoutJSON(context, WorkoutData.UserName, WorkoutName, Reps, Duration, Accuracy, scores, Hand);
		output = newWorkoutJSON.getJSONString();
		File fileParent = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "RehabApplicationJSON2019");
		if (!fileParent.exists()) {
			fileParent.mkdir();
		}
		File file = new File(fileParent, fileName);
		PrintWriter writer;
		if (file.exists()) {
			file.delete();

		}
		try {
			file.createNewFile();
			writer = new PrintWriter(new FileWriter(file, true));
			writer.append(output);
			writer.close();
			ConnectivityManager ConnectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected() == true) {
				uploadToAmazonBucket.saveData(file);
			} else {
				WorkoutData.progressCloud = 100f;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addNewWorkout(String WorkoutName, String Hand, float Duration,ArrayList<Float> Durations, float Accuracy, int Reps) {
		Calendar currentCalendar = Calendar.getInstance();
		String fileName = WorkoutData.UserName + "_" + "FullVersion" + "_" + WorkoutName + "_" + currentCalendar.get(Calendar.YEAR) + "~" + (currentCalendar.get(Calendar.MONTH)+1) + "~" +
				currentCalendar.get(Calendar.DAY_OF_MONTH) + "_[" + currentCalendar.get(Calendar.HOUR_OF_DAY) + "h~" + currentCalendar.get(Calendar.MINUTE) + "m" + currentCalendar.get(Calendar.SECOND) + "].json";
		String output = "";
		WorkoutJSON newWorkoutJSON = new WorkoutJSON(context, WorkoutData.UserName, WorkoutName, Reps, Duration, Durations, Accuracy, Hand);
		output = newWorkoutJSON.getJSONString();
		File fileParent = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "RehabApplicationJSON2019");
		if (!fileParent.exists()) {
			fileParent.mkdir();
		}
		File file = new File(fileParent, fileName);
		PrintWriter writer;
		if (file.exists()) {
			file.delete();

		}
		try {
			file.createNewFile();
			writer = new PrintWriter(new FileWriter(file, true));
			writer.append(output);
			writer.close();
			ConnectivityManager ConnectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected() == true) {
				uploadToAmazonBucket.saveData(file);
			} else {
				WorkoutData.progressCloud = 100f;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	public void addNewWorkout(String WorkoutName, String Hand, float Duration,ArrayList<Float> Durations, float Accuracy,ArrayList<Float> Accuracies, int Reps) {
		Calendar currentCalendar = Calendar.getInstance();
		String fileName = WorkoutData.UserName + "_" + "FullVersion" + "_" + WorkoutName + "_" + currentCalendar.get(Calendar.YEAR) + "~" + (currentCalendar.get(Calendar.MONTH)+1) + "~" +
				currentCalendar.get(Calendar.DAY_OF_MONTH) + "_[" + currentCalendar.get(Calendar.HOUR_OF_DAY) + "h~" + currentCalendar.get(Calendar.MINUTE) + "m" + currentCalendar.get(Calendar.SECOND) + "].json";
		String output = "";
		WorkoutJSON newWorkoutJSON = new WorkoutJSON(context, WorkoutData.UserName, WorkoutName, Reps, Duration, Durations, Accuracy,Accuracies, Hand);
		output = newWorkoutJSON.getJSONString();
		File fileParent = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "RehabApplicationJSON2019");
		if (!fileParent.exists()) {
			fileParent.mkdir();
		}
		File file = new File(fileParent, fileName);
		PrintWriter writer;
		if (file.exists()) {
			file.delete();

		}
		try {
			file.createNewFile();
			writer = new PrintWriter(new FileWriter(file, true));
			writer.append(output);
			writer.close();
			ConnectivityManager ConnectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected() == true) {
				uploadToAmazonBucket.saveData(file);
			} else {
				WorkoutData.progressCloud = 100f;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private List<File> getAllFiles(File parentDir) {

		List<File> inFiles = new ArrayList<>();
		Queue<File> files = new LinkedList<>();
        File[] filesdir  = parentDir.listFiles();
        if(filesdir == null || filesdir.length==0)
        {return inFiles;}
		files.addAll(Arrays.asList(filesdir));
		while (!files.isEmpty()) {
			File file = files.remove();
			if (file.isDirectory()) {
				files.addAll(Arrays.asList(file.listFiles()));
			} else if (file.getName().contains(WorkoutData.UserName)) {
				inFiles.add(file);
			}
		}
		return inFiles;
	}
}


