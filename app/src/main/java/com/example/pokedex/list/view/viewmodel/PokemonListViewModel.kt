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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class SearchFilterState(
    val searchText: String = "",
    val selectedTypes: List<String> = emptyList(),
    val isInitialLoad: Boolean = true
)

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val factory: PokemonListFactory,
    private val favoriteDao: FavoriteDao,
    private val mapper: PokemonListMapper
) : ViewModel() {

    // 1. Home list: Flow encadeada diretamente com cachedIn(viewModelScope)
    val homePokemonList: Flow<PagingData<PokemonListUi>> = getPokemonListUseCase(
        searchText = "",
        searchId = null,
        selectedTypes = emptyList(),
        isInitialLoad = true
    )
        .map { pagingData -> pagingData.map { factory(it) } }
        .cachedIn(viewModelScope)

    // 2. Search list: Reativa via flatMapLatest e encadeada com cachedIn(viewModelScope)
    private val _searchFilterState = MutableStateFlow(SearchFilterState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchPokemonList: Flow<PagingData<PokemonListUi>> = _searchFilterState
        .flatMapLatest { filter ->
            getPokemonListUseCase(
                searchText = filter.searchText,
                searchId = filter.searchText.toIntOrNull(),
                selectedTypes = filter.selectedTypes,
                isInitialLoad = filter.isInitialLoad
            ).map { pagingData -> pagingData.map { factory(it) } }
        }
        .cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState = _uiState.asStateFlow()

    private val _favoritePokemons = MutableStateFlow<List<PokemonListUi>>(emptyList())
    val favoritePokemons: StateFlow<List<PokemonListUi>> = _favoritePokemons.asStateFlow()

    init {
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
        _searchFilterState.value = _searchFilterState.value.copy(searchText = text, isInitialLoad = false)
    }

    fun toggleSelectedType(type: String) {
        val currentTypes = _uiState.value.selectedTypes.toMutableList()
        if (currentTypes.contains(type)) {
            currentTypes.remove(type)
        } else {
            currentTypes.add(type)
        }
        _uiState.value = _uiState.value.copy(selectedTypes = currentTypes, isInitialLoad = false)
        _searchFilterState.value = _searchFilterState.value.copy(selectedTypes = currentTypes, isInitialLoad = false)
    }

    fun clearFilters() {
        _uiState.value = _uiState.value.copy(selectedTypes = emptyList(), isInitialLoad = false)
        _searchFilterState.value = _searchFilterState.value.copy(selectedTypes = emptyList(), isInitialLoad = false)
    }
}
