package com.example.pokedex.list.view.viewmodel

import com.example.pokedex.common.model.TypeColoursEnum

data class PokemonListUiState(
    val searchText: String = "",
    val isInitialLoad: Boolean = true,
    val selectedTypes: List<String> = emptyList(),
    val availableTypes: List<String> = TypeColoursEnum.entries.map { it.typeName }
)
