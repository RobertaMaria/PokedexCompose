package com.example.pokedex.details.data.datasource.remote

import com.example.pokedex.details.data.api.model.PokemonDamageRelationsResponse
import com.example.pokedex.details.data.api.model.PokemonEvolutionResponse
import com.example.pokedex.details.data.api.model.PokemonSpecieResponse
import com.example.pokedex.details.data.api.service.PokemonDetailsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonDetailsRemoteDataSourceImp(private val service: PokemonDetailsService) :
    PokemonDetailsRemoteDataSource {
    override fun searchPokemonSpecie(id: Int): Flow<PokemonSpecieResponse> {
        return flow {
            emit(service.searchPokemonSpecie(id = id))
        }
    }

    override fun searchPokemonEvolution(url: String): Flow<PokemonEvolutionResponse> {
        return flow { emit(service.searchPokemonEvolution(url = url)) }
    }

    override fun searchDamageRelations(name: String): Flow<PokemonDamageRelationsResponse> {
        return flow { emit(service.searchDamageRelations(name = name)) }
    }
}