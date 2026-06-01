package com.example.pokedex.list.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.pokedex.list.data.database.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_table")
    fun getAll(): PagingSource<Int, PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_table WHERE id = :pokemonId")
    fun getPokemonById(pokemonId: Int): PokemonEntity?

    @RawQuery(observedEntities = [PokemonEntity::class])
    fun getPokemonByDynamicTypes(query: SupportSQLiteQuery): PagingSource<Int, PokemonEntity>
}