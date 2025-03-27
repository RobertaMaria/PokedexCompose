package com.example.pokedex.list.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.pokedex.list.data.mediator.PokemonListRemoteMediator
import com.example.pokedex.list.data.database.dao.PokemonDao
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.mapper.PokemonListMapper
import com.example.pokedex.list.domain.model.PokemonList
import com.example.pokedex.list.domain.repository.PokemonListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonListRepositoryImpl(
    private val pokemonListRemoteMediator: PokemonListRemoteMediator,
    private val pokemonDao: PokemonDao,
    private val mapper: PokemonListMapper
) : PokemonListRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun searchPokemonList(
        searchText: String,
        searchId: Int?,
        isInitialLoad: Boolean
    ): Flow<PagingData<PokemonList>> {

        val pagingSourceFactory = searchId?.let { { pokemonDao.getPagingSourceById(it) } }
            ?: if (searchText.isNotBlank()) {
                { pokemonDao.getPokemonByName(searchText) }
            } else {
                { pokemonDao.getAll() }
            }
        val remoteMediator = if (isInitialLoad) pokemonListRemoteMediator else null

        return Pager(
            config = PagingConfig(pageSize = Int.MAX_VALUE, enablePlaceholders = false),
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map(::mapPagingData)
    }

    private fun mapPagingData(pagingData: PagingData<PokemonEntity>): PagingData<PokemonList> {
        return pagingData.map { mapper.mapToDomain(it) }
    }
}