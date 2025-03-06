package com.example.pokedex.list.data.database.converters

import androidx.room.TypeConverter
import com.example.pokedex.list.data.database.entity.PokemonStats
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonStatsListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPokemonStatsList(stats: List<PokemonStats>?): String? {
        return gson.toJson(stats)
    }

    @TypeConverter
    fun toPokemonStatsList(statsJson: String?): List<PokemonStats>? {
        if (statsJson == null) {
            return null
        }
        val type = object : TypeToken<List<PokemonStats>>() {}.type
        return gson.fromJson(statsJson, type)
    }
}