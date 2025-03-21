package com.example.pokedex.details.domain.model

data class PokemonDetails(
    val id: Int = 0,
    var description: String,
    var name: String,
    var image: String,
    val type: List<String>,
    val stats: List<PokemonStats>,
    val evolutions: List<Evolutions>,
    val doubleDamage: List<String>,
    val noDamage: List<String>,
    val features: Features
)

data class PokemonStats(
    val baseStat: Int,
    val stat: Stat
)

data class Stat(
    val name: String
)

data class Evolutions(
    val name: String,
    val pokemonId: Int
)

data class Features(
    val height: Int,
    val weight: Int,
    val gender: Int,
    val category: String,
    val ability: List<String>
)