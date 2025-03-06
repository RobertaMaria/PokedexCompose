package com.example.pokedex.list.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokedex.list.data.database.converters.PokemonStatsListConverter
import com.example.pokedex.list.data.database.converters.PokemonTypeListConverter

@TypeConverters(PokemonTypeListConverter::class, PokemonStatsListConverter::class)
@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "height") var height: Int,
    @ColumnInfo(name = "weight") var weight: Int,
    @ColumnInfo(name = "type") var type: List<String>,
    @ColumnInfo(name = "stats") var stats: List<PokemonStats>
)

data class PokemonStats(
    val baseStat: Int,
    val stat: Stat
)
data class Stat(
   val name: String
)