package com.example.to_dolist

import android.provider.ContactsContract
import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<ContactsContract.CommonDataKinds.Note>> = noteDao.getAllNotes()

    suspend fun insert(note: ContactsContract.CommonDataKinds.Note) = noteDao.insert(note)
    suspend fun update(note: Notes) = noteDao.update(note)
    suspend fun delete(note: ContactsContract.CommonDataKinds.Note) = noteDao.delete(note)
}