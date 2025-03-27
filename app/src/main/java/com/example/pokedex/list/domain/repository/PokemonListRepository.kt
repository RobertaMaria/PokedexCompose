package com.example.pokedex.list.domain.repository

import androidx.paging.PagingData
import com.example.pokedex.list.domain.model.PokemonList
import kotlinx.coroutines.flow.Flow

fun interface PokemonListRepository {
    fun searchPokemonList(
        searchText: String,
        searchId: Int?,
        isInitialLoad: Boolean
    ): Flow<PagingData<PokemonList>>
}