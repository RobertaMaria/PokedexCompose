package com.example.pokedex.details.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import com.example.pokedex.details.view.model.Evolutions
import com.example.pokedex.ui.theme.PokedexTheme


@Composable
fun PokemonEvolutions(evolutions: List<Evolutions>, onClickEvolution: (id: Int) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.pokemon_evolutions_title),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        LazyRow(
            modifier = Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(items = evolutions) { evolution ->
                EvolutionItem(evolution = evolution, onClickEvolution = onClickEvolution)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PokemonEvolutions() {
    PokedexTheme {
        Surface {
            PokemonEvolutions(
                evolutions = listOf(
                    Evolutions(
                        name = "Ivysaur",
                        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                        id = 2
                    ),
                    Evolutions(
                        name = "Venusaur",
                        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/3.gif",
                        id = 3
                    )
                )
            )
        }
    }
}