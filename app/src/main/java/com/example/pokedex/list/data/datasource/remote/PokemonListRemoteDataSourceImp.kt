package com.example.pokedex.list.data.datasource.remote

import com.example.pokedex.list.data.api.model.PokemonDetailsResponse
import com.example.pokedex.list.data.api.model.PokemonListResponse
import com.example.pokedex.list.data.api.service.PokemonListService

class PokemonListRemoteDataSourceImp(private val service: PokemonListService): PokemonListRemoteDataSource {
    override suspend fun getListPokemon(limit: Int, offset: Int): PokemonListResponse {
        return service.getListPokemon(limit, offset)
    }

    override suspend fun getDetailsPokemon(id: Int): PokemonDetailsResponse {
        return service.getDetailsPokemon(id)
    }
}