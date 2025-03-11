package com.example.pokedex.details.data.repository

import com.example.pokedex.details.data.api.model.PokemonDamageRelationsResponse
import com.example.pokedex.details.data.datasource.local.PokemonDetailsLocalDataSource
import com.example.pokedex.details.data.datasource.remote.PokemonDetailsRemoteDataSource
import com.example.pokedex.details.data.mapper.PokemonDetailsMapper
import com.example.pokedex.details.domain.model.PokemonDetails
import com.example.pokedex.details.domain.repository.PokemonDetailsRepository
import com.example.pokedex.list.data.database.entity.PokemonEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class PokemonDetailsRepositoryImp(
    private val remoteDataSource: PokemonDetailsRemoteDataSource,
    private val localDataSource: PokemonDetailsLocalDataSource,
    private val mapper: PokemonDetailsMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PokemonDetailsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPokemonDetail(id: Int): Flow<PokemonDetails> = flow {
        val localSpecie = localDataSource.getPokemonSpecie(id)
        val localPokemon = localDataSource.getPokemonEntity(id)

        if (localSpecie != null && localPokemon != null) {
            emit(mapper.mapToDomain(localSpecie.species, localPokemon, localSpecie.evolution))
            return@flow
        }

        remoteDataSource.searchPokemonSpecie(id)
            .flatMapConcat { remoteSpecie ->
                fetchDamageRelations(localPokemon).flatMapConcat { remoteDamageRelations ->
                    val specieEntity =
                        mapper.mapToSpecieEntity(remoteSpecie, id, remoteDamageRelations)
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
            }
            .mapNotNull { (specieEntity, pokemonEntity, evolutions) ->
                pokemonEntity?.let {
                    mapper.mapToDomain(specieEntity, it, evolutions)
                }
            }
            .collect { pokemonDetails ->
                emit(pokemonDetails)
            }
    }.catch { exception ->
        emit(throw Exception(exception.message))
    }.flowOn(ioDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchDamageRelations(
        localPokemon: PokemonEntity?
    ): Flow<Pair<List<String>, List<String>>> = flow {
        val result = localPokemon?.type?.asFlow()?.flatMapMerge { typeName ->
            remoteDataSource.searchDamageRelations(typeName).map { damageRelations ->
                calculateWeaknesses(damageRelations)
            }
        }?.fold(Pair(emptyList<String>(), emptyList<String>())) { acc, pair ->
            Pair(acc.first + pair.first, acc.second + pair.second)
        }?.let {
            Pair(it.first.distinct(), it.second.distinct())
        } ?: Pair(emptyList(), emptyList())
        emit(result)
    }.flowOn(ioDispatcher)

    private fun calculateWeaknesses(
        damageRelations: PokemonDamageRelationsResponse
    ): Pair<List<String>, List<String>> {
        val doubleDamage = damageRelations.damageRelations.doubleDamageFrom.map { it.name }
        val noDamage = damageRelations.damageRelations.noDamageFrom.map { it.name }
        return Pair(doubleDamage, noDamage)
    }
}