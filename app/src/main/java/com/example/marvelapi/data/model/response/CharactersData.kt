package com.example.marvelapi.data.model.response

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class CharactersData(
    @field:Json(name = "offset")
    val offset : Int,

    @field:Json(name = "limit")
    val limit : Int,

    @field:Json(name = "count")
    val count : Int,

    @field:Json(name = "results")
    val results : List<MarvelCharacter>,

    @field:Json(name = "series")
    val series : CharacterSeries,
)