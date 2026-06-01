package com.example.pokedex.list.data.datasource.local

import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
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

    override fun getTypeQueryWithId(
        searchId: Int,
        selectedTypes: List<String>
    ): PagingSource<Int, PokemonEntity> {
        return pokemonDao.getPokemonByDynamicTypes(
            query = buildTypeQueryWithId(searchId, selectedTypes)
        )
    }

    override fun getTypeQueryWithName(
        searchName: String,
        selectedTypes: List<String>
    ): PagingSource<Int, PokemonEntity> {
        return pokemonDao.getPokemonByDynamicTypes(
            query = buildTypeQueryWithName(searchName, selectedTypes)
        )
    }

    override fun getByDynamicTypes(selectedTypes: List<String>): PagingSource<Int, PokemonEntity> {
        return pokemonDao.getPokemonByDynamicTypes(query = buildTypeQuery(selectedTypes))
    }

    override fun getAllPokemon(): PagingSource<Int, PokemonEntity> {
        return pokemonDao.getAll()
    }

    private fun buildTypeQuery(selectedTypes: List<String>): SupportSQLiteQuery {
        val whereConditions = selectedTypes.joinToString(" OR ") { type ->
            "type LIKE '%${type.lowercase()}%'"
        }

        val query = "SELECT * FROM pokemon_table WHERE $whereConditions"
        return SimpleSQLiteQuery(query)
    }

    private fun buildTypeQueryWithId(id: Int, selectedTypes: List<String>): SupportSQLiteQuery {
        return if (selectedTypes.isEmpty()) {
            SimpleSQLiteQuery("SELECT * FROM pokemon_table WHERE id = $id")
        } else {
            val typeConditions = selectedTypes.joinToString(" OR ") { type ->
                "type LIKE '%${type.lowercase()}%'"
            }
            SimpleSQLiteQuery("SELECT * FROM pokemon_table WHERE id = $id AND ($typeConditions)")
        }
    }

    private fun buildTypeQueryWithName(
        name: String,
        selectedTypes: List<String>
    ): SupportSQLiteQuery {
        return if (selectedTypes.isEmpty()) {
            SimpleSQLiteQuery("SELECT * FROM pokemon_table WHERE name LIKE '%$name%'")
        } else {
            val typeConditions = selectedTypes.joinToString(" OR ") { type ->
                "type LIKE '%${type.lowercase()}%'"
            }
            SimpleSQLiteQuery("SELECT * FROM pokemon_table WHERE name LIKE '%$name%' AND ($typeConditions)")
        }
    }
}