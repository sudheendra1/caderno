package com.example.caderno;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapterscore extends RecyclerView.ViewHolder {
    private final Context context1;
    public TextView testno, score;

    public Adapterscore(@NonNull View itemView) {
        super(itemView);
        context1 = itemView.getContext();
        testno = itemView.findViewById(R.id.scoretv1);
        score = itemView.findViewById(R.id.scoretv2);
    }
}
