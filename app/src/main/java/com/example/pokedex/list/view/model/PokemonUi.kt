package com.example.pokedex.list.view.model

import com.example.pokedex.list.factory.PokemonMeasureData

data class PokemonUi(
    val id: Int,
    val name: String,
    val image: String,
    val measuremList: List<PokemonMeasureData>,
    val color: List<TypeColoursEnum>
)