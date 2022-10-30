package com.example.caderno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class profile extends AppCompatActivity {

private Button pdf;
private Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pdf=(Button) findViewById(R.id.pdf);
        log=(Button)findViewById(R.id.log1);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile.this,loginpage.class));
            }
        });



       pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
golink("https://firebasestorage.googleapis.com/v0/b/caderno-419a2.appspot.com/o/textbookm%2FSem%203%20Kumbhojkar.pdf?alt=media&token=fb98924f-c454-48fa-af88-353b87d75de1");
            }

            private void golink(String s) {
                Uri uri=Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });


    }
}