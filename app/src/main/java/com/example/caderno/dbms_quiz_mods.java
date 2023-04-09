package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class dbms_quiz_mods extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbms_quiz_mods);
        Swipetorefresh();

        listView= (ListView) findViewById(R.id.dbms_quiz_mod);

        String[] subjects= {"mod1","mod2"};
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

                if (item.equals("mod1")){
                    startActivity(new Intent(dbms_quiz_mods.this,dbms_mod1.class));

                }
                else if(item.equals("mod2")){
                    startActivity(new Intent(dbms_quiz_mods.this,dbms_mod2.class));

                }



            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(dbms_quiz_mods.this,daily_quiz.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpulldbmsquizmods);
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