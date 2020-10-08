package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Date;
import java.util.List;

public class WeightScreen extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    Calendar calendar;
    Double currentWeight;
    String dateToday;
    TextView displayDateToday;
    EditText currentWeightET;
    Button save;
    Weight weight;
    ListView weightChanges;
    //String[] changesListed;

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

        currentWeightET = findViewById(R.id.et_weightToday);
        save = findViewById(R.id.btn_save);
        weightChanges = findViewById(R.id.lv_WeightChanges);

        /*
        final List<String> changesListedArrayList = new ArrayList<>(Arrays.asList(changesListed));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>
                (WeightScreen.this, android.R.layout.simple_list_item_1, changesListedArrayList);
        weightChanges.setAdapter(adapter);
         */

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

                    /*
                    myRef.child("WeightChanges").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {

                            for (DataSnapshot weightValues : snapshot.getChildren())
                            {
                                weight = weightValues.getValue(Weight.class);
                                changesListedArrayList.add(weight.toString());
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {
                            Toast.makeText(WeightScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                     */
                }
                catch (Exception ex)
                {
                    Toast.makeText(WeightScreen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}