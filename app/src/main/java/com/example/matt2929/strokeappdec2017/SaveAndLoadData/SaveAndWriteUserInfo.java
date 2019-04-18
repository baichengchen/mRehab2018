package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by matt2929 on 12/18/17.
 */

public class SaveAndWriteUserInfo {
    Context context;

    public SaveAndWriteUserInfo(Context context) {
        this.context=context;
    }

	public boolean isUserCreated() {
		List<File> files = getAllFiles(context.getFilesDir());
        Log.d("ContextSize","Context file DIR: "+context.getFilesDir().getAbsolutePath());
		Log.d("ContextSize","Context file size: "+files.size());
		return (files.size() > 0);
	}

	public User getUser() {
        Log.d("Readinguser","start reading");
		List<File> files = getAllFiles(context.getFilesDir());
        Log.d("Readinguser","File list size: "+files.size());
		for (File f : files) {
            Log.d("Readinguser","File  name: "+f.getAbsolutePath());
			User user = new User();
			String filename = f.getName();
			if(filename.contains("USER_")) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String line = "";
                    String wos = "";
                    while ((line = br.readLine()) != null) {

                        Log.d("Readinguser", line);
                        if (line.contains("Name")) {
                            user.setName(line.split(":")[1]);
                        } else if (line.contains("Age") || line.contains("age")) {
                            user.setAge(Integer.valueOf(line.split(":")[1]));
                        } else if (line.contains("Affected") || line.contains("affected")) {
                            user.setHand(Integer.valueOf(line.split(":")[1]));
                        } else if (line.contains("Goal") || line.contains("goal")) {
                            user.setGoals(line.split(":")[1]);
                        } else if (line.contains("Month") || line.contains("month")) {
                            user.setMonth(Integer.parseInt(line.split(":")[1]));
                        } else if (line.contains("Day") || line.contains("day")) {
                            user.setDay(Integer.parseInt(line.split(":")[1]));
                        } else if (line.contains("Year") || line.contains("year")) {
                            user.setYear(Integer.parseInt(line.split(":")[1]));
                        } else if(line.contains("WO_") || line.contains("wo_")) {
                            wos+=line+"\n";
                            user.setActivitiesDone(wos);
                        }
                        else {
                        }
                    }
                    br.close();
                    Log.d("Readinguser", "file ended");
                    return user;
                } catch (FileNotFoundException fe) {
                    fe.printStackTrace();
                    Log.d("ReadingUser", "FException");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ReadingUser", "Exception");
                }
            }
        }
		return null;
	}
    public void saveUser(User newUser){
        String filename = "UserActivities_"+newUser.getName()+"_"+newUser.getHand()+".txt";
        String string = "Name:"+newUser.getName();
        string += ("\nAge:"+newUser.getAge());
        string += ("\nAffected:"+newUser.getHand());
        string += ("\nGoals:"+newUser.getGoals());
        string += ("\nYear:"+newUser.getYear());
        string += ("\nMonth:"+newUser.getMonth());
        string += ("\nDay:"+newUser.getDay());
        string += ("\nDay:"+newUser.getDay());
        string += ("\n"+newUser.getActivitiesDone());
        Log.d("SavingUserWO_1",string);
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

            Log.d("FailureToSave","Context file:"+filename);
            Toast.makeText(context,"Failure to save",Toast.LENGTH_SHORT).show();
        }
    }
    public void saveUserWorkouts(User newUser){
        String filename = "USER_"+newUser.getName()+
                            "_"+newUser.getHand()+"_workoutsForWeek"+
                            ".txt";
        Log.d("SavingUserWO",filename);
        String string = "Name:"+newUser.getName();
        string += ("\nAge:"+newUser.getAge());
        string += ("\nAffected:"+newUser.getHand());
        string += ("\nGoals:"+newUser.getGoals());
        string += ("\nYear:"+newUser.getYear());
        string += ("\nMonth:"+newUser.getMonth());
        string += ("\nDay:"+newUser.getDay());
        string += ("\n"+newUser.getActivitiesDone());
        Log.d("SavingUserWO",string);
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("FailureToSave","Context file:"+filename);
            Toast.makeText(context,"FailureToSave",Toast.LENGTH_SHORT).show();
        }
    }
    private List<File> getAllFiles(File parentDir) {
        List<File> inFiles = new ArrayList<>();
        Queue<File> files = new LinkedList<>();
        files.addAll(Arrays.asList(parentDir.listFiles()));
        while (!files.isEmpty()) {
            File file = files.remove();
            if (file.isDirectory()) {
                files.addAll(Arrays.asList(file.listFiles()));
            } else if (file.getName().contains("USER")) {
                inFiles.add(file);
            } else if (file.getName().contains("UserActivities")) {
                inFiles.add(file);
            }
        }
        return inFiles;
    }
}
