package com.example.pokedex.data.datasource.remote

import com.example.pokedex.data.api.model.PokemonDetailsResponse
import com.example.pokedex.data.api.model.PokemonListResponse
import com.example.pokedex.data.api.service.PokemonService

class PokemonRemoteDataSourceImp(private val service: PokemonService): PokemonRemoteDataSource {
    override suspend fun getListPokemon(limit: Int, offset: Int): PokemonListResponse {
        return service.getListPokemon(limit, offset)
    }

    override suspend fun getDetailsPokemon(id: Int): PokemonDetailsResponse {
        return service.getDetailsPokemon(id)
    }
}