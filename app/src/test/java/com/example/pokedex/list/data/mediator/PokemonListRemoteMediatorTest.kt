import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.pokedex.list.data.api.model.PokemonDetailsResponse
import com.example.pokedex.list.data.api.model.PokemonListResponse
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.data.database.entity.RemoteKeysEntity
import com.example.pokedex.list.data.datasource.local.PokemonListLocalDataSource
import com.example.pokedex.list.data.datasource.remote.PokemonListRemoteDataSource
import com.example.pokedex.list.data.mapper.PokemonListMapper
import com.example.pokedex.list.data.mediator.PokemonListRemoteMediator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class PokemonListRemoteMediatorTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var localDataSourceMock: PokemonListLocalDataSource

    @MockK
    private lateinit var remoteDataSourceMock: PokemonListRemoteDataSource

    @MockK
    private lateinit var mapperMock: PokemonListMapper

    private lateinit var remoteMediator: PokemonListRemoteMediator

    private val testPageSize = 20
    private val startingPageIndex = 0


    @Before
    fun setUp() {
        remoteMediator = PokemonListRemoteMediator(
            remoteDataSource = remoteDataSourceMock,
            localDataSource = localDataSourceMock,
            mapper = mapperMock
        )
    }

    private fun createPagingState(
        loadedPageCount: Int = 0,
        hasReachedEnd: Boolean = false,
        anchorPosition: Int? = null
    ): PagingState<Int, PokemonEntity> {
        val pages = if (loadedPageCount > 0) {
            List(loadedPageCount) { pageIndex ->
                val items = List(testPageSize) { itemIndex ->
                    mockk<PokemonEntity>(relaxed = true) {
                        coEvery { id } returns (pageIndex * testPageSize + itemIndex + 1)
                    }
                }
                PagingSource.LoadResult.Page(
                    data = items,
                    prevKey = if (pageIndex == 0) null else pageIndex - 1,
                    nextKey = if (hasReachedEnd && pageIndex == loadedPageCount - 1) null else pageIndex + 1
                )
            }
        } else {
            emptyList()
        }

        return PagingState(
            pages = pages,
            anchorPosition = anchorPosition,
            config = PagingConfig(pageSize = testPageSize),
            leadingPlaceholderCount = 0
        )
    }


    @Test
    fun `load REFRESH returns SuccessResult when data is present`() = runTest {
        // Given
        val mockPokemonListResponse = mockk<PokemonListResponse>()
        val mockPokemonDetailsResponse = mockk<PokemonDetailsResponse>(relaxed = true)

        val pokemonIds = listOf(1)
        val remoteKeys = listOf(
            RemoteKeysEntity(
                id = 1,
                prevKey = null,
                nextKey = startingPageIndex + testPageSize
            )
        )
        coEvery { localDataSourceMock.getRemoteKeysId(any()) } returns null

        coEvery {
            remoteDataSourceMock.getListPokemon(
                limit = testPageSize,
                offset = startingPageIndex
            )
        } returns mockPokemonListResponse
        coEvery { mapperMock.mapToIdsPokemon(mockPokemonListResponse) } returns pokemonIds
        coEvery { mapperMock.mapToRemotesKeyEntity(mockPokemonListResponse) } returns remoteKeys
        coEvery { remoteDataSourceMock.getDetailsPokemon(1) } returns mockPokemonDetailsResponse

        coEvery { localDataSourceMock.insertAll(any()) } just runs
        coEvery { localDataSourceMock.saveAllRemoteKey(remoteKeys) } just runs

        val pagingState = createPagingState()
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        coVerify {
            remoteDataSourceMock.getListPokemon(
                limit = testPageSize,
                offset = startingPageIndex
            )
        }
        coVerify { mapperMock.mapToIdsPokemon(mockPokemonListResponse) }
        coVerify { remoteDataSourceMock.getDetailsPokemon(1) }
        coVerify { localDataSourceMock.insertAll(any()) }
        coVerify { localDataSourceMock.saveAllRemoteKey(remoteKeys) }
    }

    @Test
    fun `load REFRESH with no data returns Success and endOfPagination`() = runTest {
        // Given
        val mockEmptyListResponse = mockk<PokemonListResponse>(name = "mockEmptyListResponse") {
            coEvery { next } returns null
            coEvery { previous } returns null
            coEvery { results } returns emptyList()
        }

        coEvery { localDataSourceMock.getRemoteKeysId(any()) } returns null

        coEvery {
            remoteDataSourceMock.getListPokemon(limit = testPageSize, offset = startingPageIndex)
        } returns mockEmptyListResponse

        var mapToIdsPokemonCalled = false
        var idsReturnedByMock: List<Int>? = null

        coEvery { mapperMock.mapToIdsPokemon(mockEmptyListResponse) } answers {
            mapToIdsPokemonCalled = true
            idsReturnedByMock = emptyList()
            idsReturnedByMock!!
        }

        coEvery { mapperMock.mapToRemotesKeyEntity(mockEmptyListResponse) } returns emptyList()
        coEvery { localDataSourceMock.insertAll(emptyList()) } just runs
        coEvery { localDataSourceMock.saveAllRemoteKey(emptyList()) } just runs

        // When
        val pagingState = createPagingState()
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        assertTrue(mapToIdsPokemonCalled)
        Assert.assertEquals(emptyList<Int>(), idsReturnedByMock)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        val successResult = result as RemoteMediator.MediatorResult.Success
        assertTrue(successResult.endOfPaginationReached)

        coVerify(exactly = 1) {
            remoteDataSourceMock.getListPokemon(
                testPageSize,
                startingPageIndex
            )
        }
        coVerify(exactly = 1) { mapperMock.mapToIdsPokemon(mockEmptyListResponse) }
        coVerify(exactly = 1) { mapperMock.mapToRemotesKeyEntity(mockEmptyListResponse) }
        coVerify(exactly = 1) { localDataSourceMock.saveAllRemoteKey(emptyList()) }
        coVerify(exactly = 1) { localDataSourceMock.insertAll(emptyList()) }
        coVerify(exactly = 0) { remoteDataSourceMock.getDetailsPokemon(any()) }
    }

    @Test
    fun `load APPEND returns SuccessResult when data is present`() = runTest {
        // Given
        val lastItemId = testPageSize
        val nextPageOffset = testPageSize

        val lastItemRemoteKey =
            RemoteKeysEntity(id = lastItemId, prevKey = startingPageIndex, nextKey = nextPageOffset)
        coEvery { localDataSourceMock.getRemoteKeysId(lastItemId) } returns lastItemRemoteKey

        val mockPokemonListResponse = mockk<PokemonListResponse>()
        val mockPokemonDetailsResponse = mockk<PokemonDetailsResponse>(relaxed = true)

        val pokemonIds = listOf(lastItemId + 1)
        val remoteKeys = listOf(
            RemoteKeysEntity(
                id = lastItemId + 1,
                prevKey = nextPageOffset,
                nextKey = nextPageOffset + testPageSize
            )
        )

        coEvery {
            remoteDataSourceMock.getListPokemon(
                limit = testPageSize,
                offset = nextPageOffset
            )
        } returns mockPokemonListResponse
        coEvery { mapperMock.mapToIdsPokemon(mockPokemonListResponse) } returns pokemonIds
        coEvery { mapperMock.mapToRemotesKeyEntity(mockPokemonListResponse) } returns remoteKeys
        coEvery { remoteDataSourceMock.getDetailsPokemon(lastItemId + 1) } returns mockPokemonDetailsResponse

        coEvery { localDataSourceMock.insertAll(any()) } just runs
        coEvery { localDataSourceMock.saveAllRemoteKey(remoteKeys) } just runs

        // When
        val pagingState = createPagingState(loadedPageCount = 1, anchorPosition = lastItemId - 1)
        val result = remoteMediator.load(LoadType.APPEND, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        coVerify {
            remoteDataSourceMock.getListPokemon(
                limit = testPageSize,
                offset = nextPageOffset
            )
        }
        coVerify { localDataSourceMock.insertAll(any()) }
        coVerify { localDataSourceMock.saveAllRemoteKey(remoteKeys) }
    }
}