package com.example.caderno;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter1 extends RecyclerView.ViewHolder implements View.OnClickListener{
    public Itemclicklistener listener1;
    private final Context context1;
    public TextView pdftitle1;

    public Adapter1(@NonNull View itemView1) {
        super(itemView1);
        context1= itemView1.getContext();
        pdftitle1 = itemView1.findViewById(R.id.pdftv);
    }

    @Override
    public void onClick(View view) {

        listener1.onClick(view, getAdapterPosition(), false);

    }
}
