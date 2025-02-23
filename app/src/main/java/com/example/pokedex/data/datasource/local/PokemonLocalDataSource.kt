package com.example.pokedex.data.datasource.local

import com.example.pokedex.data.database.entity.PokemonEntity
import com.example.pokedex.data.database.entity.RemoteKeysEntity

interface PokemonLocalDataSource {
    fun insertAll(list: List<PokemonEntity>)
    suspend fun getRemoteKeysId(item: Int): RemoteKeysEntity?
    suspend fun saveAllRemoteKey(remoteKeys: List<RemoteKeysEntity>)
}