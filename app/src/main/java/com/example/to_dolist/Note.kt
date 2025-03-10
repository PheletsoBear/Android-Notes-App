package com.example.to_dolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
    data class Notes(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val title: String,
        val content: String,
        val date: String
    )
