package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeightChart extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Weight weight;
    Goals goals, goalsData;
    //String goalWeight, currentWeight, entryDate;
    Float goalWeight;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_chart);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
        lineChart = findViewById(R.id.mpLineChart);

        //ArrayList<String> xAxis = new ArrayList<>();
        ArrayList<Entry> yAxisGoalWeight = new ArrayList<>();
        yAxisGoalWeight.add(new Entry(1, 50));
        yAxisGoalWeight.add(new Entry(2, 50));
        yAxisGoalWeight.add(new Entry(3, 50));
        yAxisGoalWeight.add(new Entry(4, 50));
        yAxisGoalWeight.add(new Entry(5, 50));
        yAxisGoalWeight.add(new Entry(6, 50));
        yAxisGoalWeight.add(new Entry(7, 50));
        yAxisGoalWeight.add(new Entry(8, 50));
        yAxisGoalWeight.add(new Entry(9, 50));
        yAxisGoalWeight.add(new Entry(10, 50));

        ArrayList<Entry> yAxisWeightMeasured = new ArrayList<>();
        yAxisWeightMeasured.add(new Entry(1, 53,0));
        yAxisWeightMeasured.add(new Entry(2, 52,5));
        yAxisWeightMeasured.add(new Entry(3, 51,9));
        yAxisWeightMeasured.add(new Entry(4, 51,4));
        yAxisWeightMeasured.add(new Entry(5, 51,0));
        yAxisWeightMeasured.add(new Entry(6, 51,0));
        yAxisWeightMeasured.add(new Entry(7, 51,2));
        yAxisWeightMeasured.add(new Entry(8, 50,8));
        yAxisWeightMeasured.add(new Entry(9, 50,5));
        yAxisWeightMeasured.add(new Entry(10, 50,0));


        myRef.child("Goals").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                goalsData = snapshot.getValue(Goals.class);

                if (goalsData != null)
                {
                    //goalWeight = (DecimalFormat.getNumberInstance().format(goals.getGoalWeight()));
                    goalWeight = Float.parseFloat(String.valueOf(goalsData.goalWeight));
                }
                else{
                    Toast.makeText(WeightChart.this, "Please set your goal weight", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(WeightChart.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        myRef.child("WeightChanges").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                weight = new Weight();
                //weightList = new ArrayList<String>();

                for (DataSnapshot weightValues : snapshot.getChildren())
                {
                    weight = weightValues.getValue(Weight.class);
                    //weightList.add(weight.ToString());
                    float measuredWeight = Float.parseFloat(String.valueOf(weight.currentWeight));
                    //xAxis.add(String.valueOf(weight.entryDate));
                    //yAxisGoalWeight.add(new Entry(Float.parseFloat(String.valueOf(weight.entryDate)), goalWeight));
                    //yAxisWeightMeasured.add(new Entry(Float.parseFloat(String.valueOf(weight.entryDate)), measuredWeight));
                    //weightEntries.add(new Entry(measuredWeightChart), Float.parseFloat(weight.entryDate));
                    //weightEntries.add(new Entry(Float.parseFloat(weight.entryDate), Float.parseFloat(String.valueOf(weight.currentWeight))));
                }

                ArrayList<ILineDataSet> weightEntriesLineDataSet = new ArrayList<>();
                LineDataSet lineDataSet = new LineDataSet(yAxisWeightMeasured, "Weight");
                lineDataSet.setDrawCircles(false);
                lineDataSet.setColor(Color.RED);

                LineDataSet lineDataSet2 = new LineDataSet(yAxisGoalWeight, "Goal");
                lineDataSet2.setDrawCircles(false);
                lineDataSet2.setColor(Color.GREEN);

                weightEntriesLineDataSet.add(lineDataSet);
                weightEntriesLineDataSet.add(lineDataSet2);

                LineData data = new LineData(weightEntriesLineDataSet);
                lineChart.setData(data);
                lineChart.getDescription().setText("Weight Progress Chart");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(WeightChart.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}