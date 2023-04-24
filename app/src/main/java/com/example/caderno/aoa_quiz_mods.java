package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class aoa_quiz_mods extends AppCompatActivity implements View.OnClickListener{
    private SwipeRefreshLayout swipeRefreshLayout;

    TextView totalquestions,question;
    Button ansa,ansb,ansc,ansd,submit;
    ImageView scoresee;

    int score=0;
    int tq;

    int currentquestionindex=0;
    String selectedanswer="";
    int testno=0;
    private final List<Questionlist> questionlists1= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aoa_quiz_mods);
        Swipetorefresh();
        scoresee= findViewById(R.id.aoa_score);
        scoresee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(aoa_quiz_mods.this,Aoa_score.class));
            }
        });


        totalquestions=findViewById(R.id.totalquestions3_aoa);
        question=findViewById(R.id.aoa_q);
        ansa=findViewById(R.id.aoa_ansa);
        ansb=findViewById(R.id.aoa_ansb);
        ansc=findViewById(R.id.aoa_ansc);
        ansd=findViewById(R.id.aoa_ansd);
        submit=findViewById(R.id.aoa_submit);

        ansa.setOnClickListener(this);
        ansb.setOnClickListener(this);
        ansc.setOnClickListener(this);
        ansd.setOnClickListener(this);
        submit.setOnClickListener(this);
        ProgressDialog progressDialog = new ProgressDialog(aoa_quiz_mods.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child("AOA").getChildren()){
                    String questions=dataSnapshot.child("question").getValue(String.class);
                    String getansa = dataSnapshot.child("option1").getValue(String.class);
                    String getansb = dataSnapshot.child("option2").getValue(String.class);
                    String getansc = dataSnapshot.child("option3").getValue(String.class);
                    String getansd = dataSnapshot.child("option4").getValue(String.class);
                    String getans = dataSnapshot.child("answer").getValue(String.class);



                    Questionlist questionlist1 = new Questionlist(questions,getansa,getansb,getansc,getansd,getans);
                    questionlists1.add(questionlist1);

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
        tq=questionlists1.size();
        totalquestions.setText("Total Questions:"+tq);

        if(currentquestionindex == tq){
            getfrompreference();
            testno++;
            saveinpreference();
            finishquiz();
            return;

        }
        question.setText(questionlists1.get(currentquestionindex).getQuestion());
        ansa.setText(questionlists1.get(currentquestionindex).getOption1());
        ansb.setText(questionlists1.get(currentquestionindex).getOption2());
        ansc.setText(questionlists1.get(currentquestionindex).getOption3());
        ansd.setText(questionlists1.get(currentquestionindex).getOption4());
        /*question.setText(aoa_qa.questions[currentquestionindex]);
        ansa.setText(aoa_qa.choices[currentquestionindex][0]);
        ansb.setText(aoa_qa.choices[currentquestionindex][1]);
        ansc.setText(aoa_qa.choices[currentquestionindex][2]);
        ansd.setText(aoa_qa.choices[currentquestionindex][3]);*/
    }

    private void saveinpreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("testno",testno);
        editor.apply();
    }

    private void getfrompreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        testno= preferences.getInt("testno",0);
    }

    private void finishquiz() {
        String test = "test " + testno;
        String score1= Integer.toString(score);
        Score marks=new Score(test,score1);
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Test scores");
        databaseReference.child(id).child("aoa").child(test).setValue(marks);
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
        startActivity(new Intent(aoa_quiz_mods.this,daily_quiz.class));
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
        if(clickedbutton.getId()==R.id.aoa_submit){
            String getanswer = questionlists1.get(currentquestionindex).getAnswer();

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
        startActivity(new Intent(aoa_quiz_mods.this,daily_quiz.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpullaoaquizmods);
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