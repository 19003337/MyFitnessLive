package com.vc19003337.myfitnesslive;

import android.net.Uri;

public class PhotoAlbumItems
{
    String imageURL;
    String entryDate;
    String mealType;
    String description;

    public PhotoAlbumItems(String imageURL, String entryDate, String mealType, String description) {
        this.imageURL = imageURL;
        this.entryDate = entryDate;
        this.mealType = mealType;
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
