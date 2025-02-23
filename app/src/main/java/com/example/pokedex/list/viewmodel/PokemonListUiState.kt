package com.example.pokedex.list.viewmodel

import androidx.paging.PagingData
import com.example.pokedex.list.view.model.PokemonUi
import kotlinx.coroutines.flow.StateFlow

data class PokemonListUiState(
    val list: StateFlow<PagingData<PokemonUi>>
)