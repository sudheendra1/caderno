package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Textbooks extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView pdfRecyclerView;
    private DatabaseReference pRef;


    private FirebaseUser firebaseUser;
    Query query;
    ProgressBar progressBar;

    private static final String TAG="delete_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textbooks);

        Swipetorefresh();

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();



        displayPdfs();

    }
    private void displayPdfs() {

        pRef = FirebaseDatabase.getInstance().getReference().child("Books");
        pdfRecyclerView = findViewById(R.id.recyclerview_book);
        pdfRecyclerView.setHasFixedSize(true);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        progressBar = findViewById(R.id.progressbar_book);
        progressBar.setVisibility(View.VISIBLE);



        query = pRef.orderByChild("filename");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    progressBar.setVisibility(View.GONE);
                    showPdf();
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Textbooks.this, "sorry for the inconvenience", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void showPdf() {
        FirebaseRecyclerOptions<FileinModel2> options = new FirebaseRecyclerOptions.Builder<FileinModel2>()
                .setQuery(query, FileinModel2.class)
                .build();
        FirebaseRecyclerAdapter<FileinModel2, Adapter3> adapter = new FirebaseRecyclerAdapter<FileinModel2, Adapter3>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  Adapter3 holder, int position, @NonNull FileinModel2 model) {

                progressBar.setVisibility(View.GONE);
                holder.pdftitle.setText(model.getFilename());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*Intent intent = new Intent(Intent.ACTION_VIEW);*/
                        Intent intent = new Intent(Textbooks.this, pdfviewer4.class);
                        intent.setType("application/pdf");
                        intent.putExtra("pdfurl4", model.getFileurl());
                        /*intent.setData(Uri.parse(model.getFileurl()));*/
                        startActivity(intent);
                    }
                });
            }
            @NonNull
            @Override
            public Adapter3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookitem,parent,false);
                Adapter3 holder = new Adapter3(view);
                return holder;
            }
        };

        pdfRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Textbooks.this, mainpage.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    private void Swipetorefresh() {
        swipeRefreshLayout = findViewById(R.id.refreshpullbook);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0, 0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }
}