package com.example.caderno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class retrievepdf extends AppCompatActivity {
    StorageReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrievepdf);
        ListView listView=(ListView)findViewById(R.id.listview);

        List<putPDF> uploadedPDF = new ArrayList<>();
retrievepdffiles();
          }

    private void retrievepdffiles() {
        databaseReference= FirebaseStorage.getInstance().getReference("gs://caderno-419a2.appspot.com/textbookm");
        databaseReference.getDownloadUrl().addOnCompleteListener()
    }
}