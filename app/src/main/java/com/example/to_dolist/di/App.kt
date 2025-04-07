package com.example.to_dolist.di

import android.app.Application
import com.example.to_dolist.data.local.NoteDao
import com.example.to_dolist.di.RoomDbModule.provideRandomNotes
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var  noteDao : NoteDao
    override fun onCreate() {
        super.onCreate()
  /*Seeding Data*/
       CoroutineScope(Dispatchers.IO).launch {
            provideRandomNotes(noteDao)
        }
    }
}