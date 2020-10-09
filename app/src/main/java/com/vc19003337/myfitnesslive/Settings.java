package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
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

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    String unitsMeasured, weightUnit, heightUnit;
    TextView weightUnitTV, heightUnitTV;
    Button save;
    UnitSettings unitSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

        weightUnitTV = findViewById(R.id.tv_WeightUnit);
        heightUnitTV = findViewById(R.id.tv_HeightUnit);
        save = findViewById(R.id.btn_save);

        final Spinner spinnerUnitsMeasured = findViewById(R.id.spinner_UnitsMeasured);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Settings.this, R.array.unitsMeasured, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnitsMeasured.setAdapter(adapter);
        spinnerUnitsMeasured.setOnItemSelectedListener(this);

        myRef.child("Settings").addValueEventListener(new ValueEventListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot settingsValues : snapshot.getChildren())
                {
                    unitSettings = settingsValues.getValue(UnitSettings.class);
                }

                if (unitSettings != null)
                {
                    try
                    {
                        if (unitSettings.getUnitSetting().equals("Metric"))
                        {
                            spinnerUnitsMeasured.setSelection(0);
                        }
                        else if (unitSettings.getUnitSetting().equals("Imperial"))
                        {
                            spinnerUnitsMeasured.setSelection(1);
                        }

                        weightUnitTV.setText(unitSettings.getWeightUnit());
                        heightUnitTV.setText(unitSettings.getHeightUnit());
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(Settings.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    {
                        weightUnitTV.setText("kilograms");
                        heightUnitTV.setText("meters");

                        Toast.makeText(Settings.this, "Please save your selection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(Settings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {

                    weightUnit = weightUnitTV.getText().toString().trim();
                    heightUnit = heightUnitTV.getText().toString().trim();

                    unitSettings = new UnitSettings(unitsMeasured, weightUnit, heightUnit);

                    DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                    myRef.child("Settings").setValue(unitSettings)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Settings.this, "Settings saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(Settings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    //Intent openNewActivity = new Intent(Settings.this, HomeScreen.class);
                    //startActivity(openNewActivity);
                }
                catch (Exception ex)
                {
                    Toast.makeText(Settings.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
    {
        //String text = adapterView.getItemAtPosition(position).toString();
        //Toast.makeText(adapterView.getContext(),text, Toast.LENGTH_SHORT).show();

        unitsMeasured = adapterView.getItemAtPosition(position).toString();

        if (unitsMeasured.equals("Imperial"))
        {
            heightUnitTV.setText("inches");
            weightUnitTV.setText("pounds");
        }
        else
        {
            heightUnitTV.setText("meters");
            weightUnitTV.setText("kilograms");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}