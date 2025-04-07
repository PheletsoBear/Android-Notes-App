package com.example.to_dolist.repo

import androidx.lifecycle.LiveData
import com.example.to_dolist.data.local.NoteDao
import com.example.to_dolist.data.local.Notes
import javax.inject.Inject

interface INoteRepository {

    val allNotes: LiveData<List<Notes>>
    suspend fun insertNote(note: Notes)
    suspend fun deleteNote(note: Notes)
}

class NoteRepositoryImpl @Inject constructor
    (private val noteDao: NoteDao
): INoteRepository {

    override val allNotes: LiveData<List<Notes>> = noteDao.getAllNotes()
    override suspend fun insertNote(note: Notes) = noteDao.insertNote(note)
    override suspend fun deleteNote(note: Notes) = noteDao.deleteNote(note)
}