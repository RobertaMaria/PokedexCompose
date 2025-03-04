package com.example.pokedex.details.data.datasource.local

import com.example.pokedex.data.database.dao.PokemonDao
import com.example.pokedex.data.database.entity.PokemonEntity
import com.example.pokedex.details.data.database.dao.PokemonEvolutionDao
import com.example.pokedex.details.data.database.dao.PokemonSpecieDao
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity
import com.example.pokedex.details.data.database.model.SpeciesAndEvolution

class PokemonDetailLocalDataSourceImp(
    private val pokemonSpecieDao: PokemonSpecieDao,
    private val pokemonDao: PokemonDao,
    private val pokemonEvolutionDao: PokemonEvolutionDao
) :
    PokemonDetailLocalDataSource {
    override suspend fun savePokemonSpecie(pokemonSpecie: PokemonSpecieEntity) {
        pokemonSpecieDao.insert(pokemonSpecie)
    }

    override fun getPokemonSpecie(id: Int): SpeciesAndEvolution {
        return pokemonSpecieDao.getPokemonSpecie(id = id)
    }

    override fun getPokemonEntity(pokemonId: Int): PokemonEntity? {
        return pokemonDao.getPokemonById(pokemonId = pokemonId)
    }

    override suspend fun saveEvolutions(evolutions: PokemonEvolutionEntity) {
        pokemonEvolutionDao.insert(evolutions)
    }
}