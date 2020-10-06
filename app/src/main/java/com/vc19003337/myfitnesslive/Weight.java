package com.vc19003337.myfitnesslive;

import java.util.Date;

public class Weight
{
    Date entryDate;
    double currentWeight;

    public Weight(Date entryDate, double currentWeight)
    {
        this.entryDate = entryDate;
        this.currentWeight = currentWeight;
    }

    public Date getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(Date entryDate)
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
}
