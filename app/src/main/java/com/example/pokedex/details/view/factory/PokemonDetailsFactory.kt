package com.example.pokedex.details.view.factory

import android.content.Context
import com.example.pokedex.R
import com.example.pokedex.details.domain.model.PokemonDetails
import com.example.pokedex.details.view.model.Evolutions
import com.example.pokedex.details.view.model.FeaturesUi
import com.example.pokedex.details.view.model.PokemonDetailsUi
import com.example.pokedex.details.view.model.PokemonStats
import com.example.pokedex.details.view.model.Stat
import com.example.pokedex.utils.cleanDescription
import com.example.pokedex.utils.formatAbilities
import com.example.pokedex.utils.mapToTypeColors
import com.example.pokedex.utils.removePokemonSuffix
import com.example.pokedex.utils.titleCase
import com.example.pokedex.utils.toDoubleFormat

class PokemonDetailsFactory(private val context: Context) {
    operator fun invoke(pokemonDetails: PokemonDetails): PokemonDetailsUi {
        return pokemonDetails.run {
            PokemonDetailsUi(
                id = id,
                name = name.titleCase,
                image = image,
                description = description.cleanDescription(),
                stats = stats.map {
                    PokemonStats(it.baseStat, Stat(it.stat.name))
                },
                colours = type.mapToTypeColors(),
                evolutions = evolutions.map {
                    Evolutions(
                        name = it.name.titleCase,
                        image = context.getString(R.string.pokemon_image_url, it.pokemonId),
                        id = it.pokemonId
                    )
                },
                doubleDamage = doubleDamage.mapToTypeColors(),
                noDamage = noDamage.mapToTypeColors(),
                featuresUi = FeaturesUi(
                    height = features.height.toDoubleFormat(context.getString(R.string.pokemon_list_Height_format)),
                    weight = features.weight.toDoubleFormat(context.getString(R.string.pokemon_list_weight_format)),
                    gender = getGender(features.gender),
                    category = features.category.removePokemonSuffix(),
                    ability = features.ability.formatAbilities()
                )
            )
        }
    }

    private fun getGender(feeGender: Int): Int {
        return when (feeGender) {
            -1 -> R.drawable.ic_female_male
            8 -> R.drawable.ic_female
            0 -> R.drawable.ic_male
            else -> R.drawable.ic_female_male
        }
    }
}