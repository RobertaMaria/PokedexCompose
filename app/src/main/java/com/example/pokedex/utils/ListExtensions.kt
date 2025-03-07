package com.example.pokedex.utils

import com.example.pokedex.common.model.TypeColoursEnum

fun List<String>.mapToTypeColors(): List<TypeColoursEnum> {
    val defaultColor = TypeColoursEnum.NORMAL
    return map { typeString ->
        TypeColoursEnum.entries.firstOrNull { it.typeName == typeString } ?: defaultColor
    }
}

fun <T> List<T>.duplicateIfSingle(): List<T> {
    return takeIf { it.size == 1 }?.let { it + it } ?: toList()
}