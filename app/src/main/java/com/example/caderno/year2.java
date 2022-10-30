package com.example.caderno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class year2 extends AppCompatActivity {
    ImageView comps,aids,ecs,cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year2);
        comps = (ImageView) findViewById(R.id.imageView10);
        aids = (ImageView) findViewById(R.id.imageView11);
        ecs = (ImageView) findViewById(R.id.imageView12);
        cm = (ImageView) findViewById(R.id.imageView13);
        comps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year2.this,loginpage.class));
            }
        });
        aids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year2.this,loginpage.class));
            }
        });
        ecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year2.this, loginpage.class));
            }
        });
        cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year2.this,loginpage.class));
            }
        });
    }
}