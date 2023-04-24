package com.example.caderno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class papers_year3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papers_year3);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(papers_year3.this,mainpage.class));



    }
}