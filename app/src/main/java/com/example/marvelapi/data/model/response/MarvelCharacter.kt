package com.example.marvelapi.data.model.response

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "MarvelCharacters")
data class MarvelCharacter(
    @PrimaryKey(autoGenerate = true)
    val externalId : Int,

    @field:Json(name = "id")
    val id : Int,

    @field:Json(name = "name")
    val name : String,

    @field:Json(name = "description")
    val description : String,

    @Embedded
    @field:Json(name = "thumbnail")
    val thumbnail: CharacterThumbnail,

    @Embedded
    @field:Json(name = "series")
    val series: CharacterSeries,

    @ColumnInfo(name = "created_at") var createdAt: Long,

    )
