package com.example.pokedex.common.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.common.database.entity.FavoriteEntity
import com.example.pokedex.list.data.database.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_table WHERE pokemonId = :pokemonId)")
    fun isFavorite(pokemonId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM pokemon_table WHERE id IN (SELECT pokemonId FROM favorite_table)")
    fun getFavoritePokemons(): Flow<List<PokemonEntity>>
}
