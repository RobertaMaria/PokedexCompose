package com.example.pokedex.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val image: String,
    val height: Int,
    val weight: Int,
    val type: List<String>
)