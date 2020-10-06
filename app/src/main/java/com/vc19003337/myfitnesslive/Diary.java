package com.vc19003337.myfitnesslive;

import java.util.Date;

public class Diary
{
    Date entryDate;
    int breakfastCalories;
    int lunchCalories;
    int dinnerCalories;
    int snacksCalories;
    double water;
    int exerciseCaloriesBurned;

    public Diary(Date entryDate, int breakfastCalories, int lunchCalories, int dinnerCalories, int snacksCalories, double water, int exerciseCaloriesBurned)
    {
        this.entryDate = entryDate;
        this.breakfastCalories = breakfastCalories;
        this.lunchCalories = lunchCalories;
        this.dinnerCalories = dinnerCalories;
        this.snacksCalories = snacksCalories;
        this.water = water;
        this.exerciseCaloriesBurned = exerciseCaloriesBurned;
    }

    public Date getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(Date entryDate)
    {
        this.entryDate = entryDate;
    }

    public int getBreakfastCalories()
    {
        return breakfastCalories;
    }

    public void setBreakfastCalories(int breakfastCalories)
    {
        this.breakfastCalories = breakfastCalories;
    }

    public int getLunchCalories()
    {
        return lunchCalories;
    }

    public void setLunchCalories(int lunchCalories)
    {
        this.lunchCalories = lunchCalories;
    }

    public int getDinnerCalories()
    {
        return dinnerCalories;
    }

    public void setDinnerCalories(int dinnerCalories)
    {
        this.dinnerCalories = dinnerCalories;
    }

    public int getSnacksCalories()
    {
        return snacksCalories;
    }

    public void setSnacksCalories(int snacksCalories)
    {
        this.snacksCalories = snacksCalories;
    }

    public double getWater()
    {
        return water;
    }

    public void setWater(double water)
    {
        this.water = water;
    }

    public int getExerciseCaloriesBurned()
    {
        return exerciseCaloriesBurned;
    }

    public void setExerciseCaloriesBurned(int exerciseCaloriesBurned)
    {
        this.exerciseCaloriesBurned = exerciseCaloriesBurned;
    }
}
