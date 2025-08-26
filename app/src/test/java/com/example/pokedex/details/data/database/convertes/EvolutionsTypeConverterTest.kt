import com.example.pokedex.details.data.database.convertes.EvolutionsTypeConverter
import com.example.pokedex.details.data.database.entity.EvolutionsEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class EvolutionsTypeConverterTest {

    private lateinit var converter: EvolutionsTypeConverter
    private val evolutionEntity1 = EvolutionsEntity(pokemonId = 1, name = "Pikachu")
    private val evolutionEntity2 = EvolutionsEntity(pokemonId = 2, name = "Raichu")

    @Before
    fun setUp() {
        converter = EvolutionsTypeConverter()
    }

    @Test
    fun `fromEvolutionsList - empty list input returns json string for empty list`() {
        // Given
        val emptyList: List<EvolutionsEntity> = emptyList()
        // When
        val jsonResult = converter.fromEvolutionsList(emptyList)
        // Then
        assertEquals("[]", jsonResult)
    }

    @Test
    fun `conversion round trip - valid list survives conversion`() {
        // Given
        val originalList = listOf(evolutionEntity1, evolutionEntity2)
        // When
        val jsonRepresentation = converter.fromEvolutionsList(originalList)
        val convertedBackList = converter.toEvolutionsList(jsonRepresentation)
        // Then
        assertNotNull(jsonRepresentation)
        assertEquals(originalList, convertedBackList)
    }
}