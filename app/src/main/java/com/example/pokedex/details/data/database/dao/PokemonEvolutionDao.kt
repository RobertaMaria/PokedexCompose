package com.example.pokedex.details.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity


@Dao
interface PokemonEvolutionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemonEvolution: PokemonEvolutionEntity)
}