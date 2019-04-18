package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by matt2929 on 1/22/18.
 */

public class SaveActivitiesDoneWeek {

    String TAG = this.getClass().getName();

    Context context;

    public int[] getRecordsForThisWeek() {
        return recordsForThisWeek;
    }

    int[] recordsForThisWeek = new int[]{0, 0, 0};

    public SaveActivitiesDoneWeek(Context context) {
        this.context = context;
        fillRecordsForThisWeek();
    }

    private void fillRecordsForThisWeek() {
        //Program start date
        int s_year = WorkoutData.UserData.getYear();
        int s_month = WorkoutData.UserData.getMonth()-1;
        int s_day = WorkoutData.UserData.getDay();
        Log.d("DateAssignment0","UserHis: YMD"+s_year+"___"+s_month+"___"+s_day);

        Calendar start = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        int s_year2 = start.get(Calendar.YEAR);
        int s_month2 = start.get(Calendar.MONTH);
        int s_day2 = start.get(Calendar.DATE);
        Log.d("DateAssignment1","SD: YMD"+s_year2+"___"+s_month2+"___"+s_day2);

        start.set(s_year + 0, s_month +0, s_day + 0, 0, 0, 1);
        Log.d("DateAssignment2","SD: YMD"+(s_year + 0)+"___"+(s_month + 0)+"___"+(s_day + 0));
        while (start.getTimeInMillis() - now.getTimeInMillis() <= 0) {
            start.add(Calendar.DATE, 7);
        }
        start.add(Calendar.DATE, -7);
        //Nearest checkpoint date
        int t_year = start.get(Calendar.YEAR);
        int t_month = start.get(Calendar.MONTH);
        int t_day = start.get(Calendar.DATE);
        int[] t_date = new int[]{t_year, t_month, t_day};
        Log.d("DateAssignment3","NowT: YMD"+t_year+"___"+t_month+"___"+t_day);
//            "Horizontal Mug"
//            "Quick Twist Mug"
//            "Unlock With Key"
        List<File> files = getAllFiles(context.getFilesDir());
        Log.d("ReadingPrevWorkout", "Below are all workous stored");
        for (File f : files) {
            Log.d("ReadingPrevWorkout", "files length: " + files.size());
            Log.d("ReadingPrevWorkout", "file name: " + f.getAbsolutePath());
            String fileName = f.getName();
            String filePath = f.getAbsolutePath();
            if (fileName.contains(WorkoutData.UserName) && fileName.contains("USER_")) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        Log.e("ReadingPrevWorkout", "Name: " + fileName + "\tLine: " + line);
                        String[] segs = line.split(":");//, Long.valueOf((line.split(":")[1])));
                        if (line.contains("Horizontal Mug")) {
                            int[] dateTime = segDate(segs[1]);
                            if (isLargerDate(t_date, dateTime)) {
                                recordsForThisWeek[0] += 1;
                            }
                            else
                            {
                                Log.d("PastFile:",filePath);
                            }
                        } else if (line.contains("Quick Twist Mug")) {
                            int[] dateTime = segDate(segs[1]);
                            if (isLargerDate(t_date, dateTime)) {
                                recordsForThisWeek[1] += 1;
                            }
                            else
                            {
                                Log.d("PastFile:",filePath);
                            }
                        } else if (line.contains("Unlock With Key")) {
                            int[] dateTime = segDate(segs[1]);
                            if (isLargerDate(t_date, dateTime)) {
                                recordsForThisWeek[2] += 1;
                            }
                            else
                            {
                                Log.d("PastFile:",filePath);
                            }
                        } else {
                        }
                    }
                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                }
                //break;
            }
        }
    }

    private boolean isLargerDate(int[] datePrev, int dateNow[]) {
        if (dateNow == null || datePrev == null || dateNow.length != 3 || datePrev.length != 3)
            return false;
        if (dateNow[0] >= datePrev[0] && dateNow[1] * 12 + dateNow[2] >= datePrev[1] * 12 + datePrev[2])
            return true;

        return false;
    }

    private int[] segDate(String dateStr) {
        String[] dateSegs = dateStr.split("_");
        try {
            int year = Integer.parseInt(dateSegs[0]);
            int month = Integer.parseInt(dateSegs[1]);
            int day = Integer.parseInt(dateSegs[2]);
            return new int[]{year, month, day};
        } catch (Exception e) {
            Log.d("ReadingWorkout", "Parsing date failed");
            return null;
        }
    }

    public Integer getWorkoutActivityCount(String workoutName) {
        Log.d("getWorkoutCount", "Workout name = " + workoutName);
        if (workoutName.contains("Horizontal Mug")) {
            return recordsForThisWeek[0];
        } else if (workoutName.contains("Quick Twist Mug")) {
            return recordsForThisWeek[1];
        } else if (workoutName.contains("Unlock With Key")) {
            return recordsForThisWeek[2];
        } else {
            return -1;
        }
    }

    private List<File> getAllFiles(File parentDir) {
        List<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();

        Log.e("ContextFile:Dir", parentDir.getAbsolutePath());
        for(int x = 0;x<files.length;x++)
        {
            File file = files[x];
            Log.d("ContextFile:Name",file.getAbsolutePath());
            if (file.isDirectory()) {

            } else if (file.getName().contains("USER_")) {
                inFiles.add(file);
            }else if (file.getName().contains("UserActivities_")) {
                inFiles.add(file);
            }
        }
        return inFiles;
    }

}



