package com.example.pokedex.list.data.datasource.local

import com.example.pokedex.list.data.database.dao.PokemonDao
import com.example.pokedex.list.data.database.dao.RemoteKeysDao
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.database.entity.RemoteKeysEntity

class PokemonListLocalDataSourceImp(
    private val pokemonDao: PokemonDao,
    private val remoteKeysDao: RemoteKeysDao
) : PokemonListLocalDataSource {

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