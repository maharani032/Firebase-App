package com.mahar.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText name;
    private TextView textView;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference().child("Users");
    DatabaseReference reference2=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        name=findViewById(R.id.editText);
        textView=findViewById(R.id.textView);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String realname = (String) snapshot.child("Users").child("name").getValue();
                name.setText(realname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=name.getText().toString();

                reference.child("userName").setValue(username);
            }
        });
    }
}