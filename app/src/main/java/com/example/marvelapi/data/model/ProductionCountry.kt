package com.example.marvelapi.data.model

import com.squareup.moshi.Json

data class ProductionCountry(
    @field:Json(name = "name")
    val name: String?,

    @field:Json(name = "id")
    val id: Int?,
)