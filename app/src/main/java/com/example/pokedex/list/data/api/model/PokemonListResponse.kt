package com.example.pokedex.list.data.api.model

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName ("next") val next: String?,
    @SerializedName ("previous") val previous: String?,
    @SerializedName ("results") val results: List<PokemonResponse>
)

data class PokemonResponse(
    @SerializedName ("url") val url: String
)