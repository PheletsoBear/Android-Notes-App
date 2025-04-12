package com.example.to_dolist

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.to_dolist.data.local.NoteDao
import com.example.to_dolist.data.local.Notes

@Database(entities = [Notes::class], version = 2, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao
}