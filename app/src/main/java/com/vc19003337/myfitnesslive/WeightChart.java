package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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
    Goals goalsData;
    UserProfile profile;
    //String goalWeight, currentWeight, entryDate;
    Float goalWeight, startingWeight;
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

        lineChart.setDragEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setTouchEnabled(true);
        lineChart.getAxisLeft().setAxisMinimum(0);
        lineChart.getAxisRight().setAxisMinimum(0);
        //lineChart.getAxisLeft().setAxisMaximum(200);
        lineChart.animateY(2000);
        lineChart.getDescription().setText("Weight Progress Chart");

        /*
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
         */

        //ArrayList<String> xAxisDate = new ArrayList<>();
        //ArrayList<Entry> xAxisDateMeasured = new ArrayList<>();

        ArrayList<Entry> yAxisWeightMeasured = new ArrayList<>();
        /*
        yAxisWeightMeasured.add(new Entry(1, 250,0));
        yAxisWeightMeasured.add(new Entry(2, 72,5));
        yAxisWeightMeasured.add(new Entry(3, 69,9));
        yAxisWeightMeasured.add(new Entry(4, 64,4));
        yAxisWeightMeasured.add(new Entry(5, 60,0));
        yAxisWeightMeasured.add(new Entry(6, 55,0));
        yAxisWeightMeasured.add(new Entry(7, 54,2));
        yAxisWeightMeasured.add(new Entry(8, 53,8));
        yAxisWeightMeasured.add(new Entry(9, 52,5));
        yAxisWeightMeasured.add(new Entry(10, 51,0));
        */

        myRef.child("Goals").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                goalsData = snapshot.getValue(Goals.class);

                if (goalsData != null)
                {
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

        myRef.child("Profile").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                profile = snapshot.getValue(UserProfile.class);

                if (profile != null)
                {
                    startingWeight = Float.parseFloat(String.valueOf(profile.startingWeight));
                }
                else{
                    Toast.makeText(WeightChart.this, "Please set up your Profile starting weight", Toast.LENGTH_LONG).show();
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
            int count = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                weight = new Weight();

                for (DataSnapshot weightValues : snapshot.getChildren())
                {
                    count++;
                    weight = weightValues.getValue(Weight.class);

                    assert weight != null;
                    float measuredWeight = Float.parseFloat(String.valueOf(weight.currentWeight));
                    //xAxisDate.add(String.valueOf(weight.entryDate));

                    yAxisWeightMeasured.add(new Entry(Float.parseFloat(String.valueOf(count)), measuredWeight));
                    //yAxisWeightMeasured.add(new Entry(Float.parseFloat(String.valueOf(weight.entryDate)), measuredWeight));
                    //weightEntries.add(new Entry(measuredWeightChart), Float.parseFloat(weight.entryDate));
                    //weightEntries.add(new Entry(Float.parseFloat(weight.entryDate), Float.parseFloat(String.valueOf(weight.currentWeight))));
                }

                ArrayList<ILineDataSet> weightEntriesLineDataSet = new ArrayList<>();
                LineDataSet lineDataSet = new LineDataSet(yAxisWeightMeasured, "Weight");
                lineDataSet.setDrawCircles(true);
                //lineDataSet.setLineWidth(2f);
                lineDataSet.setValueTextSize(12f);
                lineDataSet.setColor(Color.BLUE);

                weightEntriesLineDataSet.add(lineDataSet);

                LimitLine target = new LimitLine(goalWeight, "Goal Weight");
                target.setLineWidth(2f);
                target.enableDashedLine(10f, 10f, 0f);
                target.setLineColor(Color.GREEN);
                target.setTextColor(Color.DKGRAY);
                target.setTextSize(15f);
                target.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);

                LimitLine start = new LimitLine(startingWeight, "Starting Weight");
                start.setLineWidth(2f);
                //start.enableDashedLine(10f, 10f, 0f);
                start.setLineColor(Color.RED);
                start.setTextColor(Color.DKGRAY);
                start.setTextSize(15f);
                start.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);


                /*
                xAxis.setValueFormatter(new IAxisValueFormatter()
                {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return xAxisDate.get((int) value);
                    }
                });
                 */

                LineData data = new LineData(weightEntriesLineDataSet);
                lineChart.setData(data);
                //lineChart.invalidate();
                lineChart.getAxisLeft().addLimitLine(target);
                lineChart.getAxisLeft().addLimitLine(start);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(WeightChart.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}