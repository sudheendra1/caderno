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
import com.google.firebase.auth.FirebaseUser;

public class loginpage extends AppCompatActivity {
    private EditText email,PASSWORD;
    private Button loginbtn;
   private TextView forgotpassword,su;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

         email= (EditText) findViewById(R.id.username2);
        PASSWORD= (EditText) findViewById(R.id.password);
         loginbtn = (Button) findViewById(R.id.log);
         forgotpassword = (TextView) findViewById(R.id.forgotpass);
         su = (TextView) findViewById(R.id.newuser);
         mAuth = FirebaseAuth.getInstance();

        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginpage.this, sidgnuppage.class));
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginpage.this,forgotpass.class));
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();

            }

            private void userlogin() {
                String usern= email.getText().toString().trim();
                String pass = PASSWORD.getText().toString().trim();
                if(usern.isEmpty()){
                    email.setError("email is required!!");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(usern).matches()){
                    email.setError("provide valid email id");
                    email.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    PASSWORD.setError("password is required");
                    PASSWORD.requestFocus();
                    return;
                }
                if(pass.length()<8){
                    PASSWORD.setError("min password length is 8 characters");
                    PASSWORD.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(usern,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()) {
                               startActivity(new Intent(loginpage.this,profile.class));
                            } else{
                                user.sendEmailVerification();
                                Toast.makeText(loginpage.this,"check your email for verification!",Toast.LENGTH_SHORT).show();
                            }


                        }
                        else{
                            Toast.makeText(loginpage.this, "Failed to login please check your credentials",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}