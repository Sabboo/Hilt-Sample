package com.saber.hiltsample.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saber.hiltsample.data.MarvelCharacter

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(marvelCharacters: List<MarvelCharacter>)

    @Query("SELECT * FROM MarvelCharacter Order By name")
    fun getMarvelCharacters(): PagingSource<Int, MarvelCharacter>

}
