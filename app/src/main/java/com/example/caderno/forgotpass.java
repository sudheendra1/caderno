package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpass extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText emailid;
    private Button resetpass;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        Swipetorefresh();
        emailid=(EditText) findViewById(R.id.email);
        resetpass=(Button) findViewById(R.id.rp);
        auth=FirebaseAuth.getInstance();

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
            }

            private void resetpassword() {
                String email = emailid.getText().toString().trim();
                if(email.isEmpty()){
                    emailid.setError("Email is required!!");
                    emailid.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailid.setError("enter a valid email");
                    emailid.requestFocus();
                    return;
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forgotpass.this, "check your email to reset password", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(forgotpass.this, "Try again!! Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(forgotpass.this,loginpage.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpullforgotpass);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light,android.R.color.holo_red_light);
    }
}