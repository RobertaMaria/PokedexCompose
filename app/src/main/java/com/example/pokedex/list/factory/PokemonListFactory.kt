package com.example.pokedex.list.factory

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.pokedex.R
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.list.view.model.PokemonUi
import com.example.pokedex.utils.mapToTypeColors
import com.example.pokedex.utils.titleCase
import com.example.pokedex.utils.toDoubleFormat


class PokemonListFactory(context: Context) {
    private val heightFormat = context.getString(R.string.pokemon_list_Height_format)
    private val weightFormat = context.getString(R.string.pokemon_list_weight_format)

    operator fun invoke(pokemon: Pokemon): PokemonUi {
        val height = pokemon.height.toDoubleFormat(heightFormat)
        val weight = pokemon.weight.toDoubleFormat(weightFormat)

        return pokemon.run {
            PokemonUi(
                id = id,
                name = name.titleCase,
                measuremList = getListPokemonMeasurem(height = height, weight = weight),
                image = image,
                color = pokemon.type.mapToTypeColors(),
            )
        }
    }

    private fun getListPokemonMeasurem(weight: String, height: String): List<PokemonMeasureData> {
        return listOf(
            PokemonMeasureData(weight, R.string.pokemon_list_weight, R.drawable.weight_kilogram),
            PokemonMeasureData(height, R.string.pokemon_list_Height, R.drawable.ruler_square)
        )
    }
}

data class PokemonMeasureData(
    val measureFormatted: String,
    @StringRes val descriptionId: Int,
    @DrawableRes val iconId: Int
)