package com.example.pokedex.details.data.database.convertes

import androidx.room.TypeConverter
import com.example.pokedex.details.data.database.entity.EvolutionsEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EvolutionsTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromEvolutionsList(evolutions: List<EvolutionsEntity>?): String? {
        return gson.toJson(evolutions)
    }

    @TypeConverter
    fun toEvolutionsList(evolutionsJson: String?): List<EvolutionsEntity>? {
        if (evolutionsJson == null) {
            return null
        }
        val type = object : TypeToken<List<EvolutionsEntity>>() {}.type
        return gson.fromJson(evolutionsJson, type)
    }
}