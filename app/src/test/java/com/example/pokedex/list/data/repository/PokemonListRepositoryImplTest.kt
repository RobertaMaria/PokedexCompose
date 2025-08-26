package com.example.pokedex.list.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.example.pokedex.list.data.database.dao.PokemonDao
import com.example.pokedex.list.data.database.entity.PokemonEntity
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
    private lateinit var mockPokemonDao: PokemonDao

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
            pokemonDao = mockPokemonDao,
            mapper = mockMapper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchPokemonList WHEN no search text or id AND initial load THEN uses getAll and RemoteMediator`() =
        runTest {
            // Given
            val searchText = ""
            val searchId: Int? = null
            val isInitialLoad = true

            every { mockPokemonDao.getAll() } returns mockPagingSource

            // When
            repository.searchPokemonList(searchText, searchId, isInitialLoad).first()

            // Then
            coVerify(exactly = 1) { mockPokemonDao.getAll() }
            coVerify(exactly = 1) { mockPokemonListRemoteMediator.initialize() }
        }

    @Test
    fun `searchPokemonList WHEN searchText provided AND initial load THEN uses getPokemonByName and RemoteMediator`() =
        runTest {
            // Given
            val searchText = "Pikachu"
            val searchId: Int? = null
            val isInitialLoad = true

            every { mockPokemonDao.getPokemonByName(searchText) } returns mockPagingSource

            // When
            repository.searchPokemonList(searchText, searchId, isInitialLoad).first()

            // Then
            coVerify(exactly = 1) { mockPokemonDao.getPokemonByName(searchText) }
            coVerify(exactly = 1) { mockPokemonListRemoteMediator.initialize() }
        }

    @Test
    fun `searchPokemonList WHEN searchId provided AND initial load THEN uses getPagingSourceById and RemoteMediator`() =
        runTest {
            // Given
            val searchText = ""
            val searchId = 25
            val isInitialLoad = true

            every { mockPokemonDao.getPagingSourceById(searchId) } returns mockPagingSource

            // When
            repository.searchPokemonList(searchText, searchId, isInitialLoad).first()

            // Then
            coVerify(exactly = 1) { mockPokemonDao.getPagingSourceById(searchId) }
            coVerify(exactly = 1) { mockPokemonListRemoteMediator.initialize() }
        }

    @Test
    fun `searchPokemonList WHEN no search text or id AND NOT initial load THEN uses getAll and NO RemoteMediator`() =
        runTest {
            // Given
            val searchText = ""
            val searchId: Int? = null
            val isInitialLoad = false

            every { mockPokemonDao.getAll() } returns mockPagingSource

            // When
            repository.searchPokemonList(searchText, searchId, isInitialLoad).first()

            // Then
            coVerify(exactly = 1) { mockPokemonDao.getAll() }
            coVerify(exactly = 0) { mockPokemonListRemoteMediator.initialize() }
        }
}