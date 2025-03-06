package com.example.pokedex.list.domain.model

data class PokemonList(
    val id: Int,
    val name: String,
    val image: String,
    val height: Int,
    val weight: Int,
    val type: List<String>
)