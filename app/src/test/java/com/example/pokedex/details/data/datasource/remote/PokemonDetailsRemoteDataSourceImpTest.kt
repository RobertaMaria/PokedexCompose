import com.example.pokedex.details.data.api.model.PokemonDamageRelationsResponse
import com.example.pokedex.details.data.api.model.PokemonEvolutionResponse
import com.example.pokedex.details.data.api.model.PokemonSpecieResponse
import com.example.pokedex.details.data.api.service.PokemonDetailsService
import com.example.pokedex.details.data.datasource.remote.PokemonDetailsRemoteDataSourceImp
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonDetailsRemoteDataSourceImpTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var mockPokemonDetailsService: PokemonDetailsService

    private lateinit var remoteDataSource: PokemonDetailsRemoteDataSourceImp

    @Before
    fun setUp() {
        remoteDataSource = PokemonDetailsRemoteDataSourceImp(mockPokemonDetailsService)
    }

    @Test
    fun `searchPokemonSpecie WHEN service returns data THEN emits PokemonSpecieResponse`() =
        runTest {
            // Given
            val pokemonId = 1
            val expectedResponse = mockk<PokemonSpecieResponse>()
            coEvery { mockPokemonDetailsService.searchPokemonSpecie(pokemonId) } returns expectedResponse

            // When
            val result = remoteDataSource.searchPokemonSpecie(pokemonId).first()

            // Then
            coVerify(exactly = 1) { mockPokemonDetailsService.searchPokemonSpecie(pokemonId) }
            assertEquals(expectedResponse, result)
        }

    @Test
    fun `searchPokemonEvolution WHEN service returns data THEN emits PokemonEvolutionResponse`() =
        runTest {
            // Given
            val url = "https://pokeapi.co/api/v2/evolution-chain/1/"
            val expectedResponse = mockk<PokemonEvolutionResponse>()
            coEvery { mockPokemonDetailsService.searchPokemonEvolution(url) } returns expectedResponse

            // When
            val result = remoteDataSource.searchPokemonEvolution(url).first()

            // Then
            coVerify(exactly = 1) { mockPokemonDetailsService.searchPokemonEvolution(url) }
            assertEquals(expectedResponse, result)
        }

    @Test
    fun `searchDamageRelations WHEN service returns data THEN emits PokemonDamageRelationsResponse`() =
        runTest {
            // Given
            val typeName = "fire"
            val expectedResponse = mockk<PokemonDamageRelationsResponse>()
            coEvery { mockPokemonDetailsService.searchDamageRelations(typeName) } returns expectedResponse

            // When
            val result = remoteDataSource.searchDamageRelations(typeName).first()

            // Then
            coVerify(exactly = 1) { mockPokemonDetailsService.searchDamageRelations(typeName) }
            assertEquals(expectedResponse, result)
        }
}