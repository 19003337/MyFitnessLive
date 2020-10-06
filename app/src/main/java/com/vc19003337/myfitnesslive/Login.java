package com.vc19003337.myfitnesslive;

public class Login
{
    String fullName;
    String emailAddress;
    String password;
    String confirmedPassword;

    public Login(String fullName, String emailAddress, String password, String confirmedPassword)
    {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getConfirmedPassword()
    {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword)
    {
        this.confirmedPassword = confirmedPassword;
    }
}
