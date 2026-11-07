package com.example.pokedex.details.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.common.database.dao.FavoriteDao
import com.example.pokedex.common.database.entity.FavoriteEntity
import com.example.pokedex.details.domain.usecase.GetPokemonDetailsUseCase
import com.example.pokedex.details.view.factory.PokemonDetailsFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private var id: Int,
    private val useCase: GetPokemonDetailsUseCase,
    private val factory: PokemonDetailsFactory,
    private val favoriteDao: FavoriteDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailsUiState>(PokemonDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        onPokemonClicked()
        observeFavoriteStatus()
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            favoriteDao.isFavorite(id).collect { isFav ->
                val currentState = _uiState.value
                if (currentState is PokemonDetailsUiState.Success) {
                    _uiState.value = PokemonDetailsUiState.Success(
                        currentState.pokemonDetailsUi.copy(isFavorite = isFav)
                    )
                }
            }
        }
    }

    private fun onPokemonClicked() {
        viewModelScope.launch {
            useCase(id)
                .catch {
                    _uiState.value = PokemonDetailsUiState.Error
                }
                .collect { pokemonDetails ->
                    val pokemonDetailsUi =
                        factory.invoke(pokemonDetails = pokemonDetails).copy(isLoaded = false)
                    _uiState.value = PokemonDetailsUiState.Success(pokemonDetailsUi)
                    observeFavoriteStatus()
                }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is PokemonDetailsUiState.Success) {
                val currentFav = currentState.pokemonDetailsUi.isFavorite
                if (currentFav) {
                    favoriteDao.deleteFavorite(FavoriteEntity(id))
                } else {
                    favoriteDao.insertFavorite(FavoriteEntity(id))
                }
            }
        }
    }

    fun onEvolutionClicked(evolutionId: Int) {
        id = evolutionId
        updateIsLoaded()
        onPokemonClicked()
        observeFavoriteStatus()
    }

    private fun updateIsLoaded() {
        _uiState.value = when (val currentState = _uiState.value) {
            is PokemonDetailsUiState.Success -> {
                val updatedDetailsUi = currentState.pokemonDetailsUi.copy(isLoaded = true)
                PokemonDetailsUiState.Success(updatedDetailsUi)
            }
            else -> currentState
        }
    }
}