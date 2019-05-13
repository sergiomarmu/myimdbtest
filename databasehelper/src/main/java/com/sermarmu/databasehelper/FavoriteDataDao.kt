package com.vp.favorites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface FavoriteDataDao {
    companion object {
        const val TABLE_NAME = "Favorite"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): List<Favorite>

    @Insert(onConflict = REPLACE)
    fun insert(favorites: Favorite)

    @Query("SELECT * FROM $TABLE_NAME WHERE imdbID = :id")
    fun findFavoriteById(id: String): Favorite

    @Delete
    fun delete(favorites: Favorite)

    @Query("DELETE from $TABLE_NAME")
    fun deleteAll()
}