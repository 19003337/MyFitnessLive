package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Calendar calendar;
    String dateToday;
    IntentHelper helper;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggleOnAndOff;
    NavigationView navigationView;
    Button camera;
    TextView caloriesRemaining, goalCalories, caloriesConsumed, caloriesBurned, latestMeal, latestMealDescription;
    ImageView latestMealIV;
    Integer calculatedCaloriesRemaining;
    Double calculatedCaloriesConsumed;
    Goals goals;
    Meals meals;
    //UserProfile userProfile;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateToday = dateFormat.format(calendar.getTime());

        calculatedCaloriesConsumed = 0.0;
        caloriesRemaining = findViewById(R.id.tv_CaloriesRemaining);
        goalCalories = findViewById(R.id.btn_TargetCalories);
        caloriesConsumed = findViewById(R.id.btn_CaloriesConsumed);
        caloriesBurned = findViewById(R.id.btn_CaloriesBurned);
        latestMealIV = findViewById(R.id.imageView_LatestMealPhoto);
        latestMeal = findViewById(R.id.tv_LatestMeal);
        latestMealDescription = findViewById(R.id.tv_LatestMealDescription);
        //displayFullName = findViewById(R.id.tv_FullName);
        //displayEmailAddress = findViewById(R.id.tv_EmailAddress);

        camera = findViewById(R.id.btn_Camera);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        //Set displayed text
        //displayEmailAddress.setText(currentUser.getEmail());
        //displayFullName.setText("New User");

        toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.nav_Drawer);
        helper = new IntentHelper();

        toggleOnAndOff = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggleOnAndOff);
        toggleOnAndOff.syncState();

        camera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HomeScreen.this, AddMeal.class);
                startActivity(i);
            }
        });

        myRef.child("Goals").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                goals = snapshot.getValue(Goals.class);
                if(goals != null)
                {
                    goalCalories.setText(String.valueOf(goals.getDailyCalorieIntake()) + " kcal");
                    calculatedCaloriesRemaining = goals.getDailyCalorieIntake();
                    caloriesRemaining.setText(calculatedCaloriesRemaining.toString() + " calories remaining");
                }
                else
                {
                    calculatedCaloriesRemaining = 0;
                    goalCalories.setText("0 kcal");
                    caloriesRemaining.setText("Goals not set");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(HomeScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        myRef.child("Meals").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot mealsValues : snapshot.getChildren())
                {
                    meals = mealsValues.getValue(Meals.class);

                    if(meals.entryDate.equals(dateToday))
                    {
                        calculatedCaloriesConsumed += meals.calories;
                    }
                }

                if(meals != null && meals.entryDate.equals(dateToday))
                {
                    Picasso.with(HomeScreen.this).load(meals.imageURL).into(latestMealIV);
                    latestMeal.setText(meals.entryDate + " - " + meals.mealType);
                    latestMealDescription.setText(meals.description);
                    caloriesConsumed.setText(calculatedCaloriesConsumed.toString() + " kcal");

                    if(calculatedCaloriesRemaining == 0)
                    {
                        caloriesRemaining.setText("Goals not set");
                    }
                    else{
                        Double totalCaloriesRemaining = (calculatedCaloriesRemaining - calculatedCaloriesConsumed);
                        caloriesRemaining.setText(totalCaloriesRemaining.toString() + " calories remaining");
                    }
                }
                else{
                    latestMeal.setText("No meals saved!");
                    latestMealDescription.setText("Press camera now!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(HomeScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view)
    {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_Home:
                helper.openIntent(this, HomeScreen.class);
                break;

            case R.id.nav_Diary:
                //helper.openIntent(this, HomeScreen.class);
                Toast.makeText(this, "New Feature Coming Soon!",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Goals:
                helper.openIntent(this, GoalsScreen.class);
                //Toast.makeText(this, "Goals are currently setup in your Profile",
                        //Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Progress:
                Toast.makeText(this, "Coming Soon!",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Weight:
                helper.openIntent(this, WeightScreen.class);
                break;

            case R.id.nav_PhotoAlbum:
                //helper.openIntent(this, HomeScreen.class);
                Toast.makeText(this, "Currently in development",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Profile:
                helper.openIntent(this, Profile.class);
                break;

            case R.id.nav_Settings:
                helper.openIntent(this, Settings.class);
                //Toast.makeText(this, "See unit settings in your Profile",
                        //Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Logout:
                mAuth.signOut();
                helper.openIntent(this, MainActivity.class);
                Toast.makeText(this, "You have successfully logged out!",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }


}