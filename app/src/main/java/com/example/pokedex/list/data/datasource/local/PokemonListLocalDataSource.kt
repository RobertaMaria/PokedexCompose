package com.example.pokedex.list.data.datasource.local

import androidx.paging.PagingSource
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.database.entity.RemoteKeysEntity

interface PokemonListLocalDataSource {
    fun insertAll(list: List<PokemonEntity>)
    suspend fun getRemoteKeysId(item: Int): RemoteKeysEntity?
    suspend fun saveAllRemoteKey(remoteKeys: List<RemoteKeysEntity>)
    fun getTypeQueryWithId(
        searchId: Int,
        selectedTypes: List<String>
    ): PagingSource<Int, PokemonEntity>

    fun getTypeQueryWithName(
        searchName: String,
        selectedTypes: List<String>
    ): PagingSource<Int, PokemonEntity>

    fun getByDynamicTypes(selectedTypes: List<String>): PagingSource<Int, PokemonEntity>
    fun getAllPokemon(): PagingSource<Int, PokemonEntity>
}