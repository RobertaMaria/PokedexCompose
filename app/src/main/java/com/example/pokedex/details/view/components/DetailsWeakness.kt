package com.example.pokedex.details.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.common.components.PokemonType
import com.example.pokedex.common.model.TypeColoursEnum

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsWeakness(doubleDamage: List<TypeColoursEnum>, title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Start
    )

    Spacer(modifier = Modifier.height(8.dp))

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        doubleDamage.forEach {
            PokemonType(it)
        }
    }
}