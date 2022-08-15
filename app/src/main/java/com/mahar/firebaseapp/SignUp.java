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

public class SignUp extends AppCompatActivity {
    private Button signUpButton;
    private EditText email,password;
    private TextView labelEmail,labelPassword,information;

    FirebaseAuth auth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email=findViewById(R.id.inputSignUpEmail);
        password=findViewById(R.id.inputSignUpPassword);
        labelEmail=findViewById(R.id.labelSignUpEmail);
        labelPassword=findViewById(R.id.labelSignUpPassword);
        signUpButton=findViewById(R.id.buttonSignUp);
        information=findViewById(R.id.information);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) labelEmail.setVisibility(View.VISIBLE);
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

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                information.setVisibility(View.INVISIBLE);
                String userMail= email.getText().toString();
                String userPassword=password.getText().toString();
                if(userPassword.length()<=5 || !userMail.contains("@")) {
                    information.setText("your password must be at least 6 characters or Your email isnot valid");
                    information.setVisibility(View.VISIBLE);
                    return;
                }
                signUpFirebase(userMail,userPassword);
            }
        });
    }
    public void signUpFirebase(String email,String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            information.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUp.this,"Your account has been created"
                            ,Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUp.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            information.setText("Authentication failed");
                            information.setVisibility(View.VISIBLE);
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }
}