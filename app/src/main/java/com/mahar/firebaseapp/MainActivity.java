package com.mahar.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Button signInButton,signUpButton;
    private EditText email,password;
    private TextView labelEmail,labelPassword;

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
}