package com.shoxrux.covid19.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SavedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedEntity: SavedEntity):Long

    // NOTE - Since we are already using LIVE-DATA no need to use suspend function
    @Query("SELECT * FROM saved")
    suspend fun getAllSaved():List<SavedEntity>


}