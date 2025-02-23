package com.example.pokedex.list.view.model

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color
import com.example.pokedex.R
import java.util.Locale

enum class TypeColoursEnum(val codColor: String, val typeName: String) {
    NORMAL("#A8A77A", "normal"),
    FIRE("#EE8130", "fire"),
    WATER("#6390F0", "water"),
    ELECTRIC("#F7D02C", "electric"),
    GRASS("#7AC74C", "grass"),
    ICE("#96D9D6", "ice"),
    FIGHTING("#C22E28", "fighting"),
    POISON("#A33EA1", "poison"),
    GROUND("#E2BF65", "ground"),
    FLYING("#A98FF3", "flying"),
    PSYCHIC("#F95587", "psychic"),
    BUG("#A6B91A", "bug"),
    ROCK("#B6A136", "rock"),
    GHOST("#735797", "ghost"),
    DRAGON("#6F35FC", "dragon"),
    DARK("#705746", "dark"),
    STEEL("#B7B7CE", "steel"),
    FAIRY("#D685AD", "fairy");

    fun getColor(): Color = Color(parseColor(codColor))

    fun getFormattedName(): String =
        name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }

    fun getDrawableId(): Int {
        return drawableMap[this] ?: R.drawable.ic_normal
    }

    companion object {
        private val drawableMap = mapOf(
            NORMAL to R.drawable.ic_normal,
            FIRE to R.drawable.ic_fire,
            WATER to R.drawable.ic_water,
            ELECTRIC to R.drawable.ic_electric,
            GRASS to R.drawable.ic_grass,
            ICE to R.drawable.ic_ice,
            FIGHTING to R.drawable.ic_fighting,
            POISON to R.drawable.ic_poison,
            GROUND to R.drawable.ic_ground,
            FLYING to R.drawable.ic_flying,
            PSYCHIC to R.drawable.ic_psychic,
            BUG to R.drawable.ic_bug,
            ROCK to R.drawable.ic_rock,
            GHOST to R.drawable.ic_ghost,
            DRAGON to R.drawable.ic_dragon,
            DARK to R.drawable.ic_dark,
            STEEL to R.drawable.ic_steel,
            FAIRY to R.drawable.ic_fairy
        )
    }
}