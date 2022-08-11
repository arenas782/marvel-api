package com.example.marvelapi.data.model.response

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class CharacterSerie(
    @field:Json(name = "name")
    val name : String,
)
