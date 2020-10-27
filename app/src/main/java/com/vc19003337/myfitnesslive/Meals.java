package com.vc19003337.myfitnesslive;

import android.media.Image;

import java.util.Date;

public class Meals
{
    //Image mealPhoto;
    String imageURL;
    String entryDate;
    String mealType;
    String description;
    int calories;
    double protein;
    double fat;
    double carbohydrates;
    double cholesterol;
    double fiber;
    double sodium;
    double potassium;

    public Meals()
    {
        //Used for data retrieval
    }

    public Meals(String imageURL, String entryDate, String mealType, String description, int calories,
                 double protein, double fat, double carbohydrates, double cholesterol, double fiber, double sodium,
                 double potassium)
    {
        this.imageURL = imageURL;
        this.entryDate = entryDate;
        this.mealType = mealType;
        this.description = description;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.cholesterol = cholesterol;
        this.fiber = fiber;
        this.sodium = sodium;
        this.potassium = potassium;
    }

    public String getImageURL() { return imageURL; }

    public void setImageURL(String imageURL) { this.imageURL= imageURL; }

    public String getEntryDate() { return entryDate; }

    public void setEntryDate(String entryDate) { this.entryDate = entryDate; }

    public String getMealType() { return mealType; }

    public void setMealType(String mealType) { this.mealType = mealType; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) { this.protein = protein; }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

}
