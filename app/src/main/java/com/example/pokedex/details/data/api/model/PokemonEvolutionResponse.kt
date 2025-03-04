package com.example.pokedex.details.data.api.model

import com.google.gson.annotations.SerializedName

data class PokemonEvolutionResponse(
    @SerializedName("chain") val chain: ChainResponse
)

data class ChainResponse(
    @SerializedName("evolves_to") val evolvesTo: List<ChainResponse>,
    @SerializedName("species") val evolutionsResponse: EvolutionsResponse
)

data class EvolutionsResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)