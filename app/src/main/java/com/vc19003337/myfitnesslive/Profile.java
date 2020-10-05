package com.vc19003337.myfitnesslive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class Profile extends AppCompatActivity
{
    TextView displayFullName, displayEmailAddress;
    EditText fullName, gender, dateOfBirth, height, startingWeight, email;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        displayFullName = findViewById(R.id.tv_FullNameLabel);
        displayEmailAddress = findViewById(R.id.tv_EmailAddress);
        fullName = findViewById(R.id.et_fullName);
        gender = findViewById(R.id.et_gender);
        dateOfBirth = findViewById(R.id.et_dateOfBirth);
        height = findViewById(R.id.et_height);
        startingWeight = findViewById(R.id.et_startingWeight);
        email = findViewById(R.id.et_email);

        dateOfBirth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Profile.this,
                        android.R.style.Theme_DeviceDefault_Light_DarkActionBar,
                        mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
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
                dateOfBirth.setText(date);
            }
        };
    }
}