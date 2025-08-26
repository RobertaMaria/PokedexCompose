package com.example.pokedex.common.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.pokedex.common.model.TypeColoursEnum
import org.junit.Rule
import org.junit.Test

class PokemonTypeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonTypeTest_rendersPokemonListTypeName() {
        composeTestRule.setContent {
            PokemonType(
                typeColoursEnum = TypeColoursEnum.GRASS
            )
        }
        composeTestRule.onNodeWithText("Grass").assertExists()
    }

    @Test
    fun pokemonTypeTest_rendersPokemonListTypeImage() {
        composeTestRule.setContent {
            PokemonType(
                typeColoursEnum = TypeColoursEnum.GRASS
            )
        }
        composeTestRule.onNodeWithContentDescription("Grass").assertExists()
    }
}