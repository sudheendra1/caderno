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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
public class view_pdf extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView pdfRecyclerView;
    private DatabaseReference pRef;
    Query query;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        Swipetorefresh();


        displayPdfs();
    }

    private void displayPdfs() {

        pRef = FirebaseDatabase.getInstance().getReference().child("pdfs");
        pdfRecyclerView = findViewById(R.id.recyclerviewpdf);
        pdfRecyclerView.setHasFixedSize(true);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        query = pRef.orderByChild("filename");
        //we will request the files with the filename, if filename exists then it will show in the recyclerView
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    progressBar.setVisibility(View.GONE);
                    showPdf();
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(view_pdf.this, "sorry for the inconvenience", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void showPdf() {
        FirebaseRecyclerOptions<FileinModel> options = new FirebaseRecyclerOptions.Builder<FileinModel>()
                .setQuery(query, FileinModel.class)
                .build();
        FirebaseRecyclerAdapter<FileinModel, Adapter> adapter = new FirebaseRecyclerAdapter<FileinModel, Adapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  Adapter holder, int position, @NonNull FileinModel model) {

                progressBar.setVisibility(View.GONE);
                holder.pdftitle.setText(model.getFilename());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*Intent intent = new Intent(Intent.ACTION_VIEW);*/
                        Intent intent = new Intent(view_pdf.this,pdfviewer.class);
                        intent.setType("application/pdf");
                        intent.putExtra("pdfurl",model.getFileurl());
                        /*intent.setData(Uri.parse(model.getFileurl()));*/
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public Adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_item,parent,false);
                Adapter holder = new Adapter(view);
                return holder;
            }
        };

        pdfRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(view_pdf.this,mainpage.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpullviewpdf);
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
