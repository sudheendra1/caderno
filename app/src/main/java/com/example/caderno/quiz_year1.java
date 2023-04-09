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

public class quiz_year1 extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_year1);

        listView= (ListView) findViewById(R.id.main_quiz_y1);

        String[] subjects= {"Engineering Mathematics I","Engineering Physics I", "Engineering Chemistry I","Engineering Mechanics","Basic Electrical Engineering","Engineering Mathematics II","Engineering Physics II","Engineering Chemistry II","C Programming","professional Communication and Ethics I"};
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