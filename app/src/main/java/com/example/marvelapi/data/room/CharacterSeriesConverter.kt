package com.example.marvelapi.data.room

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.marvelapi.data.model.response.CharacterSerie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class CharacterSeriesConverter {
    @TypeConverter
    fun fromCharacterSeriesList(characterSeriesList: List<CharacterSerie?>?): String? {
        if (characterSeriesList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<CharacterSerie?>?>() {}.type
        return gson.toJson(characterSeriesList, type)
    }

    @TypeConverter
    fun toCharacterSeriesList(characterSeriesString: String?): List<CharacterSerie>? {
        if (characterSeriesString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<CharacterSerie?>?>() {}.type
        return gson.fromJson<List<CharacterSerie>>(characterSeriesString, type)
    }
}