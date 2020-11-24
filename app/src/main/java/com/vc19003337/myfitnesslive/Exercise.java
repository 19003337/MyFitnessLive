package com.vc19003337.myfitnesslive;

import java.util.Date;

public class Exercise
{
    String entryDate;
    String exerciseType;
    int caloriesBurned;
    //int totalCaloriesBurned;

    public Exercise()
    {
        //Empty constructor very important
    }

    public Exercise(String entryDate, String exerciseType, int caloriesBurned)
    {
        this.entryDate = entryDate;
        this.exerciseType = exerciseType;
        this.caloriesBurned = caloriesBurned;
    }

    public String getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(String entryDate)
    {
        this.entryDate = entryDate;
    }

    public String getExerciseType()
    {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType)
    {
        this.exerciseType = exerciseType;
    }

    public int getCaloriesBurned()
    {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned)
    {
        this.caloriesBurned = caloriesBurned;
    }

    @Override
    public String toString() {
        return

                "\t" + caloriesBurned + " kcal" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + exerciseType;

    }
}
