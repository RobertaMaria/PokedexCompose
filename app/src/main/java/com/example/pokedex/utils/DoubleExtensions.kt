package com.example.pokedex.utils

import java.util.Locale

private const val DIVISOR = 10.0

fun Int.toDoubleFormat(measure: String): String {
    return "${String.format(Locale.getDefault(), "%.1f", this / DIVISOR)} $measure"
}