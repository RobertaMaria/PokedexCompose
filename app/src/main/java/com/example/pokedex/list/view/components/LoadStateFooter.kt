package com.example.pokedex.list.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.pokedex.R

@Composable
fun LoadStateFooter(loadState: CombinedLoadStates, itemCount: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        if (itemCount == 0 && loadState.refresh is LoadState.Loading) {
            LoadingAnimation()
        } else if (loadState.refresh is LoadState.NotLoading && itemCount == 0 && loadState.append.endOfPaginationReached) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.pokebola),
                    contentDescription = stringResource(R.string.pokemon_list_search_empty_list),
                    modifier = Modifier.size(100.dp)
                )
                Text(stringResource(R.string.pokemon_list_search_empty_list), modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}