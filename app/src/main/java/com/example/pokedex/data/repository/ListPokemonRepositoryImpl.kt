package com.example.pokedex.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.pokedex.data.mediator.PokemonRemoteMediator
import com.example.pokedex.data.database.dao.PokemonDao
import com.example.pokedex.data.mapper.PokemonMapper
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.repository.ListPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListPokemonRepositoryImpl(
    private val pokemonRemoteMediator: PokemonRemoteMediator,
    private val pokemonDao: PokemonDao,
    private val mapper: PokemonMapper
) :
    ListPokemonRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun listPokemon(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = pokemonRemoteMediator,
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