package com.example.pokedex.details.view.components


import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class PokemonDetailsHeaderTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonDetailsHeader_rendersPokemonName() {
        composeTestRule.setContent {
            PokemonDetailsHeader(
                name = "Bulbassaur",
                id = 1
            )
        }
        composeTestRule.onNodeWithText("Bulbassaur").assertExists()
    }

    @Test
    fun pokemonDetailsHeader_rendersFormattedPokemonId() {
        composeTestRule.setContent {
            PokemonDetailsHeader(
                name = "Bulbassaur",
                id = 1
            )
        }
        composeTestRule.onNodeWithText("#001").assertExists()
    }
}