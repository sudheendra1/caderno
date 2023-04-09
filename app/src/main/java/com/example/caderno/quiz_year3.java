package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class quiz_year3 extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_year3);

        listView= (ListView) findViewById(R.id.main_quiz_y3);

        String[] subjects= {"Theoretical Computer Science","Software Engineering", "AComputer Network","Data Warehousing and Mining","System Programming and Compiler Construction","Cryptography and System Security","Mobile Computing","Artificial Intelligence"};
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
    }
}