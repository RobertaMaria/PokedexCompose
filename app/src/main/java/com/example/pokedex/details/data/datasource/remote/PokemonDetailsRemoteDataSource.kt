package com.example.pokedex.details.data.datasource.remote

import com.example.pokedex.details.data.api.model.PokemonDamageRelationsResponse
import com.example.pokedex.details.data.api.model.PokemonEvolutionResponse
import com.example.pokedex.details.data.api.model.PokemonSpecieResponse
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsRemoteDataSource {
    fun searchPokemonSpecie(id: Int): Flow<PokemonSpecieResponse>
    fun searchPokemonEvolution(url: String): Flow<PokemonEvolutionResponse>
    fun searchDamageRelations(name: String): Flow<PokemonDamageRelationsResponse>
}