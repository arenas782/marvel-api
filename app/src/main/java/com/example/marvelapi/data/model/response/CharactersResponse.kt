package com.example.marvelapi.data.model.response

import com.squareup.moshi.Json

data class CharactersResponse(
    @field:Json(name = "code")
    val code : Int,


    @field:Json(name = "data")
    val data : CharactersData,
    )

