package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class pythonscore extends AppCompatActivity {
    RecyclerView pdfRecyclerView;
    private DatabaseReference pRef;
    Query query;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pythonscore);
        Displayscore();
    }
    private void Displayscore() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        pRef = FirebaseDatabase.getInstance().getReference().child("Test scores").child(id).child("python");
        pdfRecyclerView = findViewById(R.id.recyclerviewpy_score);
        pdfRecyclerView.setHasFixedSize(true);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        progressBar = findViewById(R.id.progressbar_pyscore);
        progressBar.setVisibility(View.VISIBLE);
        query = pRef.orderByChild("test");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    progressBar.setVisibility(View.GONE);
                    showscore();
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(pythonscore.this, "Not attempted any quiz yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void showscore() {
        FirebaseRecyclerOptions<Fileinmodel3> options = new FirebaseRecyclerOptions.Builder<Fileinmodel3>()
                .setQuery(query, Fileinmodel3.class)
                .build();
        FirebaseRecyclerAdapter<Fileinmodel3, Adapterscore> adapter = new FirebaseRecyclerAdapter<Fileinmodel3, Adapterscore>(options){
            @Override
            protected void onBindViewHolder(@NonNull  Adapterscore holder, int position, @NonNull Fileinmodel3 model) {

                progressBar.setVisibility(View.GONE);
                holder.testno.setText(model.getTest());
                holder.score.setText(model.getScore());



            }

            @NonNull
            @Override
            public Adapterscore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score,parent,false);
                Adapterscore holder = new Adapterscore(view);
                return holder;
            }
        };
        pdfRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}