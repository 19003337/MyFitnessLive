package com.vc19003337.myfitnesslive;

import java.util.Date;

public class UserProfile
{
    String fullName;
    String gender;
    String dateOfBirth;
    double height;
    double startingWeight;
    String emailAddress;
    String unitsMeasured;

    public UserProfile(String fullName, String gender, String dateOfBirth, double height, double startingWeight, String emailAddress, String unitsMeasured)
    {
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.startingWeight = startingWeight;
        this.emailAddress = emailAddress;
        this.unitsMeasured = unitsMeasured;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double height)
    {
        this.height = height;
    }

    public double getStartingWeight()
    {
        return startingWeight;
    }

    public void setStartingWeight(double startingWeight)
    {
        this.startingWeight = startingWeight;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getUnitsMeasured() {
        return unitsMeasured;
    }

    public void setUnitsMeasured(String unitsMeasured) {
        this.unitsMeasured = unitsMeasured;
    }
}
