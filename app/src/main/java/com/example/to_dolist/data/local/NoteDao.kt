package com.example.to_dolist.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(notes: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM notes Order BY title")
    suspend fun getAllNotesSortedByTitleAsc(): List<Notes>

    @Query("SELECT * FROM notes Order BY title DESC")
    suspend fun getAllNotesSortedByTitleDesc(): List<Notes>

    @Query("SELECT * FROM notes Order BY date")
   suspend fun getAllNotesSortedByDateAsc(): List<Notes>

    @Query("SELECT * FROM notes Order BY date DESC")
    suspend fun getAllNotesSortedByDateDesc(): List<Notes>
}