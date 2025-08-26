import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokedex.R
import com.example.pokedex.list.view.components.LoadStateFooter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadStateFooterTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var emptyListContentDescription: String
    private lateinit var emptyListText: String

    private fun setupContent(loadState: CombinedLoadStates, itemCount: Int) {
        composeTestRule.setContent {
            emptyListContentDescription =
                LocalContext.current.getString(R.string.pokemon_list_search_empty_list)
            emptyListText = LocalContext.current.getString(R.string.pokemon_list_search_empty_list)
            LoadStateFooter(
                loadState = loadState,
                itemCount = itemCount,
                modifier = Modifier
            )
        }
    }

    @Test
    fun loadStateFooter_showsEmptyState_whenItemCountIsZeroAndRefreshNotLoadingAndEndOfPagination() {
        val testLoadState = CombinedLoadStates(
            refresh = LoadState.NotLoading(endOfPaginationReached = true),
            append = LoadState.NotLoading(endOfPaginationReached = true),
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            source = LoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true),
                prepend = LoadState.NotLoading(endOfPaginationReached = true)
            ),
            mediator = null
        )

        setupContent(loadState = testLoadState, itemCount = 0)

        composeTestRule.onNodeWithText(emptyListText).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(emptyListContentDescription)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("LoadingAnimation").assertDoesNotExist()
    }

    @Test
    fun loadStateFooter_showsNothing_whenItemCountIsNonZero() {
        val testLoadState =
            CombinedLoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = false),
                append = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = false),
                source = LoadStates(
                    refresh = LoadState.NotLoading(endOfPaginationReached = false),
                    append = LoadState.NotLoading(endOfPaginationReached = false),
                    prepend = LoadState.NotLoading(endOfPaginationReached = false)
                ),
                mediator = null
            )

        setupContent(loadState = testLoadState, itemCount = 10)

        composeTestRule.onNodeWithTag("LoadingAnimation").assertDoesNotExist()
        composeTestRule.onNodeWithText(emptyListText).assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(emptyListContentDescription)
            .assertDoesNotExist()
    }

    @Test
    fun loadStateFooter_showsNothing_whenLoadingButItemsExist() {
        val testLoadState = CombinedLoadStates(
            refresh = LoadState.Loading,
            append = LoadState.NotLoading(endOfPaginationReached = false),
            prepend = LoadState.NotLoading(endOfPaginationReached = false),
            source = LoadStates(
                refresh = LoadState.Loading,
                append = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = false)
            ),
            mediator = null
        )

        setupContent(loadState = testLoadState, itemCount = 10)

        composeTestRule.onNodeWithTag("LoadingAnimation").assertDoesNotExist()
        composeTestRule.onNodeWithText(emptyListText).assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(emptyListContentDescription)
            .assertDoesNotExist()
    }
}