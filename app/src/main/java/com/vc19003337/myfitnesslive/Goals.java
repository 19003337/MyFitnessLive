package com.vc19003337.myfitnesslive;

public class Goals
{
    double goalWeight;
    int dailyCalorieIntake;

    public Goals()
    {
        //Empty constructor for data retrieval
    }

    public Goals(double goalWeight, int dailyCalorieIntake)
    {
        this.goalWeight = goalWeight;
        this.dailyCalorieIntake = dailyCalorieIntake;
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

    @Override
    public String toString()
    {
        return "Goals: " + "/n" +
                "GoalWeight = " + goalWeight + "/n" +
                "DailyCalorieIntake = " + dailyCalorieIntake;
    }
}
