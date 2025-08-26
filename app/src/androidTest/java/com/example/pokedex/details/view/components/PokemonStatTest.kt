package com.example.pokedex.details.view.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.pokedex.details.view.model.PokemonStats
import com.example.pokedex.details.view.model.Stat
import com.example.pokedex.ui.theme.PokedexTheme
import org.junit.Rule
import org.junit.Test

class PokemonStatTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val expectedStat1Name = "HP"
    private val expectedStat1Value = "45"

    private val expectedStat2Name = "Attack"
    private val expectedStat2Value = "49"

    private val expectedStat3Name = "Special-defense"
    private val expectedStat3Value = "65"


    @Test
    fun pokemonStat_confirmsKnownTextsAreDisplayed() {
        val statsForTest = listOf(
            PokemonStats(baseStat = 45, stat = Stat(name = "HP")),
            PokemonStats(baseStat = 49, stat = Stat(name = "Attack")),
            PokemonStats(baseStat = 65, stat = Stat(name = "Special-defense"))
        )
        val colorsForTest = listOf(Color.Red, Color.Green, Color.Blue)

        composeTestRule.setContent {
            PokedexTheme {
                PokemonStat(
                    stats = statsForTest,
                    colors = colorsForTest
                )
            }
        }
        composeTestRule.onNodeWithText("Estatísticas")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedStat1Name, useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedStat1Value, useUnmergedTree = true)
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(expectedStat2Name, useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedStat2Value, useUnmergedTree = true)
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(expectedStat3Name, useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedStat3Value, useUnmergedTree = true)
            .assertIsDisplayed()
    }
}