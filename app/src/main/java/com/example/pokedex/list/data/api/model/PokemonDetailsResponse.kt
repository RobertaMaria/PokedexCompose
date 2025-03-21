package com.example.pokedex.list.data.api.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailsResponse(
    @SerializedName("sprites") val image: ImageResponse,
    @SerializedName("name") val name: String,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("types") val types: List<PokemonTypesResponse>,
    @SerializedName("stats") val stats: List<PokemonStatsResponse>,
    @SerializedName("abilities") val abilities: List<PokemonAbilitiesResponse>
)

data class ImageResponse(
    @SerializedName("other") val other: OtherResponse
)

data class OtherResponse(
    @SerializedName("official-artwork") val officialImage: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("front_default") val imageDefault: String
)

data class PokemonTypesResponse(
    @SerializedName("type") val type: TypeResponse
)

data class TypeResponse(
    @SerializedName("name") val typeName: String
)

data class PokemonStatsResponse(
    @SerializedName("base_stat") val baseStat: Int,
    @SerializedName("stat") val stat: StatResponse
)

data class StatResponse(
    @SerializedName("name") val statName: String
)

data class PokemonAbilitiesResponse(
    @SerializedName("ability") val ability: AbilityResponse
)

data class AbilityResponse(
    @SerializedName("name") val abilityName: String
)