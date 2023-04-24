package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class papers extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView pdfRecyclerView;
    private DatabaseReference pRef;
    Query query;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papers);
        Swipetorefresh();
        displayPdfs();
    }

    private void displayPdfs() {

        pRef = FirebaseDatabase.getInstance().getReference().child("questionpapersy2");
        pdfRecyclerView = findViewById(R.id.recyclerviewpdf_qp);
        pdfRecyclerView.setHasFixedSize(true);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        progressBar = findViewById(R.id.progressbar_qp);
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
                    Toast.makeText(papers.this, "sorry for the inconvenience", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void showPdf() {
        FirebaseRecyclerOptions<FileinModel1> options = new FirebaseRecyclerOptions.Builder<FileinModel1>()
                .setQuery(query, FileinModel1.class)
                .build();
        FirebaseRecyclerAdapter<FileinModel1, Adapter1> adapter = new FirebaseRecyclerAdapter<FileinModel1, Adapter1>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  Adapter1 holder, int position, @NonNull FileinModel1 model) {

                progressBar.setVisibility(View.GONE);
                holder.pdftitle1.setText(model.getFilename());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*Intent intent = new Intent(Intent.ACTION_VIEW);*/
                        Intent intent = new Intent(papers.this,pdfviewer2.class);
                        intent.setType("application/pdf");
                        intent.putExtra("pdfurl2",model.getFileurl());
                        /*intent.setData(Uri.parse(model.getFileurl()));*/
                        startActivity(intent);
                    }


                });


                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(model.getFileurl2()!=null){
                            Intent intent = new Intent(papers.this,pdfviewer3.class);
                            intent.setType("application/pdf");
                            intent.putExtra("pdfurl3",model.getFileurl2());
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(papers.this, "Solutions not available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @NonNull
            @Override
            public Adapter1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_item1,parent,false);
                Adapter1 holder = new Adapter1(view);
                return holder;
            }
        };

        pdfRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(papers.this,mainpage.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpullpapers);
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