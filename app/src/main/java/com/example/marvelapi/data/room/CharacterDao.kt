package com.example.marvelapi.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvelapi.data.model.response.MarvelCharacter


@Dao
interface CharacterDao {
    @Query("SELECT * FROM marvelcharacters order by name asc ")
    fun getCharacters(): PagingSource<Int,MarvelCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(marvelCharacter : MarvelCharacter?)

    @Query("DELETE FROM marvelcharacters WHERE id = :id")
    suspend fun deleteCharacterById(id: Int)

    suspend fun insertAllCharacters(marvelCharacters: List<MarvelCharacter?>?){

        marvelCharacters?.forEach {
            insertCharacter(it.apply {
                this!!.createdAt = System.currentTimeMillis()
            })
        }
    }

    @Query("SELECT COUNT(id) from marvelcharacters")
    suspend fun count(): Int
}