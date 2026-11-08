package com.example.pokedex.list.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.database.entity.PokemonStats
import com.example.pokedex.list.data.database.entity.Stat
import com.example.pokedex.list.data.datasource.local.PokemonListLocalDataSource
import com.example.pokedex.list.data.datasource.remote.PokemonListRemoteDataSource
import com.example.pokedex.list.data.mapper.PokemonListMapper
import com.example.pokedex.list.data.mediator.PokemonListRemoteMediator
import com.example.pokedex.list.domain.model.PokemonList
import com.example.pokedex.list.domain.repository.PokemonListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PokemonListRepositoryImpl(
    private val pokemonListRemoteMediator: PokemonListRemoteMediator,
    private val localDataSource: PokemonListLocalDataSource,
    private val mapper: PokemonListMapper,
    private val remoteDataSource: PokemonListRemoteDataSource? = null
) : PokemonListRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun searchPokemonList(
        searchText: String,
        searchId: Int?,
        selectedTypes: List<String>,
        isInitialLoad: Boolean
    ): Flow<PagingData<PokemonList>> {
        val pagingSourceFactory = getPagingSourceFactory(searchId, searchText, selectedTypes)
        val remoteMediator = if (isInitialLoad) pokemonListRemoteMediator else null

        if (searchId != null || searchText.isNotBlank()) {
            val query = searchId?.toString() ?: searchText
            fetchRemotePokemonIfMissing(query)
        }

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = true
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map(::mapPagingData)
    }

    private var searchJob: Job? = null

    private fun fetchRemotePokemonIfMissing(query: String) {
        val remote = remoteDataSource ?: return
        searchJob?.cancel()
        searchJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemon = remote.getDetailsPokemonByNameOrId(query.trim().lowercase())
                val existingPokemon = localDataSource.getPokemonById(pokemon.id)
                if (existingPokemon == null) {
                    val pokemonEntity = PokemonEntity(
                        id = pokemon.id,
                        name = pokemon.name,
                        image = pokemon.image.other.officialImage.imageDefault,
                        height = pokemon.height,
                        weight = pokemon.weight,
                        type = pokemon.types.map { it.type.typeName },
                        stats = pokemon.stats.map { PokemonStats(it.baseStat, Stat(it.stat.statName)) },
                        abilities = pokemon.abilities.map { it.ability.abilityName }
                    )
                    localDataSource.insertAll(listOf(pokemonEntity))
                }
            } catch (_: Exception) {
                // Not found or network error
            }
        }
    }

    private fun getPagingSourceFactory(
        searchId: Int?,
        searchText: String,
        selectedTypes: List<String>
    ) = when {
        searchId != null -> {
            {
                localDataSource.getTypeQueryWithId(
                    searchId = searchId,
                    selectedTypes = selectedTypes
                )
            }
        }

        searchText.isNotBlank() -> {
            {
                localDataSource.getTypeQueryWithName(
                    searchName = searchText,
                    selectedTypes = selectedTypes
                )

            }
        }

        selectedTypes.isNotEmpty() -> {
            { localDataSource.getByDynamicTypes(selectedTypes = selectedTypes) }
        }

        else -> {
            { localDataSource.getAllPokemon() }
        }
    }

    private fun mapPagingData(pagingData: PagingData<PokemonEntity>): PagingData<PokemonList> {
        return pagingData.map { mapper.mapToDomain(it) }
    }
}
