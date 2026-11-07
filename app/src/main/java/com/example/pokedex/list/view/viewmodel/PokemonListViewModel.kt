package com.example.pokedex.list.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.pokedex.common.database.dao.FavoriteDao
import com.example.pokedex.common.database.entity.FavoriteEntity
import com.example.pokedex.list.data.mapper.PokemonListMapper
import com.example.pokedex.list.domain.usecase.GetPokemonListUseCase
import com.example.pokedex.list.view.factory.PokemonListFactory
import com.example.pokedex.list.view.model.PokemonListUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val factory: PokemonListFactory,
    private val favoriteDao: FavoriteDao,
    private val mapper: PokemonListMapper
) : ViewModel() {

    private val _homePokemonList =
        MutableStateFlow<PagingData<PokemonListUi>>(PagingData.from(emptyList()))
    val homePokemonList: StateFlow<PagingData<PokemonListUi>> = _homePokemonList.asStateFlow()

    private val _searchPokemonList =
        MutableStateFlow<PagingData<PokemonListUi>>(PagingData.from(emptyList()))
    val searchPokemonList: StateFlow<PagingData<PokemonListUi>> = _searchPokemonList.asStateFlow()

    private val _uiState = MutableStateFlow(PokemonListUiState(_homePokemonList.asStateFlow()))
    val uiState = _uiState.asStateFlow()

    private val _favoritePokemons = MutableStateFlow<List<PokemonListUi>>(emptyList())
    val favoritePokemons: StateFlow<List<PokemonListUi>> = _favoritePokemons.asStateFlow()

    init {
        getHomePokemonList()
        getSearchPokemonList()
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoriteDao.getFavoritePokemons().collect { entities ->
                _favoritePokemons.value = entities.map { entity ->
                    factory(mapper.mapToDomain(entity))
                }
            }
        }
    }

    fun removeFavorite(pokemonId: Int) {
        viewModelScope.launch {
            favoriteDao.deleteFavorite(FavoriteEntity(pokemonId))
        }
    }

    fun setSearchText(text: String) {
        _uiState.value = _uiState.value.copy(searchText = text, isInitialLoad = false)
        getSearchPokemonList()
    }

    fun toggleSelectedType(type: String) {
        val currentTypes = _uiState.value.selectedTypes.toMutableList()
        if (currentTypes.contains(type)) {
            currentTypes.remove(type)
        } else {
            currentTypes.add(type)
        }
        _uiState.value = _uiState.value.copy(selectedTypes = currentTypes, isInitialLoad = false)
        getSearchPokemonList()
    }

    fun clearFilters() {
        _uiState.value = _uiState.value.copy(selectedTypes = emptyList(), isInitialLoad = false)
        getSearchPokemonList()
    }

    private fun getHomePokemonList() {
        viewModelScope.launch {
            getPokemonListUseCase(
                searchText = "",
                searchId = null,
                selectedTypes = emptyList(),
                isInitialLoad = true
            )
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _homePokemonList.value = pagingData.map { factory(it) }
                }
        }
    }

    private fun getSearchPokemonList() {
        viewModelScope.launch {
            val searchText = _uiState.value.searchText
            val selectedTypes = _uiState.value.selectedTypes
            val isInitialLoad = _uiState.value.isInitialLoad
            val searchId = searchText.toIntOrNull()

            getPokemonListUseCase(
                searchText = searchText,
                searchId = searchId,
                selectedTypes = selectedTypes,
                isInitialLoad = isInitialLoad
            )
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _searchPokemonList.value = pagingData.map { factory(it) }
                }
        }
    }
}