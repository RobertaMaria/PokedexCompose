import com.example.pokedex.details.data.api.model.ChainResponse
import com.example.pokedex.details.data.api.model.EvolutionChainResponse
import com.example.pokedex.details.data.api.model.EvolutionsResponse
import com.example.pokedex.details.data.api.model.FlavorTextEntry
import com.example.pokedex.details.data.api.model.GeneraResponse
import com.example.pokedex.details.data.api.model.LanguageResponse
import com.example.pokedex.details.data.api.model.PokemonSpecieResponse
import com.example.pokedex.details.data.database.entity.EvolutionsEntity
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity
import com.example.pokedex.details.data.mapper.PokemonDetailsMapper
import com.example.pokedex.list.data.database.entity.PokemonEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.example.pokedex.list.data.database.entity.PokemonStats as EntityPokemonStats
import com.example.pokedex.list.data.database.entity.Stat as EntityStat

class PokemonDetailsMapperTest {

    private lateinit var mapper: PokemonDetailsMapper

    private val englishLanguage = LanguageResponse("en")
    private val spanishLanguage = LanguageResponse("es")

    @Before
    fun setUp() {
        mapper = PokemonDetailsMapper()
    }

    @Test
    fun `mapToSpecieEntity - maps basic data and english text correctly`() {
        // Given
        val pokemonIdInput = 1
        val flavorTextEntries = listOf(
            FlavorTextEntry("Texto en español", spanishLanguage),
            FlavorTextEntry("English description", englishLanguage)
        )
        val genera = listOf(
            GeneraResponse("Categoría ES", spanishLanguage),
            GeneraResponse("Category EN", englishLanguage)
        )
        val evolutionChainResponse =
            EvolutionChainResponse("https://pokeapi.co/api/v2/evolution-chain/1/")
        val apiResponse =
            PokemonSpecieResponse(flavorTextEntries, evolutionChainResponse, 1, genera)
        val damageRelations = Pair(listOf("fire"), listOf("ground"))

        // When
        val entity = mapper.mapToSpecieEntity(apiResponse, pokemonIdInput, damageRelations)

        // Then
        assertEquals(pokemonIdInput, entity.id)
        assertEquals("English description", entity.description)
        assertEquals(1, entity.evolutionId)
        assertEquals(listOf("fire"), entity.doubleDamage)
        assertEquals(listOf("ground"), entity.noDamage)
        assertEquals(1, entity.gender)
        assertEquals("Category EN", entity.category)
    }

    @Test
    fun `mapperToEvolutionsEntity - maps linear evolution chain correctly`() {
        // Given
        val evolutionUrl = "https://pokeapi.co/api/v2/evolution-chain/1/"
        val species3 =
            EvolutionsResponse("Venusaur", "https://pokeapi.co/api/v2/pokemon-species/3/")
        val species2 = EvolutionsResponse("Ivysaur", "https://pokeapi.co/api/v2/pokemon-species/2/")
        val species1 =
            EvolutionsResponse("Bulbasaur", "https://pokeapi.co/api/v2/pokemon-species/1/")

        val chain3 = ChainResponse(emptyList(), species3)
        val chain2 = ChainResponse(listOf(chain3), species2)
        val chain1 = ChainResponse(listOf(chain2), species1)

        // When
        val evolutionEntity = mapper.mapperToEvolutionsEntity(chain1, evolutionUrl)

        // Then
        assertEquals(1, evolutionEntity.evolutionId)
        assertEquals(3, evolutionEntity.evolutions.size)
        assertEquals("Bulbasaur", evolutionEntity.evolutions[0].name)
        assertEquals(1, evolutionEntity.evolutions[0].pokemonId)
        assertEquals("Ivysaur", evolutionEntity.evolutions[1].name)
        assertEquals(2, evolutionEntity.evolutions[1].pokemonId)
        assertEquals("Venusaur", evolutionEntity.evolutions[2].name)
        assertEquals(3, evolutionEntity.evolutions[2].pokemonId)
    }

    @Test
    fun `mapToDomain - combines data from all entities correctly`() {
        // Given
        val specieEntity = PokemonSpecieEntity(
            id = 25, description = "Mouse Pokemon", evolutionId = 10,
            doubleDamage = listOf("ground"), noDamage = emptyList(), gender = 1, category = "Mouse"
        )
        val pokemonEntityFromList = PokemonEntity(
            id = 25, name = "Pikachu", image = "pikachu.png", height = 4, weight = 60,
            type = listOf("electric"),
            stats = listOf(EntityPokemonStats(baseStat = 55, stat = EntityStat("attack"))),
            abilities = listOf("static")
        )
        val evolutionChain = PokemonEvolutionEntity(
            evolutionId = 10,
            evolutions = listOf(EvolutionsEntity(pokemonId = 172, name = "Pichu"))
        )

        // When
        val domainModel = mapper.mapToDomain(specieEntity, pokemonEntityFromList, evolutionChain)

        // Then
        assertEquals(25, domainModel.id)
        assertEquals("Pikachu", domainModel.name)
        assertEquals("Mouse Pokemon", domainModel.description)
        assertEquals("pikachu.png", domainModel.image)
        assertEquals(listOf("electric"), domainModel.type)
        assertEquals(1, domainModel.stats.size)
        assertEquals("attack", domainModel.stats[0].stat.name)
        assertEquals(55, domainModel.stats[0].baseStat)
        assertEquals(1, domainModel.evolutions.size)
        assertEquals("Pichu", domainModel.evolutions[0].name)
        assertEquals(listOf("ground"), domainModel.doubleDamage)
        assertTrue(domainModel.noDamage.isEmpty())
        assertEquals(4, domainModel.features.height)
        assertEquals(60, domainModel.features.weight)
        assertEquals(1, domainModel.features.gender)
        assertEquals("Mouse", domainModel.features.category)
        assertEquals(listOf("static"), domainModel.features.ability)
    }
}