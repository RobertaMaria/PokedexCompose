package com.example.pokedex.list.view.viewmodel

import androidx.paging.PagingData
import com.example.pokedex.common.model.TypeColoursEnum
import com.example.pokedex.list.view.model.PokemonListUi
import kotlinx.coroutines.flow.StateFlow

data class PokemonListUiState(
    val list: StateFlow<PagingData<PokemonListUi>>,
    val searchText: String = "",
    val isInitialLoad: Boolean = true,
    val selectedTypes: List<String> = emptyList(),
    val availableTypes: List<String> = TypeColoursEnum.entries.map { it.typeName }
)