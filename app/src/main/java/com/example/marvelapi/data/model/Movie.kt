package com.example.marvelapi.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val primaryId: Int,

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "title")
    val title: String? = null,

    @field:Json(name = "overview")
    val overview: String? = null,

    @field:Json(name = "poster_path")
    val poster_path: String? = null,

    @ColumnInfo(name = "created_at") var createdAt: Long,

    )
