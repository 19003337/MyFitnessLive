package com.vc19003337.myfitnesslive;

public class Calories
{
    String entryDate;
    int totalCaloriesConsumed;
    int targetCalories;
    int totalCaloriesBurned;

    public Calories()
    {
        //Blank constructor - DO NOT DELETE!
    }

    public Calories(String entryDate, int totalCaloriesConsumed, int targetCalories, int totalCaloriesBurned)
    {
        this.entryDate = entryDate;
        this.totalCaloriesConsumed = totalCaloriesConsumed;
        this.targetCalories = targetCalories;
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public String getEntryDate() { return entryDate; }

    public void setEntryDate(String entryDate) { this.entryDate = entryDate; }

    public int getTotalCaloriesConsumed()
    {
        return totalCaloriesConsumed;
    }

    public void setTotalCaloriesConsumed(int totalCaloriesConsumed)
    {
        this.totalCaloriesConsumed = totalCaloriesConsumed;
    }

    public int getTargetCalories()
    {
        return targetCalories;
    }

    public void setTargetCalories(int targetCalories)
    {
        this.targetCalories = targetCalories;
    }

    public int getTotalCaloriesBurned()
    {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(int totalCaloriesBurned)
    {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    @Override
    public String toString()
    {
        return "Calories{" +
                "totalCaloriesConsumed=" + totalCaloriesConsumed +
                ", targetCalories=" + targetCalories +
                ", totalCaloriesBurned=" + totalCaloriesBurned +
                '}';
    }
}
