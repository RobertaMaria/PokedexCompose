package com.example.pokedex.details.data.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity

data class SpeciesAndEvolution(
    @Embedded val species: PokemonSpecieEntity,
    @Relation(parentColumn = "evolutionId", entityColumn = "evolutionId")
    val evolution: PokemonEvolutionEntity
)