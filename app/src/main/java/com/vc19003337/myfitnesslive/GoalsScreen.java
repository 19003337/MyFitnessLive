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

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoalsScreen extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    Double goalWeight;
    Integer dailyCalorieIntake;
    EditText goalWeightET, dailyCalorieIntakeET;
    TextView goalWeightTV, dailyCalorieIntakeTV, goalsHeadingTV;
    Button save;
    Goals goals;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_screen);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

        goalWeightET = findViewById(R.id.et_goalWeight);
        dailyCalorieIntakeET = findViewById(R.id.et_dailyCalorieIntake);
        goalWeightTV = findViewById(R.id.tv_GoalWeight);
        dailyCalorieIntakeTV = findViewById(R.id.tv_DailyCalorieIntake);
        goalsHeadingTV = findViewById(R.id.tv_GoalsHeading);
        save = findViewById(R.id.btn_save);


        myRef.child("Goals").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                for (DataSnapshot goalValues : snapshot.getChildren())
                {
                    goals = goalValues.getValue(Goals.class);
                }

                if (goals != null)
                {
                    goalWeightET.setText(String.valueOf(goals.getGoalWeight()));
                    dailyCalorieIntakeET.setText(String.valueOf(goals.getDailyCalorieIntake()));
                }
                else{
                    Toast.makeText(GoalsScreen.this, "Please set your goals", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(GoalsScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    goalWeight = Double.parseDouble(goalWeightET.getText().toString().trim());
                    dailyCalorieIntake = Integer.parseInt(dailyCalorieIntakeET.getText().toString().trim());

                    goals = new Goals(goalWeight, dailyCalorieIntake);
                    DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                    myRef.child("Goals").setValue(goals)
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
                catch (Exception ex)
                {
                    Toast.makeText(GoalsScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}