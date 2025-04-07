package com.example.to_dolist.repo

import androidx.lifecycle.LiveData
import com.example.to_dolist.data.local.NoteDao
import com.example.to_dolist.data.local.Notes
import javax.inject.Inject

interface INoteRepository {

    suspend fun insertNote(note: Notes)
    suspend fun deleteNote(note: Notes)
    suspend fun getAllNotesSortedByTitleAsc(): List<Notes>
    suspend fun getAllNotesSortedByTitleDesc(): List<Notes>
    suspend fun getAllNotesSortedByDateAsc(): List<Notes>
    suspend fun getAllNotesSortedByDateDesc(): List<Notes>


}

class NoteRepositoryImpl @Inject constructor
    (private val noteDao: NoteDao
): INoteRepository {

    override suspend fun insertNote(note: Notes) = noteDao.insertNote(note)
    override suspend fun deleteNote(note: Notes) = noteDao.deleteNote(note)
    override suspend fun getAllNotesSortedByTitleAsc(): List<Notes> = noteDao.getAllNotesSortedByTitleAsc()
    override suspend fun getAllNotesSortedByTitleDesc(): List<Notes> = noteDao.getAllNotesSortedByTitleDesc()
    override suspend fun getAllNotesSortedByDateAsc(): List<Notes> = noteDao.getAllNotesSortedByDateAsc()
    override suspend fun getAllNotesSortedByDateDesc(): List<Notes> = noteDao.getAllNotesSortedByDateDesc()
}