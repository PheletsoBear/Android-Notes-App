package com.example.to_dolist

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteViewModel(private val noteDao: NoteDao): ViewModel() {

    private val repository = NoteRepository(noteDao)

    private val _allNotes: MutableLiveData<MutableList<Note>> = MutableLiveData(mutableListOf())
    val allNotes: LiveData<MutableList<Note>> = _allNotes


    fun getNotes(){
        _allNotes.value = repository.allNotes as MutableList<Note>?
    }


    suspend fun addNote(note: Note) {
        val currentNotes = _allNotes.value ?: mutableListOf()
        currentNotes.add(note)
        _allNotes.value = currentNotes
        repository.insert(note)
    }

    suspend fun deleteNote(note: Note) {
        val currentNotes = _allNotes.value ?: mutableListOf()
        currentNotes.remove(note)
        _allNotes.value = currentNotes
        repository.delete(note)
    }

}