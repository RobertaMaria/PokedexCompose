package com.example.pokedex.details.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.details.view.model.Evolutions
import com.example.pokedex.utils.formatPokemonId

private const val SIZE = 100

@Composable
fun EvolutionItem(evolution: Evolutions, onClickEvolution: (id: Int) -> Unit) {
    Column(
        modifier = Modifier.clickable {
            onClickEvolution(evolution.id)
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(130.dp)
                .border(4.dp, Color.LightGray, CircleShape)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            PokemonDetailsImage(
                imageUrl = evolution.image,
                size = SIZE,
                modifier = Modifier.clip(CircleShape)
            )
        }

        Text(
            text = evolution.name,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            text = evolution.id.formatPokemonId(),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}