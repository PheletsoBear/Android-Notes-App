package com.example.to_dolist

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.to_dolist.data.local.Notes

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(notes: Notes)

    @Update
    suspend fun  updateNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM notes Order BY id")
    fun getAllNotes(): LiveData<List<Note>>

}