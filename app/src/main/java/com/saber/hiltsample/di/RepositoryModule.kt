package com.saber.hiltsample.di

import com.saber.hiltsample.api.MarvelAPI
import com.saber.hiltsample.db.MarvelDb
import com.saber.hiltsample.repository.MarvelCharactersRepository
import com.saber.hiltsample.repository.MarvelCharactersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(
        db: MarvelDb,
        api: MarvelAPI
    ): MarvelCharactersRepository = MarvelCharactersRepositoryImpl(db, api)

}