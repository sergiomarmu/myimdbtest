package com.vp.favorites

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Favorite::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteDao() : FavoriteDataDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private var DATABASE_NAME = "IMDB_APP"

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, "$DATABASE_NAME.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}