package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class mainpage extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseAuth mauth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String userid,year1,branch1;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;



    TextView retrieve, DQ,own,ret_own;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        userid= firebaseUser.getUid();
        Swipetorefresh();

        databaseReference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);

                if (user == null) {
                    Toast.makeText(mainpage.this, "Something Went Wrong2 !! Sorry for The Inconvenience", Toast.LENGTH_LONG).show();
                } else {

                    year1 = user.year;
                    branch1 = user.branch;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.nav_bar);
        toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigationopen,R.string.navigationclose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.quiz_nav){
                    if(Objects.equals(year1, "First")){
                        startActivity(new Intent(mainpage.this,quiz_year1.class));
                        Toast.makeText(mainpage.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                    }
                    else if(Objects.equals(year1, "Second")){
                        startActivity(new Intent(mainpage.this, daily_quiz.class));
                    }
                    else if(Objects.equals(year1, "Third")){
                        startActivity(new Intent(mainpage.this, quiz_year3.class));
                        Toast.makeText(mainpage.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                    }
                    else if(Objects.equals(year1, "Fourth")){
                        startActivity(new Intent(mainpage.this, MainActivity2.class));
                        Toast.makeText(mainpage.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(mainpage.this, "Something Went Wrong !! Sorry for The Inconvenience", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(id==R.id.papers_nav){
                    if(Objects.equals(year1, "First")){
                        startActivity(new Intent(mainpage.this,papers_year1.class));
                    }
                    else if(Objects.equals(year1, "Second")){
                        startActivity(new Intent(mainpage.this, papers.class));
                    }
                    else if(Objects.equals(year1, "Third")){
                        startActivity(new Intent(mainpage.this, papers_year3.class));
                    }
                    else if(Objects.equals(year1, "Fourth")){
                        startActivity(new Intent(mainpage.this, papers_year4.class));
                    }
                    else{
                        Toast.makeText(mainpage.this, "Something Went Wrong !! Sorry for The Inconvenience", Toast.LENGTH_SHORT).show();
                    }

                }
                else if(id==R.id.upload_own_notes_nav){
                    startActivity(new Intent(mainpage.this,upload_own_notes.class));

                }
                else if(id==R.id.View_own_notes_nav){

                    startActivity(new Intent(mainpage.this,ret_own_pdf.class));

                }
                else if(id==R.id.upload_all_notes_nav){
                    if(Objects.equals(year1, "First")){
                        startActivity(new Intent(mainpage.this,up_pdf_y1.class));
                    }
                    else if(Objects.equals(year1, "Second")){
                        startActivity(new Intent(mainpage.this, upload_pdf.class));
                    }
                    else if(Objects.equals(year1, "Third")){
                        startActivity(new Intent(mainpage.this, up_pdf_y3.class));
                    }
                    else if(Objects.equals(year1, "Fourth")){
                        startActivity(new Intent(mainpage.this, up_pdf_y4.class));
                    }
                    else{
                        Toast.makeText(mainpage.this, "Something Went Wrong !! Sorry for The Inconvenience", Toast.LENGTH_SHORT).show();
                    }


                }
                else if(id==R.id.View_all_notes_nav){

                    if(Objects.equals(year1, "First")){
                        startActivity(new Intent(mainpage.this,view_pdf_y1.class));
                    }
                    else if(Objects.equals(year1, "Second")){
                        startActivity(new Intent(mainpage.this, view_pdf.class));
                    }
                    else if(Objects.equals(year1, "Third")){
                        startActivity(new Intent(mainpage.this, view_pdf_y3.class));
                    }
                    else if(Objects.equals(year1, "Fourth")){
                        startActivity(new Intent(mainpage.this, view_pdf_y4.class));
                    }
                    else{
                        Toast.makeText(mainpage.this, "Something Went Wrong !! Sorry for The Inconvenience", Toast.LENGTH_SHORT).show();
                    }


                }
                else if(id==R.id.View_books){
                    if(Objects.equals(year1, "First")){
                        Toast.makeText(mainpage.this, "Coming soon", Toast.LENGTH_SHORT).show();
                    }
                    else if(Objects.equals(year1, "Second")){
                        startActivity(new Intent(mainpage.this,Textbooks.class));
                    }
                    else if(Objects.equals(year1, "Third")){
                        Toast.makeText(mainpage.this, "Coming soon", Toast.LENGTH_SHORT).show();
                    }
                    else if(Objects.equals(year1, "Fourth")){
                        Toast.makeText(mainpage.this, "Coming soon", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(mainpage.this, "Something Went Wrong !! Sorry for The Inconvenience", Toast.LENGTH_SHORT).show();
                    }

                }
                else if(id==R.id.myprofilenav){
                    startActivity(new Intent(mainpage.this,profile.class));

                }

                else if(id==R.id.logout_nav){
                    SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putBoolean("islogin",false);
                    editor.apply();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(mainpage.this,MainActivity.class));
                    Toast.makeText(mainpage.this, "logged out succesfully", Toast.LENGTH_SHORT).show();
                    getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();

                }
                else if(id==R.id.Contact){
                    startActivity(new Intent(mainpage.this,ContactUS.class));
                    getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                }
                else{
                    Toast.makeText(mainpage.this, "something went wrong!!", Toast.LENGTH_SHORT).show();

                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }


        });




















    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         int id = item.getItemId();
        if(id==R.id.menu_refresh){
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }
        else if(id==R.id.menu_myprofile){
            startActivity(new Intent(mainpage.this, profile.class));
        }
        else if(id==R.id.menu_dailyquiz){
            if(Objects.equals(year1, "Year1")){
                startActivity(new Intent(mainpage.this,quiz_year1.class));
            }
            else if(Objects.equals(year1, "Year2")){
                startActivity(new Intent(mainpage.this, daily_quiz.class));
            }
            else if(Objects.equals(year1, "Year3")){
                startActivity(new Intent(mainpage.this, quiz_year3.class));
            }
            else if(Objects.equals(year1, "Year4")){
                startActivity(new Intent(mainpage.this, MainActivity2.class));
            }
            else{
                Toast.makeText(mainpage.this, "Something Went Wrong !! Sorry for The Inconvenience", Toast.LENGTH_SHORT).show();
            }
        }
        else if(id==R.id.menu_papers){
            startActivity(new Intent(mainpage.this, papers.class));
        }
        else if(id==R.id.menu_uploadpdf){
            startActivity(new Intent(mainpage.this, upload_pdf.class));
        }
        else if(id==R.id.menu_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(mainpage.this,MainActivity.class));
            Toast.makeText(mainpage.this, "logged out succesfully", Toast.LENGTH_SHORT).show();
            getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
        }
        else{
            Toast.makeText(mainpage.this, "something went wrong!!", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);


    }*/
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            moveTaskToBack(true);
        }



    }

    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpullmainpage);
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