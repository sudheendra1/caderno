package com.example.caderno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class year4 extends AppCompatActivity {
    ImageView comps,aids,ecs,cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year4);
        comps = (ImageView) findViewById(R.id.imageView18);
        aids = (ImageView) findViewById(R.id.imageView19);
        ecs = (ImageView) findViewById(R.id.imageView20);
        cm = (ImageView) findViewById(R.id.imageView21);
        comps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year4.this,loginpage.class));
            }
        });
        aids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year4.this,loginpage.class));
            }
        });
        ecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year4.this, loginpage.class));
            }
        });
        cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year4.this,loginpage.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}