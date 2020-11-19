package com.vc19003337.myfitnesslive;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeightFragment extends Fragment
{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    LineChart mpLineChart;
    Goals goals, goalsUpload;
    Weight weight;
    String goalWeight, targetCalories;
    List<String> weightChangesList;

    public WeightFragment()
    {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    /*
    public static WeightFragment newInstance(String param1, String param2) {
        WeightFragment fragment = new WeightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

        //mpLineChart = findViewById(R.id.lineChartWeight);

        myRef.child("Goals").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                goalsUpload = snapshot.getValue(Goals.class);

                if (goalsUpload != null)
                {
                    //goalWeightET.setText(DecimalFormat.getNumberInstance().format(goals.getGoalWeight()));
                    goalWeight = (String.valueOf(goalsUpload.getGoalWeight()));
                    targetCalories = (String.valueOf(goalsUpload.getDailyCalorieIntake()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                //Toast.makeText(GoalsScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        myRef.child("WeightChanges").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                weight = new Weight();
                weightChangesList = new ArrayList<String>();
                for (DataSnapshot weightValues : snapshot.getChildren())
                {
                    weight = weightValues.getValue(Weight.class);
                    weightChangesList.add(weight.ToString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                //Toast.makeText(WeightScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }
}