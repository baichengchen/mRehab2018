package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

/**
 * Created by matt2929 on 12/18/17.
 */

public class User {
    String Name = "000";
    int Age = -1;
    int hand = -1;
    String Goals = "000";

    public String getActivitiesDone() {
        return activitiesDone;
    }

    public void setActivitiesDone(String activitiesDone) {
        this.activitiesDone = activitiesDone;
    }

    String activitiesDone = "000";


    public String getDate()
    {
        return getYear()+"-"+getMonth()+"-"+getDay();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    //Baseline start date
    int day = -1;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    int month = -1;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    int year = -1;

    public User(){

    }
    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getGoals() {
        return Goals;
    }

    public void setGoals(String goals) {
        Goals = goals;
    }

    public int getHand() {
        return hand;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
