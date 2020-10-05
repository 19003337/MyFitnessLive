package com.vc19003337.myfitnesslive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    String weightUnit, heightUnit, energyUnit, waterUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner spinnerWeight = findViewById(R.id.spinner_Weight);
        ArrayAdapter<CharSequence> adapterW = ArrayAdapter.createFromResource(this, R.array.weightUnits, android.R.layout.simple_spinner_item);
        adapterW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeight.setAdapter(adapterW);
        spinnerWeight.setOnItemSelectedListener(this);

        Spinner spinnerHeight = findViewById(R.id.spinner_Height);
        ArrayAdapter<CharSequence> adapterH = ArrayAdapter.createFromResource(this, R.array.heightUnits, android.R.layout.simple_spinner_item);
        adapterH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeight.setAdapter(adapterH);
        spinnerHeight.setOnItemSelectedListener(this);

        Spinner spinnerEnergy = findViewById(R.id.spinner_Energy);
        ArrayAdapter<CharSequence> adapterE = ArrayAdapter.createFromResource(this, R.array.energyUnits, android.R.layout.simple_spinner_item);
        adapterE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnergy.setAdapter(adapterE);
        spinnerEnergy.setOnItemSelectedListener(this);

        Spinner spinnerWater = findViewById(R.id.spinner_Water);
        ArrayAdapter<CharSequence> adapterWT = ArrayAdapter.createFromResource(this, R.array.waterUnits, android.R.layout.simple_spinner_item);
        adapterWT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWater.setAdapter(adapterWT);
        spinnerWater.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
    {
        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}