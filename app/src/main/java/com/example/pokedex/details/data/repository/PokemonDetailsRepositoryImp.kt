package com.example.pokedex.details.data.repository

import com.example.pokedex.details.data.api.model.PokemonDamageRelationsResponse
import com.example.pokedex.details.data.api.model.PokemonSpecieResponse
import com.example.pokedex.details.data.datasource.local.PokemonDetailsLocalDataSource
import com.example.pokedex.details.data.datasource.remote.PokemonDetailsRemoteDataSource
import com.example.pokedex.details.data.mapper.PokemonDetailsMapper
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity
import com.example.pokedex.details.domain.model.PokemonDetails
import com.example.pokedex.details.domain.repository.PokemonDetailsRepository
import com.example.pokedex.details.data.translator.PokemonTranslator
import com.example.pokedex.details.data.translator.PokemonTranslatorImpl
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

class PokemonDetailsRepositoryImp(
    private val remoteDataSource: PokemonDetailsRemoteDataSource,
    private val localDataSource: PokemonDetailsLocalDataSource,
    private val mapper: PokemonDetailsMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val translator: PokemonTranslator = PokemonTranslatorImpl()
) : PokemonDetailsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPokemonDetail(id: Int): Flow<PokemonDetails> = flow {
        val localResult = getPokemonLocal(id)
        if (localResult != null) {
            emit(localResult)
            return@flow
        }

        fetchRemotePokemonDetail(id)
            .collect { pokemonDetails ->
                emit(pokemonDetails)
            }
    }.catch { exception ->
        emit(throw Exception(exception.message))
    }.flowOn(ioDispatcher)

    private fun getPokemonLocal(id: Int): PokemonDetails? {
        val localSpecie = localDataSource.getPokemonSpecie(id) ?: return null
        val localPokemon = localDataSource.getPokemonEntity(id) ?: return null

        return mapper.mapToDomain(
            specieEntity = localSpecie.species,
            pokemonEntity = localPokemon,
            evolutionEntity = localSpecie.evolution
        )
    }

    private fun getOrFetchPokemonEntity(id: Int): Flow<PokemonEntity> = flow {
        val localPokemon = localDataSource.getPokemonEntity(id)
        if (localPokemon != null) {
            emit(localPokemon)
        } else {
            remoteDataSource.searchPokemonDetails(id)
                .collect { response ->
                    val entity = mapper.mapDetailsResponseToPokemonEntity(response)
                    localDataSource.savePokemonEntity(entity)
                    emit(entity)
                }
        }
    }.flowOn(ioDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchDamageRelations(
        pokemonEntity: PokemonEntity
    ): Flow<Pair<List<String>, List<String>>> = flow {
        val result = pokemonEntity.type.asFlow().flatMapMerge { typeName ->
            remoteDataSource.searchDamageRelations(typeName).map { damageRelations ->
                calculateWeaknesses(damageRelations)
            }
        }.fold(Pair(emptyList<String>(), emptyList<String>())) { acc, pair ->
            Pair(acc.first + pair.first, acc.second + pair.second)
        }.let {
            Pair(it.first.distinct(), it.second.distinct())
        }
        emit(result)
    }.flowOn(ioDispatcher)

    private fun calculateWeaknesses(
        damageRelations: PokemonDamageRelationsResponse
    ): Pair<List<String>, List<String>> {
        val doubleDamage = damageRelations.damageRelations.doubleDamageFrom.map { it.name }
        val noDamage = damageRelations.damageRelations.noDamageFrom.map { it.name }
        return Pair(doubleDamage, noDamage)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchRemotePokemonDetail(id: Int): Flow<PokemonDetails> =
        getOrFetchPokemonEntity(id)
            .flatMapConcat { pokemonEntity ->
                remoteDataSource.searchPokemonSpecie(id)
                    .flatMapConcat { remoteSpecie ->
                        fetchAndBuildPokemonDetail(id, remoteSpecie, pokemonEntity)
                    }
            }
            .flowOn(ioDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchAndBuildPokemonDetail(
        id: Int,
        remoteSpecie: PokemonSpecieResponse,
        pokemonEntity: PokemonEntity
    ): Flow<PokemonDetails> {
        return fetchDamageRelations(pokemonEntity)
            .flatMapConcat { remoteDamageRelations ->
                buildAndSavePokemonData(id, remoteSpecie, remoteDamageRelations, pokemonEntity)
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun buildAndSavePokemonData(
        id: Int,
        remoteSpecie: PokemonSpecieResponse,
        remoteDamageRelations: Pair<List<String>, List<String>>,
        pokemonEntity: PokemonEntity
    ): Flow<PokemonDetails> = flow {
        val rawEnglishDescription = mapper.extractEnglishFlavorText(remoteSpecie)
        val translatedDescription = translator.translate(rawEnglishDescription)

        val specieEntity = mapper.mapToSpecieEntity(
            response = remoteSpecie,
            id = id,
            damageRelations = remoteDamageRelations,
            translatedDescription = translatedDescription
        )
        localDataSource.savePokemonSpecie(specieEntity)

        fetchAndSaveEvolutions(remoteSpecie)
            .collect { evolutions ->
                mapToPokemonDetails(specieEntity, pokemonEntity, evolutions)
                    .collect { pokemonDetails ->
                        emit(pokemonDetails)
                    }
            }
    }.flowOn(ioDispatcher)

    private fun fetchAndSaveEvolutions(remoteSpecie: PokemonSpecieResponse): Flow<PokemonEvolutionEntity?> {
        return remoteDataSource.searchPokemonEvolution(remoteSpecie.chainResponse.url)
            .map { remoteEvolution ->
                val evolutions = mapper.mapperToEvolutionsEntity(
                    remoteEvolution.chain,
                    remoteSpecie.chainResponse.url
                )
                localDataSource.saveEvolutions(evolutions)
                evolutions
            }
    }

    private fun mapToPokemonDetails(
        specieEntity: PokemonSpecieEntity,
        pokemonEntity: PokemonEntity,
        evolutionEntity: PokemonEvolutionEntity?
    ): Flow<PokemonDetails> = flow {
        val evolution = evolutionEntity ?: PokemonEvolutionEntity(evolutions = emptyList())
        val pokemonDetails = mapper.mapToDomain(specieEntity, pokemonEntity, evolution)
        emit(pokemonDetails)
    }
}
