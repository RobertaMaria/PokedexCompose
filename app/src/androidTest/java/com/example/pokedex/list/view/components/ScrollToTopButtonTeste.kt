import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokedex.R
import com.example.pokedex.list.view.components.ScrollToTopButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ScrollToTopButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockListState: LazyListState
    private lateinit var contentDescription: String

    private fun setScrollToTopButtonContent(
        showButton: Boolean,
        coroutineScope: CoroutineScope
    ) {
        composeTestRule.setContent {
            contentDescription = LocalContext.current.getString(
                R.string.pokemon_list_scroll_top_button_content_description
            )
            mockListState = LazyListState(firstVisibleItemIndex = 5)

            ScrollToTopButton(
                listState = mockListState,
                showButton = showButton,
                coroutineScope = coroutineScope,
                modifier = Modifier
            )
        }
    }

    @Test
    fun scrollToTopButton_isDisplayed_whenShowButtonIsTrue() = runTest {
        setScrollToTopButtonContent(showButton = true, coroutineScope = this)

        composeTestRule.onNodeWithContentDescription(contentDescription).assertIsDisplayed()
    }

    @Test
    fun scrollToTopButton_isNotDisplayed_whenShowButtonIsFalse() = runTest {
        setScrollToTopButtonContent(showButton = false, coroutineScope = this)

        composeTestRule.onNodeWithContentDescription(contentDescription).assertDoesNotExist()
    }
}