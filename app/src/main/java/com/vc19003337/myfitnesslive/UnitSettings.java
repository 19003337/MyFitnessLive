package com.vc19003337.myfitnesslive;

public class UnitSettings
{
    String unitSetting;
    String weightUnit;
    String heightUnit;
    //String energyUnit;
    //String waterUnit;

    public UnitSettings()
    {
        //Empty constructor for data retrieval
    }

    public UnitSettings(String unitSetting, String weightUnit, String heightUnit)
    {
        this.unitSetting = unitSetting;
        this.weightUnit = weightUnit;
        this.heightUnit = heightUnit;
    }

    public String getUnitSetting() { return unitSetting; }

    public void setUnitSetting(String unitSetting) { this.unitSetting = unitSetting; }

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


}
