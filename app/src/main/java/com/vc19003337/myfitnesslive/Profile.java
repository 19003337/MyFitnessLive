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
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("profiles");
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    String unitsMeasured, fullName, gender, dateOfBirth, emailAddress;
    Double height, startingWeight;
    TextView displayFullName, displayEmailAddress, heightUnit, weightUnit;
    EditText fullNameET, dateOfBirthET, heightET, startingWeightET, emailET;
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

        displayFullName = findViewById(R.id.tv_FullNameLabel);
        displayEmailAddress = findViewById(R.id.tv_EmailAddress);
        fullNameET = findViewById(R.id.et_fullName);
        dateOfBirthET = findViewById(R.id.et_dateOfBirth);
        heightET = findViewById(R.id.et_height);
        startingWeightET = findViewById(R.id.et_startingWeight);
        emailET = findViewById(R.id.et_email);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup_Gender);
        heightUnit = findViewById(R.id.tv_HeightUnit);
        weightUnit = findViewById(R.id.tv_WeightUnit);
        save = findViewById(R.id.btn_save);

        //Set displayed text
        displayFullName.setText(fullNameET.getText().toString());
        displayEmailAddress.setText(currentUser.getEmail());
        //displayEmailAddress.setText(emailET.getText().toString().trim());

        if (unitsMeasured.equals("Imperial"))
        {
            heightUnit.setText("inches");
            weightUnit.setText("pounds");
        }
        else
        {
            heightUnit.setText("cm");
            weightUnit.setText("kg");
        }

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

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserProfile userProfile = snapshot.getValue(UserProfile.class);

                if (userProfile != null && userProfile.emailAddress.equals(currentUser.getEmail()))
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                // Failed to read value
            }
        });


        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int selectedId=radioSexGroup.getCheckedRadioButtonId();
                radioSexButton=(RadioButton)findViewById(selectedId);

                fullName = fullNameET.getText().toString();
                gender = radioSexButton.getText().toString();
                dateOfBirth = dateOfBirthET.getText().toString();
                emailAddress = emailET.getText().toString().trim();
                height = Double.parseDouble(heightET.getText().toString().trim());
                startingWeight = Double.parseDouble(startingWeightET.getText().toString().trim());

                userProfile = new UserProfile(fullName, gender, dateOfBirth, height, startingWeight, emailAddress, unitsMeasured);
                myRef.push().setValue(userProfile)
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
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
    {
        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(),text, Toast.LENGTH_SHORT).show();
        unitsMeasured = adapterView.getContext().toString();;
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