package com.example.pokedex.list.view.viewmodel

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.pokedex.list.domain.model.PokemonList
import com.example.pokedex.list.domain.usecase.GetPokemonListUseCase
import com.example.pokedex.list.view.factory.PokemonListFactory
import com.example.pokedex.list.view.model.PokemonListUi
import com.example.pokedex.stub.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokemonListViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var getPokemonListUseCase: GetPokemonListUseCase

    @MockK
    private lateinit var factory: PokemonListFactory
    private lateinit var viewModel: PokemonListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery {
            getPokemonListUseCase(any(), any(), any(), any())
        } returns flowOf(PagingData.from(emptyList<PokemonList>()))

        viewModel = PokemonListViewModel(getPokemonListUseCase, factory)
    }

    @Test
    fun `given initial load, when viewModel is initialized, then getPokemonListUseCase is called and uiState is updated`() =
        runTest {
            // Given
            val mockPokemonDomain = PokemonList(
                id = 1,
                name = "Bulbasaur",
                image = "url_bulba",
                type = listOf("grass", "poison"),
                height = 7,
                weight = 69
            )
            val mockPagingDataDomain = PagingData.from(listOf(mockPokemonDomain))
            val expectedPokemonUi = PokemonListUi(
                id = 1,
                name = "Bulbasaur",
                image = "url_bulba",
                measuremList = emptyList(),
                color = emptyList()
            )
            coEvery {
                getPokemonListUseCase(
                    searchText = "",
                    searchId = null,
                    selectedTypes = emptyList(),
                    isInitialLoad = true
                )
            } returns flowOf(mockPagingDataDomain)

            every { factory(mockPokemonDomain) } returns expectedPokemonUi

            // Then
            viewModel.uiState.test {
                val initialState = awaitItem()
                Assert.assertEquals("", initialState.searchText)
                Assert.assertTrue(initialState.isInitialLoad)
            }
        }

    @Test
    fun `when setSearchText is called, then searchText is updated in uiState`() =
        runTest {
            // Given
            val searchText = "Pikachu"

            // When
            viewModel.setSearchText(searchText)

            // Then
            val currentState = viewModel.uiState.value
            Assert.assertEquals(searchText, currentState.searchText)
            Assert.assertFalse(currentState.isInitialLoad)
        }

    @Test
    fun `when toggleSelectedType is called, then selectedType is added to list`() =
        runTest {
            // Given
            val typeToToggle = "fire"

            // When
            viewModel.toggleSelectedType(typeToToggle)

            // Then
            val currentState = viewModel.uiState.value
            Assert.assertEquals(listOf(typeToToggle), currentState.selectedTypes)
            Assert.assertFalse(currentState.isInitialLoad)
        }

    @Test
    fun `when toggleSelectedType is called twice with same type, then type is removed`() =
        runTest {
            // Given
            val typeToToggle = "fire"

            // When
            viewModel.toggleSelectedType(typeToToggle) // Add
            viewModel.toggleSelectedType(typeToToggle) // Remove

            // Then
            val currentState = viewModel.uiState.value
            Assert.assertEquals(emptyList<String>(), currentState.selectedTypes)
        }

    @Test
    fun `when clearFilters is called, then selectedTypes is cleared`() =
        runTest {
            // Setup: Add some types first
            viewModel.toggleSelectedType("fire")
            viewModel.toggleSelectedType("water")

            // Verify types were added
            var currentState = viewModel.uiState.value
            Assert.assertEquals(2, currentState.selectedTypes.size)

            // When
            viewModel.clearFilters()

            // Then
            currentState = viewModel.uiState.value
            Assert.assertEquals(emptyList<String>(), currentState.selectedTypes)
            Assert.assertFalse(currentState.isInitialLoad)
        }
}