package com.example.to_dolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_dolist.data.local.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewNoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    fun getAllNotes(): LiveData<List<Notes>> = repository.allNotes

    fun insert(note: Notes) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun delete(note: Notes) = viewModelScope.launch {
        repository.deleteNote(note)
    }

}