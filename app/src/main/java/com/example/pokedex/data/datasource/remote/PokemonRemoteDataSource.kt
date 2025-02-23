package com.example.pokedex.data.datasource.remote

import com.example.pokedex.data.api.model.PokemonDetailsResponse
import com.example.pokedex.data.api.model.PokemonListResponse

interface PokemonRemoteDataSource {
    suspend fun getListPokemon(
        limit: Int,
        offset: Int
    ): PokemonListResponse

    suspend fun getDetailsPokemon(
        id: Int
    ): PokemonDetailsResponse
}