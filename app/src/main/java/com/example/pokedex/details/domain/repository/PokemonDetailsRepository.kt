package com.example.pokedex.details.domain.repository

import com.example.pokedex.details.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsRepository {
    fun getPokemonDetail(id: Int): Flow<PokemonDetails>
}