package com.example.pokedex.details.view.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import com.example.pokedex.details.view.model.FeaturesUi
import com.example.pokedex.ui.theme.PokedexTheme

@Composable
fun PokemonFeatures(featuresUi: FeaturesUi, colours: List<Color>) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = colours.plus(Color.Transparent)
                    )
                )
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            FeatureHeader(expanded = expanded, onClick = { expanded = !expanded })

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(animationSpec = tween(durationMillis = 300)),
                exit = shrinkVertically(animationSpec = tween(durationMillis = 300))
            ) {
                FeatureDetails(featuresUi = featuresUi)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PokemonFeaturesPreview() {
    PokedexTheme {
        Surface {
            PokemonFeatures(
                featuresUi = FeaturesUi(
                    height = "0.7 m",
                    weight = "6.9 kg",
                    gender = R.drawable.ic_female_male,
                    category = "Seed Pokémon",
                    ability = listOf("Overgrow", "Chlorophyll")
                ),
                colours = listOf(Color.Red, Color.Blue)
            )
        }
    }
}