package com.example.marvelapi.data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.marvelapi.data.api.CharactersService
import com.example.marvelapi.data.model.RemoteKeys
import com.example.marvelapi.data.model.response.MarvelCharacter
import com.example.marvelapi.data.room.AppDatabase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class CharactersRemoteMediator @Inject constructor(private val api : CharactersService, private val appDatabase: AppDatabase): RemoteMediator<Int, MarvelCharacter>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, MarvelCharacter>): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {
                if (appDatabase.characterDao().count() > 0) return MediatorResult.Success(false)
                null
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                getKey()
            }
        }

        try {
            if (key != null) {
                if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = key?.nextKey ?: 0

            val apiResponse = api.characters(offset = page, limit = LOAD_SIZE )

            val charactersList = apiResponse.data.results

            val endOfPaginationReached =
                charactersList.isEmpty()

            appDatabase.withTransaction {
                val nextKey = page + LOAD_SIZE

                appDatabase.remoteKeysDao().insertKey(
                    RemoteKeys(
                        0,
                        nextKey = nextKey,
                        isEndReached = endOfPaginationReached == true
                    )
                )
                appDatabase.characterDao().insertAllCharacters(charactersList)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
    private suspend fun getKey(): RemoteKeys? {
        return appDatabase.remoteKeysDao().getKeys().firstOrNull()
    }

    companion object{
        const val LOAD_SIZE = 10
    }
}