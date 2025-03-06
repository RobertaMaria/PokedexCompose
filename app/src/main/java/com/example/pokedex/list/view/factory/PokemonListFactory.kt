package com.example.pokedex.list.view.factory

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.pokedex.R
import com.example.pokedex.list.domain.model.PokemonList
import com.example.pokedex.list.view.model.PokemonListUi
import com.example.pokedex.utils.mapToTypeColors
import com.example.pokedex.utils.titleCase
import com.example.pokedex.utils.toDoubleFormat


class PokemonListFactory(context: Context) {
    private val heightFormat = context.getString(R.string.pokemon_list_Height_format)
    private val weightFormat = context.getString(R.string.pokemon_list_weight_format)

    operator fun invoke(pokemonList: PokemonList): PokemonListUi {
        val height = pokemonList.height.toDoubleFormat(heightFormat)
        val weight = pokemonList.weight.toDoubleFormat(weightFormat)

        return pokemonList.run {
            PokemonListUi(
                id = id,
                name = name.titleCase,
                measuremList = getListPokemonMeasurem(height = height, weight = weight),
                image = image,
                color = pokemonList.type.mapToTypeColors(),
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