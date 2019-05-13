package com.vp.favorites.holder;

import android.view.View;
import android.widget.TextView;

import com.vp.favorites.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle;
    TextView textViewYear;

    FavoriteHolder(@NonNull  View v) {
        super(v);
        textViewTitle = itemView.findViewById(R.id.txtViewTitle);
        textViewYear = itemView.findViewById(R.id.txtViewYear);
    }
}
