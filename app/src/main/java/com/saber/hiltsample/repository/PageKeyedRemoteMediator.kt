package com.saber.hiltsample.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.saber.hiltsample.api.MarvelAPI
import com.saber.hiltsample.db.MarvelDb
import com.saber.hiltsample.db.CharactersDao
import com.saber.hiltsample.db.MarvelCharactersRemoteKeyDao
import com.saber.hiltsample.data.CharacterRemoteKey
import com.saber.hiltsample.data.MarvelCharacter
import retrofit2.HttpException
import java.io.IOException
import java.util.*

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val db: MarvelDb,
    private val marvelAPI: MarvelAPI,
    private var offset: Int,
) : RemoteMediator<Int, MarvelCharacter>() {

    private val charactersDao: CharactersDao = db.characters()
    private val remoteKeyDao: MarvelCharactersRemoteKeyDao = db.remoteKeys()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MarvelCharacter>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                REFRESH -> null
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.remoteKeyByOffset((offset * state.config.pageSize).toString())
                    }
                    if (remoteKey.nextPageKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    offset++
                    remoteKey.nextPageKey
                }
            }

            val timeStamp = Date().time

            val data = marvelAPI.getCharacters(
                offset = loadKey,
                limit = state.config.pageSize,
                ts = timeStamp.toString()
            ).data

            db.withTransaction {
                remoteKeyDao.insert(
                    CharacterRemoteKey(
                        data.offset,
                        (data.offset.toInt() + state.config.pageSize).toString()
                    )
                )
                charactersDao.insertAll(data.results)
            }

            return MediatorResult.Success(endOfPaginationReached = data.results.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}
