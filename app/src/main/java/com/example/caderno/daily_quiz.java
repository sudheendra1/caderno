package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
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
                    AlertDialog.Builder builder= new AlertDialog.Builder(daily_quiz.this);

                    builder.setMessage("Do you wish to view your previous scores or continue to the quiz");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,em4_quiz_mods.class));
                        }
                    });

                    builder.setNegativeButton("Score", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,em4score.class));
                        }
                    });
                    AlertDialog alertDialog= builder.create();
                    alertDialog.show();


                }
                else if(item.equals("Microprocessor")){
                    AlertDialog.Builder builder1= new AlertDialog.Builder(daily_quiz.this);

                    builder1.setMessage("Do you wish to view your previous scores or continue to the quiz");
                    builder1.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,mp_quiz_mods.class));
                        }
                    });

                    builder1.setNegativeButton("Score", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,mpscore.class));
                        }
                    });
                    AlertDialog alertDialog1= builder1.create();
                    alertDialog1.show();


                }

                else if(item.equals("Analysis of Algorithms")){
                    AlertDialog.Builder builder2= new AlertDialog.Builder(daily_quiz.this);

                    builder2.setMessage("Do you wish to view your previous scores or continue to the quiz");
                    builder2.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,aoa_quiz_mods.class));
                        }
                    });

                    builder2.setNegativeButton("Score", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,Aoa_score.class));
                        }
                    });
                    AlertDialog alertDialog2= builder2.create();
                    alertDialog2.show();



                }
                else if(item.equals("Database Management Systems")){
                    AlertDialog.Builder builder3= new AlertDialog.Builder(daily_quiz.this);

                    builder3.setMessage("Do you wish to view your previous scores or continue to the quiz");
                    builder3.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,dbms_mod1.class));
                        }
                    });

                    builder3.setNegativeButton("Score", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,dbmsscore.class));
                        }
                    });
                    AlertDialog alertDialog3= builder3.create();
                    alertDialog3.show();


                }
                else if(item.equals("Operating System")){
                    AlertDialog.Builder builder4= new AlertDialog.Builder(daily_quiz.this);

                    builder4.setMessage("Do you wish to view your previous scores or continue to the quiz");
                    builder4.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,os_quiz_mods.class));
                        }
                    });

                    builder4.setNegativeButton("Score", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,osscore.class));
                        }
                    });
                    AlertDialog alertDialog4= builder4.create();
                    alertDialog4.show();


                }
                else if(item.equals("Python")){
                    AlertDialog.Builder builder5= new AlertDialog.Builder(daily_quiz.this);

                    builder5.setMessage("Do you wish to view your previous scores or continue to the quiz");
                    builder5.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(daily_quiz.this,Python.class));
                        }
                    });

                    builder5.setNegativeButton("Score", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            startActivity(new Intent(daily_quiz.this,pythonscore.class));
                        }
                    });
                    AlertDialog alertDialog5= builder5.create();
                    alertDialog5.show();


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