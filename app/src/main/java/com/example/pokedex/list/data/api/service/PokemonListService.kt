package com.example.pokedex.list.data.api.service

import com.example.pokedex.list.data.api.model.PokemonDetailsResponse
import com.example.pokedex.list.data.api.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonListService {
    @GET("pokemon")
    suspend fun getListPokemon(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getDetailsPokemon(
        @Path("id") id: Int
    ): PokemonDetailsResponse
}