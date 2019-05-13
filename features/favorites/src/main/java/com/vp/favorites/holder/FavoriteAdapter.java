package com.vp.favorites.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vp.favorites.Favorite;
import com.vp.favorites.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteHolder> {
    private List<Favorite> myFavoriteList;

    public FavoriteAdapter(List<Favorite> myList) {
        myFavoriteList = myList;
    }

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_holder, parent, false);
        FavoriteHolder vh = new FavoriteHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(FavoriteHolder holder, int position) {
        holder.textViewTitle.setText(myFavoriteList.get(position).getTitle());
        holder.textViewYear.setText(myFavoriteList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return myFavoriteList.size();
    }
}
