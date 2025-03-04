package com.example.pokedex.details.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import com.example.pokedex.details.view.model.PokemonStats
import com.example.pokedex.details.view.model.Stat
import com.example.pokedex.ui.theme.PokedexTheme

@Composable
fun PokemonStat(stats: List<PokemonStats>, colors: List<Color>) {
    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Column(
            verticalArrangement = spacedBy(8.dp)
        ) {

            Text(
                text = stringResource(R.string.pokemon_stat_title),
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                fontWeight = FontWeight.Bold
            )

            ProgressBarStat(stats = stats, colors = colors)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PokemonStatsPreview() {
    PokedexTheme {
        Surface {
            PokemonStat(
                stats = listOf(
                    PokemonStats(
                        baseStat = 45,
                        stat = Stat(name = "HP")
                    ),
                    PokemonStats(
                        baseStat = 49,
                        stat = Stat(name = "Attack")
                    ),
                    PokemonStats(
                        baseStat = 49,
                        stat = Stat(name = "Special-defenseee")
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