package com.example.to_dolist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.to_dolist.constants.SortType
import com.example.to_dolist.data.local.Notes
import com.example.to_dolist.repo.INoteRepository
import com.example.to_dolist.repo.NoteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewNoteViewModel @Inject constructor(
    private val repository: INoteRepository
) : ViewModel() {

    private val _allNotes = MutableLiveData<List<Notes>>()
    val allNotes: LiveData<List<Notes>> get() = _allNotes

    private val _filteredNotes = MutableLiveData<List<Notes>>()
    val filteredNotes: LiveData<List<Notes>> get() = _filteredNotes

    init {
        fetchSortedNotes(SortType.DATE_ASC)
    }

    fun insert(note: Notes) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun delete(note: Notes) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun fetchSortedNotes(sortType: SortType){
        viewModelScope.launch {
        when(sortType){

            SortType.A_Z -> {
                _allNotes.value = repository.getAllNotesSortedByTitleAsc()
            }

            SortType.Z_A -> {
                _allNotes.value = repository.getAllNotesSortedByTitleDesc()
            }

           SortType.DATE_ASC -> {
               _allNotes.value = repository.getAllNotesSortedByDateAsc()
           }

           SortType.DATE_DESC -> {
               _allNotes.value = repository.getAllNotesSortedByDateDesc()
           }
        }

      }
   }
     fun searchQuery(query: String){

        viewModelScope.launch {
            _filteredNotes.value = repository.searchNotes(query)
        }
     }
}