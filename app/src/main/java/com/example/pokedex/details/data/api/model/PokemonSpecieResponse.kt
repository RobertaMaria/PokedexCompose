package com.example.pokedex.details.data.api.model

import com.google.gson.annotations.SerializedName

data class PokemonSpecieResponse(
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>,
    @SerializedName("evolution_chain") val chainResponse: EvolutionChainResponse
)

data class FlavorTextEntry(
    @SerializedName("flavor_text") val flavorText: String,
    @SerializedName("language") val language: LanguageResponse
)

data class LanguageResponse(
    @SerializedName("name") val name: String
)

data class EvolutionChainResponse(
    @SerializedName("url") val url: String
)