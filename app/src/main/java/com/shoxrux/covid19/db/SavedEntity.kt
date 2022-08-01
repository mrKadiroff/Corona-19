package com.shoxrux.covid19.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SAVED")
data class SavedEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val saqlandi: String?,

)