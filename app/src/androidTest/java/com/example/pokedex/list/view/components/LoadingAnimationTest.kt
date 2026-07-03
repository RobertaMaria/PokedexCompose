package com.example.pokedex.list.view.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class LoadingAnimationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingAnimation_isDisplayed() {
        composeTestRule.setContent {
            LoadingAnimation(isCentered = false)
        }

        composeTestRule.onNodeWithTag(LOADING_ANIMATION_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun loadingAnimation_whenCentered_boxIsDisplayed() {
        composeTestRule.setContent {
            LoadingAnimation(isCentered = true)
        }

        composeTestRule.onNodeWithTag(LOADING_ANIMATION_BOX_TEST_TAG)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(LOADING_ANIMATION_TEST_TAG)
            .assertIsDisplayed()
    }
}
