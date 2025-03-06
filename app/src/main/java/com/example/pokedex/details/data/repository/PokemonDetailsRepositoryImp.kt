package com.example.pokedex.details.data.repository

import com.example.pokedex.details.data.datasource.local.PokemonDetailsLocalDataSource
import com.example.pokedex.details.data.datasource.remote.PokemonDetailsRemoteDataSource
import com.example.pokedex.details.data.mapper.PokemonDetailsMapper
import com.example.pokedex.details.domain.model.PokemonDetails
import com.example.pokedex.details.domain.repository.PokemonDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PokemonDetailsRepositoryImp(
    private val remoteDataSource: PokemonDetailsRemoteDataSource,
    private val localDataSource: PokemonDetailsLocalDataSource,
    private val mapper: PokemonDetailsMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PokemonDetailsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPokemonDetail(id: Int): Flow<PokemonDetails> = flow {
        try {
            val localSpecie = localDataSource.getPokemonSpecie(id = id)
            val localPokemon = localDataSource.getPokemonEntity(pokemonId = id)

            if (localSpecie != null && localPokemon != null) {
                emit(mapper.mapToDomain(localSpecie.species, localPokemon, localSpecie.evolution))
                return@flow
            }

            remoteDataSource.searchPokemonSpecie(id)
                .flatMapConcat { remoteSpecie ->
                    val specieEntity = mapper.mapToEntity(remoteSpecie, id)
                    localDataSource.savePokemonSpecie(specieEntity)

                    remoteDataSource.searchPokemonEvolution(remoteSpecie.chainResponse.url)
                        .map { remoteEvolution ->
                            val evolutions = mapper.mapperToEvolutionsEntity(
                                remoteEvolution.chain,
                                remoteSpecie.chainResponse.url
                            )
                            localDataSource.saveEvolutions(evolutions)
                            Triple(specieEntity, localPokemon, evolutions)
                        }
                }
                .collect { (specieEntity, localPokemon, evolutions) ->
                    localPokemon?.let {
                        emit(mapper.mapToDomain(specieEntity, localPokemon, evolutions))
                    } ?: run {
                        throw Exception()
                    }
                }


        } catch (e: Exception) {
            throw Exception()
        }
    }.flowOn(ioDispatcher)
}