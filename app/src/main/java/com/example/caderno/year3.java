package com.example.caderno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class year3 extends AppCompatActivity {
    ImageView comps,aids,ecs,cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year3);
        comps = (ImageView) findViewById(R.id.imageView6);
        aids = (ImageView) findViewById(R.id.imageView7);
        ecs = (ImageView) findViewById(R.id.imageView8);
        cm = (ImageView) findViewById(R.id.imageView9);
        comps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year3.this,loginpage.class));
            }
        });
        aids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year3.this,loginpage.class));
            }
        });
        ecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year3.this, loginpage.class));
            }
        });
        cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(year3.this,loginpage.class));
            }
        });
    }
}