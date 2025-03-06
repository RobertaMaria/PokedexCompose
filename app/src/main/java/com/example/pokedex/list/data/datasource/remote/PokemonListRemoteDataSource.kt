package com.example.pokedex.list.data.datasource.remote

import com.example.pokedex.list.data.api.model.PokemonDetailsResponse
import com.example.pokedex.list.data.api.model.PokemonListResponse

interface PokemonListRemoteDataSource {
    suspend fun getListPokemon(
        limit: Int,
        offset: Int
    ): PokemonListResponse

    suspend fun getDetailsPokemon(
        id: Int
    ): PokemonDetailsResponse
}