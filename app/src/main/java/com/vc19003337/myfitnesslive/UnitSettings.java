package com.vc19003337.myfitnesslive;

public class UnitSettings
{
    String weightUnit;
    String heightUnit;
    String energyUnit;
    String waterUnit;

    public UnitSettings(String weightUnit, String heightUnit, String energyUnit, String waterUnit)
    {
        this.weightUnit = weightUnit;
        this.heightUnit = heightUnit;
        this.energyUnit = energyUnit;
        this.waterUnit = waterUnit;
    }

    public String getWeightUnit()
    {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit)
    {
        this.weightUnit = weightUnit;
    }

    public String getHeightUnit()
    {
        return heightUnit;
    }

    public void setHeightUnit(String heightUnit)
    {
        this.heightUnit = heightUnit;
    }

    public String getEnergyUnit()
    {
        return energyUnit;
    }

    public void setEnergyUnit(String energyUnit)
    {
        this.energyUnit = energyUnit;
    }

    public String getWaterUnit()
    {
        return waterUnit;
    }

    public void setWaterUnit(String waterUnit)
    {
        this.waterUnit = waterUnit;
    }
}
