import android.net.Uri
import com.example.pokedex.list.data.api.model.PokemonListResponse
import com.example.pokedex.list.data.api.model.PokemonResponse
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.database.entity.PokemonStats
import com.example.pokedex.list.data.database.entity.RemoteKeysEntity
import com.example.pokedex.list.data.database.entity.Stat
import com.example.pokedex.list.data.mapper.PokemonListMapper
import com.example.pokedex.list.domain.model.PokemonList
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PokemonListMapperTest {

    private lateinit var mapper: PokemonListMapper

    @Before
    fun setUp() {
        mapper = PokemonListMapper()
        mockkStatic(Uri::class)
    }

    @After
    fun tearDown() {
        unmockkStatic(Uri::class)
    }

    @Test
    fun `mapToIdsPokemon WHEN valid response THEN returns correct list of IDs`() {
        // Given
        val pokemonApiResponses = listOf(
            PokemonResponse(url = "https://pokeapi.co/api/v2/pokemon/1/"),
            PokemonResponse(url = "https://pokeapi.co/api/v2/pokemon/25/"),
            PokemonResponse(url = "https://pokeapi.co/api/v2/pokemon/151/")
        )
        val listResponse = PokemonListResponse(
            next = null,
            previous = null,
            results = pokemonApiResponses
        )
        val expectedIds = listOf(1, 25, 151)

        // When
        val actualIds = mapper.mapToIdsPokemon(listResponse)

        // Then
        assertEquals(expectedIds, actualIds)
    }

    @Test
    fun `mapToRemotesKeyEntity WHEN valid response THEN returns correct list of RemoteKeysEntity`() {
        // Given
        val urlNext = "https://pokeapi.co/api/v2/pokemon/?offset=20&limit=20"
        val urlPrevious = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=20"

        val mockUriNext: Uri = mockk()
        val mockUriPrevious: Uri = mockk()

        every { Uri.parse(urlNext) } returns mockUriNext
        every { mockUriNext.getQueryParameter("offset") } returns "20"

        every { Uri.parse(urlPrevious) } returns mockUriPrevious
        every { mockUriPrevious.getQueryParameter("offset") } returns "0"

        val pokemonApiResponses = listOf(
            PokemonResponse(url = "https://pokeapi.co/api/v2/pokemon/1/"),
            PokemonResponse(url = "https://pokeapi.co/api/v2/pokemon/2/")
        )
        val listResponse = PokemonListResponse(
            next = urlNext,
            previous = urlPrevious,
            results = pokemonApiResponses
        )
        val expectedRemoteKeys = listOf(
            RemoteKeysEntity(id = 1, prevKey = 0, nextKey = 20),
            RemoteKeysEntity(id = 2, prevKey = 0, nextKey = 20)
        )

        // When
        val actualRemoteKeys = mapper.mapToRemotesKeyEntity(listResponse)

        // Then
        assertEquals(expectedRemoteKeys, actualRemoteKeys)
    }

    @Test
    fun `mapToDomain WHEN valid PokemonEntity THEN returns correct PokemonList`() {
        // Given
        val exampleStat = Stat(name = "hp")
        val examplePokemonStats = PokemonStats(baseStat = 45, stat = exampleStat)
        val pokemonEntity = PokemonEntity(
            id = 1,
            name = "Bulbasaur",
            image = "bulbasaur.png",
            height = 7,
            weight = 69,
            type = listOf("grass", "poison"),
            stats = listOf(examplePokemonStats),
            abilities = listOf("overgrow")
        )
        val expectedPokemonList = PokemonList(
            id = 1,
            name = "Bulbasaur",
            image = "bulbasaur.png",
            height = 7,
            weight = 69,
            type = listOf("grass", "poison")
        )

        // When
        val actualPokemonList = mapper.mapToDomain(pokemonEntity)

        // Then
        assertEquals(expectedPokemonList, actualPokemonList)
    }
}