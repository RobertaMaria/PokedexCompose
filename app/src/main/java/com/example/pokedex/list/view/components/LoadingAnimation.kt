package com.example.pokedex.list.view.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

private const val TARGET_VALUE = 360f
private const val DURATION = 2000
private const val INITIAL_VALUE = 0f

@Composable
fun LoadingAnimation() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        targetValue = TARGET_VALUE,
        animationSpec = infiniteRepeatable(
            animation = tween(DURATION, easing = LinearEasing)
        ),
        initialValue = INITIAL_VALUE
    )

    LoadingItem(rotation)
}