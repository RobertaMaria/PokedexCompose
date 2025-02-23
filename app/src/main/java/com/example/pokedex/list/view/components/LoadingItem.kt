package com.example.pokedex.list.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokedex.R

@Composable
fun LoadingItem(rotationLoading: Float) {
    Image(
        painter = painterResource(R.drawable.pokebola),
        contentDescription = "",
        modifier = Modifier
            .rotate(rotationLoading)
            .size(64.dp)
            .padding(8.dp)
    )
}