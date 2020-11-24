//19003337
//Charmaine Dobbin
//OPSC Task 2
/*_____________________________________code attribution________________________________________________
The code used for exiting the app was adapted from Tutorialspoint:
Author: Azhar.
Link: Tutorialspoint. 2019. [Online] Available at: https://www.tutorialspoint.com/how-to-quit-application-programmatically
[Accessed 27 October 2020].
____________________________________________end________________________________________________________
*/

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Locale;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Calendar calendar;
    String dateToday;
    IntentHelper helper;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggleOnAndOff;
    NavigationView navigationView;
    Button camera, caloriesBurnedBTN, goalCaloriesBTN, caloriesConsumedBTN, saveTotalCaloriesBTN;
    TextView caloriesRemainingTV, latestMealTV, latestMealDescriptionTV;
    ImageView latestMealIV;
    Integer calculatedCaloriesRemaining, calculatedCaloriesConsumed, calculatedCaloriesBurned;
    //Double calculatedCaloriesConsumed;
    int targetCalories, caloriesConsumed, caloriesBurned;
    Calories calories;
    Goals goals;
    Meals meals;
    Exercise exercise;
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

        calculatedCaloriesConsumed = 0;
        calculatedCaloriesBurned = 0;
        caloriesBurned = 0;

        caloriesRemainingTV = findViewById(R.id.tv_CaloriesRemaining);
        goalCaloriesBTN = findViewById(R.id.btn_TargetCalories);
        caloriesConsumedBTN = findViewById(R.id.btn_CaloriesConsumed);
        caloriesBurnedBTN = findViewById(R.id.btn_CaloriesBurned);
        saveTotalCaloriesBTN = findViewById(R.id.btn_saveTotalCalories);
        latestMealIV = findViewById(R.id.imageView_LatestMealPhoto);
        latestMealTV = findViewById(R.id.tv_LatestMeal);
        latestMealDescriptionTV = findViewById(R.id.tv_LatestMealDescription);
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

        goalCaloriesBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HomeScreen.this, GoalsScreen.class);
                startActivity(i);
            }
        });

        caloriesBurnedBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HomeScreen.this, ExerciseScreen.class);
                startActivity(i);
            }
        });

        caloriesConsumedBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(HomeScreen.this, PhotoAlbum.class);
                startActivity(i);
            }
        });

        saveTotalCaloriesBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    caloriesConsumed = calculatedCaloriesConsumed;
                    caloriesBurned = calculatedCaloriesBurned;
                    calories = new Calories(dateToday, caloriesConsumed, targetCalories, caloriesBurned);

                    myRef.child("Calories").push().setValue(calories).addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            Toast.makeText(HomeScreen.this, "Final Calories saved successfully", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(HomeScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                catch (Exception ex)
                {
                    Toast.makeText(HomeScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        myRef.child("Goals").addValueEventListener(new ValueEventListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                goals = snapshot.getValue(Goals.class);
                if(goals != null)
                {
                    goalCaloriesBTN.setText(String.valueOf(goals.getDailyCalorieIntake()) + " kcal");
                    calculatedCaloriesRemaining = goals.getDailyCalorieIntake();
                    caloriesRemainingTV.setText(calculatedCaloriesRemaining.toString() + " calories remaining");
                }
                else
                {
                    calculatedCaloriesRemaining = 0;
                    goalCaloriesBTN.setText("0 kcal");
                    caloriesRemainingTV.setText("Goals not set");
                }

                targetCalories = Integer.parseInt(String.valueOf(goals.dailyCalorieIntake));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(HomeScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        myRef.child("Meals").addValueEventListener(new ValueEventListener()
        {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot mealsValues : snapshot.getChildren())
                {
                    meals = mealsValues.getValue(Meals.class);
                    assert meals != null;
                    if(meals.entryDate.equals(dateToday))
                    {
                        calculatedCaloriesConsumed += meals.calories;
                    }
                }

                if(meals != null && meals.entryDate.equals(dateToday))
                {
                    Picasso.with(HomeScreen.this).load(meals.imageURL).into(latestMealIV);
                    latestMealTV.setText(meals.entryDate + " - " + meals.mealType);
                    latestMealDescriptionTV.setText(meals.description);
                    caloriesConsumedBTN.setText(calculatedCaloriesConsumed.toString() + " kcal");
                    //caloriesConsumed.setText(String.format(Locale.ENGLISH,"%.2f",calculatedCaloriesConsumed) + " kcal");

                    if(calculatedCaloriesRemaining == 0)
                    {
                        caloriesRemainingTV.setText("Goals not set");
                    }
                    else{
                        int totalCaloriesRemaining = (calculatedCaloriesRemaining - calculatedCaloriesConsumed);
                        caloriesRemainingTV.setText(totalCaloriesRemaining + " calories remaining");
                        //Double totalCaloriesRemaining = (calculatedCaloriesRemaining - calculatedCaloriesConsumed);
                        //caloriesRemaining.setText(String.format(Locale.ENGLISH,"%.2f",totalCaloriesRemaining) + " calories remaining");
                    }
                }
                else{
                    latestMealTV.setText("No meals saved!");
                    latestMealDescriptionTV.setText("Press camera now!");
                    latestMealIV.setImageDrawable(getResources().getDrawable(R.drawable.nomeals));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(HomeScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        myRef.child("Exercise").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot exerciseActivities : snapshot.getChildren())
                {
                    exercise = exerciseActivities.getValue(Exercise.class);
                    assert exercise != null;
                    if(exercise.entryDate.equals(dateToday))
                    {
                        //caloriesBurned += exercise.caloriesBurned;
                        calculatedCaloriesBurned += exercise.caloriesBurned;
                    }
                }

                if(exercise != null && exercise.entryDate.equals(dateToday))
                {
                    //caloriesBurnedBTN.setText(String.format(Locale.ENGLISH,"%.2f", calculatedCaloriesBurned) + " kcal");
                    caloriesBurnedBTN.setText(calculatedCaloriesBurned.toString() + " kcal");
                }
                else {
                    caloriesBurnedBTN.setText("0 kcal");
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
        //To do code
    }

    @SuppressLint("NonConstantResourceId")
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
                Toast.makeText(this, "New feature to be developed!",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Goals:
                helper.openIntent(this, GoalsScreen.class);
                //Toast.makeText(this, "Goals are currently setup in your Profile",
                        //Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Progress:
                helper.openIntent(this, ProcessReportsScreen.class);
                //helper.openIntent(this, ProcessScreen.class);
                //Toast.makeText(this, "Currently in development!",
                        //Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Weight:
                helper.openIntent(this, WeightScreen.class);
                break;

            case R.id.nav_PhotoAlbum:
                helper.openIntent(this, PhotoAlbum.class);
                //Toast.makeText(this, "Currently in development",
                        //Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Exercise:
                helper.openIntent(this, ExerciseScreen.class);
                //Toast.makeText(this, "Currently being developed",
                        //Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_WaterIntake:
                //helper.openIntent(this, PhotoAlbum.class);
                Toast.makeText(this, "New feature coming soon",
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
                /*
                helper.openIntent(this, MainActivity.class);
                mAuth.signOut();
                Toast.makeText(this, "You have successfully logged out!",
                    Toast.LENGTH_SHORT).show();
                 */
                Toast.makeText(this, "You have successfully logged out!",
                        Toast.LENGTH_SHORT).show();
                //HomeScreen.this.finish();
                //finish();
                mAuth.signOut();
                finishAffinity();
                System.exit(0);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        //To do code
    }
}