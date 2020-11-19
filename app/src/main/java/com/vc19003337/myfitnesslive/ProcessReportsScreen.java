package com.vc19003337.myfitnesslive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProcessReportsScreen extends AppCompatActivity
{
    Button caloriesChart, weightChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_reports_screen);

        caloriesChart = findViewById(R.id.btn_viewCaloriesChart);
        weightChart = findViewById(R.id.btn_viewWeightChart);

        caloriesChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent openNewActivity = new Intent(ProcessReportsScreen.this, CaloriesChart.class);
                startActivity(openNewActivity);
            }
        });

        weightChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent openNewActivity = new Intent(ProcessReportsScreen.this, WeightChart.class);
                startActivity(openNewActivity);
            }
        });
    }
}