package com.example.pokedex.details.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.details.domain.usecase.GetPokemonDetailsUseCase
import com.example.pokedex.details.view.factory.PokemonDetailsFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val id: Int,
    private val useCase: GetPokemonDetailsUseCase,
    private val factory: PokemonDetailsFactory
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onPokemonClicked()
    }

    private fun onPokemonClicked() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            useCase.invoke(id)
                .collect { pokemonDetails ->
                    val pokemonDetailsUi = factory.invoke(pokemonDetails = pokemonDetails)
                    _uiState.value = _uiState.value.copy(
                        pokemonDetailsUi = pokemonDetailsUi,
                        isLoading = false
                    )
                }
        }
    }
}