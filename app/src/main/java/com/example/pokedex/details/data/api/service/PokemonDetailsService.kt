package com.example.pokedex.details.data.api.service

import com.example.pokedex.details.data.api.model.PokemonEvolutionResponse
import com.example.pokedex.details.data.api.model.PokemonSpecieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokemonDetailsService {

    @GET("pokemon-species/{id}")
    suspend fun searchPokemonSpecie(@Path("id") id: Int): PokemonSpecieResponse

    @GET
    suspend fun searchPokemonEvolution(@Url url: String): PokemonEvolutionResponse
}