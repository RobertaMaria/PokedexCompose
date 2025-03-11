package com.example.pokedex.details.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.utils.formatPokemonId

@Composable
fun PokemonDetailsHeader(name: String, id: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 23.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = id.formatPokemonId(),
            fontWeight = FontWeight.Bold,
            fontSize = 23.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}