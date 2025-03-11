package com.example.pokedex.utils

import java.util.Locale

private const val idFormat = "#%03d"

fun Int.formatPokemonId(): String {
    return String.format(Locale.US, idFormat, this)
}

val String.titleCase: String
    get() = replaceFirstChar { it.titlecase(Locale.getDefault()) }

fun String.cleanDescription(): String {
    return this
        .replace("\n", " ")
        .replace("\u000C", " ")
        .replace("\\s+".toRegex(), " ")
        .trim()
}