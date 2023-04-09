package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class loginpage extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText email,PASSWORD;
    private Button loginbtn;
   private TextView forgotpassword,su;
    private FirebaseAuth mAuth;
    BiometricPrompt biometricPrompt1;
    BiometricPrompt.PromptInfo promptInfo1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        Swipetorefresh();

         email= (EditText) findViewById(R.id.username2);
        PASSWORD= (EditText) findViewById(R.id.password);
         loginbtn = (Button) findViewById(R.id.log);
         forgotpassword = (TextView) findViewById(R.id.forgotpass);
         su = (TextView) findViewById(R.id.newuser);
         mAuth = FirebaseAuth.getInstance();
        ImageView imageshowhidepwd;
        imageshowhidepwd= findViewById(R.id.show_hide_password);
        imageshowhidepwd.setImageResource(R.drawable._17_2178237_open_eye_vector_show_hide_password_icon);
        imageshowhidepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PASSWORD.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    PASSWORD.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageshowhidepwd.setImageResource(R.drawable._81_4810872_hide_password_hide_show_password_icon_png_transparent);

                }
                else{
                    PASSWORD.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageshowhidepwd.setImageResource(R.drawable._17_2178237_open_eye_vector_show_hide_password_icon);
                }
            }
        });

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
                                startActivity(new Intent(loginpage.this,mainpage.class));
                                getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();
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


            onStart();

            {
                if (mAuth.getCurrentUser() != null) {
                    BiometricManager biometricManager = BiometricManager.from(this);

                    switch (biometricManager.canAuthenticate()) {

                        case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                            Toast.makeText(getApplicationContext(), "Device doesn't have fingerprint sensor", Toast.LENGTH_SHORT).show();
                            break;

                        case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                            Toast.makeText(getApplicationContext(), "Device fingerprint sensor not working", Toast.LENGTH_SHORT).show();
                            break;

                        case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                            Toast.makeText(getApplicationContext(), "No fingerprint enrolled", Toast.LENGTH_SHORT).show();

                            break;

                    }

                    Executor executor = ContextCompat.getMainExecutor(this);

                    biometricPrompt1 = new BiometricPrompt(loginpage.this, executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                            Toast.makeText(getApplicationContext(), "Authentication error!!! Try again", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(loginpage.this, mainpage.class));
                            getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            finish();

                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            Toast.makeText(getApplicationContext(), "Authentication failed!!! Try again", Toast.LENGTH_SHORT).show();
                        }
                    });

                    promptInfo1 = new BiometricPrompt.PromptInfo.Builder().setTitle("CADERNO").setDescription("Use Fingerprint To Login").setDeviceCredentialAllowed(true).build();
                    biometricPrompt1.authenticate(promptInfo1);


                }
            }
        }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(loginpage.this,MainActivity.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpullloginpage);
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