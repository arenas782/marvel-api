package com.example.marvelapi.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelapi.data.api.CharactersService
import com.example.marvelapi.data.model.Movie
import com.example.marvelapi.data.model.response.CharactersData
import com.example.marvelapi.data.model.response.MarvelCharacter
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharactersRemotePagingSource @Inject constructor(private val backend : CharactersService) : PagingSource<Int, MarvelCharacter>(){
    override fun getRefreshKey(state: PagingState<Int, MarvelCharacter>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarvelCharacter> {
        val offset = params.key ?: 0

        return try {
            val response = backend.characters(offset,10)
            val movies = response.data.results
            LoadResult.Page(
                data = movies,
                prevKey = if (offset == 0) null else offset - 1,
                nextKey = if (movies.isEmpty()) null else offset + 10
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}