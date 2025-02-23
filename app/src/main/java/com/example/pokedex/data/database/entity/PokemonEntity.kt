package com.example.pokedex.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokedex.data.database.converters.TypePokemonListConverter

@TypeConverters(TypePokemonListConverter::class)
@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "height") var height: Int,
    @ColumnInfo(name = "weight") var weight: Int,
    @ColumnInfo(name = "type") var type: List<String>
)