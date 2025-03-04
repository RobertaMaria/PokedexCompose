package com.example.pokedex.details.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "species_table")
data class PokemonSpecieEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "evolutionId") var evolutionId: Int,
)