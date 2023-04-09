package com.example.caderno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class selectyr extends AppCompatActivity {
    private ImageView checkbox,checkbox1,checkbox2,checkbox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectyr);
       checkbox=(ImageView) findViewById(R.id.imageView2);
       checkbox1=(ImageView) findViewById(R.id.imageView3);
        checkbox2=(ImageView) findViewById(R.id.imageView4);
        checkbox3=(ImageView) findViewById(R.id.imageView5);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selectyr.this,year1.class));
            }
        });
        checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selectyr.this,year2.class));
            }
        });
        checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selectyr.this,year3.class));
            }
        });
        checkbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(selectyr.this,year4.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}