package com.example.pokedex.details.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokedex.details.view.model.PokemonDetailsUi

@Composable
fun PokemonDetails(
    pokemonDetailsUi: PokemonDetailsUi,
    onToggleFavorite: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        PokemonDetailsHeader(
            name = pokemonDetailsUi.name,
            id = pokemonDetailsUi.id
        )
        PokemonDetailsImage(pokemonDetailsUi.image)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            SmallFloatingActionButton(
                onClick = onToggleFavorite,
                modifier = Modifier.align(Alignment.CenterEnd),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = if (pokemonDetailsUi.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Icon(
                    imageVector = if (pokemonDetailsUi.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito"
                )
            }
        }

        Text(
            text = pokemonDetailsUi.description,
            modifier = Modifier.fillMaxWidth(),
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