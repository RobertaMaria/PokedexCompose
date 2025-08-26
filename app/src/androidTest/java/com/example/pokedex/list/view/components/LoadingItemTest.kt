package com.example.pokedex.list.view.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test

class LoadingItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingItem_isDisplayed_withCorrectContentDescription() {
        composeTestRule.setContent {
            LoadingItem(rotationLoading = 0f)
        }
        composeTestRule.onNodeWithContentDescription("Carregando lista")
            .assertIsDisplayed()
    }
}