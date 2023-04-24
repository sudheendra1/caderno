package com.example.caderno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class papers_year1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papers_year1);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(papers_year1.this,mainpage.class));



    }
}