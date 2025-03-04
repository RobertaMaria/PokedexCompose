package com.example.pokedex.details.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex.details.view.model.PokemonStats
import com.example.pokedex.details.view.model.Stat
import com.example.pokedex.ui.theme.PokedexTheme

@Composable
fun ProgressBarStat(stats: List<PokemonStats>, colors: List<Color>) {

    stats.forEach { stat ->
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = SpaceBetween
        ) {

            Text(text = stat.stat.name)

            CustomProgressBar(baseStat = stat.baseStat, colors = colors)
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProgressBarStatPreview() {
    PokedexTheme {
        Surface {
            ProgressBarStat(
                stats = listOf(
                    PokemonStats(
                        baseStat = 90,
                        stat = Stat(
                            name = "HP"
                        )
                    )
                ),
                colors = listOf(
                    Color.Red,
                    Color.Green,
                    Color.Blue,
                )
            )
        }
    }
}