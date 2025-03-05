package com.example.pokedex.details.view.viewmodel

import com.example.pokedex.details.view.model.PokemonDetailsUi

sealed class PokemonDetailsUiState {
    data object Loading : PokemonDetailsUiState()
    data class Success(val pokemonDetailsUi: PokemonDetailsUi) : PokemonDetailsUiState()
    data object Error : PokemonDetailsUiState()
}