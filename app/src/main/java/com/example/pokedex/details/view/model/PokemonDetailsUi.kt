package com.example.pokedex.details.view.model

import com.example.pokedex.common.model.TypeColoursEnum

data class PokemonDetailsUi(
    val id: Int = 0,
    var description: String = String(),
    var name: String = String(),
    var colours: List<TypeColoursEnum> = listOf(),
    var image: String = String(),
    val stats: List<PokemonStats> = listOf(),
    val evolutions: List<Evolutions> = listOf(),
    val isLoaded: Boolean = false
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
    val image: String,
    val id: Int
)