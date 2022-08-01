package com.shoxrux.covid19.di.room

import androidx.lifecycle.LiveData
import com.shoxrux.covid19.db.AppDatabase
import com.shoxrux.covid19.db.SavedEntity

import javax.inject.Inject

class DBRepository @Inject constructor(val appDatabase: AppDatabase) {

    suspend fun insertSaved(savedEntity: SavedEntity): Long {
        return appDatabase.savedDao()
            .insert(savedEntity)
    }


    suspend fun getAllValue() = appDatabase.savedDao().getAllSaved()


}