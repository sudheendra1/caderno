package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class daily_quiz extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz);
        Swipetorefresh();

        listView= (ListView) findViewById(R.id.main_quiz);

        String[] subjects= {"Engineering Mathematics III","Discrete Structure and Graph Theory","Data Structure","Digital Logic and Computer Architecture","Computer Graphics","Engineering Mathematics IV","Microprocessor", "Analysis of Algorithms","Database Management Systems","Operating System","Python"};
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,subjects){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                TextView textView= (TextView) view.findViewById(android.R.id.text1);
                return view;
            }
        };

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = listView.getItemAtPosition(i).toString();

                if (item.equals("Engineering Mathematics IV")){
                    startActivity(new Intent(daily_quiz.this,em4_quiz_mods.class));

                }
                else if(item.equals("Microprocessor")){
                    startActivity(new Intent(daily_quiz.this,mp_quiz_mods.class));

                }

                else if(item.equals("Analysis of Algorithms")){
                    startActivity(new Intent(daily_quiz.this,aoa_quiz_mods.class));

                }
                else if(item.equals("Database Management Systems")){
                    startActivity(new Intent(daily_quiz.this,dbms_mod1.class));

                }
                else if(item.equals("Operating System")){
                    startActivity(new Intent(daily_quiz.this,os_quiz_mods.class));

                }

            }
        });




    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(daily_quiz.this,mainpage.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }
    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpulldailyquiz);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light,android.R.color.holo_red_light);
    }
}