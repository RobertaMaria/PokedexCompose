package com.example.pokedex.details.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.pokedex.details.domain.model.PokemonDetails
import com.example.pokedex.details.domain.model.Features
import com.example.pokedex.details.domain.usecase.GetPokemonDetailsUseCase
import com.example.pokedex.details.view.factory.PokemonDetailsFactory
import com.example.pokedex.details.view.model.PokemonDetailsUi
import com.example.pokedex.details.view.model.FeaturesUi
import com.example.pokedex.stub.MainCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var getPokemonDetailsUseCase: GetPokemonDetailsUseCase

    @MockK
    private lateinit var pokemonDetailsFactory: PokemonDetailsFactory

    private lateinit var viewModel: PokemonDetailsViewModel

    private val fakePokemonId = 1
    private val fakePokemonDetailsDomain = PokemonDetails(
        id = fakePokemonId,
        name = "Bulbasaur",
        description = "A strange seed was planted on its back at birth.",
        image = "url_to_image",
        type = listOf("grass", "poison"),
        stats = emptyList(),
        evolutions = emptyList(),
        doubleDamage = emptyList(),
        noDamage = emptyList(),
        features = Features(0, 0, 0, "", emptyList())
    )
    private val fakePokemonDetailsUi = PokemonDetailsUi(
        id = fakePokemonId,
        name = "Bulbasaur",
        description = "A strange seed was planted on its back at birth.",
        image = "url_to_image",
        colours = emptyList(),
        stats = emptyList(),
        evolutions = emptyList(),
        isLoaded = false,
        doubleDamage = emptyList(),
        noDamage = emptyList(),
        featuresUi = FeaturesUi("", "", 0, "", emptyList())
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `init SHOULD set uiState to Loading then Success WHEN useCase returns data`() = runTest {
            // Given

            coEvery { getPokemonDetailsUseCase(fakePokemonId) } returns flowOf(
                fakePokemonDetailsDomain
            )
            every { pokemonDetailsFactory(fakePokemonDetailsDomain) } returns fakePokemonDetailsUi

            // When

            viewModel = PokemonDetailsViewModel(
                fakePokemonId,
                getPokemonDetailsUseCase,
                pokemonDetailsFactory
            )

            //Then
            viewModel.uiState.test {
                val initialState = awaitItem()
                assertTrue(initialState is PokemonDetailsUiState.Loading)

                val successState = awaitItem()
                assertTrue(
                    successState is PokemonDetailsUiState.Success
                )
                expectNoEvents()
            }
        }

    @Test
    fun `init SHOULD set uiState to Error WHEN useCase throws exception`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { getPokemonDetailsUseCase(fakePokemonId) } returns flow {
            throw RuntimeException( errorMessage)
        }
        every { pokemonDetailsFactory.invoke(any()) } returns mockk()

        // When
        viewModel = PokemonDetailsViewModel(fakePokemonId, getPokemonDetailsUseCase, pokemonDetailsFactory)

        //Then
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertTrue(initialState is PokemonDetailsUiState.Loading)

            val errorState = awaitItem()
            assertTrue(
                errorState is PokemonDetailsUiState.Error
            )
            expectNoEvents()
        }
    }

    @Test
    fun `init SHOULD set uiState to Success WHEN useCase returns data`() = runTest {
        // Given
        coEvery { getPokemonDetailsUseCase.invoke(fakePokemonId) } returns flowOf(
            fakePokemonDetailsDomain
        )
        every { pokemonDetailsFactory.invoke(fakePokemonDetailsDomain) } returns fakePokemonDetailsUi
        // When
        viewModel =
            PokemonDetailsViewModel(fakePokemonId, getPokemonDetailsUseCase, pokemonDetailsFactory)


        // Then
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertTrue(initialState is PokemonDetailsUiState.Loading)

            val successState = awaitItem()
            assertTrue(successState is PokemonDetailsUiState.Success)
            val actualUiDetails = (successState as PokemonDetailsUiState.Success).pokemonDetailsUi
            assertEquals(
                fakePokemonDetailsUi,
                actualUiDetails
            )
            expectNoEvents()
        }
    }
}
