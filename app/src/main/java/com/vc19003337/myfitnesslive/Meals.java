package com.vc19003337.myfitnesslive;

import android.media.Image;

import java.util.Date;

public class Meals
{
    Date entryDate;
    Image mealPhoto;
    String title;
    double calories;
    double protein;
    double fat;
    double carbohydrates;
    double cholesterol;
    double fiber;
    double sodium;
    double potassium;

    public Meals(Date entryDate, Image mealPhoto, String title, double calories, double protein, double fat, double carbohydrates, double cholesterol, double fiber, double sodium, double potassium)
    {
        this.entryDate = entryDate;
        this.mealPhoto = mealPhoto;
        this.title = title;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.cholesterol = cholesterol;
        this.fiber = fiber;
        this.sodium = sodium;
        this.potassium = potassium;
    }

    public Date getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(Date entryDate)
    {
        this.entryDate = entryDate;
    }

    public Image getMealPhoto()
    {
        return mealPhoto;
    }

    public void setMealPhoto(Image mealPhoto)
    {
        this.mealPhoto = mealPhoto;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public double getCalories()
    {
        return calories;
    }

    public void setCalories(double calories)
    {
        this.calories = calories;
    }

    public double getProtein()
    {
        return protein;
    }

    public void setProtein(double protein)
    {
        this.protein = protein;
    }

    public double getFat()
    {
        return fat;
    }

    public void setFat(double fat)
    {
        this.fat = fat;
    }

    public double getCarbohydrates()
    {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates)
    {
        this.carbohydrates = carbohydrates;
    }

    public double getCholesterol()
    {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol)
    {
        this.cholesterol = cholesterol;
    }

    public double getFiber()
    {
        return fiber;
    }

    public void setFiber(double fiber)
    {
        this.fiber = fiber;
    }

    public double getSodium()
    {
        return sodium;
    }

    public void setSodium(double sodium)
    {
        this.sodium = sodium;
    }

    public double getPotassium()
    {
        return potassium;
    }

    public void setPotassium(double potassium)
    {
        this.potassium = potassium;
    }
}
