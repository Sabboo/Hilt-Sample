package com.saber.hiltsample.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.saber.hiltsample.api.MarvelAPI
import com.saber.hiltsample.db.MarvelDb
import javax.inject.Inject


class MarvelCharactersRepositoryImpl @Inject constructor(
    val db: MarvelDb,
    private val marvelAPI: MarvelAPI
) :
    MarvelCharactersRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun marvelCharacters(pageSize: Int, offset: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PageKeyedRemoteMediator(db, marvelAPI, offset)
    ) {
        db.characters().getMarvelCharacters()
    }.flow
}
