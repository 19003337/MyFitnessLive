package com.vc19003337.myfitnesslive;

import java.util.Date;

public class Weight
{
    String entryDate;
    double currentWeight;

    public Weight()
    {
        //Used for data retrieval
    }

    public Weight(String entryDate, double currentWeight)
    {
        this.entryDate = entryDate;
        this.currentWeight = currentWeight;
    }

    public String  getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(String  entryDate)
    {
        this.entryDate = entryDate;
    }

    public double getCurrentWeight()
    {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight)
    {
        this.currentWeight = currentWeight;
    }

    @Override
    public String toString()
    {
        return "Entry Date: " + entryDate + "/n" +
                "Current Weight: " + currentWeight;
    }
}
