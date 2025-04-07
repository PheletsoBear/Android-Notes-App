package com.example.to_dolist.di

import android.app.Application
import androidx.room.Room
import com.example.to_dolist.NoteDatabase
import com.example.to_dolist.data.local.NoteDao
import com.example.to_dolist.data.local.Notes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDbModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = NoteDatabase::class.java,
            name = "notes_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    /*Seeding the the database with data*/

    @Provides
    @Singleton
    suspend fun provideRandomNotes(noteDao: NoteDao) {
            val randomNotes = generateRandomNote()
            noteDao.insertNote(randomNotes)
    }

     private fun generateRandomNote(): Notes {
        val randomTitle = "Sometimes It’s in those quiet seconds, where time slows down "
        val randomContent = "Sometimes the most, ng patiently for us to notice. It’s in those quiet seconds, where time slows down and hearts open, that we find meaning, peace, and the courage to move forward through anything"
        return Notes(
            title = randomTitle,
            content = randomContent,
            id = 10,
            date = "06/04/2024"
        )
    }
}
