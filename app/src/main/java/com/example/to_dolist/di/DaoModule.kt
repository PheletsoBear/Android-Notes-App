package com.example.to_dolist.di

import com.example.to_dolist.NoteDatabase
import com.example.to_dolist.data.local.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDatabase): NoteDao {
        return database.noteDao()
    }

}