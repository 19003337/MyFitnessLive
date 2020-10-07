package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GoalsScreen extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("goals");
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    Double startingWeight, currentWeight, goalWeight;
    Integer dailyCalorieIntake;
    TextView startingWeightTV, currentWeightTV;
    EditText goalWeightET, dailyCalorieIntakeET;
    Button save;
    Goals goals;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_screen);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        startingWeightTV = findViewById(R.id.tv_startingWeightOutput);
        currentWeightTV = findViewById(R.id.tv_currentWeightOutput);
        goalWeightET = findViewById(R.id.et_goalWeight);
        dailyCalorieIntakeET = findViewById(R.id.et_dailyCalorieIntake);
        save = findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    startingWeight = Double.parseDouble(startingWeightTV.getText().toString().trim());
                    currentWeight = Double.parseDouble(currentWeightTV.getText().toString().trim());
                    goalWeight = Double.parseDouble(goalWeightET.getText().toString().trim());
                    dailyCalorieIntake = Integer.parseInt(startingWeightTV.getText().toString().trim());

                    goals = new Goals(startingWeight, currentWeight, goalWeight, dailyCalorieIntake);
                }
                catch (Exception ex)
                {
                    Toast.makeText(GoalsScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

                myRef.push().setValue(goals)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(GoalsScreen.this, "Goals saved successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(GoalsScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}