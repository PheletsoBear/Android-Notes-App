package com.example.to_dolist.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(notes: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM notes Order BY id")
    fun getAllNotes(): LiveData<List<Notes>>
}