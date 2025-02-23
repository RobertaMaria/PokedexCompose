package com.example.pokedex.domain.repository

import androidx.paging.PagingData
import com.example.pokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

fun interface ListPokemonRepository {
    fun listPokemon(): Flow<PagingData<Pokemon>>
}