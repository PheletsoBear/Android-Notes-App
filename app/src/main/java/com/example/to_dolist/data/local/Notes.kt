package com.example.to_dolist.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
    data class Notes(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val  title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "date") val date: String
    ): Parcelable
