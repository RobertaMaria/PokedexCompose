package com.example.pokedex.details.view.viewmodel

import com.example.pokedex.details.view.model.PokemonDetailsUi

data class PokemonDetailsUiState (
    val pokemonDetailsUi: PokemonDetailsUi = PokemonDetailsUi(),
    val isLoading: Boolean = false,
)