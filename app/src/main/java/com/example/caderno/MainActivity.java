package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

  BiometricPrompt biometricPrompt;
  BiometricPrompt.PromptInfo promptInfo;
  ConstraintLayout fingerprint;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fingerprint=findViewById(R.id.mainlayout);
        ImageView cad= (ImageView) findViewById(R.id.caderno);
        TextView caderno = (TextView) findViewById(R.id.textView);
        TextView saifincreasework=(TextView)findViewById(R.id.whyudodis);

        BiometricManager biometricManager= BiometricManager.from(this);

        switch (biometricManager.canAuthenticate())
        {

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

        Executor executor= ContextCompat.getMainExecutor(this);

        biometricPrompt=new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error!!! Try again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
              fingerprint.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed!!! Try again", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("CADERNO").setDescription("Use Fingerprint To Login").setDeviceCredentialAllowed(true).build();
        biometricPrompt.authenticate(promptInfo);

        cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, loginpage.class));
            }
        });
        caderno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, loginpage.class));
            }
        });
        saifincreasework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,sidgnuppage.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}