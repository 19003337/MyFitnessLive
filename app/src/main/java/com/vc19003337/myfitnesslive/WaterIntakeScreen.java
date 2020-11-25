package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Calendar;

public class WaterIntakeScreen extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Calendar calendar;
    String dateToday;

    TextView currentWaterIntakeDisplayTV;
    Button plusBTN, minusBTN, plus250mlBTN, plus500mlBTN,  plus1500mlBTN;
    int totalWaterIntake;
    WaterIntake waterIntake;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_screen);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateToday = dateFormat.format(calendar.getTime());

        currentWaterIntakeDisplayTV = findViewById(R.id.tv_currentWaterIntakeDisplay);
        plusBTN = findViewById(R.id.btn_Plus);
        minusBTN = findViewById(R.id.btn_Minus);
        plus250mlBTN = findViewById(R.id.btn_Plus250ml);
        plus500mlBTN = findViewById(R.id.btn_Plus500ml);
        plus1500mlBTN = findViewById(R.id.btn_Plus1500ml);

        myRef.child("WaterIntake").addValueEventListener(new ValueEventListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                waterIntake = snapshot.getValue(WaterIntake.class);

                if (waterIntake != null)
                {
                    if(waterIntake.entryDate.equals(dateToday))
                    {
                        totalWaterIntake = waterIntake.getTotalWaterIntake();
                        currentWaterIntakeDisplayTV.setText(String.valueOf(waterIntake.getTotalWaterIntake()) + " ml");
                    }
                }
                else{
                    totalWaterIntake = 0;
                    currentWaterIntakeDisplayTV.setText("0 ml");
                    Toast.makeText(WaterIntakeScreen.this, "Please add your water intake for today", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(WaterIntakeScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        plusBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    totalWaterIntake = totalWaterIntake + 1;
                    waterIntake = new WaterIntake(dateToday, totalWaterIntake);
                    DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                    myRef.child("WaterIntake").setValue(waterIntake)
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    //Toast.makeText(WaterIntakeScreen.this, "Goals saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(WaterIntakeScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                catch (Exception ex)
                {
                    Toast.makeText(WaterIntakeScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        minusBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    totalWaterIntake = totalWaterIntake - 1;
                    waterIntake = new WaterIntake(dateToday, totalWaterIntake);
                    DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                    myRef.child("WaterIntake").setValue(waterIntake)
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    //Toast.makeText(WaterIntakeScreen.this, "Goals saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(WaterIntakeScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                catch (Exception ex)
                {
                    Toast.makeText(WaterIntakeScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        plus250mlBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    totalWaterIntake = totalWaterIntake + 250;
                    waterIntake = new WaterIntake(dateToday, totalWaterIntake);
                    DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                    myRef.child("WaterIntake").setValue(waterIntake)
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    //Toast.makeText(WaterIntakeScreen.this, "Goals saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(WaterIntakeScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                catch (Exception ex)
                {
                    Toast.makeText(WaterIntakeScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        plus500mlBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    totalWaterIntake = totalWaterIntake + 500;
                    waterIntake = new WaterIntake(dateToday, totalWaterIntake);
                    DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                    myRef.child("WaterIntake").setValue(waterIntake)
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    //Toast.makeText(WaterIntakeScreen.this, "Goals saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(WaterIntakeScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                catch (Exception ex)
                {
                    Toast.makeText(WaterIntakeScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        plus1500mlBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    totalWaterIntake = totalWaterIntake + 1500;
                    waterIntake = new WaterIntake(dateToday, totalWaterIntake);
                    DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                    myRef.child("WaterIntake").setValue(waterIntake)
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    //Toast.makeText(WaterIntakeScreen.this, "Goals saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(WaterIntakeScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                catch (Exception ex)
                {
                    Toast.makeText(WaterIntakeScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}