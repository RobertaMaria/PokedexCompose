package com.example.pokedex.details.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokedex.details.data.database.convertes.EvolutionsTypeConverter

@TypeConverters(EvolutionsTypeConverter::class)
@Entity(tableName = "evolution_table")
data class PokemonEvolutionEntity(
    @PrimaryKey(autoGenerate = true) val evolutionId: Int = 0,
    @ColumnInfo(name = "evolutions") var evolutions: List<EvolutionsEntity>
    )

data class EvolutionsEntity(
    @ColumnInfo(name = "pokemonId") var pokemonId: Int,
    @ColumnInfo(name = "name") var name: String
)