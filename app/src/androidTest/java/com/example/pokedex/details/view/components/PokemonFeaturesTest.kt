package com.example.pokedex.details.view.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.pokedex.R
import com.example.pokedex.details.view.model.FeaturesUi
import com.example.pokedex.ui.theme.PokedexTheme
import org.junit.Rule
import org.junit.Test

class PokemonFeaturesTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleFeaturesUi = FeaturesUi(
        height = "1.0 m",
        weight = "10.0 kg",
        gender = R.drawable.ic_female_male,
        category = "Test Category",
        ability = listOf("Ability1", "Ability2")
    )
    private val sampleColors = listOf(Color.Cyan, Color.Magenta)

    private val featureHeaderTitleTag = "feature_header_title"
    private val featureHeaderIconTag = "feature_header_icon"
    private val featureDetailsContainerTag = "feature_details_column"
    private val featureRowTitleTag = "feature_row_title"

    private val heightTextTag = "feature_height_text"
    private val weightTextTag = "feature_weight_text"
    private val categoryTextTag = "feature_category_text"
    private val drawableTextTag = "feature_drawable_text"
    private val abilitiesTextTag = "feature_abilities_text"

    @Test
    fun pokemonFeatures_initialState_headerVisible_detailsHidden() {
        composeTestRule.setContent {
            PokedexTheme {
                PokemonFeatures(featuresUi = sampleFeaturesUi, colours = sampleColors)
            }
        }
        composeTestRule.onNodeWithTag(featureHeaderTitleTag, useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(featureHeaderIconTag, useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(featureDetailsContainerTag, useUnmergedTree = true)
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(heightTextTag, useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun pokemonFeatures_clickHeader_expandsAndShowsDetailsTexts() {
        composeTestRule.setContent {
            PokedexTheme {
                PokemonFeatures(featuresUi = sampleFeaturesUi, colours = sampleColors)
            }
        }
        composeTestRule.onNodeWithTag(featureRowTitleTag)
            .performClick()

        composeTestRule.onNodeWithTag(featureDetailsContainerTag, useUnmergedTree = true)
            .assertIsDisplayed()


        composeTestRule.onNodeWithTag(heightTextTag, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextContains(sampleFeaturesUi.height)

        composeTestRule.onNodeWithTag(weightTextTag, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextContains(sampleFeaturesUi.weight)

        composeTestRule.onNodeWithTag(categoryTextTag, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextContains(sampleFeaturesUi.category)

        composeTestRule.onNodeWithTag(drawableTextTag, useUnmergedTree = true)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(abilitiesTextTag, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun pokemonFeatures_clickHeaderTwice_collapsesAndHidesDetailsTexts() {
        composeTestRule.setContent {
            PokedexTheme {
                PokemonFeatures(featuresUi = sampleFeaturesUi, colours = sampleColors)
            }
        }
        composeTestRule.onNodeWithTag(featureRowTitleTag)
            .performClick()
        composeTestRule.onNodeWithTag(featureRowTitleTag)
            .performClick()

        composeTestRule.onNodeWithTag(heightTextTag, useUnmergedTree = true).assertDoesNotExist()

        composeTestRule.onNodeWithTag(featureDetailsContainerTag, useUnmergedTree = true)
            .assertIsNotDisplayed()
    }
}