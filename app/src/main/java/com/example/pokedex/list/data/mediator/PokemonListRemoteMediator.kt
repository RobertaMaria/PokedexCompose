package com.example.pokedex.list.data.mediator

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.database.entity.PokemonStats
import com.example.pokedex.list.data.database.entity.RemoteKeysEntity
import com.example.pokedex.list.data.database.entity.Stat
import com.example.pokedex.list.data.datasource.local.PokemonListLocalDataSource
import com.example.pokedex.list.data.datasource.remote.PokemonListRemoteDataSource
import com.example.pokedex.list.data.mapper.PokemonListMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

private const val POKEMON_STARTING_PAGE_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class PokemonListRemoteMediator(
    private val remoteDataSource: PokemonListRemoteDataSource,
    private val localDataSource: PokemonListLocalDataSource,
    private val mapper: PokemonListMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteMediator<Int, PokemonEntity>() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return withContext(dispatcher) {
            getPage(loadType, state)?.let { page ->
                try {
                    val response = remoteDataSource.getListPokemon(state.config.pageSize, page)
                    val idList = mapper.mapToIdsPokemon(response)
                    val remoteKeysList = mapper.mapToRemotesKeyEntity(response)
                    val pokemonListEntities = updatePokemonList(idList)
                    localDataSource.insertAll(pokemonListEntities)
                    localDataSource.saveAllRemoteKey(remoteKeysList)
                    MediatorResult.Success(endOfPaginationReached = idList.isEmpty())
                } catch (exception: Exception) {
                    MediatorResult.Error(exception)
                }
            } ?: MediatorResult.Success(endOfPaginationReached = false)
        }
    }

    private suspend fun updatePokemonList(idList: List<Int>): List<PokemonEntity> {
        return coroutineScope {
            idList.map { id ->
                async(dispatcher) {
                    try {
                        val pokemon = remoteDataSource.getDetailsPokemon(id)
                        val pokemonEntity = PokemonEntity(
                            id = id,
                            name = pokemon.name,
                            image = pokemon.image.other.officialImage.imageDefault,
                            height = pokemon.height,
                            weight = pokemon.weight,
                            type = pokemon.types.map {
                                it.type.typeName
                            },
                            stats = pokemon.stats.map {
                                PokemonStats(
                                    baseStat = it.baseStat,
                                    stat = Stat(it.stat.statName)
                                )
                            }
                        )
                        Result.success(pokemonEntity)
                    } catch (e: Exception) {
                        Result.failure(e)
                    }
                }
            }.awaitAll().mapNotNull { it.getOrNull() }
        }
    }

    private suspend fun getPage(loadType: LoadType, state: PagingState<Int, PokemonEntity>): Int? {
        return when (loadType) {
            LoadType.REFRESH -> getRemoteKeyClosestToCurrentPosition(state)?.nextKey?.minus(1)
                ?: POKEMON_STARTING_PAGE_INDEX

            LoadType.APPEND -> getRemoteKeyForLastItem(state)?.nextKey

            LoadType.PREPEND -> getRemoteKeyForFirstItem(state)?.prevKey
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { item ->
                localDataSource.getRemoteKeysId(item)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                localDataSource.getRemoteKeysId(item.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                localDataSource.getRemoteKeysId(item.id)
            }
    }
}