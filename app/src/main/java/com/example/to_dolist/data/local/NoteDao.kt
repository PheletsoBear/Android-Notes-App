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
    fun getAllNotesSortedByTitleAsc(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes Order BY title DESC")
    fun getAllNotesSortedByTitleDesc(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes Order BY date")
    fun getAllNotesSortedByDateAsc(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes Order BY date DESC")
    fun getAllNotesSortedByDateDesc(): LiveData<List<Notes>>
}