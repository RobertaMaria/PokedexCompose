package com.example.pokedex.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.pokedex.list.domain.usecase.GetPokemonListUseCase
import com.example.pokedex.list.view.factory.PokemonListFactory
import com.example.pokedex.list.view.model.PokemonListUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val factory: PokemonListFactory
) : ViewModel() {

    private val _listPokemon =
        MutableStateFlow<PagingData<PokemonListUi>>(PagingData.from(emptyList()))
    private val _uiState = MutableStateFlow(PokemonListUiState(_listPokemon.asStateFlow()))
    val uiState = _uiState.asStateFlow()

    init {
        getPokemonList()
    }

    fun setSearchText(text: String) {
        _uiState.value = _uiState.value.copy(searchText = text, isInitialLoad = false)
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModelScope.launch {
            val searchText = _uiState.value.searchText
            val isInitialLoad = _uiState.value.isInitialLoad
            val searchId = searchText.toIntOrNull()

            getPokemonListUseCase(
                searchText = searchText,
                searchId = searchId,
                isInitialLoad = isInitialLoad
            )
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _listPokemon.value = pagingData.map { factory(it) }
                }
        }
    }
}