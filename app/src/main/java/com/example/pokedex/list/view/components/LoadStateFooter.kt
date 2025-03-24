package com.example.pokedex.list.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

@Composable
fun LoadStateFooter(loadState: CombinedLoadStates) {
    Box(contentAlignment = Alignment.BottomEnd) {
        when (loadState.refresh) {
            LoadState.Loading -> LoadingAnimation()
            else -> {}
        }

        when (loadState.append) {
            is LoadState.Loading -> LoadingAnimation()
            else -> {}
        }
    }
}