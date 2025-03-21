package com.example.pokedex.details.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokedex.details.view.model.PokemonDetailsUi

@Composable
fun PokemonDetails(pokemonDetailsUi: PokemonDetailsUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        PokemonDetailsHeader(pokemonDetailsUi.name, pokemonDetailsUi.id)
        PokemonDetailsImage(pokemonDetailsUi.image)
        Text(
            text = pokemonDetailsUi.description,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        PokemonStat(
            stats = pokemonDetailsUi.stats,
            colors = pokemonDetailsUi.colours.map { it.getColor() }
        )
        PokemonFeatures(
            featuresUi = pokemonDetailsUi.featuresUi,
            colours = pokemonDetailsUi.colours.map { it.getColor() }
        )
        PokemonDetailsType(typeColoursEnum = pokemonDetailsUi.colours)
        PokemonDetailsDamage(
            doubleDamage = pokemonDetailsUi.doubleDamage,
            noDamage = pokemonDetailsUi.noDamage
        )
    }
}