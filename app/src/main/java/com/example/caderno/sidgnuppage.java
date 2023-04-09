package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class sidgnuppage extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private EditText username1,email,pass,conpass,dob;
    private Button signup;
    private TextView log;
    private RadioGroup radiogroupyear,radiogroupbranch;
    private RadioButton radioselectedyear,radioselectedbranch;
    private DatePickerDialog picker;
    DatabaseReference databaseReference;
    String name,passw,em,year,branch,date,cp;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidgnuppage);
        pb=findViewById(R.id.signpb);
        mAuth = FirebaseAuth.getInstance();
        username1 = (EditText) findViewById(R.id.username3);
         email = (EditText) findViewById(R.id.emailid);
         pass = (EditText) findViewById(R.id.password2);
         conpass = (EditText)findViewById(R.id.con_password);
         dob=(EditText) findViewById(R.id.date_of_birth);
         signup = (Button) findViewById(R.id.signup1);
         log = (TextView) findViewById(R.id.login);
         radiogroupyear=findViewById(R.id.radio_grp_year);
         radiogroupyear.clearCheck();

         radiogroupbranch=findViewById(R.id.radio_grp_branch);
         radiogroupbranch.clearCheck();

         dob.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 final Calendar calendar=Calendar.getInstance();
                 int day=calendar.get(Calendar.DAY_OF_MONTH);
                 int month=calendar.get(Calendar.MONTH);
                 int year1=calendar.get(Calendar.YEAR);

                 picker=new DatePickerDialog(sidgnuppage.this, new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                         dob.setText(i +"/" +(i1+1)+ "/"+ i2);

                     }
                 }, year1,month,day);
                 picker.show();
             }
         });


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sidgnuppage.this, loginpage.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.signup1:
                        registeruser();
                        startActivity(new Intent(sidgnuppage.this,loginpage.class));
                        break;

                }
            }

            private void registeruser() {
                int selectedyearid = radiogroupyear.getCheckedRadioButtonId();
                radioselectedyear=findViewById(selectedyearid);

                int sectedbranchid= radiogroupbranch.getCheckedRadioButtonId();
                radioselectedbranch=findViewById(sectedbranchid);
                 name= username1.getText().toString().trim();
                 passw= pass.getText().toString().trim();
                 em= email.getText().toString().trim();
                 date=dob.getText().toString().trim();
                 cp=conpass.getText().toString().trim();
                if(name.isEmpty()){
                    username1.setError("Full name is required");
                    username1.requestFocus();
                    return;
                }
                if(passw.isEmpty()){
                    pass.setError("password is required");
                    pass.requestFocus();
                    return;
                }
                if(em.isEmpty()){
                    email.setError("email is required");
                    email.requestFocus();
                    return;
                }
                if(date.isEmpty()){
                    dob.setError("date of birth is required");
                    dob.requestFocus();
                    return;
                }
                if(radiogroupyear.getCheckedRadioButtonId()==-1){
                    Toast.makeText(sidgnuppage.this,"please select your year",Toast.LENGTH_SHORT).show();
                    radioselectedyear.setError("year is required");
                    radioselectedyear.requestFocus();
                    return;
                }
                if(radiogroupbranch.getCheckedRadioButtonId()==-1){
                    Toast.makeText(sidgnuppage.this,"please select your branch",Toast.LENGTH_SHORT).show();
                    radioselectedbranch.setError("branch is required");
                    radioselectedbranch.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){
                    email.setError("please provide valid email!");
                    email.requestFocus();
                    return;
                }
                if(passw.length()<8){
                    pass.setError("password should minimum 8 characters long");
                    pass.requestFocus();
                    return;
                }
                if(cp.isEmpty()){
                    conpass.setError("please confirm your password");
                    conpass.requestFocus();
                    return;
                }
                if(!cp.equals(passw)){
                    Toast.makeText(sidgnuppage.this,"please type in the same password",Toast.LENGTH_SHORT).show();
                    conpass.setError("please confirm your password");
                    conpass.requestFocus();
                    conpass.clearComposingText();
                    pass.clearComposingText();
                }

                year=radioselectedyear.getText().toString();
                branch=radioselectedbranch.getText().toString();
                pb.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(em,passw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    User user= new User(name,em,year,branch,date);
                                    databaseReference= FirebaseDatabase.getInstance().getReference("user");
                                    String id =FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    databaseReference.child(id).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(sidgnuppage.this,"registration successfull",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(sidgnuppage.this,"registration failed Try agin!!", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });


                                    /*FirebaseDatabase.getInstance().getReference("users").push().getKey().child((FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(sidgnuppage.this,"registration successfull",Toast.LENGTH_SHORT).show();

                                            }
                                            else{
                                                Toast.makeText(sidgnuppage.this,"registration failed Try agin!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });*/




                                } else{
                                    Toast.makeText(sidgnuppage.this, "registration failed!! try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(sidgnuppage.this,MainActivity.class));
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }
}