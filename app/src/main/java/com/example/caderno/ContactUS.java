package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUS extends AppCompatActivity {
    private EditText username,email,mesaage;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String userid,name,emailid,msg;
    private Button button;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        username=findViewById(R.id.username3_contact);
        email=findViewById(R.id.emailid_contact);
        mesaage=findViewById(R.id.tv_message);
        pb=findViewById(R.id.pb_contact);
        button=findViewById(R.id.send_msg);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        userid=firebaseUser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("messages");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendmsg();
            }
        });


    }

    private void sendmsg() {
        pb.setVisibility(View.VISIBLE);
        name=username.getText().toString().trim();
        emailid=email.getText().toString().trim();
        msg=mesaage.getText().toString().trim();

        if(name.isEmpty()){
            username.setError("Full name is required");
            pb.setVisibility(View.GONE);
            username.requestFocus();
            return;
        }
        if(emailid.isEmpty()){
            email.setError("email is required");
            pb.setVisibility(View.GONE);
            email.requestFocus();
            return;
        }
        if(msg.isEmpty()){
            mesaage.setError("message is required");
            pb.setVisibility(View.GONE);
            mesaage.requestFocus();
            return;
        }

        message messsage = new message(name,emailid,msg);
        databaseReference.child(userid).setValue(messsage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pb.setVisibility(View.GONE);
                Toast.makeText(ContactUS.this, "Message has been sent successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ContactUS.this,mainpage.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ContactUS.this, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onBackPressed() {
       startActivity(new Intent(ContactUS.this,mainpage.class));



    }
}