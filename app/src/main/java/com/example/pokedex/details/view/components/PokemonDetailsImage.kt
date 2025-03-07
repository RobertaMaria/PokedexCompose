package com.example.pokedex.details.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokedex.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

private const val SIZE_DEFAULT = 250

@Composable
fun PokemonDetailsImage(imageUrl: String, size: Int = SIZE_DEFAULT, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.pokebola),
        error = painterResource(R.drawable.pokebola)
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier.size(size.dp),
        contentScale = ContentScale.Fit,
    )
}