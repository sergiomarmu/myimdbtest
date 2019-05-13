package com.vp.favorites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import com.vp.favorites.holder.FavoriteAdapter;

public class FavoriteActivity extends AppCompatActivity {
    private final static String DATABASE_NAME = "IMDB_APP";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView = findViewById(R.id.recyclerView);
        init();
    }

    private void init() {
        /**
         * Init DB
         */
        mDB = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new FavoriteAdapter(mDB.favoriteDao().getAll());
        recyclerView.setAdapter(mAdapter);
    }
}
