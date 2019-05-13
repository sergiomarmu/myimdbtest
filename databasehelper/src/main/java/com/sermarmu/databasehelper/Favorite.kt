package com.vp.favorites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
        @PrimaryKey
        val imdbID: String,

        @ColumnInfo(name = "title")
        val title: String?,

        @ColumnInfo(name = "year")
        val year: String?,

        @ColumnInfo(name = "poster")
        val poster: String?
)


