package com.vc19003337.myfitnesslive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public  class PhotoAlbumItemsAdapter extends RecyclerView.Adapter<PhotoAlbumItemsAdapter.PhotoAlbumViewHolder>
{
    private ArrayList<PhotoAlbumItems> PhotoAlbumList;
    private Context context;

    public static class PhotoAlbumViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mealPhotoIV;
        public TextView mealTypeTV, mealDescriptionTV;

        public PhotoAlbumViewHolder(@NonNull View itemView)
        {
            super(itemView);
            mealPhotoIV = (ImageView) itemView.findViewById(R.id.imageView_MealPhoto);
            mealTypeTV = itemView.findViewById(R.id.tv_MealType);
            mealDescriptionTV = itemView.findViewById(R.id.tv_MealDescription);
        }
    }

    public PhotoAlbumItemsAdapter(ArrayList<PhotoAlbumItems> photoAlbumList, Context context)
    {
        PhotoAlbumList = photoAlbumList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photoalbum_item, parent, false);
        PhotoAlbumViewHolder pvh = new PhotoAlbumViewHolder(v);
        return pvh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PhotoAlbumViewHolder holder, int position)
    {
        PhotoAlbumItems currentPhotoItem = PhotoAlbumList.get(position);

        //holder.mealPhotoIV.setImageURI(Uri.parse(currentPhotoItem.imageURL));
        //Picasso.get().load(currentPhotoItem.getImageURL()).into(holder.mealPhotoIV);
        //holder.mealPhotoIV.setImageURI(Uri.fromFile(new File(currentPhotoItem.imageURL)));
        Picasso.with(context).load(currentPhotoItem.getImageURL()).into(holder.mealPhotoIV);
        holder.mealTypeTV.setText(currentPhotoItem.entryDate + " - " + currentPhotoItem.mealType);
        holder.mealDescriptionTV.setText(currentPhotoItem.description);
    }

    @Override
    public int getItemCount()
    {
        return PhotoAlbumList.size();
    }
}
