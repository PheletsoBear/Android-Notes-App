package com.example.to_dolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.to_dolist.data.local.NoteDao
import com.example.to_dolist.data.local.Notes

@Database(entities = [Notes::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}