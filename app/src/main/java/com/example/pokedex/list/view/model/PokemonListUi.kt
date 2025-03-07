package com.example.pokedex.list.view.model

import com.example.pokedex.common.model.TypeColoursEnum
import com.example.pokedex.list.view.factory.PokemonMeasureData

data class PokemonListUi(
    val id: Int,
    val name: String,
    val image: String,
    val measuremList: List<PokemonMeasureData>,
    val color: List<TypeColoursEnum>
)