package com.vc19003337.myfitnesslive;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddMeal extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Calendar calendar;
    String mealTypeSelected, dateToday, mealDescription;
    Image photoToUpload;
    ImageView photoToUploadIV;
    TextView displayDateTodayTV;
    EditText mealDescriptionET, caloriesET, proteinET, fatET, carbohydratesET, cholesterolET, fiberET, sodiumET, potassiumET;
    Button save;
    Meals meals;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateToday = dateFormat.format(calendar.getTime());

        displayDateTodayTV = findViewById(R.id.tv_DateToday);
        displayDateTodayTV.setText(dateToday);

        photoToUploadIV = findViewById(R.id.imageView_MealPhoto);
        mealDescriptionET = findViewById(R.id.et_mealDescription);
        caloriesET = findViewById(R.id.et_calories);
        proteinET = findViewById(R.id.et_protein);
        fatET = findViewById(R.id.et_fat);
        carbohydratesET = findViewById(R.id.et_carbohydrates);
        cholesterolET = findViewById(R.id.et_cholesterol);
        fiberET = findViewById(R.id.et_fiber);
        sodiumET = findViewById(R.id.et_sodium);
        potassiumET = findViewById(R.id.et_potassium);

        final Spinner spinnerMealTypes = findViewById(R.id.spinner_MealTypes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddMeal.this, R.array.mealTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMealTypes.setAdapter(adapter);
        spinnerMealTypes.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
    {
        mealTypeSelected = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
        Toast.makeText(AddMeal.this, "Please select your meal type", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        //Nothing to code here at this stage
    }
}