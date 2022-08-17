package com.mahar.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetActivity extends AppCompatActivity {

    TextView label;
    EditText inputEmail;
    Button reset;

    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        label=findViewById(R.id.labelEmailForgetPassword);
        inputEmail=findViewById(R.id.inputEmailForgetPassword);
        reset=findViewById(R.id.resetButton);

        inputEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) label.setVisibility(View.VISIBLE);
                else label.setVisibility(View.INVISIBLE);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=inputEmail.getText().toString();

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(forgetActivity.this,"Please check ur mail"
                                 ,Toast.LENGTH_SHORT).show();
                         finish();
                     }
                     else{
                         Toast.makeText(forgetActivity.this,"Your email not register"
                                 ,Toast.LENGTH_SHORT).show();
                     }
                    }
                });

            }
        });
    }
}