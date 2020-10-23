package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
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
    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Calendar calendar;
    String mealTypeSelected, dateToday, mealDescription;
    Image photoToUpload;
    ImageView photoToUploadIV;
    TextView displayDateTodayTV;
    EditText mealDescriptionET, caloriesET, proteinET, fatET, carbohydratesET, cholesterolET, fiberET, sodiumET, potassiumET;
    Button saveBTN, cameraBTN, galleryBTN;
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
        saveBTN = findViewById(R.id.btn_Save);
        cameraBTN = findViewById(R.id.btn_Camera);
        galleryBTN = findViewById(R.id.btn_Gallery);

        final Spinner spinnerMealTypes = findViewById(R.id.spinner_MealTypes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddMeal.this, R.array.mealTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMealTypes.setAdapter(adapter);
        spinnerMealTypes.setOnItemSelectedListener(this);

        cameraBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AskCameraPermissions();
            }
        });

        galleryBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        saveBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }

    private void AskCameraPermissions()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
        else {
            OpenCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                OpenCamera();
            }
            else {
                Toast.makeText(this, "Camera Permission is required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OpenCamera()
    {
        Intent camera = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
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