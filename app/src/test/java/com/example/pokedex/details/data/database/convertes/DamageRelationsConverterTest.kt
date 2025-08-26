import com.example.pokedex.details.data.database.convertes.DamageRelationsConverter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class DamageRelationsConverterTest {

    private lateinit var converter: DamageRelationsConverter

    @Before
    fun setUp() {
        converter = DamageRelationsConverter()
    }

    @Test
    fun `fromStringList - empty list input returns json string for empty list`() {
        // Given
        val emptyList: List<String> = emptyList()
        // When
        val jsonResult = converter.fromStringList(emptyList)
        // Then
        assertEquals("[]", jsonResult)
    }

    @Test
    fun `conversion round trip - valid list survives conversion`() {
        // Given
        val originalList = listOf("rock", "ground", "steel")
        // When
        val jsonRepresentation = converter.fromStringList(originalList)
        val convertedBackList = converter.toStringList(jsonRepresentation)
        // Then
        assertNotNull(jsonRepresentation)
        assertEquals(originalList, convertedBackList)
    }
}