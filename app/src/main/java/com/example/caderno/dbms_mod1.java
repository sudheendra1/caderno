package com.example.caderno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class dbms_mod1 extends AppCompatActivity implements View.OnClickListener{
    private SwipeRefreshLayout swipeRefreshLayout;

    TextView totalquestions,question;
    Button ansa,ansb,ansc,ansd,submit;

    int score=0;
    int tq=dbms_mod1_questionanswers.questions.length;
    int currentquestionindex=0;
    String selectedanswer="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbms_mod1);
        Swipetorefresh();
        totalquestions=findViewById(R.id.totalquestions3);
        question=findViewById(R.id.dbms_quiz1_q);
        ansa=findViewById(R.id.dbms_quiz1_q1_ansa);
        ansb=findViewById(R.id.dbms_quiz1_q1_ansb);
        ansc=findViewById(R.id.dbms_quiz1_q1_ansc);
        ansd=findViewById(R.id.dbms_quiz1_q1_ansd);
        submit=findViewById(R.id.dbms_quiz1_q1_submit);

        ansa.setOnClickListener(this);
        ansb.setOnClickListener(this);
        ansc.setOnClickListener(this);
        ansd.setOnClickListener(this);
        submit.setOnClickListener(this);

        totalquestions.setText("Total Questions:"+tq);
        loadnewquestion();


    }

    private void loadnewquestion() {

        if(currentquestionindex == tq){
            finishquiz();
            return;

        }
        question.setText(dbms_mod1_questionanswers.questions[currentquestionindex]);
        ansa.setText(dbms_mod1_questionanswers.choices[currentquestionindex][0]);
        ansb.setText(dbms_mod1_questionanswers.choices[currentquestionindex][1]);
        ansc.setText(dbms_mod1_questionanswers.choices[currentquestionindex][2]);
        ansd.setText(dbms_mod1_questionanswers.choices[currentquestionindex][3]);
    }

    private void finishquiz() {
        String passstatus="";
        if(score>tq*0.60){
            passstatus="passed";
        }
        else{
            passstatus="failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passstatus)
                .setMessage("score is"+ score+"out of"+tq)
                .setPositiveButton("restart",((dialogInterface, i) -> restartquiz()))
                .setNegativeButton("go back",((dialogInterface, i) -> goback()))
                .setCancelable(false)
                .show();
    }

    private void goback() {
        startActivity(new Intent(dbms_mod1.this,daily_quiz.class));
    }

    private void restartquiz() {
        score=0;
        currentquestionindex=0;
        loadnewquestion();
    }

    @Override
    public void onClick(View view) {
        ansa.setBackgroundColor(Color.BLACK);
        ansb.setBackgroundColor(Color.BLACK);
        ansc.setBackgroundColor(Color.BLACK);
        ansd.setBackgroundColor(Color.BLACK);
        submit.setBackgroundColor(Color.LTGRAY);

        Button clickedbutton = (Button) view;
        if(clickedbutton.getId()==R.id.dbms_quiz1_q1_submit){

            if(selectedanswer.equals(dbms_mod1_questionanswers.correctAnswers[currentquestionindex])){
                score++;
            }
            currentquestionindex++;
            loadnewquestion();
        }
        else{
            selectedanswer=clickedbutton.getText().toString();
            clickedbutton.setBackgroundColor(Color.GREEN);
        }

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(dbms_mod1.this,dbms_quiz_mods.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpulldbmsmod1);
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