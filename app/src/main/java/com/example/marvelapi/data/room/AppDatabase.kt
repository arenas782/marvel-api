package com.example.marvelapi.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.marvelapi.data.model.RemoteKeys
import com.example.marvelapi.data.model.response.MarvelCharacter

@Database(entities = [MarvelCharacter::class, RemoteKeys::class], version = 14)
@TypeConverters(CharacterSeriesConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao() : RemoteKeysDao

    companion object {
        const val DATABASE_NAME : String = "marvel_characters_db"
    }
}