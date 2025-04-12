package com.example.to_dolist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(notes: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Update
    suspend fun updateNote(note: Notes)

    @Query("SELECT * FROM notes Order BY title")
    suspend fun getAllNotesSortedByTitleAsc(): List<Notes>

    @Query("SELECT * FROM notes Order BY title DESC")
    suspend fun getAllNotesSortedByTitleDesc(): List<Notes>

    @Query("SELECT * FROM notes Order BY date")
   suspend fun getAllNotesSortedByDateAsc(): List<Notes>

    @Query("SELECT * FROM notes Order BY date DESC")
    suspend fun getAllNotesSortedByDateDesc(): List<Notes>

    @Query("SELECT * FROM notes WHERE title LIKE :query OR content LIKE :query")
    suspend fun searchNotes(query: String): List<Notes>
}