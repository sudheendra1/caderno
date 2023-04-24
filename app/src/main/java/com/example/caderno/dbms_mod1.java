package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dbms_mod1 extends AppCompatActivity implements View.OnClickListener{
    private SwipeRefreshLayout swipeRefreshLayout;

    TextView totalquestions,question;
    Button ansa,ansb,ansc,ansd,submit;

    int score=0;
    int tq;
    int currentquestionindex=0;
    String selectedanswer="";
    int testno=0;
    private final List<Questionlist> questionlists2= new ArrayList<>();

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
        ProgressDialog progressDialog = new ProgressDialog(dbms_mod1.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child("DBMS").getChildren()){
                    String questions=dataSnapshot.child("question").getValue(String.class);
                    String getansa = dataSnapshot.child("option1").getValue(String.class);
                    String getansb = dataSnapshot.child("option2").getValue(String.class);
                    String getansc = dataSnapshot.child("option3").getValue(String.class);
                    String getansd = dataSnapshot.child("option4").getValue(String.class);
                    String getans = dataSnapshot.child("answer").getValue(String.class);



                    Questionlist questionlist2 = new Questionlist(questions,getansa,getansb,getansc,getansd,getans);
                    questionlists2.add(questionlist2);

                }
                loadnewquestion();
                progressDialog.hide();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void loadnewquestion() {
        tq=questionlists2.size();
        totalquestions.setText("Total Questions:"+tq);


        if(currentquestionindex == tq){
            getfrompreference();
            testno++;
            saveinpreference();
            finishquiz();
            return;

        }
        question.setText(questionlists2.get(currentquestionindex).getQuestion());
        ansa.setText(questionlists2.get(currentquestionindex).getOption1());
        ansb.setText(questionlists2.get(currentquestionindex).getOption2());
        ansc.setText(questionlists2.get(currentquestionindex).getOption3());
        ansd.setText(questionlists2.get(currentquestionindex).getOption4());
    }

    private void saveinpreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("testnodb",testno);
        editor.apply();
    }

    private void getfrompreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        testno= preferences.getInt("testnodb",0);
    }

    private void finishquiz() {
        String test = "test " + testno;
        String score1= Integer.toString(score);
        Score marks1=new Score(test,score1);
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Test scores");
        databaseReference.child(id).child("dbms").child(test).setValue(marks1);
        String passstatus="";
        if(score>tq*0.60){
            passstatus="passed";
        }
        else{
            passstatus="failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passstatus)
                .setMessage("score is "+ score+" out of "+tq)
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
        ansa.setBackgroundDrawable(getDrawable(R.drawable.button_qa));
        ansb.setBackgroundDrawable(getDrawable(R.drawable.button_qa));
        ansc.setBackgroundDrawable(getDrawable(R.drawable.button_qa));
        ansd.setBackgroundDrawable(getDrawable(R.drawable.button_qa));

        Button clickedbutton = (Button) view;
        if(clickedbutton.getId()==R.id.dbms_quiz1_q1_submit){
            String getanswer = questionlists2.get(currentquestionindex).getAnswer();

            if(selectedanswer.equals(getanswer)){
                score++;
            }
            currentquestionindex++;
            loadnewquestion();
        }
        else{
            selectedanswer=clickedbutton.getText().toString();
            clickedbutton.setBackgroundDrawable(getDrawable(R.drawable.selected_ans));
        }

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(dbms_mod1.this,daily_quiz.class));
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