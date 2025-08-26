import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokedex.list.view.components.SearchTextField
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchTextField_displaysHint_whenEmpty() {
        val hintText = "Buscar por nome ou id"

        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }
            SearchTextField(
                searchText = text,
                onSearchTextChange = { newText -> text = newText }
            )
        }
        composeTestRule.onNodeWithText(hintText).assertIsDisplayed()
    }

    @Test
    fun searchTextField_updatesText_whenUserInput() {
        val inputText = "Pikachu"
        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }
            SearchTextField(
                searchText = text,
                onSearchTextChange = { newText -> text = newText }
            )
        }
        val hintText = "Buscar por nome ou id"
        composeTestRule.onNodeWithText(hintText).performTextInput(inputText)
        composeTestRule.onNodeWithText(inputText).assertIsDisplayed()
    }

    @Test
    fun searchTextField_clearButton_appearsWhenTextIsNotEmpty_andClearsTextOnClick() {
        val inputText = "Charizard"
        val clearButtonContentDescription = "Clean"

        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }
            SearchTextField(
                searchText = text,
                onSearchTextChange = { newText -> text = newText }
            )
        }
        val hintText = "Buscar por nome ou id"
        composeTestRule.onNodeWithText(hintText).performTextInput(inputText)

        composeTestRule.onNodeWithText(inputText).assertIsDisplayed()

        val clearButton =
            composeTestRule.onNodeWithContentDescription(clearButtonContentDescription)
        clearButton.assertIsDisplayed()

        clearButton.performClick()

        composeTestRule.onNodeWithText(hintText).assertIsDisplayed()
        composeTestRule.onNodeWithText(inputText).assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(clearButtonContentDescription)
            .assertDoesNotExist()
    }

    @Test
    fun searchTextField_clearButton_isNotDisplayed_whenTextIsEmpty() {
        val clearButtonContentDescription = "Clean"

        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }
            SearchTextField(
                searchText = text,
                onSearchTextChange = { newText -> text = newText }
            )
        }
        composeTestRule.onNodeWithContentDescription(clearButtonContentDescription)
            .assertDoesNotExist()
    }
}