package com.example.pokedex.details.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.details.view.model.Evolutions


@Composable
fun PokemonEvolutions(evolutions: List<Evolutions>, onClickEvolution: (id: Int) -> Unit = {}) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(items = evolutions) { evolution ->
            EvolutionItem(evolution = evolution, onClickEvolution = onClickEvolution)
        }
    }
}

@Composable
fun EvolutionItem(evolution: Evolutions, onClickEvolution: (id: Int) -> Unit) {
    Column(
        modifier = Modifier.clickable {
            onClickEvolution(evolution.id)
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokemonDetailsImage(imageUrl = evolution.image, size = 150)
        Text(
            text = evolution.name,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}