import com.example.pokedex.list.data.database.converters.PokemonStatsListConverter
import com.example.pokedex.list.data.database.entity.PokemonStats
import com.example.pokedex.list.data.database.entity.Stat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PokemonStatsListConverterTest {

    private lateinit var converter: PokemonStatsListConverter

    @Before
    fun setUp() {
        converter = PokemonStatsListConverter()
    }

    @Test
    fun `fromPokemonStatsList with empty list returns non-null json string for empty list`() {
        // Given
        val emptyStatsList: List<PokemonStats> = emptyList()

        // When
        val jsonResult = converter.fromPokemonStatsList(emptyStatsList)

        // Then
        assertEquals("[]", jsonResult)
    }

    @Test
    fun `fromPokemonStatsList with valid list returns correct json string`() {
        // Given
        val statsList = listOf(
            PokemonStats(baseStat = 80, stat = Stat(name = "attack")),
            PokemonStats(baseStat = 45, stat = Stat(name = "hp"))
        )

        // When
        val jsonResult = converter.fromPokemonStatsList(statsList)

        // Then
        val deserializedList = converter.toPokemonStatsList(jsonResult)
        assertEquals(statsList, deserializedList)
    }
}