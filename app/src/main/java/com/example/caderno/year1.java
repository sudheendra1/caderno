package com.example.caderno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class year1 extends AppCompatActivity {
    ImageView comps,aids,ecs,cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year1);
        comps = (ImageView) findViewById(R.id.imageView14);
        aids = (ImageView) findViewById(R.id.imageView15);
        ecs = (ImageView) findViewById(R.id.imageView16);
        cm = (ImageView) findViewById(R.id.imageView17);
        comps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year1.this,loginpage.class));
            }
        });
        aids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year1.this,loginpage.class));
            }
        });
        ecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year1.this, loginpage.class));
            }
        });
        cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year1.this,loginpage.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}