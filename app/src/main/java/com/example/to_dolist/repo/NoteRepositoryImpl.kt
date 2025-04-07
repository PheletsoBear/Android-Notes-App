package com.example.to_dolist.repo

import androidx.lifecycle.LiveData
import com.example.to_dolist.data.local.NoteDao
import com.example.to_dolist.data.local.Notes

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Notes>> = noteDao.getAllNotes()
    suspend fun insertNote(note: Notes) = noteDao.insertNote(note)
    suspend fun deleteNote(note: Notes) = noteDao.deleteNote(note)
}