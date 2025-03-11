package com.example.pokedex.details.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokedex.details.data.database.convertes.DamageRelationsConverter

@Entity(tableName = "species_table")
@TypeConverters(DamageRelationsConverter::class)
data class PokemonSpecieEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "evolutionId") var evolutionId: Int,
    @ColumnInfo(name = "doubleDamage") var doubleDamage: List<String>,
    @ColumnInfo(name = "noDamage") var noDamage: List<String>
)