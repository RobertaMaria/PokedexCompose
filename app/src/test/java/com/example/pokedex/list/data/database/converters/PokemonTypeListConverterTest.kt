import com.example.pokedex.list.data.database.converters.PokemonTypeListConverter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class PokemonTypeListConverterTest {

    private lateinit var converter: PokemonTypeListConverter

    @Before
    fun setUp() {
        converter = PokemonTypeListConverter()
    }

    @Test
    fun `toString with empty list returns json string for empty list`() {
        // Given
        val emptyTypeList: List<String> = emptyList()

        // When
        val jsonResult = converter.toString(emptyTypeList)

        // Then
        assertEquals("[]", jsonResult)
    }

    @Test
    fun `toString with valid list returns correct json string`() {
        // Given
        val typeList = listOf("grass", "poison")
        val expectedJson = """["grass","poison"]"""

        // When
        val jsonResult = converter.toString(typeList)

        // Then
        assertEquals(expectedJson, jsonResult)
    }

    @Test
    fun `fromString with valid json string returns correct list`() {
        // Given
        val validJsonString = """["water","ice"]"""
        val expectedTypeList = listOf("water", "ice")

        // When
        val typeListResult = converter.fromString(validJsonString)

        // Then
        assertEquals(expectedTypeList, typeListResult)
    }

    @Test
    fun `conversion round trip from list to json and back to list is successful`() {
        // Given
        val originalTypeList = listOf("electric", "steel")

        // When
        val jsonRepresentation = converter.toString(originalTypeList)
        val convertedBackList = converter.fromString(jsonRepresentation)

        // Then
        assertNotNull(jsonRepresentation)
        assertEquals(originalTypeList, convertedBackList)
    }
}