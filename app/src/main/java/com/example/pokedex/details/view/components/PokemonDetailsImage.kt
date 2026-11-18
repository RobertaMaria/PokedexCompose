package com.example.pokedex.details.view.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.R

private const val SIZE_DEFAULT = 250

@Composable
fun PokemonDetailsImage(imageUrl: String, size: Int = SIZE_DEFAULT, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier
            .size(size.dp)
            .testTag("pokemon_image_tag"),
        contentScale = ContentScale.Fit,
        placeholder = painterResource(R.drawable.pokebola),
        error = painterResource(R.drawable.pokebola)
    )
}