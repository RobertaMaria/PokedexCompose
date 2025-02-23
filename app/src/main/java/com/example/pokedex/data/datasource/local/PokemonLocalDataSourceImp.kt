package com.example.pokedex.data.datasource.local

import com.example.pokedex.data.database.dao.PokemonDao
import com.example.pokedex.data.database.dao.RemoteKeysDao
import com.example.pokedex.data.database.entity.PokemonEntity
import com.example.pokedex.data.database.entity.RemoteKeysEntity

class PokemonLocalDataSourceImp(
    private val pokemonDao: PokemonDao,
    private val remoteKeysDao: RemoteKeysDao
) : PokemonLocalDataSource {

    override fun insertAll(list: List<PokemonEntity>) {
        pokemonDao.insertAll(list)
    }

    override suspend fun getRemoteKeysId(item: Int): RemoteKeysEntity? {
        return remoteKeysDao.getRemoteKeysId(item)
    }

    override suspend fun saveAllRemoteKey(remoteKeys: List<RemoteKeysEntity>) {
        remoteKeysDao.insertAll(remoteKeys)
    }
}