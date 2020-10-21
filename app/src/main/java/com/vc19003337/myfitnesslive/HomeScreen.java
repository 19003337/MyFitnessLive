package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class HomeScreen extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("profiles");
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    IntentHelper helper;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggleOnAndOff;
    NavigationView navigationView;
    Button camera;
    TextView caloriesRemaining, goalCalories, caloriesConsumed, caloriesBurned;
    //String dailyCalorieIntake;
    Goals goals;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

        caloriesRemaining = findViewById(R.id.tv_CaloriesRemaining);
        goalCalories = findViewById(R.id.btn_TargetCalories);
        caloriesConsumed = findViewById(R.id.btn_CaloriesConsumed);
        caloriesBurned = findViewById(R.id.btn_CaloriesBurned);

        camera = findViewById(R.id.btn_Camera);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

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

        myRef.child("Goals").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                goals = snapshot.getValue(Goals.class);
                if(goals != null)
                {
                    goalCalories.setText(String.valueOf(goals.getDailyCalorieIntake()) + " kcal");
                }
                else
                {
                    goalCalories.setText("Goals not set");
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