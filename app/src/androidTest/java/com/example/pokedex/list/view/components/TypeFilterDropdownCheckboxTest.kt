package com.example.pokedex.list.view.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokedex.R
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class TypeFilterDropdownCheckboxTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun typeFilterDropdownCheckbox_displaysHint_whenEmpty() {
        val hintText = context.getString(R.string.pokemon_list_type_filter_count)

        composeTestRule.setContent {
            TypeFilterDropdownCheckbox(
                availableTypes = emptyList(),
                selectedTypes = emptyList(),
                onTypeToggle = {},
                onClearFilters = {}
            )
        }
        composeTestRule.onNodeWithText(hintText).assertIsDisplayed()
    }

    @Test
    fun typeFilterDropdownCheckbox_displaysTypeName_whenOneSelected() {
        val selectedType = "fire"
        val expectedText = "Fire"

        composeTestRule.setContent {
            TypeFilterDropdownCheckbox(
                availableTypes = listOf("fire", "water"),
                selectedTypes = listOf(selectedType),
                onTypeToggle = {},
                onClearFilters = {}
            )
        }
        composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
    }

    @Test
    fun typeFilterDropdownCheckbox_displaysCount_whenMultipleSelected() {
        val selectedTypes = listOf("fire", "water")
        val hintText = context.getString(R.string.pokemon_list_type_filter_count)
        val expectedText = "$hintText (2)"

        composeTestRule.setContent {
            TypeFilterDropdownCheckbox(
                availableTypes = listOf("fire", "water", "grass"),
                selectedTypes = selectedTypes,
                onTypeToggle = {},
                onClearFilters = {}
            )
        }
        composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
    }

    @Test
    fun typeFilterDropdownCheckbox_opensMenu_onButtonClick() {
        val dropdownDescription = "Dropdown"

        composeTestRule.setContent {
            TypeFilterDropdownCheckbox(
                availableTypes = listOf("fire", "water"),
                selectedTypes = emptyList(),
                onTypeToggle = {},
                onClearFilters = {}
            )
        }

        composeTestRule.onNodeWithContentDescription(dropdownDescription).performClick()
        
        // Verifica se as opções aparecem
        composeTestRule.onNodeWithText("Fire").assertIsDisplayed()
        composeTestRule.onNodeWithText("Water").assertIsDisplayed()
    }

    @Test
    fun typeFilterDropdownCheckbox_callsOnTypeToggle_whenItemClicked() {
        var toggledType = ""
        val dropdownDescription = "Dropdown"

        composeTestRule.setContent {
            TypeFilterDropdownCheckbox(
                availableTypes = listOf("fire"),
                selectedTypes = emptyList(),
                onTypeToggle = { toggledType = it },
                onClearFilters = {}
            )
        }

        // Abre o menu
        composeTestRule.onNodeWithContentDescription(dropdownDescription).performClick()
        
        // Clica no item
        composeTestRule.onNodeWithText("Fire").performClick()

        // Verifica se a função foi chamada com o tipo correto
        Assert.assertEquals("fire", toggledType)
    }

    @Test
    fun typeFilterDropdownCheckbox_doesNotShowClearButton_whenFiltersEmpty() {
        val dropdownDescription = "Dropdown"

        composeTestRule.setContent {
            TypeFilterDropdownCheckbox(
                availableTypes = listOf("fire"),
                selectedTypes = emptyList(),
                onTypeToggle = {},
                onClearFilters = {}
            )
        }
        composeTestRule.onNodeWithContentDescription(dropdownDescription).performClick()
        composeTestRule.onNodeWithText("Limpar").assertDoesNotExist()
    }

    @Test
    fun typeFilterDropdownCheckbox_showsClearButton_whenFiltersActive() {
        val dropdownDescription = "Dropdown"

        composeTestRule.setContent {
            TypeFilterDropdownCheckbox(
                availableTypes = listOf("fire"),
                selectedTypes = listOf("fire"),
                onTypeToggle = {},
                onClearFilters = {}
            )
        }
        composeTestRule.onNodeWithContentDescription(dropdownDescription).performClick()
        composeTestRule.onNodeWithText("Limpar").assertIsDisplayed()
    }

    @Test
    fun typeFilterDropdownCheckbox_callsOnClearFilters_whenClearClicked() {
        var clearCalled = false
        val dropdownDescription = "Dropdown"

        composeTestRule.setContent {
            TypeFilterDropdownCheckbox(
                availableTypes = listOf("fire"),
                selectedTypes = listOf("fire"),
                onTypeToggle = {},
                onClearFilters = { clearCalled = true }
            )
        }

        composeTestRule.onNodeWithContentDescription(dropdownDescription).performClick()
        composeTestRule.onNodeWithText("Limpar").performClick()

        Assert.assertTrue(clearCalled)
    }
}