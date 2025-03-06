package com.example.pokedex.list.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.pokedex.list.data.mediator.PokemonListRemoteMediator
import com.example.pokedex.list.data.database.dao.PokemonDao
import com.example.pokedex.list.data.mapper.PokemonListMapper
import com.example.pokedex.list.domain.model.PokemonList
import com.example.pokedex.list.domain.repository.PokemonListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonListRepositoryImpl(
    private val pokemonListRemoteMediator: PokemonListRemoteMediator,
    private val pokemonDao: PokemonDao,
    private val mapper: PokemonListMapper
) :
    PokemonListRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun listPokemon(): Flow<PagingData<PokemonList>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = pokemonListRemoteMediator,
            pagingSourceFactory = { pokemonDao.getAll() }
        ).flow.map { pagingData ->
            pagingData.map {
                mapper.mapToDomain(it)
            }
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 300
    }
}