package com.example.marvelapi.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelapi.data.api.CharactersService
import com.example.marvelapi.data.model.response.CharactersResponse
import com.example.marvelapi.data.model.response.MarvelCharacter
import com.example.marvelapi.data.model.response.MovieDetailsResponse
import com.example.marvelapi.data.remotemediator.CharactersRemoteMediator
import com.example.marvelapi.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CharactersRepository @Inject constructor(private val db: AppDatabase, private val CharactersService: CharactersService) {

    private val pagingSourceFactory = {
        db.characterDao().getCharacters()
    }

    suspend fun getCharacterDetails(characterId : String):CharactersResponse {
        return CharactersService.getCharacter(characterId)
    }

    @ExperimentalPagingApi
    fun getCharacters():Flow<PagingData<MarvelCharacter>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                jumpThreshold = 20,
                initialLoadSize = 40
            ),
            remoteMediator = CharactersRemoteMediator(
                CharactersService,
                db),
            pagingSourceFactory = pagingSourceFactory).flow
    }

    suspend fun deleteMovieById(id : Int) {
        try {
            db.characterDao().deleteCharacterById(id)
        }catch (e : Exception){
            Log.e("TAG", "$e.localizedMessage")
        }
    }
}