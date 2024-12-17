package com.example.to_dolist

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notes: Note)

    @Update
    suspend fun  update(note: Notes)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes Order BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

}