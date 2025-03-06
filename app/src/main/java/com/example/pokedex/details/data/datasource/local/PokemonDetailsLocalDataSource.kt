package com.example.pokedex.details.data.datasource.local

import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity
import com.example.pokedex.details.data.database.model.SpeciesAndEvolutions

interface PokemonDetailsLocalDataSource {
    suspend fun savePokemonSpecie(pokemonDetail: PokemonSpecieEntity)
    fun getPokemonSpecie(id: Int): SpeciesAndEvolutions?
    fun getPokemonEntity(pokemonId: Int): PokemonEntity?
    suspend fun saveEvolutions(evolutions: PokemonEvolutionEntity)
}