package com.example.pokedex.details.view.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedex.common.model.TypeColoursEnum
import com.example.pokedex.details.view.model.PokemonDetailsUi
import com.example.pokedex.list.view.components.LoadingAnimation
import com.example.pokedex.ui.theme.PokedexTheme

@Composable
fun DetailsScreen(
    pokemonDetailsUi: PokemonDetailsUi,
    onClickEvolution: (id: Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top

    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = pokemonDetailsUi.colours.map {
                            it.getColor()
                        }
                            .plus(Color.Transparent)
                    )
                )
        ) {
            PokemonDetails(pokemonDetailsUi = pokemonDetailsUi)

            if (pokemonDetailsUi.isLoaded) {
                Box(
                    modifier = Modifier.matchParentSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    LoadingAnimation(isCentered = true)
                }
            }
        }

        PokemonEvolutions(
            evolutions = pokemonDetailsUi.evolutions,
            onClickEvolution = onClickEvolution
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsScreenPreview() {
    PokedexTheme {
        Surface {
            DetailsScreen(
                PokemonDetailsUi(
                    id = 0,
                    name = "Bulbasaur",
                    description = "This Pokémon is vulnerable while its shell is soft, exposing its weak and tender body.",
                    colours = listOf(
                        TypeColoursEnum.DRAGON,
                        TypeColoursEnum.ELECTRIC
                    )
                )
            )
        }
    }
}