package com.vc19003337.myfitnesslive;

import java.util.Date;

public class WaterIntake
{
    Date entryDate;
    double waterAmount;
    double totalWaterIntake;

    public WaterIntake(Date entryDate, double waterAmount, double totalWaterIntake)
    {
        this.entryDate = entryDate;
        this.waterAmount = waterAmount;
        this.totalWaterIntake = totalWaterIntake;
    }

    public Date getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(Date entryDate)
    {
        this.entryDate = entryDate;
    }

    public double getWaterAmount()
    {
        return waterAmount;
    }

    public void setWaterAmount(double waterAmount)
    {
        this.waterAmount = waterAmount;
    }

    public double getTotalWaterIntake()
    {
        return totalWaterIntake;
    }

    public void setTotalWaterIntake(double totalWaterIntake)
    {
        this.totalWaterIntake= totalWaterIntake;
    }
}
