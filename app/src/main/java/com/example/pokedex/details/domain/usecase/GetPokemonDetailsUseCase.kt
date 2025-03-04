package com.example.pokedex.details.domain.usecase

import com.example.pokedex.details.domain.model.PokemonDetails
import com.example.pokedex.details.domain.repository.PokemonDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonDetailsUseCase(private val repository: PokemonDetailsRepository) {

    operator fun invoke(id: Int): Flow<PokemonDetails> {
        return repository.getPokemonDetail(id)
    }
}