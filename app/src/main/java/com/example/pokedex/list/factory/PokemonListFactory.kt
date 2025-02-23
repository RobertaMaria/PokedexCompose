package com.example.pokedex.list.factory

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.pokedex.R
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.list.view.model.PokemonUi
import com.example.pokedex.list.view.model.TypeColoursEnum
import java.util.Locale


class PokemonListFactory(context: Context) {

    private val idFormat = context.getString(R.string.pokemon_list_id_format)
    private val heightFormat = context.getString(R.string.pokemon_list_Height_format)
    private val weightFormat = context.getString(R.string.pokemon_list_weight_format)

    operator fun invoke(pokemon: Pokemon): PokemonUi {

        val typeColoursEnums = mapPokemonTypeToColors(pokemon.type)
        val height = pokemon.height.toDoubleFormat(heightFormat)
        val weight = pokemon.weight.toDoubleFormat(weightFormat)

        return pokemon.run {
            PokemonUi(
                id = formatPokemonId(id),
                name = name.titleCase,
                measuremList = getListPokemonMeasurem(height = height, weight = weight),
                image = image,
                color = typeColoursEnums
            )
        }
    }

    private fun formatPokemonId(id: Int): String {
        return String.format(idFormat, id)
    }

    private val String.titleCase: String
        get() = replaceFirstChar { it.titlecase(Locale.getDefault()) }


    private fun mapPokemonTypeToColors(types: List<String>): List<TypeColoursEnum> {
        val defaultColor = TypeColoursEnum.NORMAL
        return types.map { typeString ->
            TypeColoursEnum.entries.firstOrNull { it.typeName == typeString } ?: defaultColor
        }
    }

    private fun Int.toDoubleFormat(measure: String): String {
        return "${String.format(Locale.getDefault(), "%.1f", this / 10.0)}$measure"
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