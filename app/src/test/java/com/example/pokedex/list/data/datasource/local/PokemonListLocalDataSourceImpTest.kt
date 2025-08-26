import com.example.pokedex.list.data.database.dao.PokemonDao
import com.example.pokedex.list.data.database.dao.RemoteKeysDao
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.database.entity.RemoteKeysEntity
import com.example.pokedex.list.data.datasource.local.PokemonListLocalDataSourceImp
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonListLocalDataSourceImpTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var mockPokemonDao: PokemonDao

    @MockK
    private lateinit var mockRemoteKeysDao: RemoteKeysDao

    private lateinit var localDataSource: PokemonListLocalDataSourceImp

    @Before
    fun setUp() {
        localDataSource = PokemonListLocalDataSourceImp(mockPokemonDao, mockRemoteKeysDao)
    }

    @Test
    fun `insertAll (PokemonEntity) WHEN called THEN calls pokemonDao insertAll`() {
        // Given
        val pokemonEntities = listOf(
            PokemonEntity(
                id = 1,
                name = "Bulbasaur",
                image = "",
                height = 7,
                weight = 69,
                type = listOf("grass", "poison"),
                stats = emptyList(),
                abilities = emptyList()
            ),
            PokemonEntity(
                id = 2,
                name = "Ivysaur",
                image = "",
                height = 10,
                weight = 130,
                type = listOf("grass", "poison"),
                stats = emptyList(),
                abilities = emptyList()
            )
        )
        every { mockPokemonDao.insertAll(pokemonEntities) } just runs

        // When
        localDataSource.insertAll(pokemonEntities)

        // Then
        verify(exactly = 1) { mockPokemonDao.insertAll(pokemonEntities) }
    }

    @Test
    fun `getRemoteKeysId WHEN dao returns key THEN returns RemoteKeysEntity`() = runTest {
        // Given
        val itemId = 1
        val expectedRemoteKey = RemoteKeysEntity(id = itemId, prevKey = null, nextKey = 2)

        coEvery { mockRemoteKeysDao.getRemoteKeysId(itemId) } returns expectedRemoteKey

        // When
        val actualRemoteKey = localDataSource.getRemoteKeysId(itemId)

        // Then
        assertEquals(expectedRemoteKey, actualRemoteKey)
        coVerify(exactly = 1) { mockRemoteKeysDao.getRemoteKeysId(itemId) }
    }

    @Test
    fun `getRemoteKeysId WHEN dao returns null THEN returns null`() = runTest {
        // Given
        val itemId = 99

        coEvery { mockRemoteKeysDao.getRemoteKeysId(itemId) } returns null

        // When
        val actualRemoteKey = localDataSource.getRemoteKeysId(itemId)

        // Then
        assertNull(actualRemoteKey)
        coVerify(exactly = 1) { mockRemoteKeysDao.getRemoteKeysId(itemId) }
    }

    @Test
    fun `saveAllRemoteKey WHEN called THEN calls remoteKeysDao insertAll`() = runTest {
        // Given
        val remoteKeysEntities = listOf(
            RemoteKeysEntity(id = 1, prevKey = null, nextKey = 2),
            RemoteKeysEntity(id = 2, prevKey = 1, nextKey = 3)
        )
        coEvery { mockRemoteKeysDao.insertAll(remoteKeysEntities) } just runs

        // When
        localDataSource.saveAllRemoteKey(remoteKeysEntities)

        // Then
        coVerify(exactly = 1) { mockRemoteKeysDao.insertAll(remoteKeysEntities) }
    }
}