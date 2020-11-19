package com.vc19003337.myfitnesslive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ProcessScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_screen);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabItem tabCalories = findViewById(R.id.tabCalories);
        TabItem tabWeight = findViewById(R.id.tabWeight);
        ViewPager viewPager = findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        //Change tabs view when the tab is selected of clicked
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
    }
}