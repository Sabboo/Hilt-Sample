package com.saber.hiltsample.repository

import androidx.paging.PagingData
import com.saber.hiltsample.data.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelCharactersRepository {
    fun marvelCharacters(
        pageSize: Int,
        offset: Int
    ): Flow<PagingData<MarvelCharacter>>
}