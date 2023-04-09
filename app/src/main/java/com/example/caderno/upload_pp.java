package com.example.caderno;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class upload_pp extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;

    private ImageView disp;
    private Button selectimg,uploadimg;
    private TextView ahfb;
    private ProgressBar progressBar;
    private FirebaseAuth mauth;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    private static final int Pick_img_req=1;
    private Uri uriImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pp);
        Swipetorefresh();

        disp=findViewById(R.id.picturedisp);
        selectimg=findViewById(R.id.select_img);
        uploadimg=findViewById(R.id.upload_img);
        ahfb=findViewById(R.id.tv_uploadpp);
        progressBar=findViewById(R.id.uploadimgpb);

       mauth=FirebaseAuth.getInstance();
       firebaseUser=mauth.getCurrentUser();
       storageReference= FirebaseStorage.getInstance().getReference("Diplay Pics");
       Uri uri=firebaseUser.getPhotoUrl();
        Picasso.with(upload_pp.this).load(uri).into(disp);
        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileManage();
            }



        });

        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                uploadpic();
            }


        });

    }

    private void uploadpic() {
        if(uriImg!=null){
            StorageReference filereference=storageReference.child(mauth.getCurrentUser().getUid() + "." + getfileextension(uriImg));
            filereference.putFile(uriImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloaduri=uri;
                            firebaseUser=mauth.getCurrentUser();
                            UserProfileChangeRequest ppupdate=new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(downloaduri).build();
                            firebaseUser.updateProfile(ppupdate);
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(upload_pp.this, "image uploaded successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(upload_pp.this,profile.class));
                }
            });

        }
    }

    private String getfileextension(Uri uriImg) {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uriImg));
    }

    private void openFileManage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,Pick_img_req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Pick_img_req && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uriImg=data.getData();
            disp.setImageURI(uriImg);

        }
    }

    private void Swipetorefresh() {
        swipeRefreshLayout=findViewById(R.id.refreshpulluploadpp);
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