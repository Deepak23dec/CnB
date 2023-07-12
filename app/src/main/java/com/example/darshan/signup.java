package com.example.darshan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class signup extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button signupbtn=findViewById(R.id.signupbtn);
        Button allreadyaccbtn=findViewById(R.id.allreadyaccbtn);
        EditText emailbox2=findViewById(R.id.emailbox2);
        EditText passwordbox2=findViewById(R.id.passwordbox2);
        EditText name2=findViewById(R.id.name2);
        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail,pass,name;
                mail=emailbox2.getText().toString();
                pass=passwordbox2.getText().toString();
                name=name2.getText().toString();
                User user=new User();
                user.setEmail(mail);
                user.setPass(pass);
                user.setName(name);

                auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("Users")
                            .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(signup.this, "Account is created", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(signup.this, login.class));
                                        }
                                    });

                        }
                 else    {Toast.makeText(signup.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();}
                    }
                });

            }
        });


        allreadyaccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, login.class));
            }
        });
    }
}