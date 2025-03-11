package com.example.pokedex.details.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import com.example.pokedex.common.model.TypeColoursEnum


@Composable
fun PokemonDetailsDamage(doubleDamage: List<TypeColoursEnum>, noDamage: List<TypeColoursEnum>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(
            doubleDamage to stringResource(R.string.pokemon_weakness_title),
            noDamage to stringResource(R.string.pokemon_immune_title)
        ).forEach { (list, title) ->
            list.takeIf {
                it.isNotEmpty()
            }?.let {
                DetailsWeakness(it, title)
            }
        }
    }
}