package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class profile extends AppCompatActivity {

    private TextView tvwelcome,tvname,tvemail,tvdob,tvyear,tvbranch;
    private ProgressBar progressBar;
    private String name=null,em=null,year1=null,branch1=null,date=null;
    private String userid;
    private ImageView profilepic;
    private FirebaseAuth mauth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        Swipetorefresh();


        tvwelcome=findViewById(R.id.tvwelcome);
        tvname=findViewById(R.id.profile_username);
        tvemail=findViewById(R.id.profile_email);
        tvdob=findViewById(R.id.profile_dob);
        tvyear=findViewById(R.id.profile_year);
        tvbranch=findViewById(R.id.profile_branch);
        profilepic=findViewById(R.id.imageView);
        mauth= FirebaseAuth.getInstance();

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        userid= firebaseUser.getUid();

        progressBar=findViewById(R.id.profilepb);


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this,upload_pp.class));
                getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();

            }
        });



        if(firebaseUser==null){
            Toast.makeText(profile.this, "Something Went Wrong !! Sorry for The Inconvenience", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            showuserprofile(firebaseUser);

        }




       /*pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
golink("https://firebasestorage.googleapis.com/v0/b/caderno-419a2.appspot.com/o/textbookm%2FSem%203%20Kumbhojkar.pdf?alt=media&token=fb98924f-c454-48fa-af88-353b87d75de1");
            }

            private void golink(String s) {
                Uri uri=Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu_up_profile_p){
            startActivity(new Intent(profile.this, update_profile.class));
        }
        else if(id==R.id.menu_up_em_p){
            startActivity(new Intent(profile.this, update_email.class));
        }
        else if(id==R.id.menu_change_pass_p){
            startActivity(new Intent(profile.this,change_password.class));

        }
        else if(id==R.id.menu_delete_user_p){
            startActivity(new Intent(profile.this, delete_user.class));
        }
        else if(id==R.id.menu_logout_p){
            SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
            editor.putBoolean("islogin",false);
            editor.apply();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(profile.this,MainActivity.class));
            Toast.makeText(profile.this, "logged out succesfully", Toast.LENGTH_SHORT).show();
            getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
        }

        else{
            Toast.makeText(profile.this, "something went wrong!!", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);


    }

    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpullprofile);
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

    private void showuserprofile(FirebaseUser firebaseUser) {






        databaseReference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user == null) {
                    Toast.makeText(profile.this, "Something Went Wrong2 !! Sorry for The Inconvenience", Toast.LENGTH_LONG).show();
                } else {
                    name=user.fullname;
                    em=firebaseUser.getEmail();
                    date=user.dob;
                    year1=user.year;
                    branch1=user.branch;




                    settext();








                }
                progressBar.setVisibility(View.GONE);
            }

            public void settext() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvwelcome.setText(getString(R.string.welcomemsg,name));
                        tvname.setText(name);
                        tvemail.setText(em);
                        tvdob.setText(date);
                        tvyear.setText(getString(R.string.year,year1));
                        tvbranch.setText(branch1);

                        Uri uri =firebaseUser.getPhotoUrl();
                        if(uri!=null){
                            Picasso.with(profile.this).load(uri).into(profilepic);

                        }else{
                            profilepic.setImageResource(R.drawable.ic_baseline_person_outline_24);

                        }



                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                Toast.makeText(profile.this, "Something Went Wrong1 !! Sorry for The Inconvenience", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        });




    }









    @Override
    public void onBackPressed() {
        startActivity(new Intent(profile.this,mainpage.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }
}