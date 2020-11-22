package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddMeal extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Calendar calendar;
    String mealTypeSelected, dateToday, currentPhotoPath, mealDescription, imageName, imageURL;
    Double protein, fat, carbohydrates, cholesterol, fiber, sodium, potassium;
    Integer calories;
    Image photoToUpload;
    ImageView photoToUploadIV;
    TextView displayDateTodayTV, progressBarTV;
    EditText mealDescriptionET, caloriesET, proteinET, fatET, carbohydratesET, cholesterolET, fiberET, sodiumET, potassiumET;
    Button saveBTN, cameraBTN, galleryBTN;
    Meals meals;
    Uri contentUri, downloadUrl;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference(mAuth.getCurrentUser().getUid());
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
        progressBarTV = findViewById(R.id.textView_Progress);
        progressBar = findViewById(R.id.progressBar_Progress);
        progressBarTV.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

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
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });

        saveBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    mealDescription = mealDescriptionET.getText().toString().trim();
                    calories = Integer.parseInt(caloriesET.getText().toString().trim());
                    //calories = Double.parseDouble(caloriesET.getText().toString().trim());
                    protein = Double.parseDouble(proteinET.getText().toString().trim());
                    fat = Double.parseDouble(fatET.getText().toString().trim());
                    carbohydrates = Double.parseDouble(carbohydratesET.getText().toString().trim());
                    cholesterol = Double.parseDouble(cholesterolET.getText().toString().trim());
                    fiber = Double.parseDouble(fiberET.getText().toString().trim());
                    sodium = Double.parseDouble(sodiumET.getText().toString().trim());
                    potassium = Double.parseDouble(potassiumET.getText().toString().trim());

                    //StorageReference mStorageRef = FirebaseStorage.getInstance().getReference(mAuth.getCurrentUser().getUid());
                    final StorageReference image = mStorageRef.child("Photos/" + imageName);

                    //mStorageRef.child("Photos")
                    image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri)
                                {
                                    progressBarTV.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    imageURL= uri.toString();

                                    meals = new Meals(imageURL, dateToday, mealTypeSelected, mealDescription, calories, protein, fat, carbohydrates, cholesterol, fiber, sodium, potassium);
                                    DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                                    myRef.child("Meals").push().setValue(meals)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    progressBar.setProgress(0);
                                                    progressBarTV.setText("Uploaded 100%");
                                                    Toast.makeText(AddMeal.this, "Meal saved successfully", Toast.LENGTH_SHORT).show();
                                                    Intent openNewActivity = new Intent(AddMeal.this, HomeScreen.class);
                                                    startActivity(openNewActivity);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener()
                                            {
                                                @Override
                                                public void onFailure(@NonNull Exception e)
                                                {
                                                    Toast.makeText(AddMeal.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(AddMeal.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot)
                        {
                            double progress = (100 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                            progressBar.setProgress((int)progress);
                            progressBarTV.setText(progress + "%");
                        }
                    });
                }
                catch (Exception ex)
                {
                    Toast.makeText(AddMeal.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                dispatchTakePictureIntent();
            }
            else {
                Toast.makeText(this, "Camera Permission is required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
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

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                File f = new File(currentPhotoPath);
                photoToUploadIV.setImageURI(Uri.fromFile(f));
                Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                //currentPhotoPath = contentUri.getPath();
                imageName = f.getName();
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.d("tag", "onActivityRequest: Gallery Image Uri: " + imageFileName);
                photoToUploadIV.setImageURI(contentUri);
                //currentPhotoPath = contentUri.getPath();
                imageName = imageFileName;
            }
        }
    }

    private String getFileExt(Uri contentUri)
    {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.vc19003337.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

}