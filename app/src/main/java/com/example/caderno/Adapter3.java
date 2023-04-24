package com.example.caderno;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter3 extends RecyclerView.ViewHolder implements View.OnClickListener {
    public Itemclicklistener listener;
    private final Context context;
    public TextView pdftitle;

    public Adapter3(@NonNull View itemView) {
        super(itemView);
        context= itemView.getContext();
        pdftitle = itemView.findViewById(R.id.booktv);

    }
    @Override
    public void onClick(View view) {

        listener.onClick(view, getAdapterPosition(), false);

    }
}
