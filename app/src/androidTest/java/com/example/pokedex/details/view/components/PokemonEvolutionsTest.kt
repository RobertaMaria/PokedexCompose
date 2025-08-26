package com.example.pokedex.details.view.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.pokedex.details.view.model.Evolutions
import org.junit.Rule
import org.junit.Test

class PokemonEvolutionsTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val evolutions = listOf(
        Evolutions(
            name = "Ivysaur",
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            id = 2
        ),
        Evolutions(
            name = "Venusaur",
            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/3.gif",
            id = 3
        )
    )

    @Test
    fun pokemonEvolutionsTest_rendersPokemonEvolutionsTitle() {
        composeTestRule.setContent {
            PokemonEvolutions(
                evolutions = evolutions
            )
        }
        composeTestRule.onNodeWithText("Evoluções").assertExists()
    }

    @Test
    fun pokemonEvolutionsTest_rendersPokemonEvolutionsName() {
        composeTestRule.setContent {
            PokemonEvolutions(
                evolutions = evolutions
            )
        }
        composeTestRule.onNodeWithText("Ivysaur").assertExists()
        composeTestRule.onNodeWithText("Venusaur").assertExists()
    }

    @Test
    fun pokemonEvolutionsTest_rendersPokemonEvolutionsId() {
        composeTestRule.setContent {
            PokemonEvolutions(
                evolutions = evolutions
            )
        }
        composeTestRule.onNodeWithText("#002").assertExists()
        composeTestRule.onNodeWithText("#003").assertExists()
    }
}