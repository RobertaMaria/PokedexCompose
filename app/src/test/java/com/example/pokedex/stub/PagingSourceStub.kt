package com.example.pokedex.stub

import androidx.paging.PagingSource
import androidx.paging.PagingState

fun <T : Any> createMockPagingSource(data: List<T> = emptyList()): PagingSource<Int, T> {
    return object : PagingSource<Int, T>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> =
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = null
            )

        override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
    }
}

