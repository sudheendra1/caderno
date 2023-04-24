package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class update_email extends AppCompatActivity {
    private FirebaseAuth mauth;
    private FirebaseUser firebaseUser;
    private ProgressBar pb;
    private TextView currentemail,authentication;
    private EditText newemail,password;
    private Button button,button1;
    private String oldemail,newemailid,pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        pb=findViewById(R.id.pb_up_em);
        currentemail=findViewById(R.id.username3_upem);
        newemail=findViewById(R.id.username3_upem_chng);
        password=findViewById(R.id.password2_upem);
        button=findViewById(R.id.signup1_upem);
        button1=findViewById(R.id.signup1_upem_chng);
        authentication=findViewById(R.id.tv_upem_chng);
        button1.setEnabled(false);
        newemail.setEnabled(false);
        mauth=FirebaseAuth.getInstance();
        firebaseUser=mauth.getCurrentUser();
        oldemail=firebaseUser.getEmail();
        currentemail.setText(oldemail);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        if(firebaseUser.equals("")){
            Toast.makeText(this, "Something Went Wrong!!!!", Toast.LENGTH_SHORT).show();
        }
        else{
            reAutheticate(firebaseUser);
        }

    }

    private void reAutheticate(FirebaseUser firebaseUser) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd=password.getText().toString();

                if(pwd.isEmpty()) {
                    password.setError("password is required");
                    password.requestFocus();
                    return;
                }
                else{
                    pb.setVisibility(View.VISIBLE);
                    AuthCredential authCredential= EmailAuthProvider.getCredential(oldemail,pwd);
                    firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pb.setVisibility(View.GONE);
                                Toast.makeText(update_email.this, "Password has been verified you can update email now", Toast.LENGTH_SHORT).show();
                                authentication.setText("You are verified.You can update email now");
                                newemail.setEnabled(true);
                                password.setEnabled(false);
                                button.setEnabled(false);
                                button1.setEnabled(true);
                                newemailid=newemail.getText().toString();

                                button1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String[] split = newemailid.split("@");
                                        String domain= split[1];

                                        if(newemailid.isEmpty()) {
                                            newemail.setError("New EmailID is required");
                                            newemail.requestFocus();
                                            return;
                                        }
                                        else if(!Objects.equals(domain, "eng.rizvi.edu.in")){
                                            newemail.setError("Use only college EmailID");
                                            newemail.clearComposingText();
                                            newemail.requestFocus();
                                            return;
                                        }
                                        else if(!Patterns.EMAIL_ADDRESS.matcher(newemailid).matches()){
                                            newemail.setError("please provide valid email!");
                                            newemail.clearComposingText();
                                            newemail.requestFocus();
                                            return;
                                        }
                                        else if(oldemail.matches(newemailid)){
                                            newemail.setError("Please Enter New EmailID");
                                            newemail.clearComposingText();
                                            newemail.requestFocus();
                                            return;
                                        }
                                        else{
                                            pb.setVisibility(View.VISIBLE);
                                            updateemail(firebaseUser);
                                        }
                                    }

                                });
                            }
                            else{
                                try {
                                    throw task.getException();
                                } catch (Exception e){
                                    Toast.makeText(update_email.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }


                        }
                    });
                }

            }
        });
    }

    private void updateemail(FirebaseUser firebaseUser) {
        firebaseUser.updateEmail(newemailid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    firebaseUser.sendEmailVerification();
                    Toast.makeText(update_email.this, "Email has been updated. Please verify your new EmailID", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(update_email.this,MainActivity.class));

                    getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                }
                else{
                    try {
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(update_email.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
                pb.setVisibility(View.GONE);
            }
        });

    }
}