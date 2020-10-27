package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class PhotoAlbum extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    PhotoAlbumItems photos;
    Meals meals;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

        final ArrayList<PhotoAlbumItems> photoAlbumItemsArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);

        myRef.child("Meals").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                meals = new Meals();
                for (DataSnapshot mealsValues : snapshot.getChildren())
                {
                    meals = mealsValues.getValue(Meals.class);
                    assert meals != null;
                    photos = new PhotoAlbumItems(meals.imageURL, meals.entryDate, meals.mealType, meals.description);
                    photoAlbumItemsArrayList.add(photos);
                }

                //Picasso.with(PhotoAlbum.this).load(meals.imageURL).into(MealIV);
                //mealTypeTV.setText(meals.entryDate + " - " + meals.mealType);
                //mealDescriptionTV.setText(meals.description);

                try
                {
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setHasFixedSize(true);
                    Collections.reverse(photoAlbumItemsArrayList);
                    adapter = new PhotoAlbumItemsAdapter(photoAlbumItemsArrayList, PhotoAlbum.this);
                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(adapter);


                }
                catch (Exception ex)
                {
                    Toast.makeText(PhotoAlbum.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(PhotoAlbum.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}