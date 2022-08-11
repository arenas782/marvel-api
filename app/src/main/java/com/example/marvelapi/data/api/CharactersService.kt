package com.example.marvelapi.data.api

import com.example.marvelapi.data.model.response.MovieDetailsResponse
import com.example.marvelapi.data.model.response.CharactersResponse
import retrofit2.http.*


interface   CharactersService {
    @GET("characters?apikey=9593eb56bc3b26ce5aac8a815c254fb7&hash=41ed887848e53be24596e68f320356fc&ts=1660244053")
    suspend fun characters(@Query("offset") offset : Int,@Query("limit") limit : Int): CharactersResponse

    @GET("characters/{characterId}?apikey=9593eb56bc3b26ce5aac8a815c254fb7&hash=41ed887848e53be24596e68f320356fc&ts=1660244053")
    suspend fun getCharacter(@Path("characterId") characterId : String): CharactersResponse
}
