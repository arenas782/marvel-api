package com.example.marvelapi.data.model.response

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class CharacterSeries(
    @field:Json(name = "items")
    val items : List<CharacterSerie>,
)
