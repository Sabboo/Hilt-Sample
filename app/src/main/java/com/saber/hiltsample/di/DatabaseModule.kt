package com.saber.hiltsample.di

import android.content.Context
import androidx.room.Room
import com.saber.hiltsample.db.MarvelDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): MarvelDb {
        val databaseBuilder = Room.databaseBuilder(appContext, MarvelDb::class.java, "marvel.db")
        return databaseBuilder
            .fallbackToDestructiveMigration()
            .build()
    }
}