package com.example.pokedex.list.view.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.pokedex.R
import org.junit.Rule
import org.junit.Test

class PokemonMeasureTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonMeasureTest_rendersMeasureTitle() {
        composeTestRule.setContent {
            PokemonMeasure(
                measureFormatted = "100,Kg",
                iconTitle = "Peso",
                iconId = R.drawable.ruler_square
            )
        }
        composeTestRule.onNodeWithText("Peso").assertExists()
    }

    @Test
    fun pokemonMeasureTest_rendersMeasureImage() {
        composeTestRule.setContent {
            PokemonMeasure(
                measureFormatted = "100,Kg",
                iconTitle = "Peso",
                iconId = R.drawable.ruler_square
            )
        }
        composeTestRule.onNodeWithContentDescription("Peso").assertExists()
    }

    @Test
    fun pokemonMeasureTest_rendersMeasureFormatted() {
        composeTestRule.setContent {
            PokemonMeasure(
                measureFormatted = "100,Kg",
                iconTitle = "Peso",
                iconId = R.drawable.ruler_square
            )
        }
        composeTestRule.onNodeWithText("100,Kg").assertExists()
    }
}