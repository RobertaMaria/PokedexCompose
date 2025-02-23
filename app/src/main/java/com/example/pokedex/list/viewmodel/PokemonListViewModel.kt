package com.example.pokedex.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.pokedex.domain.usecase.GetPokemonListUseCase
import com.example.pokedex.list.factory.PokemonListFactory
import com.example.pokedex.list.view.model.PokemonUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val factory: PokemonListFactory
) : ViewModel() {

    private val _listPokemon = MutableStateFlow<PagingData<PokemonUi>>(PagingData.from(emptyList()))
    private val _uiState = MutableStateFlow(PokemonListUiState(_listPokemon.asStateFlow()))
    val uiState = _uiState.asStateFlow()

    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModelScope.launch {
            getPokemonListUseCase().cachedIn(viewModelScope).collect { pageData ->
                _listPokemon.value = pageData.map { pokemon ->
                    factory(pokemon)
                }
            }
        }
    }
}