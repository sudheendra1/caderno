package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
  private static final String CHANNEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 100;
    Notification notification;
    NotificationManagerCompat notificationManagerCompat;
    private SharedPreferences sharedPreferences;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        boolean notopened =sharedPreferences.getBoolean("notopened",false);
      /* Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_baseline_library_books_24,null);
        BitmapDrawable bitmapDrawable= (BitmapDrawable) drawable;
        Bitmap largeicon = bitmapDrawable.getBitmap();*/



        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            NotificationChannel channel= new NotificationChannel("My Channel","new channel",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager= (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"My Channel");
                builder.setSmallIcon(R.drawable.whatsapp_image_2023_04_11_at_23_38_02);
                       builder.setContentTitle("new message");
                                builder.setContentText("A new PDF was just uploaded check it out");
        notification=builder.build();
        notificationManagerCompat=NotificationManagerCompat.from(this);

        if(notopened){
            notificationManagerCompat.notify(1,notification);
        }*/


        /*else{
            notification=new Notification.Builder(this)
                    .setLargeIcon(largeicon)
                    .setSmallIcon(R.drawable.whatsapp_image_2023_04_11_at_23_38_02)
                    .setContentText("A new PDF was just uploaded")
                    .setSubText("mew message")
                    .build();
        }*/

        Intent Backgroundservice =new Intent(this,pushnotificationfirebase.class);
        startService(Backgroundservice);
        fingerprint=findViewById(R.id.mainlayout);
        ImageView cad= (ImageView) findViewById(R.id.caderno);
        TextView caderno = (TextView) findViewById(R.id.textView);
        TextView saifincreasework=(TextView)findViewById(R.id.whyudodis);

       /* BiometricManager biometricManager= BiometricManager.from(this);

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
        biometricPrompt.authenticate(promptInfo);*/

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