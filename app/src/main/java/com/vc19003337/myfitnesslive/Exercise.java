package com.vc19003337.myfitnesslive;

import java.util.Date;

public class Exercise
{
    Date entryDate;
    String exerciseType;
    int caloriesBurned;
    int totalCaloriesBurned;

    public Exercise(Date entryDate, String exerciseType, int caloriesBurned, int totalCaloriesBurned)
    {
        this.entryDate = entryDate;
        this.exerciseType = exerciseType;
        this.caloriesBurned = caloriesBurned;
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public Date getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(Date entryDate)
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

    public int getTotalCaloriesBurned()
    {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(int totalCaloriesBurned)
    {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }
}
