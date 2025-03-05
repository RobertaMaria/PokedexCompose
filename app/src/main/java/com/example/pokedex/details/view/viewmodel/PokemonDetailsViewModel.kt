package com.example.pokedex.details.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.details.domain.usecase.GetPokemonDetailsUseCase
import com.example.pokedex.details.view.factory.PokemonDetailsFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private var id: Int,
    private val useCase: GetPokemonDetailsUseCase,
    private val factory: PokemonDetailsFactory
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailsUiState>(PokemonDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        onPokemonClicked()
    }

    private fun onPokemonClicked() {
        viewModelScope.launch {
            useCase.invoke(id)
                .catch { PokemonDetailsUiState.Error }
                .collect { pokemonDetails ->
                    val pokemonDetailsUi =
                        factory.invoke(pokemonDetails = pokemonDetails).copy(isLoaded = false)
                    _uiState.value = PokemonDetailsUiState.Success(pokemonDetailsUi)
                }
        }
    }

    fun onEvolutionClicked(evolutionId: Int) {
        id = evolutionId
        updateIsLoaded()
        onPokemonClicked()
    }

    private fun updateIsLoaded() {
        if (uiState.value is PokemonDetailsUiState.Success) {
            val currentSuccessState = uiState.value as PokemonDetailsUiState.Success
            val updatedDetailsUi = currentSuccessState.pokemonDetailsUi.copy(isLoaded = true)
            _uiState.value = PokemonDetailsUiState.Success(updatedDetailsUi)
        }
    }
}