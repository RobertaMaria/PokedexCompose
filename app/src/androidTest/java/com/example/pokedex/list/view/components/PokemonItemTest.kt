package com.example.pokedex.list.view.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.pokedex.R
import com.example.pokedex.common.model.TypeColoursEnum
import com.example.pokedex.list.view.factory.PokemonMeasureData
import com.example.pokedex.list.view.model.PokemonListUi
import org.junit.Rule
import org.junit.Test

class PokemonItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val pokemonItem = PokemonListUi(
        id = 10,
        name = "Pikachu",
        image = "",
        measuremList = listOf(
            PokemonMeasureData(
                measureFormatted = "100,Kg",
                descriptionId = R.string.pokemon_list_weight,
                iconId = R.drawable.weight_kilogram
            ),
            PokemonMeasureData(
                measureFormatted = "2,0m",
                descriptionId = R.string.pokemon_list_Height,
                iconId = R.drawable.ruler_square
            )
        ),
        color = listOf(
            TypeColoursEnum.DRAGON,
            TypeColoursEnum.ELECTRIC
        )
    )

    @Test
    fun pokemonItemTest_rendersPokemonImage() {
        composeTestRule.setContent {
            PokemonItem(
                pokemon = pokemonItem
            )
        }
        composeTestRule.onNodeWithContentDescription("Pikachu").assertExists()
    }

    @Test
    fun pokemonItemTest_rendersPokemonName() {
        composeTestRule.setContent {
            PokemonItem(
                pokemon = pokemonItem
            )
        }
        composeTestRule.onNodeWithText("Pikachu").assertExists()
    }

    @Test
    fun pokemonItemTest_rendersPokemonId() {
        composeTestRule.setContent {
            PokemonItem(
                pokemon = pokemonItem
            )
        }
        composeTestRule.onNodeWithText("#010").assertExists()
    }
}