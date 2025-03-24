package com.example.pokedex.list.domain.usecase

import androidx.paging.PagingData
import com.example.pokedex.list.domain.repository.PokemonListRepository
import com.example.pokedex.list.domain.model.PokemonList
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(private val pokemonListRepository: PokemonListRepository) {
    operator fun invoke(
        searchName: String,
        searchId: Int?
    ): Flow<PagingData<PokemonList>> {
        return pokemonListRepository.listPokemon(searchName, searchId)
    }
}