package com.example.pokedex.details.data.api.model

import com.google.gson.annotations.SerializedName

data class PokemonDamageRelationsResponse(
    @SerializedName("damage_relations") val damageRelations: DamageRelationsResponse
)

data class DamageRelationsResponse(
    @SerializedName("double_damage_from") val doubleDamageFrom: List<DoubleDamageFromResponse>,
    @SerializedName("no_damage_from") val noDamageFrom: List<NoDamageFromResponse>
)

data class DoubleDamageFromResponse(
    @SerializedName("name") val name: String
)

data class NoDamageFromResponse(
    @SerializedName("name") val name: String
)