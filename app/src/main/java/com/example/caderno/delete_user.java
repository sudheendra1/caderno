package com.example.caderno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class delete_user extends AppCompatActivity {
    private FirebaseAuth mauth;
    private FirebaseUser firebaseUser;
    private ProgressBar pb;
    private TextView authentication;
    private EditText password;
    private Button button,button1;
    private String pwd,uid;
    private FirebaseStorage firebaseStorage;
    private static final String TAG="delete_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        pb=findViewById(R.id.pb_delu);
        password=findViewById(R.id.password2_delu);
        button=findViewById(R.id.signup1_delu);
        button1=findViewById(R.id.signup1_delete);
        authentication=findViewById(R.id.tv_delete);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        button1.setEnabled(false);
        mauth=FirebaseAuth.getInstance();
        firebaseUser=mauth.getCurrentUser();
        uid=firebaseUser.getUid();
        if(firebaseUser.equals("")){
            Toast.makeText(this, "Something Went Wrong!!!!", Toast.LENGTH_SHORT).show();
        }
        else{
            reAutheticate(firebaseUser);
        }

    }

    private void reAutheticate(FirebaseUser firebaseUser) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd=password.getText().toString();

                if(pwd.isEmpty()) {
                    password.setError("password is required");
                    password.requestFocus();
                    return;
                }
                else{
                    pb.setVisibility(View.VISIBLE);
                    AuthCredential authCredential= EmailAuthProvider.getCredential(firebaseUser.getEmail(),pwd);
                    firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pb.setVisibility(View.GONE);
                                Toast.makeText(delete_user.this, "Password has been verified you can delete user now", Toast.LENGTH_SHORT).show();
                                authentication.setText("You are verified.You can Delete Your Account now");

                                password.setEnabled(false);
                                button.setEnabled(false);
                                button1.setEnabled(true);
                                button1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        pb.setVisibility(View.VISIBLE);


                                            showAlertDialog();

                                    }

                                });
                            }
                            else{
                                try {
                                    throw task.getException();
                                } catch (Exception e){
                                    pb.setVisibility(View.GONE);
                                    Toast.makeText(delete_user.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    password.clearComposingText();
                                }
                            }



                        }
                    });
                }

            }
        });
    }

    public  void showAlertDialog(){
        pb.setVisibility(View.GONE);
        AlertDialog.Builder builder= new AlertDialog.Builder(delete_user.this);
        builder.setTitle("Delete User and Related DATA");
        builder.setMessage("Do you really want to delete your account and related data? This action is irreversible");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 deleteuser(firebaseUser);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(delete_user.this,mainpage.class));
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
            }
        });
        alertDialog.show();
    }

    private void deleteuser(FirebaseUser firebaseUser) {
        SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putBoolean("islogin",false);
        editor.apply();
        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    deleteuserdata();
                    mauth.signOut();
                    Toast.makeText(delete_user.this, "User has been deleted successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(delete_user.this,MainActivity.class));

                }
                else{
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (Exception e){
                        Toast.makeText(delete_user.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                pb.setVisibility(View.GONE);
            }

        });
    }

    private void deleteuserdata() {




        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference(uid+"own");
        databaseReference1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"onSuccess:PDFs Deleted");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,e.getMessage());
                Toast.makeText(delete_user.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

try {
    StorageReference storageReference1=firebaseStorage.getReference(uid+"uploads/");
    storageReference1.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void unused) {
            Log.d(TAG,"onSuccess:PDFs Deleted");
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure( Exception e) {
            Log.d(TAG,e.getMessage());
            Toast.makeText(delete_user.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}catch (Exception e){

}


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"onSuccess:User data Deleted");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,e.getMessage());
                Toast.makeText(delete_user.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        try {
            firebaseStorage=FirebaseStorage.getInstance();
            StorageReference storageReference=firebaseStorage.getReferenceFromUrl((firebaseUser.getPhotoUrl()).toString());
            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG,"onSuccess:Photo Deleted");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG,e.getMessage());
                    Toast.makeText(delete_user.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){

        }
        try{
            DatabaseReference databaseReference3= FirebaseDatabase.getInstance().getReference("Test scores");
            databaseReference3.child(uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG,"onSuccess:Test scores Deleted");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG,e.getMessage());
                    Toast.makeText(delete_user.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e){

        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("testno",0);
        editor.apply();

        SharedPreferences preferencesdb = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editordb = preferencesdb.edit();
        editordb.putInt("testnodb",0);
        editordb.apply();

        SharedPreferences preferencesmp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editormp = preferencesmp.edit();
        editormp.putInt("testnomp",0);
        editormp.apply();

        SharedPreferences preferencespy = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorpy = preferencespy.edit();
        editorpy.putInt("testnop",0);
        editorpy.apply();

    }
}