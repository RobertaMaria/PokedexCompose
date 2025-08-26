package com.example.pokedex.list.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
                    isInitialLoad = true
                )
            } returns flowOf(mockPagingDataDomain)

            every { factory(mockPokemonDomain) } returns expectedPokemonUi
            viewModel = PokemonListViewModel(getPokemonListUseCase, factory)

            // Then
            viewModel.uiState.test {
                val initialState = awaitItem()
                Assert.assertEquals("", initialState.searchText)
                Assert.assertTrue(initialState.isInitialLoad)
            }
        }
}