package com.example.pokedex.list.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.pokedex.R
import com.example.pokedex.common.model.TypeColoursEnum
import com.example.pokedex.list.view.factory.PokemonMeasureData
import com.example.pokedex.list.view.model.PokemonListUi
import com.example.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    pokemonLazyPagingItems: LazyPagingItems<PokemonListUi>,
    onClickPokemon: (id: Int) -> Unit = {},
    onSearchTextChange: (String) -> Unit,
    searchText: String
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SearchTextField(searchText = searchText, onSearchTextChange = onSearchTextChange)
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = pokemonLazyPagingItems.itemCount,
                    key = pokemonLazyPagingItems.itemKey { it.id }
                ) { index ->
                    pokemonLazyPagingItems[index]?.let { pokemon ->
                        PokemonItem(pokemon = pokemon, onClickPokemon)
                    }
                }
            }
        }
        LoadStateFooter(pokemonLazyPagingItems.loadState, pokemonLazyPagingItems.itemCount)
    }
}

@Preview("Pixel 4", device = Devices.PIXEL_4)
@Preview("Pixel 4- Dark", device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PokemonListPreview() {
    PokedexTheme {
        Surface {
            HomeScreen(
                pokemonLazyPagingItems = flowOf(
                    PagingData.from(
                        listOf(
                            PokemonListUi(
                                id = 10,
                                name = "Pikachu",
                                image = "",
                                measuremList = listOf(
                                    PokemonMeasureData(
                                        measureFormatted = "100,Kg",
                                        descriptionId = R.string.pokemon_list_weight,
                                        iconId = R.drawable.weight_kilogram
                                    ),
                                    PokemonMeasureData(
                                        measureFormatted = "2,0m",
                                        descriptionId = R.string.pokemon_list_Height,
                                        iconId = R.drawable.ruler_square
                                    )
                                ),
                                color = listOf(
                                    TypeColoursEnum.DRAGON,
                                    TypeColoursEnum.ELECTRIC
                                )
                            )
                        )
                    )
                ).collectAsLazyPagingItems(),
                onClickPokemon = {},
                searchText = "",
                onSearchTextChange = {}
            )
        }
    }
}
