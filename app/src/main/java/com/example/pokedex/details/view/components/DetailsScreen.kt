package com.example.pokedex.details.view.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.details.view.model.PokemonDetailsUi
import com.example.pokedex.list.view.components.LoadingAnimation
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.utils.formatPokemonId

@Composable
fun DetailsScreen(pokemonDetailsUi: PokemonDetailsUi, isLoaded: Boolean) {
    if (isLoaded) {
        LoadingAnimation(isCentered = true)
    } else {
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
                            colors = pokemonDetailsUi.colours.plus(Color.Transparent)
                        )
                    )
            ) {

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = pokemonDetailsUi.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Text(
                            text = pokemonDetailsUi.id.formatPokemonId(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    PokemonDetailsImage(pokemonDetailsUi.image)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = pokemonDetailsUi.description,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    PokemonStat(pokemonDetailsUi.stats, pokemonDetailsUi.colours)
                }
            }
        }
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
                        Color.Red,
                        Color.Green,
                        Color.Blue,
                    )
                ),
                isLoaded = false
            )
        }
    }
}