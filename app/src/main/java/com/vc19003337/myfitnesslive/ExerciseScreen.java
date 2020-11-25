package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ExerciseScreen extends AppCompatActivity implements ExeciseInput.ExeciseInputListener
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    List<String> exerciseActivitiesList;
    ArrayAdapter adapter;
    Calendar calendar;
    String dateToday, exerciseType;
    int totalCaloriesBurned, caloriesBurned;
    Exercise exercise;
    ListView exerciseActivitiesLV;
    TextView currentCaloriesBurnedDisplayTV;
    Button addNewActivityBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_screen);
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateToday = dateFormat.format(calendar.getTime());

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

        currentCaloriesBurnedDisplayTV = findViewById(R.id.tv_currentCaloriesBurnedDisplay);
        addNewActivityBTN = findViewById(R.id.btn_AddNewActivity);
        exerciseActivitiesLV = findViewById(R.id.lv_ExerciseActivities);

        totalCaloriesBurned = 0;

        myRef.child("Exercise").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                exercise = new Exercise();
                exerciseActivitiesList = new ArrayList<String>();

                for (DataSnapshot exerciseValues : snapshot.getChildren())
                {
                    exercise  = exerciseValues.getValue(Exercise.class);

                    assert exercise != null;
                    if(exercise.entryDate.equals(dateToday))
                    {
                        exerciseActivitiesList.add(exercise.toString());
                        totalCaloriesBurned += exercise.caloriesBurned;
                    }
                }

                adapter = new ArrayAdapter(ExerciseScreen.this, android.R.layout.simple_list_item_1, exerciseActivitiesList);
                Collections.reverse(exerciseActivitiesList);
                exerciseActivitiesLV.setAdapter(adapter);

                currentCaloriesBurnedDisplayTV.setText(totalCaloriesBurned + " kcal");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(ExerciseScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        addNewActivityBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDialog();
            }
        });
    }

    public void openDialog()
    {
        ExeciseInput exerciseInput = new ExeciseInput();
        exerciseInput.show(getSupportFragmentManager(), "Exercise input");
    }

    @Override
    public void applyTexts(String type, int calories)
    {
        exerciseType = type;
        caloriesBurned = calories;

        try
        {
            exercise = new Exercise(dateToday, exerciseType, caloriesBurned);
            DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
            myRef.child("Exercise").push().setValue(exercise)
                    .addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            Toast.makeText(ExerciseScreen.this, "New Exercise Activity successfully saved", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(ExerciseScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        catch (Exception ex)
        {
            Toast.makeText(ExerciseScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}