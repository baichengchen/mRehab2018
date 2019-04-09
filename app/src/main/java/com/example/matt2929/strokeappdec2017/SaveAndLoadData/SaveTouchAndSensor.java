package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.matt2929.strokeappdec2017.AmazonCloud.UploadToAmazonBucket;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SaveTouchAndSensor extends AsyncTask<Void, Void, Void> {


	public static String _fileName;
	Context _context;
	String _workoutName = "";
	ArrayList<float[]> dataQueue = new ArrayList<float[]>();
	String heading;
	UploadToAmazonBucket uploadToAmazonBucket;
	Calendar cal;
	private Float Duration, Score;
	private Integer Reps;
	private String Hand;

	private ArrayList<Float> Durations = new ArrayList<>();
	private ArrayList<Float> Scores = new ArrayList<>();

	public SaveTouchAndSensor(Context context, String workoutName, String heading) {
		_workoutName = workoutName;
		_context = context;
		cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		this.heading = heading;
		_fileName = WorkoutData.UserName + "_" + workoutName + "_" + "FullVersion" + "_" + (month) + "-" + day + "-" + year + "_[" + hour + "h~" + minute + "m~" + second + "s]_CSV.csv";
		uploadToAmazonBucket = new UploadToAmazonBucket(_context);
	}

	public void addData(float[] data) {
		dataQueue.add(data);

	}
	public void saveAllData(Float Duration,Float Score,ArrayList<Float> Durations, ArrayList<Float> Scores, Integer Reps, String Hand) {
		this.Duration = Duration;
		this.Durations = Durations;
		this.Score = Score;
		this.Scores = Scores;
		Log.d("Size","Scores..."+Scores.size());
		Log.d("Size","Durations..."+Durations.size());
		this.Reps = Reps;
		this.Hand = Hand;
		this.execute();
	}
	/*
	public void saveAllData(Float Duration,ArrayList<Float> Durations, Float Smoothness, Integer Reps, String Hand) {
		this.Duration = Duration;
		this.Durations = Durations;
		this.Smoothness = Smoothness;
		Log.d("Size","Durations..."+Durations.size());
		this.Reps = Reps;
		this.Hand = Hand;
		this.execute();
	}
	public void saveAllData(Float Duration,Float Smoothness,ArrayList<Float> Smoothnesses,  Integer Reps, String Hand) {
		this.Duration = Duration;
		this.Smoothness = Smoothness;
		this.Smoothnesses = Smoothnesses;
		Log.d("Size","Smoothnesses..."+Smoothnesses.size());
		this.Reps = Reps;
		this.Hand = Hand;
		this.execute();
	}
	*/
	@Override
	protected Void doInBackground(Void... voids) {
		try {
			File fileParent = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "RehabApplicationCSV2019");
			if (!fileParent.exists()) {
				fileParent.mkdir();
			}
			File file = new File(fileParent, _fileName);
			PrintWriter writer;
			if (file.exists()) {
				file.delete();

			}
			try {
				file.createNewFile();
				writer = new PrintWriter(new FileWriter(file, true));

				String d = "";
				for(int x = 0;x<Durations.size() && x<Reps;x++)
				{
					if(Durations.size() == 1) {
						break;
					}

					d += "Duration#" +(x+1)+":"+this.Durations.get(x)+",";
				}
				String s = "";
				for(int x = 0;x<Scores.size() && x<Reps;x++)
				{
					if(Scores.size() == 1) {
						break;
					}
					s += "Score#" +(x+1)+":"+this.Scores.get(x)+",";
				}

				writer.append("Name:" + WorkoutData.UserName + ","
						+ "Workout:" + _workoutName + ","
						+ "Date HR:" + humanReadableTime(cal.getTimeInMillis()) + ","
						+ "Date MS:" + cal.getTimeInMillis() + ","
						+ "Duration:" + this.Duration + ","+d
						+ "Score:" + this.Score + ","+s
						+ "Reps:" + this.Reps + ","
						+ "Hand:" + this.Hand
						+ "\n" + heading + "\n");
				for (int i = 0; i < dataQueue.size(); i++) {
					for (int j = 0; j < dataQueue.get(i).length; j++) {
						if (j == dataQueue.get(i).length - 1) {
							writer.append(dataQueue.get(i)[j] + "\n");
						} else {
							writer.append(dataQueue.get(i)[j] + ",");
						}
						Log.e("Progress Asynch", "" + WorkoutData.progressLocal);
						if (_fileName.contains(".csv")) {
							WorkoutData.progressLocal = Float.valueOf(((float) i / (float) dataQueue.size()) * 100);
						}
					}
				}
				writer.close();
				ConnectivityManager ConnectionManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected() == true && file.exists()) {
					uploadToAmazonBucket.saveData(file);
					Log.e("Internet", "Some");
				} else {
					WorkoutData.progressCloud = 100f;
					Log.e("Internet", "None");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String humanReadableTime(long time) {
		long yourmilliseconds = time;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultdate = new Date(yourmilliseconds);
		return sdf.format(resultdate);
	}
}

