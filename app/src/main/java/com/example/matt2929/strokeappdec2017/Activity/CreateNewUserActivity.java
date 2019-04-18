package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveAndWriteUserInfo;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.User;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

public class CreateNewUserActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();

    User newUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        RadioGroup handChoice = findViewById(R.id.enterRadios);
        final EditText nameEnter = findViewById(R.id.enterName);
        Button enterSave = findViewById(R.id.enterSave);
        final EditText dateEnter = findViewById(R.id.lockDate);

        //Name
        nameEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newUser.setName((nameEnter.getText().toString()) + "_BASELINE");

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //Date
        dateEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String dateStr = dateEnter.getText().toString();

                if (dateStr != null) {
                    dateStr = dateStr.replace('\n', ' ');
                    dateStr = dateStr.trim();
                    if (dateStr.length() == 10) {
                        try {
                            String[] segs = dateStr.split("-");
                            if (segs != null && segs.length == 3) {
                                int m = Integer.parseInt(segs[0]);
                                int d = Integer.parseInt(segs[1]);
                                int y = Integer.parseInt(segs[2]);
                                if (y == 2019) {
                                    if (m >= 1 && m <= 12) {
                                        if (d >= 1 && d <= 31) {
                                            newUser.setDay(d);
                                            newUser.setMonth(m);
                                            newUser.setYear(y);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.d(TAG+"UR", "Date illegal");
                            newUser.setDay(-1);
                            newUser.setMonth(-1);
                            newUser.setYear(-1);
                        }
                    }
                    else
                    {
                        newUser.setDay(-1);
                        newUser.setMonth(-1);
                        newUser.setYear(-1);
                    }
                }
                else
                {
                    newUser.setDay(-1);
                    newUser.setMonth(-1);
                    newUser.setYear(-1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        //Goals
        //Left Hand = 0 || Right Hand = 1
        handChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.enterLeft) {
                    newUser.setHand(0);
                } else {
                    newUser.setHand(1);
                }
            }
        });
        //Age
        //saveAllData but only if data has been entered into each field
        enterSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    SaveAndWriteUserInfo saveAndWriteUserInfo = new SaveAndWriteUserInfo(getApplicationContext());
                    saveAndWriteUserInfo.saveUser(newUser);
                    saveAndWriteUserInfo.saveUserWorkouts(newUser);
                    WorkoutData.UserData = newUser;
                    WorkoutData.UserName = newUser.getName();
                    WorkoutData.hand = newUser.getHand();
                    WorkoutData.year = newUser.getYear();
                    WorkoutData.month = newUser.getMonth();
                    WorkoutData.year = newUser.getYear();
                    WorkoutData.workOutsDoneInPast = "";
                }
                else
                {
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
                startActivity(intent);
            }
        });
    }

    //check to see if newUser filled in all text fields
    boolean validateInput() {
        int count = 0;
        String issues = "The Following Errors Were Found:\n";
        if (newUser.getHand() == -1) {
            count++;
            issues += ("\nNo Hand Selected");
        }
        if (newUser.getName().equals("000")) {
            count++;
            issues += ("\nNo Name Set");
        }
        if (newUser.getYear() == -1 || newUser.getMonth() == -1 || newUser.getDay() == -1) {
            count++;
            issues += ("\nNo Time set");
        }
        if (count == 0) {
            Log.d(TAG+"SU","Success");
            return true;
        } else {
            Toast.makeText(getApplicationContext(), issues, Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG,"Failing");
        return false;
    }

}
