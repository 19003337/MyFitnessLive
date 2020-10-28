//19003337
//Charmaine Dobbin
//OPSC Task 2
/*_____________________________________code attribution________________________________________________
The code used for sorting the List<> was taking from Stackoverflow:
Author: Rawat, H.
Link: Stackoverflow. 2017. [Online] Available at: https://stackoverflow.com/questions/34156996/firebase-data-desc-sorting-in-android/34158197#34158197
[Accessed 13 October 2020].
____________________________________________end________________________________________________________
*/

package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WeightScreen extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    List<String> weightChangesList;
    ArrayAdapter adapter;

    Calendar calendar;
    Double currentWeight;
    String dateToday;
    TextView displayDateToday, weightUnit;
    EditText currentWeightET;
    Button save;
    Weight weight;
    ListView weightChangesLV;
    UnitSettings unitSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_screen);
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateToday = dateFormat.format(calendar.getTime());

        displayDateToday = findViewById(R.id.tv_DateToday);
        displayDateToday.setText(dateToday);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());

        currentWeightET = findViewById(R.id.et_weightToday);
        save = findViewById(R.id.btn_save);
        weightChangesLV = findViewById(R.id.lv_WeightChanges);
        weightUnit = findViewById(R.id.tv_WeightUnit);

        myRef.child("WeightChanges").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                weight = new Weight();
                weightChangesList = new ArrayList<String>();
                for (DataSnapshot weightValues : snapshot.getChildren())
                {
                    weight = weightValues.getValue(Weight.class);
                    weightChangesList.add(weight.ToString());
                }

                adapter = new ArrayAdapter(WeightScreen.this, android.R.layout.simple_list_item_1, weightChangesList);
                Collections.reverse(weightChangesList);
                weightChangesLV.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(WeightScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        myRef.child("Settings").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                unitSettings = snapshot.getValue(UnitSettings.class);

                if (unitSettings != null)
                {
                    try
                    {
                        if (unitSettings.getUnitSetting().equals("Metric"))
                        {
                            weightUnit.setText("kgs");
                        }
                        if (unitSettings.getUnitSetting().equals("Imperial"))
                        {
                            weightUnit.setText("pounds");
                        }

                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(WeightScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //If unitSetting has not been selected
                    weightUnit.setText("kgs");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(WeightScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    currentWeight = Double.parseDouble(currentWeightET.getText().toString().trim());

                    weight = new Weight(dateToday, currentWeight);
                    DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                    myRef.child("WeightChanges").push().setValue(weight)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(WeightScreen.this, "Today's weight change successfully updated", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(WeightScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                catch (Exception ex)
                {
                    Toast.makeText(WeightScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}