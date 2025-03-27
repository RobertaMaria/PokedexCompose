package com.example.pokedex.list.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

@Composable
fun LoadStateFooter(loadState: CombinedLoadStates, itemCount: Int) {
    Box(contentAlignment = Alignment.BottomEnd) {
        if (itemCount == 0 && loadState.refresh is LoadState.Loading) {
            LoadingAnimation()
        }
    }
}