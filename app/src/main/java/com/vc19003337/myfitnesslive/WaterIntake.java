package com.vc19003337.myfitnesslive;

import java.util.Date;

public class WaterIntake
{
    String entryDate;
    int totalWaterIntake;

    public WaterIntake()
    {
        //Empty constructor - IMPORTANT!!!
    }

    public WaterIntake(String entryDate, int totalWaterIntake)
    {
        this.entryDate = entryDate;
        this.totalWaterIntake = totalWaterIntake;
    }

    public String getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(String entryDate)
    {
        this.entryDate = entryDate;
    }

    public int getTotalWaterIntake()
    {
        return totalWaterIntake;
    }

    public void setTotalWaterIntake(int totalWaterIntake)
    {
        this.totalWaterIntake = totalWaterIntake;
    }
}
