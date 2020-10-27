package com.vc19003337.myfitnesslive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{
    EditText email, password;
    Button login, register, forgotPassword;
    CheckBox rememberMe;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    CardView loginCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.btn_signIn);
        register = findViewById(R.id.btn_signUp);
        forgotPassword = findViewById(R.id.btn_forgotPassword);
        rememberMe = findViewById(R.id.checkbox_rememberMe);
        loginCardView = findViewById(R.id.cardView_login);

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent openNewActivity = new Intent(MainActivity.this, SignUp.class);
                startActivity(openNewActivity);
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String enteredEmail = email.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Logged in "
                                            + mAuth.getCurrentUser().getEmail() + " successfully",
                                    Toast.LENGTH_SHORT).show();

                            Intent openNewActivity = new Intent(MainActivity.this, HomeScreen.class);
                            startActivity(openNewActivity);

                            //Clear entered credentials
                            email.setText("");
                            password.setText("");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent openNewActivity = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(openNewActivity);
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        /*
        if (currentUser != null)
        {
            Toast.makeText(MainActivity.this, "You are already logged in " + currentUser.getEmail(),
                    Toast.LENGTH_LONG).show();

            Intent openNewActivity = new Intent(MainActivity.this, HomeScreen.class);
            startActivity(openNewActivity);

            mAuth.signOut();
        }
        */
    }
}