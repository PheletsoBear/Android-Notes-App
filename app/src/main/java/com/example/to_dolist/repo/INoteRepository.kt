package com.example.to_dolist.repo

import androidx.lifecycle.LiveData
import com.example.to_dolist.data.local.Notes

interface INoteRepository {

    val allNotes: LiveData<List<Notes>>
    suspend fun insertNote(note: Notes)
    suspend fun deleteNote(note: Notes)
}