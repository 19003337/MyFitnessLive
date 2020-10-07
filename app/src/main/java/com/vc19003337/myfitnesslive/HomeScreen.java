package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

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

public class HomeScreen extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
    IntentHelper helper;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggleOnAndOff;
    NavigationView navigationView;
    private FirebaseAuth mAuth;
    Button camera;
    TextView caloriesRemaining, goalCalories, caloriesConsumed, caloriesBurned;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

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
                helper.openIntent(this, HomeScreen.class);
                break;

            case R.id.nav_Goals:
                helper.openIntent(this, GoalsScreen.class);
                break;

            case R.id.nav_Progress:
                Toast.makeText(this, "Coming Soon! Functionality currently in development.",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Weight:
                helper.openIntent(this, WeightScreen.class);
                break;

            case R.id.nav_PhotoAlbum:
                helper.openIntent(this, HomeScreen.class);
                break;

            case R.id.nav_Profile:
                helper.openIntent(this, Profile.class);
                break;

            case R.id.nav_Settings:
                helper.openIntent(this, Settings.class);
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