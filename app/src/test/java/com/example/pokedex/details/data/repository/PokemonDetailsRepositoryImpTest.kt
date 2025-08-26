package com.example.pokedex.details.data.repository

import com.example.pokedex.details.data.api.model.ChainResponse
import com.example.pokedex.details.data.api.model.PokemonDamageRelationsResponse
import com.example.pokedex.details.data.api.model.PokemonEvolutionResponse
import com.example.pokedex.details.data.api.model.PokemonSpecieResponse
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity
import com.example.pokedex.details.data.database.model.SpeciesAndEvolutions
import com.example.pokedex.details.data.datasource.local.PokemonDetailsLocalDataSource
import com.example.pokedex.details.data.datasource.remote.PokemonDetailsRemoteDataSource
import com.example.pokedex.details.data.mapper.PokemonDetailsMapper
import com.example.pokedex.details.domain.model.PokemonDetails
import com.example.pokedex.list.data.database.entity.PokemonEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonDetailsRepositoryImpTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var remoteDataSource: PokemonDetailsRemoteDataSource

    @MockK
    private lateinit var localDataSource: PokemonDetailsLocalDataSource

    @MockK
    private lateinit var mapper: PokemonDetailsMapper

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: PokemonDetailsRepositoryImp

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = PokemonDetailsRepositoryImp(
            remoteDataSource,
            localDataSource,
            mapper,
            testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPokemonDetail WHEN local data exists THEN return mapped local data`() =
        runTest(testDispatcher) {
            // Given
            val pokemonId = 1
            val mockSpecieEntity = mockk<SpeciesAndEvolutions>(relaxed = true)
            val mockPokemonEntity = mockk<PokemonEntity>(relaxed = true)
            val mockPokemonDetails = mockk<PokemonDetails>(relaxed = true)

            coEvery { localDataSource.getPokemonSpecie(pokemonId) } returns mockSpecieEntity
            coEvery { localDataSource.getPokemonEntity(pokemonId) } returns mockPokemonEntity
            every {
                mapper.mapToDomain(
                    mockSpecieEntity.species,
                    mockPokemonEntity,
                    any()
                )
            } returns mockPokemonDetails

            // When
            val result = repository.getPokemonDetail(pokemonId).first()

            // Then
            assertEquals(mockPokemonDetails, result)
            verify(exactly = 0) { remoteDataSource.searchPokemonSpecie(pokemonId) }
            verify(exactly = 1) {
                mapper.mapToDomain(
                    mockSpecieEntity.species,
                    mockPokemonEntity,
                    any()
                )
            }
        }

    @Test
    fun `getPokemonDetail WHEN local data is empty THEN fetch from remote and save and emit mapped data`() =
        runTest(testDispatcher) {
            //Given
            val pokemonId = 1
            val mockPokemonEntity = mockk<PokemonEntity>(relaxed = true)
            val mockRemoteSpecieResponse = mockk<PokemonSpecieResponse>(relaxed = true)
            val pokemonDamageRelationsResponse =
                mockk<PokemonDamageRelationsResponse>(relaxed = true)
            val pokemonSpecieEntity = mockk<PokemonSpecieEntity>(relaxed = true)
            val pokemonEvolutionResponse = mockk<PokemonEvolutionResponse>(relaxed = true)
            val pokemonEvolutionEntity = mockk<PokemonEvolutionEntity>(relaxed = true)
            val pokemonDetails = mockk<PokemonDetails>(relaxed = true)

            coEvery { localDataSource.getPokemonSpecie(pokemonId) } returns null
            coEvery { localDataSource.getPokemonEntity(pokemonId) } returns mockPokemonEntity

            coEvery { remoteDataSource.searchPokemonSpecie(pokemonId) } returns flowOf(
                mockRemoteSpecieResponse
            )
            coEvery { remoteDataSource.searchDamageRelations(ofType<String>()) } returns flowOf(
                pokemonDamageRelationsResponse
            )
            coEvery {
                mapper.mapToSpecieEntity(
                    mockRemoteSpecieResponse,
                    pokemonId,
                    Pair(emptyList(), emptyList())
                )
            } returns pokemonSpecieEntity

            coEvery { localDataSource.savePokemonSpecie(any()) } returns Unit
            every { remoteDataSource.searchPokemonEvolution(ofType<String>()) } returns flowOf(
                pokemonEvolutionResponse
            )
            coEvery {
                mapper.mapperToEvolutionsEntity(
                    ofType<ChainResponse>(),
                    ofType<String>()
                )
            } returns pokemonEvolutionEntity
            coEvery { localDataSource.saveEvolutions(ofType<PokemonEvolutionEntity>()) } returns Unit
            every {
                mapper.mapToDomain(
                    ofType<PokemonSpecieEntity>(),
                    ofType<PokemonEntity>(),
                    ofType<PokemonEvolutionEntity>()
                )
            } returns pokemonDetails

            val result = repository.getPokemonDetail(pokemonId).first()

            coVerify(exactly = 1) { localDataSource.savePokemonSpecie(ofType<PokemonSpecieEntity>()) }
            coVerify(exactly = 1) { localDataSource.saveEvolutions(ofType<PokemonEvolutionEntity>()) }
            assertEquals(pokemonDetails, result)
        }
}