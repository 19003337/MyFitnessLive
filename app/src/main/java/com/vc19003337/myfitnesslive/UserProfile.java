package com.vc19003337.myfitnesslive;

import java.util.Date;

public class UserProfile
{
    String fullName;
    String emailAddress;
    String gender;
    String dateOfBirth;
    double height;
    double startingWeight;
    //String unitsMeasured;

    public UserProfile()
    {
        //Empty constructor - Don't delete!
    }

    public UserProfile(String fullName, String emailAddress, String gender, String dateOfBirth,
                       double height, double startingWeight)
    {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.startingWeight = startingWeight;
        //this.unitsMeasured = unitsMeasured;
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

    public String ToString()
    {
        return "Full Name: " + fullName + "/n"
                + "Email Address: " + emailAddress+ "/n"
                + "Gender: " + gender + "/n"
                + "Date of Birth: " + dateOfBirth+ "/n"
                + "Height: " + height + "/n"
                + "Starting Weight: " + startingWeight;
                //+ "Units Measured: " + unitsMeasured;
    }
}
