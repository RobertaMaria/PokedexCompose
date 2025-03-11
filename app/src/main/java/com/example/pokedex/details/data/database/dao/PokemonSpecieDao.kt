package com.example.pokedex.details.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity
import com.example.pokedex.details.data.database.model.SpeciesAndEvolutions

@Dao
interface PokemonSpecieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemonSpecie: PokemonSpecieEntity)

    @Transaction
    @Query("SELECT * FROM species_table WHERE id = :id")
    fun getPokemonSpecie(id: Int): SpeciesAndEvolutions
}