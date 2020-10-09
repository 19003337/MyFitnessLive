package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("logins");
    EditText fullName, email, password, confirmPassword;
    Button signUp, signIn;
    private FirebaseAuth mAuth;
    Login logins;
    //String usersName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.et_fullName);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        confirmPassword = findViewById(R.id.et_passwordConfirmation);
        signIn = findViewById(R.id.btn_signIn);
        signUp = findViewById(R.id.btn_signUp);

        signIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent openNewActivity = new Intent(SignUp.this, MainActivity.class);
                startActivity(openNewActivity);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    final String enteredFullName = fullName.getText().toString().trim();
                    String enteredEmail = email.getText().toString().trim();
                    String enteredPassword = password.getText().toString().trim();
                    String enteredPasswordConfirmation = confirmPassword.getText().toString().trim();

                    logins = new Login(enteredFullName, enteredEmail);
                    //DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
                    //myRef.child("Login").setValue(logins);

                    if (enteredPassword.equals(enteredPasswordConfirmation))
                    {
                        mAuth.createUserWithEmailAndPassword(enteredEmail, enteredPassword)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            myRef.push().setValue(logins);
                                            Toast.makeText(SignUp.this, "User "
                                                            + mAuth.getCurrentUser().getEmail() + "successfully registered. Please setup your profile.",
                                                    Toast.LENGTH_SHORT).show();

                                            Intent openNewActivity = new Intent(SignUp.this, Profile.class);
                                            openNewActivity.putExtra("FullName", enteredFullName);
                                            startActivity(openNewActivity);
                                        }

                                        else
                                        {
                                            Toast.makeText(SignUp.this, "Error! Not registered",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(SignUp.this, "Password and Password Confirmation entered do not match.", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(SignUp.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}