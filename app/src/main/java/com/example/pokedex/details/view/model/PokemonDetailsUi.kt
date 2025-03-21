package com.example.pokedex.details.view.model

import androidx.annotation.DrawableRes
import com.example.pokedex.common.model.TypeColoursEnum

data class PokemonDetailsUi(
    val id: Int = 0,
    var description: String = String(),
    var name: String = String(),
    var colours: List<TypeColoursEnum> = listOf(),
    var image: String = String(),
    val stats: List<PokemonStats> = listOf(),
    val evolutions: List<Evolutions> = listOf(),
    val isLoaded: Boolean = false,
    val doubleDamage: List<TypeColoursEnum> = listOf(),
    val noDamage: List<TypeColoursEnum> = listOf(),
    val featuresUi: FeaturesUi
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

data class FeaturesUi(
    val height: String,
    val weight: String,
    @DrawableRes val gender: Int,
    val category: String,
    val ability: List<String>
)