package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.startup.AppInitializer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import app.rive.runtime.kotlin.RiveInitializer;

public class update_profile extends AppCompatActivity {
    private EditText username_up, dob_up;
    private Button update_profile, Update_ppu, update_email;
    private RadioGroup radiogroupyear, radiogroupbranch;
    private RadioButton radioselectedyear, radioselectedbranch;
    private DatePickerDialog picker;
    String name, year, branch, date,email;
    private FirebaseAuth mAuth;
    ProgressBar pb;
    DatabaseReference databaseReference;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        pb = findViewById(R.id.up_profilepb);

        username_up = (EditText) findViewById(R.id.username3_up);
        dob_up = (EditText) findViewById(R.id.date_of_birth_up);
        update_profile = (Button) findViewById(R.id.signup1_up);
        update_email = (Button) findViewById(R.id.update_em_pru);
        Update_ppu = (Button) findViewById(R.id.update_pp_pr);
        radiogroupyear = findViewById(R.id.radio_grp_year_up);
        radiogroupyear.clearCheck();

        radiogroupbranch = findViewById(R.id.radio_grp_branch_up);
        radiogroupbranch.clearCheck();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        userid = firebaseUser.getUid();
        showuserprofile(firebaseUser);
        Update_ppu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.example.caderno.update_profile.this,upload_pp.class));
            }
        });

        update_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.example.caderno.update_profile.this,update_email.class));
            }
        });
        dob_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year1=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(update_profile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dob_up.setText(i2 +"/" +(i1+1)+ "/"+ i);

                    }
                }, year1,month,day);
                picker.show();
            }
        });
        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateprofile(firebaseUser);
            }
        });

    }

    private void updateprofile(FirebaseUser firebaseUser) {
        int selectedyear=radiogroupyear.getCheckedRadioButtonId();
        radioselectedyear=findViewById(selectedyear);

        int sectedbranchid= radiogroupbranch.getCheckedRadioButtonId();
        radioselectedbranch=findViewById(sectedbranchid);


        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please provide a username", Toast.LENGTH_SHORT).show();
            username_up.setError("Full name is required");
            username_up.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(date)){
            Toast.makeText(this, "Date of birth cannot be empty", Toast.LENGTH_SHORT).show();
            dob_up.setError("date of birth is required");
            dob_up.requestFocus();
            return;
        }
        else if(radiogroupyear.getCheckedRadioButtonId()==-1){
            Toast.makeText(update_profile.this,"please select your year",Toast.LENGTH_SHORT).show();
            radioselectedyear.setError("year is required");
            radioselectedyear.requestFocus();
            return;
        }
        else if(radiogroupbranch.getCheckedRadioButtonId()==-1){
            Toast.makeText(update_profile.this,"please select your branch",Toast.LENGTH_SHORT).show();
            radioselectedbranch.setError("branch is required");
            radioselectedbranch.requestFocus();
            return;
        }
        else {
            year=radioselectedyear.getText().toString();
            branch=radioselectedbranch.getText().toString();
            name= username_up.getText().toString().trim();
            date=dob_up.getText().toString().trim();

            User user= new User(name,email,year,branch,date);

            databaseReference = FirebaseDatabase.getInstance().getReference("user");
            pb.setVisibility(View.VISIBLE);
            databaseReference.child(userid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(com.example.caderno.update_profile.this,"Update Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(com.example.caderno.update_profile.this,profile.class));
                    } else{
                        try {
                            throw task.getException();

                        } catch (Exception e){
                            Toast.makeText(com.example.caderno.update_profile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    pb.setVisibility(View.GONE);
                }
            });
        }

    }

    private void showuserprofile(FirebaseUser firebaseUser) {
        pb.setVisibility(View.VISIBLE);
        databaseReference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user == null) {
                    Toast.makeText(update_profile.this, "Something Went Wrong !! Sorry for The Inconvenience", Toast.LENGTH_LONG).show();
                } else {
                    name = user.fullname;
                    email=firebaseUser.getEmail();


                    date = user.dob;
                    year = user.year;
                    branch = user.branch;
                    settext();

                }
            }

            public void settext() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        username_up.setText(name);

                        dob_up.setText(date);
                        {
                            if (year.equals("First")) {
                                radioselectedyear = findViewById(R.id.radio_year1_up);

                            } else if (year.equals("Second")) {
                                radioselectedyear = findViewById(R.id.radio_year2_up);
                            } else if (year.equals("Fourth")) {
                                radioselectedyear = findViewById(R.id.radio_year3_up);
                            }
                            else {
                                radioselectedyear = findViewById(R.id.radio_year4_up);
                            }
                            radioselectedyear.setChecked(true);
                        }
                        {
                            if (branch.equals("Comps")) {
                                radioselectedbranch = findViewById(R.id.radio_comps_up);

                            } else if (year.equals("AIDS")) {
                                radioselectedbranch = findViewById(R.id.radio_aids_up);
                            } else if (year.equals("ECS")) {
                                radioselectedbranch = findViewById(R.id.radio_ecs_up);

                            } else {
                                radioselectedbranch = findViewById(R.id.radio_mech_civil_up);
                            }
                            radioselectedbranch.setChecked(true);
                        }
                        pb.setVisibility(View.GONE);


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                Toast.makeText(update_profile.this, "Something Went Wrong !! Sorry for The Inconvenience", Toast.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);

            }
        });
    }


}