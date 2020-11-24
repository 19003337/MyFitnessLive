package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CaloriesChart extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    float targetCalories, caloriesConsumed, caloriesBurned, entryDate;
    //String entryDate;
    Calories calories;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_chart);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
        barChart = findViewById(R.id.mpBarChart);

        barChart.setDragEnabled(true);
        barChart.setPinchZoom(true);
        barChart.setTouchEnabled(true);
        barChart.setFitBars(true);
        barChart.animateY(2000);
        barChart.getDescription().setText("Daily Calories Consumed Chart");

        ArrayList<BarEntry> yAxisCaloriesConsumed = new ArrayList<>();

        myRef.child("Calories").addValueEventListener(new ValueEventListener()
        {
            int count;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                calories = new Calories();

                for (DataSnapshot caloriesValues : snapshot.getChildren())
                {
                    count++;
                    calories = caloriesValues.getValue(Calories.class);

                    assert calories != null;
                    targetCalories = Float.parseFloat(String.valueOf(calories.targetCalories));
                    caloriesConsumed = Float.parseFloat(String.valueOf(calories.totalCaloriesConsumed));
                    caloriesBurned = Float.parseFloat(String.valueOf(calories.totalCaloriesBurned));
                    //entryDate = Float.parseFloat(String.valueOf(calories.entryDate));

                    yAxisCaloriesConsumed.add(new BarEntry(Float.parseFloat(String.valueOf(count)), caloriesConsumed));
                    //yAxisCaloriesConsumed.add(new BarEntry(entryDate, caloriesConsumed));
                }

                BarDataSet barDataSet = new BarDataSet(yAxisCaloriesConsumed,"Calories Consumed");
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                //barDataSet.setColor(Color.CYAN);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(15f);

                BarData barData = new BarData(barDataSet);

                barChart.setData(barData);

                LimitLine target = new LimitLine(targetCalories, "Target Calories");
                target.setLineWidth(2f);
                target.enableDashedLine(10f, 10f, 0f);
                target.setLineColor(Color.RED);
                target.setTextColor(Color.DKGRAY);
                target.setTextSize(15f);
                target.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);

                barChart.getAxisLeft().addLimitLine(target);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(CaloriesChart.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}