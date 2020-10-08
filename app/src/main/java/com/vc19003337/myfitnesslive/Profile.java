package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.Calendar;

public class Profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("profiles");
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    //DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

    String unitsMeasured, fullName, gender, dateOfBirth, emailAddress;
    Double height, startingWeight, targetWeight;
    Integer targetCalories;
    TextView displayFullName, displayEmailAddress, heightUnit, weightUnit, weightUnitGoal;
    EditText fullNameET, dateOfBirthET, heightET, startingWeightET, emailET, targetWeightET, targetCaloriesET;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button save;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

        displayFullName = findViewById(R.id.tv_FullName);
        displayEmailAddress = findViewById(R.id.tv_Email);
        fullNameET = findViewById(R.id.et_fullName);
        dateOfBirthET = findViewById(R.id.et_dateOfBirth);
        heightET = findViewById(R.id.et_height);
        startingWeightET = findViewById(R.id.et_startingWeight);
        targetWeightET = findViewById(R.id.et_goalWeight);
        targetCaloriesET = findViewById(R.id.et_targetCalories);

        emailET = findViewById(R.id.et_emailAddress);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup_Gender);
        heightUnit = findViewById(R.id.tv_HeightUnit);
        weightUnit = findViewById(R.id.tv_WeightUnit);
        weightUnitGoal = findViewById(R.id.tv_WeightUnitGoal);
        save = findViewById(R.id.btn_save);

        //Set displayed text
        //fullName = getIntent().getStringExtra("Full Name");
        //displayFullName.setText(fullName);
        //fullNameET.setText(fullName);
        displayEmailAddress.setText(currentUser.getEmail());
        emailET.setText(currentUser.getEmail());

        Spinner spinnerUnits = findViewById(R.id.spinner_UnitsMeasured);
        ArrayAdapter<CharSequence> adapterW = ArrayAdapter.createFromResource(this, R.array.unitsMeasured, android.R.layout.simple_spinner_item);
        adapterW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnits.setAdapter(adapterW);
        spinnerUnits.setOnItemSelectedListener(this);

        dateOfBirthET.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Profile.this,
                        android.R.style.Theme_Light_Panel, mDateSetListener, year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = day+ "/" + month + "/" + year;
                dateOfBirthET.setText(date);
            }
        };

        myRef.child("Profile").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot profileValues : snapshot.getChildren())
                {
                    userProfile = profileValues.getValue(UserProfile.class);
                }

                if (userProfile != null && userProfile.emailAddress.equals(currentUser.getEmail()))
                {
                    fullNameET.setText(userProfile.getFullName());
                    radioSexButton.setText(userProfile.getGender());
                    dateOfBirthET.setText(userProfile.getDateOfBirth());
                    emailET.setText(userProfile.getEmailAddress());
                    //heightET.setText(userProfile.getHeight());
                    //startingWeightET.setText(userProfile.getStartingWeight());
                    //targetWeightET.setText(userProfile.getGoalWeight());
                    //targetCaloriesET.setText(userProfile.getDailyCalorieIntake());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(Profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    int selectedId=radioSexGroup.getCheckedRadioButtonId();
                    radioSexButton=(RadioButton)findViewById(selectedId);

                    fullName = fullNameET.getText().toString().trim();
                    gender = radioSexButton.getText().toString().trim();
                    dateOfBirth = dateOfBirthET.getText().toString().trim();
                    emailAddress = emailET.getText().toString().trim();
                    height = Double.parseDouble(heightET.getText().toString().trim());
                    startingWeight = Double.parseDouble(startingWeightET.getText().toString().trim());
                    targetWeight = Double.parseDouble(targetWeightET.getText().toString().trim());
                    targetCalories = Integer.parseInt(targetCaloriesET.getText().toString().trim());

                    userProfile = new UserProfile(fullName, emailAddress, gender, dateOfBirth, height, startingWeight,  targetWeight, targetCalories, unitsMeasured);

                    DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                    myRef.child("Profile").setValue(userProfile)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Profile.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                catch (Exception ex)
                {
                    Toast.makeText(Profile.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
    {
        unitsMeasured = adapterView.getItemAtPosition(position).toString();
        //String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(),unitsMeasured, Toast.LENGTH_SHORT).show();

        if (unitsMeasured.equals("Imperial"))
        {
            heightUnit.setText("inches");
            weightUnit.setText("pounds");
            weightUnitGoal.setText("pounds");
        }
        else
        {
            heightUnit.setText("cm");
            weightUnit.setText("kg");
            weightUnitGoal.setText("kg");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }
}