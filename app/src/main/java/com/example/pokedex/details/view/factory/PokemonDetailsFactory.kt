package com.example.pokedex.details.view.factory

import android.content.Context
import com.example.pokedex.R
import com.example.pokedex.details.domain.model.PokemonDetails
import com.example.pokedex.details.view.model.Evolutions
import com.example.pokedex.details.view.model.PokemonDetailsUi
import com.example.pokedex.details.view.model.PokemonStats
import com.example.pokedex.details.view.model.Stat
import com.example.pokedex.utils.mapToTypeColors
import com.example.pokedex.utils.titleCase

class PokemonDetailsFactory(private val context: Context) {
    operator fun invoke(pokemonDetails: PokemonDetails): PokemonDetailsUi {
        return pokemonDetails.run {
            PokemonDetailsUi(
                id = id,
                name = name.titleCase,
                image = image,
                description = description,
                stats = stats.map {
                    PokemonStats(it.baseStat, Stat(it.stat.name))
                },
                colours = type.mapToTypeColors().map {
                    it.getColor()
                },
                evolutions = evolutions.map {
                    Evolutions(
                        name = it.name,
                        image = context.getString(R.string.pokemon_image_url, it.pokemonId),
                        id = it.pokemonId
                    )
                }
            )
        }
    }
}