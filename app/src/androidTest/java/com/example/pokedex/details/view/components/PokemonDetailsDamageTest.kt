package com.example.pokedex.details.view.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.pokedex.common.model.TypeColoursEnum
import org.junit.Rule
import org.junit.Test

class PokemonDetailsDamageTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonDetailsDamageTest_rendersPokemonWeaknessTitle() {
        composeTestRule.setContent {
            DetailsWeakness(
                doubleDamage = listOf(TypeColoursEnum.GRASS),
                title = "Fraquezas"
            )
        }
        composeTestRule.onNodeWithText("Fraquezas").assertExists()
    }

    @Test
    fun pokemonDetailsDamageTest_rendersPokemonWeaknessList() {
        composeTestRule.setContent {
            DetailsWeakness(
                doubleDamage = listOf(TypeColoursEnum.GRASS),
                title = "Fraquezas"
            )
        }
        composeTestRule.onNodeWithText("Grass").assertExists()
    }
}