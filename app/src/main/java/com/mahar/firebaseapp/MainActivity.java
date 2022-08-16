package com.mahar.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private Button signInButton,signUpButton;
    private EditText email,password;
    private TextView labelEmail,labelPassword;

    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.inputSignUpEmail);
        password=findViewById(R.id.inputSignUpPassword);
        labelEmail=findViewById(R.id.labelSignUpEmail);
        labelPassword=findViewById(R.id.labelSignUpPassword);
        signUpButton=findViewById(R.id.signUpButton);
        signInButton=findViewById(R.id.signInButton);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) labelEmail.setVisibility(View.VISIBLE);
                else labelEmail.setVisibility(View.INVISIBLE);
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) labelPassword.setVisibility(View.VISIBLE);
                else labelPassword.setVisibility(View.INVISIBLE);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userMail=email.getText().toString();
                String userPassword=password.getText().toString();
                signInFirebase(userMail,userPassword);

            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpActivity= new Intent(MainActivity.this,SignUp.class);
                startActivity(signUpActivity);
            }
        });
    }
    public void signInFirebase(String email,String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent i = new Intent(MainActivity.this,HomePage.class);
                            startActivity(i);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"Mail or Password is not correct",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            Intent i = new Intent(MainActivity.this,HomePage.class);
            startActivity(i);
            finish();
        }
    }
}