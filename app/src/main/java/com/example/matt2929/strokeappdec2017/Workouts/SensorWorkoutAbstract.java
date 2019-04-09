package com.example.matt2929.strokeappdec2017.Workouts;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.EndRepTrigger;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutData;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.OutputWorkoutStrings;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechTrigger;
import com.example.matt2929.strokeappdec2017.Utilities.AverageValue;
import com.example.matt2929.strokeappdec2017.Utilities.JerkScoreCalculation;
import com.example.matt2929.strokeappdec2017.Utilities.SFXPlayer;

import java.util.ArrayList;

/**
 * Created by matt2929 on 12/19/17.
 */

public abstract class SensorWorkoutAbstract extends WorkoutAbstract {

    private JerkScoreCalculation jerkScoreCalculation;

    public void SensorWorkout(String Name, Integer reps, SpeechTrigger speechTrigger, EndRepTrigger endRepTrigger, SFXPlayer sfxPlayer, OutputWorkoutData outputWorkoutData, OutputWorkoutStrings outputWorkoutStrings) {
        super.Workout(Name, reps, speechTrigger, endRepTrigger, sfxPlayer, outputWorkoutData, outputWorkoutStrings);
    }

    public ArrayList<Float> getJerkScores() {
        return jerkScoreCalculation.GetAllJerks();
    }

    public void SensorDataIn(float[] data) {
        if (dataLast == null) {
            dataLast = data;
            AverageValues = new AverageValue[data.length];
            AverageDataValue = new float[data.length];
            for (int i = 0; i < data.length; i++) {
                AverageValues[i] = new AverageValue(25);
            }
        }
        for (int i = 0; i < data.length; i++) {
            AverageDataValue[i] = AverageValues[i].addData(data[i]);
        }

    }


}
