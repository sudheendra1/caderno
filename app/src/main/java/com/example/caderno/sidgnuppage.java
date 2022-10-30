package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class sidgnuppage extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private EditText username1,email,pass;
    private Button signup;
    private TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidgnuppage);
        mAuth = FirebaseAuth.getInstance();
        username1 = (EditText) findViewById(R.id.username3);
         email = (EditText) findViewById(R.id.emailid);
         pass = (EditText) findViewById(R.id.password2);
         signup = (Button) findViewById(R.id.signup1);
         log = (TextView) findViewById(R.id.login);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sidgnuppage.this, loginpage.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.signup1:
                        registeruser();
                        startActivity(new Intent(sidgnuppage.this,selectyr.class));
                        break;

                }
            }

            private void registeruser() {
                String name= username1.getText().toString().trim();
                String passw= pass.getText().toString().trim();
                String em= email.getText().toString().trim();
                if(name.isEmpty()){
                    username1.setError("Full name is required");
                    username1.requestFocus();
                    return;
                }
                if(passw.isEmpty()){
                    pass.setError("password is required");
                    pass.requestFocus();
                    return;
                }
                if(em.isEmpty()){
                    email.setError("email is required");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){
                    email.setError("please provide valid email!");
                    email.requestFocus();
                    return;
                }
                if(passw.length()<8){
                    pass.setError("password should minimum 8 characters long");
                    pass.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(em,passw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user= new User(name,em);
                                    FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(sidgnuppage.this,"registration successfull",Toast.LENGTH_SHORT).show();

                                            }
                                            else{
                                                Toast.makeText(sidgnuppage.this,"registration failed Try agin!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else{
                                    Toast.makeText(sidgnuppage.this, "registration failed!! try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
}