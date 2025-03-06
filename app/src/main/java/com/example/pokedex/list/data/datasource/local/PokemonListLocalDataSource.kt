package com.example.pokedex.list.data.datasource.local

import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.database.entity.RemoteKeysEntity

interface PokemonListLocalDataSource {
    fun insertAll(list: List<PokemonEntity>)
    suspend fun getRemoteKeysId(item: Int): RemoteKeysEntity?
    suspend fun saveAllRemoteKey(remoteKeys: List<RemoteKeysEntity>)
}