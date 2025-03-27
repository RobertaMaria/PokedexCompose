package com.example.pokedex.list.domain.usecase

import androidx.paging.PagingData
import com.example.pokedex.list.domain.repository.PokemonListRepository
import com.example.pokedex.list.domain.model.PokemonList
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(private val pokemonListRepository: PokemonListRepository) {
    operator fun invoke(
        searchText: String,
        searchId: Int?,
        isInitialLoad: Boolean
    ): Flow<PagingData<PokemonList>> {
        return pokemonListRepository.searchPokemonList(
            searchText = searchText,
            searchId = searchId,
            isInitialLoad = isInitialLoad
        )
    }
}