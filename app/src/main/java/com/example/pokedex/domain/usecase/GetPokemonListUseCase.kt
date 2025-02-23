package com.example.pokedex.domain.usecase

import androidx.paging.PagingData
import com.example.pokedex.domain.repository.ListPokemonRepository
import com.example.pokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(private val listPokemonRepository: ListPokemonRepository) {

    operator fun invoke(): Flow<PagingData<Pokemon>> {
        return listPokemonRepository.listPokemon()
    }
}