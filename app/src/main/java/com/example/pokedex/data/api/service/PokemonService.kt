package com.example.pokedex.data.api.service

import com.example.pokedex.data.api.model.PokemonDetailsResponse
import com.example.pokedex.data.api.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
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