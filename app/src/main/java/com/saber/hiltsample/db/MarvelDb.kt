package com.saber.hiltsample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saber.hiltsample.data.CharacterRemoteKey
import com.saber.hiltsample.data.MarvelCharacter


@Database(
    entities = [MarvelCharacter::class, CharacterRemoteKey::class],
    version = 1, exportSchema = false
)
abstract class MarvelDb : RoomDatabase() {

    abstract fun characters(): CharactersDao
    abstract fun remoteKeys(): MarvelCharactersRemoteKeyDao

}