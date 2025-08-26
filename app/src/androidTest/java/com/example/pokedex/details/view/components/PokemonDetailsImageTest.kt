package com.example.pokedex.details.view.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class PokemonDetailsImageTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonDetailsImage_rendersPokemonImage() {
        composeTestRule.setContent {
            PokemonDetailsImage(
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
            )
        }
        composeTestRule.onNodeWithTag("pokemon_image_tag")
            .assertExists()
            .assertIsDisplayed()
    }
}