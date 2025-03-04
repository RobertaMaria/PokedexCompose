package com.example.pokedex.details.data.datasource.local

import com.example.pokedex.data.database.entity.PokemonEntity
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity
import com.example.pokedex.details.data.database.model.SpeciesAndEvolution

interface PokemonDetailLocalDataSource {
    suspend fun savePokemonSpecie(pokemonDetail: PokemonSpecieEntity)
    fun getPokemonSpecie(id: Int): SpeciesAndEvolution?
    fun getPokemonEntity(pokemonId: Int): PokemonEntity?
    suspend fun saveEvolutions(evolutions: PokemonEvolutionEntity)
}