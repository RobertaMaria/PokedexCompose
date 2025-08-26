import com.example.pokedex.details.data.database.dao.PokemonEvolutionDao
import com.example.pokedex.details.data.database.dao.PokemonSpecieDao
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity
import com.example.pokedex.details.data.database.model.SpeciesAndEvolutions
import com.example.pokedex.details.data.datasource.local.PokemonDetailsLocalDataSourceImp
import com.example.pokedex.list.data.database.dao.PokemonDao
import com.example.pokedex.list.data.database.entity.PokemonEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonDetailsLocalDataSourceImpTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var mockPokemonSpecieDao: PokemonSpecieDao

    @MockK
    private lateinit var mockPokemonDao: PokemonDao

    @MockK
    private lateinit var mockPokemonEvolutionDao: PokemonEvolutionDao

    private lateinit var localDataSource: PokemonDetailsLocalDataSourceImp

    @Before
    fun setUp() {
        localDataSource = PokemonDetailsLocalDataSourceImp(
            pokemonSpecieDao = mockPokemonSpecieDao,
            pokemonDao = mockPokemonDao,
            pokemonEvolutionDao = mockPokemonEvolutionDao
        )
    }

    @Test
    fun `savePokemonSpecie WHEN called THEN calls pokemonSpecieDao insert`() = runTest {
        // Given
        val mockPokemonSpecieEntity = mockk<PokemonSpecieEntity>()
        coEvery { mockPokemonSpecieDao.insert(mockPokemonSpecieEntity) } just runs

        // When
        localDataSource.savePokemonSpecie(mockPokemonSpecieEntity)

        // Then
        coVerify(exactly = 1) { mockPokemonSpecieDao.insert(mockPokemonSpecieEntity) }
    }

    @Test
    fun `getPokemonSpecie WHEN dao returns data THEN returns SpeciesAndEvolutions`() {
        // Given
        val pokemonId = 1
        val mockSpeciesEntity = mockk<PokemonSpecieEntity>()
        val mockEvolutionChain = mockk<PokemonEvolutionEntity>(relaxed = true)

        val expectedSpeciesAndEvolutions = SpeciesAndEvolutions(
            species = mockSpeciesEntity,
            evolution = mockEvolutionChain
        )
        every { mockPokemonSpecieDao.getPokemonSpecie(pokemonId) } returns expectedSpeciesAndEvolutions

        // When
        val result = localDataSource.getPokemonSpecie(pokemonId)

        // Then
        io.mockk.verify(exactly = 1) { mockPokemonSpecieDao.getPokemonSpecie(pokemonId) }
        assertEquals(expectedSpeciesAndEvolutions, result)
    }

    @Test
    fun `getPokemonEntity WHEN dao returns data THEN returns PokemonEntity`() {
        // Given
        val pokemonId = 1
        val expectedPokemonEntity = mockk<PokemonEntity>()
        every { mockPokemonDao.getPokemonById(pokemonId) } returns expectedPokemonEntity

        // When
        val result = localDataSource.getPokemonEntity(pokemonId)

        // Then
        io.mockk.verify(exactly = 1) { mockPokemonDao.getPokemonById(pokemonId) }
        assertEquals(expectedPokemonEntity, result)
    }

    @Test
    fun `getPokemonEntity WHEN dao returns null THEN returns null`() {
        // Given
        val pokemonId = 999
        every { mockPokemonDao.getPokemonById(pokemonId) } returns null

        // When
        val result = localDataSource.getPokemonEntity(pokemonId)

        // Then
        io.mockk.verify(exactly = 1) { mockPokemonDao.getPokemonById(pokemonId) }
        assertNull(result)
    }

    @Test
    fun `saveEvolutions WHEN called THEN calls pokemonEvolutionDao insert`() = runTest {
        // Given
        val mockPokemonEvolutionEntity = mockk<PokemonEvolutionEntity>()
        coEvery { mockPokemonEvolutionDao.insert(mockPokemonEvolutionEntity) } just runs

        // When
        localDataSource.saveEvolutions(mockPokemonEvolutionEntity)

        // Then
        coVerify(exactly = 1) { mockPokemonEvolutionDao.insert(mockPokemonEvolutionEntity) }
    }
}