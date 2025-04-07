package com.example.to_dolist.repo

import androidx.lifecycle.LiveData
import com.example.to_dolist.data.local.NoteDao
import com.example.to_dolist.data.local.Notes
import javax.inject.Inject

interface INoteRepository {

    suspend fun insertNote(note: Notes)
    suspend fun deleteNote(note: Notes)
    fun getAllNotesSortedByTitleAsc(): LiveData<List<Notes>>
    fun getAllNotesSortedByTitleDesc(): LiveData<List<Notes>>
    fun getAllNotesSortedByDateAsc(): LiveData<List<Notes>>
    fun getAllNotesSortedByDateDesc(): LiveData<List<Notes>>


}

class NoteRepositoryImpl @Inject constructor
    (private val noteDao: NoteDao
): INoteRepository {

    override suspend fun insertNote(note: Notes) = noteDao.insertNote(note)
    override suspend fun deleteNote(note: Notes) = noteDao.deleteNote(note)
    override fun getAllNotesSortedByTitleAsc(): LiveData<List<Notes>> = noteDao.getAllNotesSortedByTitleAsc()
    override fun getAllNotesSortedByTitleDesc(): LiveData<List<Notes>> = noteDao.getAllNotesSortedByTitleDesc()
    override fun getAllNotesSortedByDateAsc(): LiveData<List<Notes>> = noteDao.getAllNotesSortedByDateAsc()
    override fun getAllNotesSortedByDateDesc(): LiveData<List<Notes>> = noteDao.getAllNotesSortedByDateDesc()
}