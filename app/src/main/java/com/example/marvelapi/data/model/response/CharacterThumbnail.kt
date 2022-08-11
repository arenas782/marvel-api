package com.example.marvelapi.data.model.response

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class CharacterThumbnail(
    @field:Json(name = "path")
    val path : String,

    @field:Json(name = "extension")
    val extension : String,
)
