package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ret_own_pdf extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView pdfRecyclerView;
    private DatabaseReference pRef;
    private FirebaseAuth mauth;
    private String userid,userid1,name;
    private FirebaseUser firebaseUser;
    Query query;
    ProgressBar progressBar;

    private static final String TAG="delete_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ret_own_pdf);
        Swipetorefresh();
        mauth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        userid=firebaseUser.getUid()+"own";


        displayPdfs();

    }

    private void displayPdfs() {

        pRef = FirebaseDatabase.getInstance().getReference().child(userid);
        pdfRecyclerView = findViewById(R.id.recyclerview_ret_ownpdf);
        pdfRecyclerView.setHasFixedSize(true);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        progressBar = findViewById(R.id.progressbar_ret_own);
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
                    Toast.makeText(ret_own_pdf.this, "sorry for the inconvenience", Toast.LENGTH_SHORT).show();
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
        FirebaseRecyclerAdapter<FileinModel2, Adapter2> adapter = new FirebaseRecyclerAdapter<FileinModel2, Adapter2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  Adapter2 holder, int position, @NonNull FileinModel2 model) {

                progressBar.setVisibility(View.GONE);
                holder.pdftitle.setText(model.getFilename());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*Intent intent = new Intent(Intent.ACTION_VIEW);*/
                        Intent intent = new Intent(ret_own_pdf.this,pdfviewer1.class);
                        intent.setType("application/pdf");
                        intent.putExtra("pdfurl1",model.getFileurl());
                        /*intent.setData(Uri.parse(model.getFileurl()));*/
                        startActivity(intent);
                    }
                });
                /*holder.delete.setEnabled(false);*/
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ret_own_pdf.this)
                                .setTitle("Delete your PDF")
                                .setMessage("Do You Really Want To Delete Your PDF? It is Irreversible")
                                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                        Query query1 = pRef.orderByChild("filename").equalTo(model.getFilename());
                                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                                                    appleSnapshot.getRef().removeValue();
                                                    Toast.makeText(ret_own_pdf.this, "PDF Deleted successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e(TAG, "onCancelled", error.toException());

                                            }
                                        });


                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(ret_own_pdf.this, ret_own_pdf.class));
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                            }
                        });
                        alertDialog.show();
                    }
                });






                }



            @NonNull
            @Override
            public Adapter2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.odfitem2,parent,false);
                Adapter2 holder = new Adapter2(view);
                return holder;
            }
        };

        pdfRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }







    @Override
    public void onBackPressed() {
        startActivity(new Intent(ret_own_pdf.this, mainpage.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    private void Swipetorefresh() {
        swipeRefreshLayout = findViewById(R.id.refreshpullret_own);
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