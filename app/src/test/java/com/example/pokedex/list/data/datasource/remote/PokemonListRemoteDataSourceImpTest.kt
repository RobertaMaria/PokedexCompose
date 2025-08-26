package com.example.pokedex.list.data.datasource.remote

import com.example.pokedex.list.data.api.model.AbilityResponse
import com.example.pokedex.list.data.api.model.ImageResponse
import com.example.pokedex.list.data.api.model.OfficialArtwork
import com.example.pokedex.list.data.api.model.OtherResponse
import com.example.pokedex.list.data.api.model.PokemonAbilitiesResponse
import com.example.pokedex.list.data.api.model.PokemonDetailsResponse
import com.example.pokedex.list.data.api.model.PokemonListResponse
import com.example.pokedex.list.data.api.model.PokemonResponse
import com.example.pokedex.list.data.api.model.PokemonStatsResponse
import com.example.pokedex.list.data.api.model.PokemonTypesResponse
import com.example.pokedex.list.data.api.model.StatResponse
import com.example.pokedex.list.data.api.model.TypeResponse
import com.example.pokedex.list.data.api.service.PokemonListService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonListRemoteDataSourceImpTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var mockPokemonListService: PokemonListService

    private lateinit var remoteDataSource: PokemonListRemoteDataSourceImp

    @Before
    fun setUp() {
        remoteDataSource = PokemonListRemoteDataSourceImp(mockPokemonListService)
    }

    @Test
    fun `getListPokemon WHEN service returns success THEN returns PokemonListResponse`() = runTest {
        // Given
        val limit = 20
        val offset = 0
        val expectedPokemonItems = listOf(
            PokemonResponse(url = "https://pokeapi.co/api/v2/pokemon/1/"),
            PokemonResponse(url = "https://pokeapi.co/api/v2/pokemon/2/")
        )
        val expectedListResponse = PokemonListResponse(
            next = "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
            previous = null,
            results = expectedPokemonItems
        )

        coEvery {
            mockPokemonListService.getListPokemon(
                limit,
                offset
            )
        } returns expectedListResponse

        // When
        val actualResponse = remoteDataSource.getListPokemon(limit, offset)

        // Then
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedListResponse, actualResponse)
        coVerify(exactly = 1) { mockPokemonListService.getListPokemon(limit, offset) }
    }

    @Test
    fun `getDetailsPokemon WHEN service returns success THEN returns PokemonDetailsResponse`() =
        runTest {
            // Given
            val pokemonId = 1
            val expectedDetailsResponse = PokemonDetailsResponse(
                name = "bulbasaur",
                height = 7,
                weight = 69,
                image = ImageResponse(
                    other = OtherResponse(
                        officialImage = OfficialArtwork(
                            imageDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
                        )
                    )
                ),
                stats = listOf(
                    PokemonStatsResponse(baseStat = 45, stat = StatResponse(statName = "hp")),
                    PokemonStatsResponse(baseStat = 49, stat = StatResponse(statName = "attack"))
                ),
                types = listOf(
                    PokemonTypesResponse(type = TypeResponse(typeName = "grass")),
                    PokemonTypesResponse(type = TypeResponse(typeName = "poison"))
                ),
                abilities = listOf(
                    PokemonAbilitiesResponse(ability = AbilityResponse(abilityName = "overgrow")),
                    PokemonAbilitiesResponse(ability = AbilityResponse(abilityName = "chlorophyll"))
                )
            )

            coEvery { mockPokemonListService.getDetailsPokemon(pokemonId) } returns expectedDetailsResponse

            // When
            val actualResponse = remoteDataSource.getDetailsPokemon(pokemonId)

            // Then
            Assert.assertNotNull(actualResponse)
            Assert.assertEquals(expectedDetailsResponse, actualResponse)
            coVerify(exactly = 1) { mockPokemonListService.getDetailsPokemon(pokemonId) }
        }

    @Test(expected = RuntimeException::class)
    fun `getDetailsPokemon WHEN service throws an exception THEN throws that exception`() =
        runTest {
            // Given
            val pokemonId = 25
            val expectedException = RuntimeException("API error for details")

            coEvery { mockPokemonListService.getDetailsPokemon(pokemonId) } throws expectedException

            // When
            remoteDataSource.getDetailsPokemon(pokemonId)

            // Then
            coVerify(exactly = 1) { mockPokemonListService.getDetailsPokemon(pokemonId) }
        }
}