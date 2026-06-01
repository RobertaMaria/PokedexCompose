package com.example.pokedex.list.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.pokedex.list.data.mediator.PokemonListRemoteMediator
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.datasource.local.PokemonListLocalDataSource
import com.example.pokedex.list.data.mapper.PokemonListMapper
import com.example.pokedex.list.domain.model.PokemonList
import com.example.pokedex.list.domain.repository.PokemonListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonListRepositoryImpl(
    private val pokemonListRemoteMediator: PokemonListRemoteMediator,
    private val localDataSource: PokemonListLocalDataSource,
    private val mapper: PokemonListMapper
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

        return Pager(
            config = PagingConfig(pageSize = Int.MAX_VALUE, enablePlaceholders = false),
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map(::mapPagingData)
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