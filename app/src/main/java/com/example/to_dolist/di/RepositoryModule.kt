package com.example.to_dolist.di

import com.example.to_dolist.repo.INoteRepository
import com.example.to_dolist.repo.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindINoteRepository(impl: NoteRepositoryImpl): INoteRepository
}