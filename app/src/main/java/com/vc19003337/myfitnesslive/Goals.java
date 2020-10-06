package com.vc19003337.myfitnesslive;

public class Goals
{
    double startingWeight;
    double currentWeight;
    double goalWeight;
    int dailyCalorieIntake;

    public Goals(double startingWeight, double currentWeight, double goalWeight, int dailyCalorieIntake)
    {
        this.startingWeight = startingWeight;
        this.currentWeight = currentWeight;
        this.goalWeight = goalWeight;
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public double getStartingWeight()
    {
        return startingWeight;
    }

    public void setStartingWeight(double startingWeight)
    {
        this.startingWeight = startingWeight;
    }

    public double getCurrentWeight()
    {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight)
    {
        this.currentWeight = currentWeight;
    }

    public double getGoalWeight()
    {
        return goalWeight;
    }

    public void setGoalWeight(double goalWeight)
    {
        this.goalWeight = goalWeight;
    }

    public int getDailyCalorieIntake()
    {
        return dailyCalorieIntake;
    }

    public void setDailyCalorieIntake(int dailyCalorieIntake)
    {
        this.dailyCalorieIntake = dailyCalorieIntake;
    }
}
