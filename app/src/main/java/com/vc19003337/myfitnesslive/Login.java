package com.vc19003337.myfitnesslive;

public class Login
{
    String fullName;
    String emailAddress;
    //String password;
    //String confirmedPassword;

    public Login(String fullName, String emailAddress)
    {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String toString()
    {
        return "Full Name " + fullName;
    }

}
