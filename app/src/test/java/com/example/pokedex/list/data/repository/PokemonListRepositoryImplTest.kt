package com.example.pokedex.list.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.datasource.local.PokemonListLocalDataSource
import com.example.pokedex.list.data.mapper.PokemonListMapper
import com.example.pokedex.list.data.mediator.PokemonListRemoteMediator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class PokemonListRepositoryImplTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var mockPokemonListRemoteMediator: PokemonListRemoteMediator

    @MockK
    private lateinit var localDataSource: PokemonListLocalDataSource

    @MockK
    private lateinit var mockMapper: PokemonListMapper

    private lateinit var repository: PokemonListRepositoryImpl
    private lateinit var mockPagingSource: PagingSource<Int, PokemonEntity>
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockPagingSource = mockk<PagingSource<Int, PokemonEntity>>(relaxed = true)

        coEvery { mockPokemonListRemoteMediator.initialize() } returns RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH

        repository = PokemonListRepositoryImpl(
            pokemonListRemoteMediator = mockPokemonListRemoteMediator,
            localDataSource = localDataSource,
            mapper = mockMapper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchPokemonList WHEN no search text or id AND initial load THEN uses getAllPokemon and RemoteMediator`() =
        runTest {
            // Given
            val searchText = ""
            val searchId: Int? = null
            val selectedTypes = emptyList<String>()
            val isInitialLoad = true

            every { localDataSource.getAllPokemon() } returns mockPagingSource

            // When
            repository.searchPokemonList(searchText, searchId, selectedTypes, isInitialLoad).first()

            // Then
            coVerify(exactly = 1) { localDataSource.getAllPokemon() }
            coVerify(exactly = 1) { mockPokemonListRemoteMediator.initialize() }
        }

    @Test
    fun `searchPokemonList WHEN searchText provided AND initial load THEN uses getTypeQueryWithName`() =
        runTest {
            // Given
            val searchText = "Pikachu"
            val searchId: Int? = null
            val isInitialLoad = true
            val selectedTypes = emptyList<String>()

            every { localDataSource.getTypeQueryWithName(searchText, selectedTypes) } returns mockPagingSource

            // When
            repository.searchPokemonList(searchText, searchId, selectedTypes, isInitialLoad).first()

            // Then
            coVerify(exactly = 1) { localDataSource.getTypeQueryWithName(searchText, selectedTypes) }
            coVerify(exactly = 1) { mockPokemonListRemoteMediator.initialize() }
        }

    @Test
    fun `searchPokemonList WHEN searchId provided AND initial load THEN uses getTypeQueryWithId`() =
        runTest {
            // Given
            val searchText = ""
            val searchId = 25
            val selectedTypes = emptyList<String>()
            val isInitialLoad = true

            every { localDataSource.getTypeQueryWithId(searchId, selectedTypes) } returns mockPagingSource

            // When
            repository.searchPokemonList(searchText, searchId, selectedTypes, isInitialLoad).first()

            // Then
            coVerify(exactly = 1) { localDataSource.getTypeQueryWithId(searchId, selectedTypes) }
            coVerify(exactly = 1) { mockPokemonListRemoteMediator.initialize() }
        }

    @Test
    fun `searchPokemonList WHEN no search text or id AND NOT initial load THEN uses getAllPokemon and NO RemoteMediator`() =
        runTest {
            // Given
            val searchText = ""
            val searchId: Int? = null
            val selectedTypes = emptyList<String>()
            val isInitialLoad = false

            every { localDataSource.getAllPokemon() } returns mockPagingSource

            // When
            repository.searchPokemonList(searchText, searchId, selectedTypes, isInitialLoad).first()

            // Then
            coVerify(exactly = 1) { localDataSource.getAllPokemon() }
            coVerify(exactly = 0) { mockPokemonListRemoteMediator.initialize() }
        }

    @Test
    fun `searchPokemonList WHEN selectedTypes provided THEN uses getByDynamicTypes`() =
        runTest {
            // Given
            val searchText = ""
            val searchId: Int? = null
            val selectedTypes = listOf("fire", "water")
            val isInitialLoad = false

            every { localDataSource.getByDynamicTypes(selectedTypes) } returns mockPagingSource

            // When
            repository.searchPokemonList(searchText, searchId, selectedTypes, isInitialLoad).first()

            // Then
            coVerify(exactly = 1) { localDataSource.getByDynamicTypes(selectedTypes) }
        }
}